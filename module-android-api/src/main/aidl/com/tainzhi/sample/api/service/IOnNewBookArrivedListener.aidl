package com.tainzhi.sample.api.service;

import com.tainzhi.sample.api.service.Book;

interface IOnNewBookArrivedListener {
    // Remote Service发布消息到Client
    // BookManager 更新新书到了的消息到 借阅用户
    void onNewBookArrived(in Book book);
}