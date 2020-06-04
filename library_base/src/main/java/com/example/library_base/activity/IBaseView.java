package com.example.library_base.activity;

/**
 * 界面UI的显示切换
 */
public interface IBaseView {

    void showContent();

    void  showLoading();

    void showEmpty();

    void showFailure(String message);
}
