package com.example.library_base.viewmodel;

/**
 * 定义ViewModel与V的关联
 */
public interface IMvvMBaseViewModel<V> {

    void attachUi(V view);

    V getPageView();

    boolean isUiAttached();


    void detachUi();


}
