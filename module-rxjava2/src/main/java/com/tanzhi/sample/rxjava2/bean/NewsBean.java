package com.tanzhi.sample.rxjava2.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-30 20:26
 * @description:
 **/

public class NewsBean {
	private boolean error;
	private List<NewsResultBean> results = new ArrayList<>();
	
	public boolean isError() {
		return error;
	}
	
	public void setError(boolean error) {
		this.error = error;
	}
	
	public List<NewsResultBean> getResults() {
		return results;
	}
	
	public void setResults(List<NewsResultBean> results) {
		this.results = results;
	}
}
