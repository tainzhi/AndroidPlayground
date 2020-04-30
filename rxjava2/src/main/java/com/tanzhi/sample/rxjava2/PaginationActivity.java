package com.tanzhi.sample.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tanzhi.sample.rxjava2.adapter.NewsAdapter;
import com.tanzhi.sample.rxjava2.adapter.TypeAdapter;
import com.tanzhi.sample.rxjava2.bean.NewsBean;
import com.tanzhi.sample.rxjava2.bean.NewsResultBean;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaginationActivity extends AppCompatActivity {
	
	final static int VISIBLE_THREDHOLD = 1;
	private CompositeDisposable compositeDisposable = new CompositeDisposable();
	private PublishProcessor<Integer> paginator = PublishProcessor.create();
	private NewsAdapter adapter;
	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	private boolean loading = false;
	private int pageNumber = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pagination);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		adapter = new NewsAdapter();
		progressBar = findViewById(R.id.progressBar);
		
		recyclerView = findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
		recyclerView.setAdapter(adapter);
		
		setUpLoadingListener();
		subscribeForData();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		compositeDisposable.clear();
	}
	
	private void setUpLoadingListener() {
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				LinearLayoutManager layoutManager =
						(LinearLayoutManager) recyclerView.getLayoutManager();
				int totalCount = layoutManager.getItemCount();
				int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
				if (!loading && totalCount <= (lastVisibleItem + VISIBLE_THREDHOLD)) {
					pageNumber++;
					paginator.onNext(pageNumber);
					loading = true;
				}
			}
		});
	}
	
	private void subscribeForData() {
		Disposable disposable = paginator
				                        .onBackpressureDrop()
				                        .doOnNext(new Consumer<Integer>() {
					                        @Override
					                        public void accept(Integer integer) throws Exception {
						                        loading = true;
						                        progressBar.setVisibility(View.VISIBLE);
					                        }
				                        })
										.concatMap(new Function<Integer, Publisher<?>>() {
											@Override
											public Publisher<?> apply(Integer page) throws Exception {
												return dataFromNetwork(page)
														       .subscribeOn(Schedulers.io())
														       .doOnError(throwable -> {})
														       .onErrorReturn(throwable -> new ArrayList<>());
											}
										})
				                        .observeOn(AndroidSchedulers.mainThread())
				                        .subscribe(items -> {
					                        adapter.addData((List<NewsResultBean>) items);
					                        loading = false;
					                        progressBar.setVisibility(View.INVISIBLE);
				                        });
		compositeDisposable.add(disposable);
		
		paginator.onNext(pageNumber);
	}
	
	private Flowable<List<NewsResultBean>> dataFromNetwork(final int page) {
		return Observable.just(page)
				       .subscribeOn(Schedulers.io())
				       .flatMap(new Function<Integer, ObservableSource<List<NewsResultBean>>>() {
					       @Override
					       public ObservableSource<List<NewsResultBean>> apply(Integer integer) throws Exception {
						       Observable<NewsBean> androidNews = getNewsObservable("Android", page);
						       Observable<NewsBean> iosNews = getNewsObservable("iOS", page);
						       return Observable.zip(androidNews, iosNews, new BiFunction<NewsBean, NewsBean, List<NewsResultBean>>() {
							       @Override
							       public List<NewsResultBean> apply(NewsBean newsBean, NewsBean newsBean2) throws Exception {
								       List<NewsResultBean> result = new ArrayList<>();
								       result.addAll(newsBean.getResults());
								       result.addAll(newsBean2.getResults());
								       return result;
							       }
						       });
					       }
				       })
				.toFlowable(BackpressureStrategy.DROP);
	}
	
	private Observable<NewsBean> getNewsObservable(String category, int page) {
		NewsApi api = new Retrofit.Builder()
				              .baseUrl("http://gank.io")
				              .addConverterFactory(GsonConverterFactory.create())
				              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				              .build().create(NewsApi.class);
		return api.getNews(category, 10, page);
	}
}
