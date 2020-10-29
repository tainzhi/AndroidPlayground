// IBookManager.aidl
package com.tainzhi.sample.api.service;

import com.tainzhi.sample.api.service.IOnNewBookArrivedListener;
import com.tainzhi.sample.api.service.Book;

interface IBookManager {
    void registerCallback(IOnNewBookArrivedListener cb);
    void unregisterCallback(IOnNewBookArrivedListener cb);

    List<Book> getBookList();
    void addBook(in Book book);
}