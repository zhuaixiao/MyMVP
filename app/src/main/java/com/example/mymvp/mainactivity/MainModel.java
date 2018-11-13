package com.example.mymvp.mainactivity;

import android.content.Context;
import android.util.Log;

import com.example.mymvp.adapter.AdapterItem;
import com.example.mymvp.apiservice.PicService;
import com.example.mymvp.apiservice.ProvinceService;
import com.example.mymvp.application.DaggerAppComponent;
import com.example.mymvp.db.ProvinceDb;
import com.example.mymvp.gson.ProvinceJs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainModel implements MainContract.IModel {
    @Inject
    Retrofit retrofit;
    ProvinceService provinceService;
    PicService picService;
    List<AdapterItem> itemList;
    List<String> list;
    ProvinceDb db;
    Context context;
    String pic;
    private static final String TAG = "MainModel";

    public MainModel(Context context) {
        this.context = context;
        list = new ArrayList<>();
        itemList = new ArrayList<>();
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void requestData(final onListener listener) {
        provinceService = retrofit.create(ProvinceService.class);
        picService = retrofit.create(PicService.class);
        picService.getPic().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response != null) {
                    try {
                        //TODO Retrofit获取返回的网址使用“new String(response.body.bytes());"
                        pic = new String(response.body().bytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    requestProvince(listener);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.error();
            }
        });
    }

    private void requestProvince(final onListener listener) {
        provinceService.getProvinces()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ProvinceJs>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ProvinceJs> provinceJs) {
                        for (ProvinceJs i : provinceJs
                                ) {
                            itemList.add(new AdapterItem(pic, i.getName()));
                            //TODO 数据库仅用于测试
                            db = new ProvinceDb();
                            db.setName(i.getName());
                            db.setCode(i.getId());
                            db.save();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: 获取省份数据失败");
                        listener.error();
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete(itemList);
                    }
                });
    }


}
