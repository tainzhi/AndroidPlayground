// IBookManager.aidl
package com.tainzhi.sample.api.service;

import com.tainzhi.sample.api.service.IRemoteServiceCallback;
parcelable Book;

interface IBookManager {
    /**
     * Often you want to allow a service to call back to its clients.
     * This shows how to do so, by registering a callback interface with
     * the service.
     */
    void registerCallback(IRemoteServiceCallback cb);

    /**
     * Remove a previously registered callback interface.
     */
    void unregisterCallback(IRemoteServiceCallback cb);

    List<Book> getBookList();
    void addBook(in Book book);
}