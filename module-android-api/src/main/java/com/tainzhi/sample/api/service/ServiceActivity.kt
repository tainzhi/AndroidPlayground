package com.tainzhi.sample.api.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tainzhi.sample.api.R
import kotlinx.android.synthetic.main.activity_service.bindBtn
import kotlinx.android.synthetic.main.activity_service.runStateDetailTv
import kotlinx.android.synthetic.main.activity_service.unBindBtn

class ServiceActivity : AppCompatActivity() {

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = IBookManager.Stub.asInterface(service)
            printRunningDetail("onServiceConnected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            printRunningDetail("onServiceDisconnected")
        }

        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
        }

        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
        }
    }

    private lateinit var bookManager: IBookManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        bindBtn.setOnClickListener { bindService() }
        unBindBtn.setOnClickListener { unBindService() }
    }

    private fun bindService() {
        val intent = Intent(this, RemoteService::class.java).apply {
            action = IBookManager::class.java.name
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun unBindService() {
        unbindService(connection)
    }

    private fun printRunningDetail(detail: String) {
        runStateDetailTv.append(detail)
        Log.d("Api/ServiceActivity", "$detail\n")
    }
}