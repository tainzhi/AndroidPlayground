package com.tanzhi.sample.rxjava2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

public class CombineLastActivity extends AppCompatActivity {
	
	private EditText mEtName;
	private EditText mEtPassword;
	private Button mBtnLogin;
	
	private PublishSubject<String> mNameSubject;
	private PublishSubject<String> mPasswordSubject;
	private CompositeDisposable mCompositeDisposable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combine_last);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		mEtName = findViewById(R.id.tv_name);
		mEtPassword = findViewById(R.id.tv_password);
		mBtnLogin = findViewById(R.id.btn_submit);
		
		mNameSubject = PublishSubject.create();
		mPasswordSubject = PublishSubject.create();
		
		mEtName.addTextChangedListener(new EditTextMonitor(mNameSubject));
		mEtPassword.addTextChangedListener(new EditTextMonitor(mPasswordSubject));
		
		Observable<Boolean> observable = Observable.combineLatest(mNameSubject, mPasswordSubject,
				new BiFunction<String, String, Boolean>() {
					@Override
					public Boolean apply(String name, String password) throws Exception {
						int nameLen = name.length();
						int passwordLen = password.length();
						return nameLen >= 4 && passwordLen >= 4 && passwordLen <= 16;
					}
				});
		
		DisposableObserver<Boolean> disposableObserver = new DisposableObserver<Boolean>() {
			@Override
			public void onNext(Boolean value) {
				mBtnLogin.setText(value ? "登录" : "用户名或者密码错误");
			}
			
			@Override
			public void onError(Throwable e) {
			
			}
			
			@Override
			public void onComplete() {
			
			}
		};
		
		observable.subscribe(disposableObserver);
		
		mCompositeDisposable = new CompositeDisposable();
		mCompositeDisposable.add(disposableObserver);
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCompositeDisposable.clear();
	}
	
	private class EditTextMonitor implements TextWatcher {
		private PublishSubject<String> mPublishSubject;
		
		EditTextMonitor(PublishSubject<String> publishSubject) {
			mPublishSubject = publishSubject;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			mPublishSubject.onNext(s.toString());
		}
	}
	
}
