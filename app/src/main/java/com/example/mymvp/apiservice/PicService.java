package com.example.mymvp.apiservice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PicService {
    @GET("api/bing_pic")
    Call<ResponseBody> getPic();
}
