package com.tanzhi.sample.rxjava2.rxbus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tanzhi.sample.rxjava2.MyApp;
import com.tanzhi.sample.rxjava2.R;
import com.tanzhi.sample.rxjava2.rxbus.RxEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxbusActivity extends AppCompatActivity {
	
	private final CompositeDisposable disposable = new CompositeDisposable();
	
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rxbus);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		tv = findViewById(R.id.tv);
		
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((MyApp) getApplication())
						.bus()
						.send(new RxEvent.TapEvent());
			}
		});
		
		disposable.add(((MyApp) getApplication())
				               .bus()
				               .toObservable()
				               .subscribeOn(Schedulers.io())
				               .observeOn(AndroidSchedulers.mainThread())
				               .subscribe(new Consumer<Object>() {
					               @Override
					               public void accept(Object o) throws Exception {
						               if (o instanceof RxEvent.TapEvent) {
							               tv.setText("TAP event received");
						               } else {
							               tv.setText("Auto event received");
						               }}}));
	}
}
