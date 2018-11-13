package com.example.mymvp.apiservice;


import com.example.mymvp.gson.ProvinceJs;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ProvinceService {
    @GET("api/china")
    Observable<List<ProvinceJs>> getProvinces();
}
