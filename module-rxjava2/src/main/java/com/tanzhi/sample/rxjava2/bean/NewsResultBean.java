package com.tanzhi.sample.rxjava2.bean;

import java.util.List;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-30 20:23
 * @description:
 **/

public class NewsResultBean {
	
	/**
	 * _id : 5db0ff5b9d21226808a3e9a8
	 * createdAt : 2019-10-24T01:33:15.558Z
	 * desc : webview封装库，极大提高开发效率
	 * images : ["http://img.gank.io/cc45d086-526a-40e2-8b16-402dbd39f0a4","http://img.gank.io/e86728de-36ba-4b18-8058-cb6af71647cd","http://img.gank.io/d2d096c3-44c6-49f7-b69b-3a20177b48a2","http://img.gank.io/ef1577e2-4d9f-43ed-a5c0-4c2cffe4b3cd","http://img.gank.io/4577d7bd-d7b9-4eac-b2ef-fbdec7dbc538","http://img.gank.io/abfa62d1-67d0-4663-a19e-fd0f42d966b3","http://img.gank.io/d1c7dd0f-3638-4b1e-9186-ca7737d1f644"]
	 * publishedAt : 2019-10-24T01:34:26.445Z
	 * source : web
	 * type : Android
	 * url : https://github.com/yangchong211/YCWebView/blob/master/README.md
	 * used : true
	 * who : 潇湘剑雨
	 */
	
	private String _id;
	private String createdAt;
	private String desc;
	private String publishedAt;
	private String source;
	private String type;
	private String url;
	private boolean used;
	private String who;
	private List<String> images;
	
	public String get_id() { return _id;}
	
	public void set_id(String _id) { this._id = _id;}
	
	public String getCreatedAt() { return createdAt;}
	
	public void setCreatedAt(String createdAt) { this.createdAt = createdAt;}
	
	public String getDesc() { return desc;}
	
	public void setDesc(String desc) { this.desc = desc;}
	
	public String getPublishedAt() { return publishedAt;}
	
	public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt;}
	
	public String getSource() { return source;}
	
	public void setSource(String source) { this.source = source;}
	
	public String getType() { return type;}
	
	public void setType(String type) { this.type = type;}
	
	public String getUrl() { return url;}
	
	public void setUrl(String url) { this.url = url;}
	
	public boolean isUsed() { return used;}
	
	public void setUsed(boolean used) { this.used = used;}
	
	public String getWho() { return who;}
	
	public void setWho(String who) { this.who = who;}
	
	public List<String> getImages() { return images;}
	
	public void setImages(List<String> images) { this.images = images;}
}
