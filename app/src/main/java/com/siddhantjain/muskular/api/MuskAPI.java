package com.siddhantjain.muskular.api;

import com.siddhantjain.muskular.models.ProgramSelectResponse;
import com.siddhantjain.muskular.models.ProgramSelectResponseData;
import com.siddhantjain.muskular.models.UserAuth;
import com.siddhantjain.muskular.models.UserAuthResponse;
import com.siddhantjain.muskular.models.UserCreateRequest;
import com.siddhantjain.muskular.models.UserProfilerRequest;
import com.siddhantjain.muskular.models.UserProfilerResponse;
import com.siddhantjain.muskular.models.UserProfilerResponseData;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by akash.jatangi on 9/19/15.
 */
public interface MuskAPI {
    @GET("/user/{user_id}/")
    void getUser(@Path("user_id") String userid,APICallback<UserAuthResponse,UserAuth> response);

    @POST("/user/create/")
    void createUser(@Body UserCreateRequest uc,APICallback<UserAuthResponse,UserAuth> response);

    @POST("/user/authenticate/")
    void authenticateUser(@Body UserCreateRequest uc,APICallback<UserAuthResponse,UserAuth> response);

    @POST("/user/profile_add/{user_id}/")
    void profileUser(@Path("user_id") String userid,@Body UserProfilerRequest userProfilerRequest,APICallback<UserProfilerResponse,UserProfilerResponseData> response);

    @GET("/user/program_select/{user_id}/")
    void programSelect(@Path("user_id") String userid, @Query("pid") String pid, @Query("uwya_id") String uwya_id, @Query("uwyw_id") String uwuw_id, APICallback<ProgramSelectResponse,ProgramSelectResponseData> response);
}
