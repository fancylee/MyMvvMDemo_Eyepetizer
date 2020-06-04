package com.example.library_base.activity;

public interface IBasePagingView extends IBaseView {

    void onLoadMoreFailure(String message);

    void onLoadMoreEmpty();
}
