package com.tanzhi.sample.rxjava2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

public class BufferActivity extends AppCompatActivity {
	
	private PublishSubject<Double> publishSubject;
	private CompositeDisposable compositeDisposable;
	private TextView textView;
	private SourceHandler sourceHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buffer);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		textView = findViewById(R.id.tv);
		publishSubject = PublishSubject.create();
		DisposableObserver<List<Double>> disposableObserver = new DisposableObserver<List<Double>>() {
			@Override
			public void onNext(List<Double> doubles) {
				double result = 0;
				if (doubles.size() > 0) {
					for (double o : doubles) {
						result += o;
					}
					result = result / doubles.size();
				}
				textView.setText("过去 3s 收到了" + doubles.size() + "个数据, 平均温度为: " + result);
			}
			
			@Override
			public void onError(Throwable e) {
			
			}
			
			@Override
			public void onComplete() {
			
			}
		};
		
		publishSubject.buffer(3, TimeUnit.SECONDS)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(disposableObserver);
		compositeDisposable = new CompositeDisposable();
		compositeDisposable.add(disposableObserver);
		
		sourceHandler = new SourceHandler();
		sourceHandler.sendEmptyMessage(0);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sourceHandler.removeCallbacksAndMessages(null);
		compositeDisposable.clear();
	}
	
	private void updateTemperature(double temperature) {
		publishSubject.onNext(temperature);
	}
	
	private class SourceHandler extends Handler {
		@Override
		public void handleMessage(@NonNull Message msg) {
			double temperature = Math.random()* 25 + 5;
			updateTemperature(temperature);
			sendEmptyMessageDelayed(0, 250 + (long)(250 * Math.random()));
		}
	}
}
