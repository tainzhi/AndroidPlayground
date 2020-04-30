package com.tanzhi.sample.rxjava2;

import com.tanzhi.sample.rxjava2.bean.NewsBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-30 20:27
 * @description:
 **/

public interface NewsApi {
	@GET("api/data/{category}/{count}/{page}")
	Observable<NewsBean> getNews(@Path("category")String category, @Path("count")int count,
	                           @Path( "page" )int page);
}
