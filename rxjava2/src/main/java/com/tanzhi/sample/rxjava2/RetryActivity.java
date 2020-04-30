package com.tanzhi.sample.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RetryActivity extends AppCompatActivity {
	
	private static final String MSG_WAIT_SHORT = "wait_short";
	private static final String MSG_WAIT_LONG = "wait_long";
	private static final String[] MSG_ARRAY = new String[]{
			MSG_WAIT_SHORT,
			MSG_WAIT_SHORT,
			MSG_WAIT_LONG,
			MSG_WAIT_LONG
	};
	CompositeDisposable mCompositeDisposable;
	private TextView mTvRetryWhen;
	private int mMsgIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retry);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		mTvRetryWhen = findViewById(R.id.text);
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				startRetryRequest();
			}
		});
		
		mCompositeDisposable = new CompositeDisposable();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCompositeDisposable.clear();
	}
	
	private void startRetryRequest() {
		Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				int msgLen = MSG_ARRAY.length;
				doWork();
				if (mMsgIndex < msgLen) {
					emitter.onError(new Throwable(MSG_ARRAY[mMsgIndex]));
					mMsgIndex++;
				} else {
					mTvRetryWhen.append("Work Success\n");
					emitter.onNext("Work Success");
					emitter.onComplete();
				}
			}
		}).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
			private int mRetryCount;
			
			@Override
			public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
				return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
					@Override
					public ObservableSource<?> apply(Throwable throwable) throws Exception {
						String errorMsg = throwable.getMessage();
						long waitTime = 0;
						switch (errorMsg) {
							case MSG_WAIT_SHORT:
								waitTime = 2000;
								break;
							case MSG_WAIT_LONG:
								waitTime = 4000;
								break;
							default:
								break;
						}
						Logger.d("发生错误, 尝试等待时间=" + waitTime + ", 当前重试次数=" + mRetryCount);
						mTvRetryWhen.append("发生错误, 尝试等待时间=" + waitTime + ", 当前重试次数=" + mRetryCount);
						mTvRetryWhen.append("\n");
						mRetryCount++;
						return waitTime > 0 && mRetryCount <= 4 ? Observable.timer(waitTime,
								TimeUnit.MILLISECONDS) :
								       Observable.error(throwable);
					}
				});
			}
		});
		
		DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
			@Override
			public void onNext(String s) {
				mTvRetryWhen.append("onNext value=" + s);
				mTvRetryWhen.append("\n");
				Logger.d("onNext value=" + s);
			}
			
			@Override
			public void onError(Throwable e) {
				Logger.d("error");
				
			}
			
			@Override
			public void onComplete() {
				Logger.d("complete");
			}
		};
		
		observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
		mCompositeDisposable.add(disposableObserver);
	}
	
	private void doWork() {
		long workTime = (long) (Math.random() * 500) + 500;
		try {
			Thread.sleep(workTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
