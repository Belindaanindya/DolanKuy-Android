package com.example.dolankuyandroid.API;

import com.example.dolankuyandroid.Model.ResponseLogin;
import com.example.dolankuyandroid.Model.ResponseLogout;
import com.example.dolankuyandroid.Model.ResponseModelDashboard;
import com.example.dolankuyandroid.Model.ResponseModelDetailListLocations;
import com.example.dolankuyandroid.Model.ResponseModelListLocations;
import com.example.dolankuyandroid.Model.ResponseRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIRequestData {
    @GET("acomodation")
    Call<ResponseModelDashboard> ardLocationsWisataDashboard();
    @GET("locations")
    Call<ResponseModelListLocations> ardLocationsWisata();
    @GET("locations/{id}")
    Call<ResponseModelDetailListLocations> getDetailLocations(@Path("id") int id);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseRegister> ardRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> ardLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("logout")
    Call<ResponseLogout> ardLogout(
            @Header("Authorization") String token
    );

}