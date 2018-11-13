package com.example.mymvp.mainactivity;

import android.content.Context;

import com.example.mymvp.adapter.AdapterItem;

import java.util.List;

public class MainPresenter {
    MainContract.IView iView;
    MainContract.IModel model;
    private static final String TAG = "MainPresenter";

    public MainPresenter(MainContract.IView view, Context context) {
        iView = view;
        model = new MainModel(context);
    }

    public void handleData() {
        model.requestData(new MainContract.IModel.onListener() {
            @Override
            public void onComplete(List<AdapterItem> list) {
                iView.loadData(list);
            }

            @Override
            public void error() {
                iView.error();
            }
        });
    }
}
