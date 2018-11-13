package com.example.mymvp.mainactivity;

import com.example.mymvp.adapter.AdapterItem;

import java.util.List;

public interface MainContract {
    interface IView {
        void loadData(List<AdapterItem > list);
        void error();

    }

    interface IModel {
        void requestData(onListener listener);

        interface onListener {
            void onComplete(List<AdapterItem> list);
            void error();
        }
    }
}
