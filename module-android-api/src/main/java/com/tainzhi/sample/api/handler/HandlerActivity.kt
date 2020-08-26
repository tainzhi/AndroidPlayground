package com.tainzhi.sample.api.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tainzhi.sample.api.R;

public class HandlerActivity extends AppCompatActivity {
	
	TextView mTvDescription;
	
	private Handler mMainHandler = new Handler() {
		@Override
		public void handleMessage(@NonNull Message msg) {
			super.handleMessage(msg);
			if (msg.what == Constants.SUB1_2_MAIN) {
				System.out.println("I am from Sub thread");
				System.out.println("thread=" + Thread.currentThread().getName());
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		mTvDescription = findViewById(R.id.tv_description);
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		
		// 子线程到主线程
		testSub2Main();
		// 主线程到子线程
		testMain2Sub();
		// 子线程到子线程
		testSub2Sub();
	}
	
	private void testSub2Main() {
		new Thread(new Runnable1(mMainHandler)).start();
	}
	
	private void testMain2Sub() {
		Runnable2 runnable2 = new Runnable2();
		new Thread(runnable2).start();
		Message msg = new Message();
		msg.what = Constants.MAIN_2_SUB2;
		while (true) {
			if (runnable2.mylooper2 != null) {
				runnable2.handler2.sendMessage(msg);
				return;
			}
		}
	}
	
	private void testSub2Sub() {
		Runnable3 runnable3 = new Runnable3();
		new Thread(runnable3).start();
		
		while (true) {
			if (runnable3.myLooper3 != null) {
				Runnable4 runnable4 = new Runnable4(runnable3.handler3);
				new Thread(runnable4).start();
				return;
			}
		}
	}
	
}
