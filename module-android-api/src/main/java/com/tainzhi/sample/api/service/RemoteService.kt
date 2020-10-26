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
import android.util.Log
import com.tainzhi.sample.api.R

class RemoteService : Service() {

    companion object {
        const val TAG = "RemoteService"
    }

    override fun onCreate() {
        super.onCreate()
        showNotification()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return mBookManagerIBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    private val mBookManagerIBinder = object: IBookManager.Stub() {
        override fun registerCallback(cb: IRemoteServiceCallback?) {
            TODO("Not yet implemented")
        }

        override fun unregisterCallback(cb: IRemoteServiceCallback?) {
            TODO("Not yet implemented")
        }

        override fun getBookList(): MutableList<Book> {
            TODO("Not yet implemented")
        }

        override fun addBook(book: Book?) {
            TODO("Not yet implemented")
        }
    }

    private fun showNotification() {
        val channelId = "Remote Service"
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(this, 0,
            Intent(this, ServiceActivity::class.java), 0)
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
}