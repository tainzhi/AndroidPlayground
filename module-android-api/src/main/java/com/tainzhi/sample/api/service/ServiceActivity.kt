package com.tainzhi.sample.api.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tainzhi.sample.api.R
import kotlinx.android.synthetic.main.activity_service.*
import kotlin.random.Random

class ServiceActivity : AppCompatActivity() {

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = IBookManager.Stub.asInterface(service)
            bookManager.registerCallback(onIOnNewBookArrivedListener)
            (bookManager as Binder).linkToDeath(connectionDeathRecipient, 0)
            printRunningDetail("onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bookManager.unregisterCallback(onIOnNewBookArrivedListener)
            printRunningDetail("onServiceDisconnected")
        }

        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
        }

        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
        }
    }

    private val connectionDeathRecipient = object: IBinder.DeathRecipient {
        override fun binderDied() {
            if (!(bookManager as Binder).isBinderAlive) {
                bookManager.asBinder().unlinkToDeath(this, 0)
                bindService()
            }
        }
    }

    private lateinit var secondaryService : ISecondary
    private val secondConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            secondaryService = ISecondary.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    private lateinit var bookManager: IBookManager
    private var isBinded = false

    val handler = object: Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                1 -> {
                    printRunningDetail("Arrive new book: ${(msg.obj as Book).name}")
                }
            }

        }
    }

    private val onIOnNewBookArrivedListener = object: IOnNewBookArrivedListener.Stub(){
        override fun onNewBookArrived(book: Book) {
            handler.obtainMessage(1, book).sendToTarget()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        bindBtn.setOnClickListener { bindService() }
        unBindBtn.setOnClickListener { unBindService() }
        killBtn.setOnClickListener {
            Process.killProcess(secondaryService.pid)
        }
        addBookBtn.setOnClickListener {
            if (isBinded) {
                Log.d("qfq_activity.addBook", "pid=${Process.myPid()}, tid=${Thread.currentThread().id}")
                bookManager.addBook(Book(Random.nextInt(100), "Book_${Random.nextInt()}"))
            }
        }
        getBooksBtn.setOnClickListener {
            if (isBinded) {
                val bookList = bookManager.bookList
                printRunningDetail("BookManager now has ${bookList.size} books")
            }
        }
    }

    override fun onDestroy() {
        if (this::bookManager.isInitialized && bookManager.asBinder().isBinderAlive) {
            bookManager.unregisterCallback(onIOnNewBookArrivedListener)
        }
        unbindService(connection)
        unbindService(secondConnection)
        super.onDestroy()
    }

    private fun bindService() {
        // bind IBookManager
        bindService(Intent(IBookManager::class.java.name, null, this, RemoteService::class.java),
            connection, Context.BIND_AUTO_CREATE)
        // bind ISecondary
        bindService(Intent(ISecondary::class.java.name, null, this, RemoteService::class.java),
            secondConnection, Context.BIND_AUTO_CREATE)
        isBinded = true
    }

    private fun unBindService() {
        unbindService(connection)
        unbindService(secondConnection)
        isBinded = false
    }

    private fun printRunningDetail(detail: String) {
        val detailWithSuffix = "$detail\n"
        runStateDetailTv.append(detailWithSuffix)
        Log.d("Api/ServiceActivity", detailWithSuffix)
    }
}