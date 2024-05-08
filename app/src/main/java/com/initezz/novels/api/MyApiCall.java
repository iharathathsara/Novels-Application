package com.initezz.novels.api;

import com.initezz.novels.model.Items;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApiCall {
    @GET("loadAllNovel.php")
    Call<ArrayList<Items>> getData();
}
