package com.example.ecommercetask.network

import com.example.ecommercetask.Utils.Constant
import com.example.ecommercetask.model.ProductReqModel
import com.example.ecommercetask.model.ProductResponseModel
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface RetrofitService {

    @FormUrlEncoded
    @GET("6701/253620")
    fun display(
        @Body reqData: ProductReqModel
    ): Call<ProductResponseModel>


    companion object {
        var retrofitService: RetrofitService? = null
        private var prevUrl = ""

        fun getInstance(url: String = Constant.BASE_URL): RetrofitService {
            if (prevUrl != url) {
                getApiService(url)
                prevUrl = url
            } else {
                if (retrofitService == null) {
                    getApiService(url)
                }
            }
            return retrofitService!!
        }

        private fun getApiService(url: String) {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }).readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
        }
    }
}