package com.tainzhi.sample.api.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.Process
import android.os.RemoteCallbackList
import android.util.Log
import com.tainzhi.sample.api.R
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class RemoteService : Service() {

    companion object {
        const val TAG = "RemoteService"
    }

    private var isSerivceDestroyed = AtomicBoolean(false)
    private val books = CopyOnWriteArrayList<Book>()
    private val listeners = RemoteCallbackList<IOnNewBookArrivedListener>()

    override fun onCreate() {
        super.onCreate()
        showNotification()
        Log.d(TAG, "onCreate")
        Thread(AutoAddBookWorker()).start()
    }

    override fun onBind(intent: Intent): IBinder? {
        if (IBookManager::class.java.name == intent.action) {
            return mBookManagerIBinder
        } else if (ISecondary::class.java.name == intent.action) {
            return secondaryBinder
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isSerivceDestroyed.set(true)
        Log.d(TAG, "onDestroy")
    }

    private val mBookManagerIBinder = object: IBookManager.Stub() {
        override fun registerCallback(cb: IOnNewBookArrivedListener?) {
            listeners.register(cb)
            listeners.beginBroadcast()
            listeners.finishBroadcast()
        }

        override fun unregisterCallback(cb: IOnNewBookArrivedListener?) {
            listeners.unregister(cb)
            listeners.beginBroadcast()
            listeners.finishBroadcast()
        }

        override fun getBookList(): List<Book> {
            return books
        }

        override fun addBook(book: Book) {
            books.add(book)
        }

    }

    private val secondaryBinder = object: ISecondary.Stub() {
        override fun getPid(): Int {
            return Process.myPid()
        }
    }

    private fun showNotification() {
        val channelId = "Remote Service"
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(this, 0,
            Intent(this, ServiceActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            0)
        lateinit var notificationChannel: NotificationChannel
        val notificationBuilder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT).also {
                it.enableLights(true)
                it.lightColor = Color.RED
                it.setShowBadge(false)
            }
            nm.createNotificationChannel(notificationChannel)

            Notification.Builder(this, channelId)
        } else {
            Notification.Builder(this)
        }
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setTicker(channelId)
            .setWhen(System.currentTimeMillis())
            .setContentTitle("Remote Service: BookManager")
            .setContentText("content text")
            .setContentIntent(pendingIntent)

        nm.notify("Remote Service has started".hashCode(), notificationBuilder.build())
    }

    fun arriveNewBook(book: Book) {
        books.add(book)
        val listenSize = listeners.beginBroadcast()
        for (i in 0 until listenSize) {
            listeners.getBroadcastItem(i)?.onNewBookArrived(book)
        }
        listeners.finishBroadcast()

    }

    inner class AutoAddBookWorker: Runnable {

        override fun run() {
            while (!isSerivceDestroyed.get()) {
                Thread.sleep(2000)
                val bookId = Random.nextInt(10000)
                arriveNewBook(Book(bookId, "Book $bookId AutoAdded_by_BookManager"))
            }
        }
    }
}