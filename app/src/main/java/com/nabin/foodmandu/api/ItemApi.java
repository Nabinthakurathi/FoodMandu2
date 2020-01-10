package com.nabin.foodmandu.api;

import com.nabin.foodmandu.model.Item;
import com.nabin.foodmandu.serverresponse.ImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ItemApi {


    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("item/all")
    Call<List<Item>> getALlItems();

}
