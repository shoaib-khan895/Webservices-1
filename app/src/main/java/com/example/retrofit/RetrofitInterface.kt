package com.example.retrofit

import android.widget.TextView
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("posts")
    fun getPost(
    @Query("id")
    postId: Int
    ):Call<List<PostModel>>
}