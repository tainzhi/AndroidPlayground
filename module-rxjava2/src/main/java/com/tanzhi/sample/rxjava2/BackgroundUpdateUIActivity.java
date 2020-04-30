package com.tanzhi.sample.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BackgroundUpdateUIActivity extends AppCompatActivity {
	
	TextView tv;
	private CompositeDisposable disposable = new CompositeDisposable();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_background_update_ui);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		tv = findViewById(R.id.tv);
		
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changeUI();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		disposable.clear();
	}
	
	private void changeUI() {
		final Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				for (int i = 0; i < 100; i++) {
					if (i % 20 == 0) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							if (!emitter.isDisposed()) {
								emitter.onError(e);
							}
						}
						emitter.onNext(i);
					}
				}
				emitter.onComplete();
			}
		});
		
		DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {
			@Override
			public void onNext(Integer integer) {
				Logger.d("");
				tv.setText(integer.toString());
			}
			
			@Override
			public void onError(Throwable e) {
				Logger.d("");
			}
			
			@Override
			public void onComplete() {
				Logger.d("");
			}
		};
		
		observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(disposableObserver);
		disposable.add(disposableObserver);
	}
}
