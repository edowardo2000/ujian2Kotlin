package com.example.edo_ujianretrofit.apiservice

import com.example.edo_ujianretrofit.model.ResponseGetAllData
import com.example.edo_ujianretrofit.model.ResponseSuccess
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface APIServices {

    @GET("trxpinjaman/all")
    fun getAllData(): Call<ResponseGetAllData>

    @Multipart
    @POST("trxpinjaman/add")
    fun addDataPinjaman(@Part("NamaLengkap") NamaLengkap: RequestBody, @Part("Alamat")
    Alamat: RequestBody, @Part("JumlahOutstanding") JumlahOutstanding: RequestBody) : Call<ResponseSuccess>

    @Multipart
    @POST("trxpinjaman/update")
    fun updateDataPinjaman(@Part("Id") id: RequestBody, @Part("NamaLengkap") namaLengkap: RequestBody, @Part("Alamat")
    alamat: RequestBody, @Part("JumlahOutstanding") jumlahOutstanding: RequestBody
    ) : Call<ResponseSuccess>


    @Multipart
    @POST("trxpinjaman/delete")
    fun deleteDataPinjaman(@Part("Id") id: RequestBody) : Call<ResponseSuccess>

    @GET("trxpinjaman/all")
    fun getAllDataByFilter(@Query("filters[0][co][2][fl]") filterField : String,
                           @Query("filters[0][co][2][op]") filterOperator : String,
                           @Query("filters[0][co][2][vl]") filterValue : String

    ) : Call<ResponseGetAllData>
}