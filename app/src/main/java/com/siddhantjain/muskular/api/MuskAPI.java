package com.siddhantjain.muskular.api;

import com.siddhantjain.muskular.models.UserAuth;
import com.siddhantjain.muskular.models.UserAuthResponse;
import com.siddhantjain.muskular.models.UserCreateRequest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by akash.jatangi on 9/19/15.
 */
public interface MuskAPI {
    @GET("/user/{user_id}/")
    void getUser(@Path("user_id") String userid,APICallback<UserAuthResponse,UserAuth> response);

    @POST("/user/create/")
    void createUser(@Body UserCreateRequest uc,APICallback<UserAuthResponse,UserAuth> response);
}