package com.example.treemarket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApi {

    @GET("/products/app")
    Call<List<ProductsAppResponse>> getAllPosts();

}
