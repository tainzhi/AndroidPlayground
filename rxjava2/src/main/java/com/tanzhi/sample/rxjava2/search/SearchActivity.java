package com.tanzhi.sample.rxjava2.search;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.tanzhi.sample.rxjava2.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchActivity extends AppCompatActivity {
	
	private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		SearchView searchView = findViewById(R.id.svSearch);
		TextView tvResult = findViewById(R.id.tvResult);
		
		Disposable disposable = getSearchObservableFromView(searchView)
				                        // 防抖, 在 300ms 内无输入才会发送事件; 否则等待 300ms 在发送
				                        .debounce(300, TimeUnit.MILLISECONDS)
										// 过滤长度大于 0 的搜索
				                        .filter(new Predicate<String>() {
					                        @Override
					                        public boolean test(String s) throws Exception {
						                        if (s.isEmpty()) {
							                        tvResult.setText("");
							                        return false;
						                        } else {
							                        return true;
						                        }
					                        }
				                        })
				                        .distinctUntilChanged()
										// 当发起来 abc 的请求后, 基石 ab 的结果返回, 也不会发送给下游
										// 不同的线程, 原来线程发送的数据不再处理
				                        .switchMap(new Function<String, ObservableSource<?>>() {
					                        @Override
					                        public ObservableSource<String> apply(String s) throws Exception {
						                        return dataFromNetwork(s)
								                               .doOnError(throwable -> {})
								                               .onErrorReturn(throwable -> "");
					                        }
				                        })
				                        .subscribeOn(Schedulers.io())
				                        .observeOn(AndroidSchedulers.mainThread())
				                        .subscribe((s) -> {
					                        tvResult.setText("Result:" + s);
				                        });
		mCompositeDisposable.add(disposable);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mCompositeDisposable.clear();
	}
	
	private PublishSubject<String> getSearchObservableFromView(SearchView searchView) {
		final PublishSubject<String> subject = PublishSubject.create();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				subject.onNext(query);
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				subject.onNext(newText);
				return true;
			}
		});
		return subject;
	}
	
	private Observable<String> dataFromNetwork(final String query) {
		return Observable.just(true)
				       .delay(2, TimeUnit.SECONDS)
				       .map(new Function<Boolean, String>() {
					       @Override
					       public String apply(Boolean aBoolean) throws Exception {
						       return query;
					       }
				       });
	}
	
}
