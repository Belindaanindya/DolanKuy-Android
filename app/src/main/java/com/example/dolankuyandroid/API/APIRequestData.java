package com.example.dolankuyandroid.API;

import com.example.dolankuyandroid.Model.ResponseAcomodation;
import com.example.dolankuyandroid.Model.ResponseCategory;
import com.example.dolankuyandroid.Model.ResponseLogin;
import com.example.dolankuyandroid.Model.ResponseLogout;
import com.example.dolankuyandroid.Model.ResponseModelDashboard;
import com.example.dolankuyandroid.Model.ResponseModelDetailListLocations;
import com.example.dolankuyandroid.Model.ResponseModelListLocations;
import com.example.dolankuyandroid.Model.ResponseRegister;
import com.example.dolankuyandroid.Model.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface APIRequestData {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("dashboard")
    Call<ResponseModelDashboard> ardLocationsWisataDashboard(
            @Header("Authorization") String token,
            @Query(value = "userLat", encoded = true) Double userLat,
            @Query(value = "userLong", encoded = true) Double userLong
    );

    @GET("category")
    Call<ResponseCategory> ardCategory();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("category/{id}")
    Call<ResponseAcomodation> ardAcomodation(
            @Path("id") int id,
            @Header("Authorization") String token,
            @Query(value = "userLat", encoded = true) Double userLat,
            @Query(value = "userLong", encoded = true) Double userLong
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("locations")
    Call<ResponseModelListLocations> ardLocationsWisata(
            @Header("Authorization") String token,
            @Query(value = "userLat", encoded = true) Double userLat,
            @Query(value = "userLong", encoded = true) Double userLong
    );

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

    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @FormUrlEncoded
    @POST("editProfile")
    Call<ResponseUser> ardEditProfile(
            @Header("Authorization") String token,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("editProfile")
    Call<ResponseUser> ardEditEmail(
            @Header("Authorization") String token,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("editProfile")
    Call<ResponseUser> ardEditPassword(
            @Header("Authorization") String token,
            @Field("password") String password
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("me")
    Call<ResponseUser> ardUser(
            @Header("Authorization") String token
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("logout")
    Call<ResponseLogout> ardLogout(
            @Header("Authorization") String token
    );

}