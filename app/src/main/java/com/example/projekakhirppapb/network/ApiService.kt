package com.example.projekakhirppapb.network

import com.example.projekakhirppapb.model.Novel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import android.telecom.Call as Call

interface ApiService {
    @GET("novel")
    fun getAllNovel(): retrofit2.Call<List<Novel>>

    @POST("novel")
    fun addNovel(@Body requestBody: RequestBody): retrofit2.Call<Novel>


    @POST("novel/{id}")
    fun updateNovel(@Path("id") eventId: String, @Body event: Novel): retrofit2.Call<Novel>


    @DELETE("novel/{id}")
    fun deleteNovel(@Path("id") id: String): retrofit2.Call<Unit>
}