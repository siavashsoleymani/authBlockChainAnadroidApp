package com.example.qrcodereader;

public interface BaseView<T extends BasePresenter> extends ContextProvider {

    void setPresenter(T presenter);

}