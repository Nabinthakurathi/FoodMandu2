package com.nabin.foodmandu.bll;

import java.io.IOException;

import com.nabin.foodmandu.api.UsersAPI;
import com.nabin.foodmandu.serverresponse.SignUpResponse;
import com.nabin.foodmandu.url.Url;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBll {
    boolean isSuccess = false;


    public boolean checkUser(String username, String password){

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> usersCall = usersAPI.checkUser(username, password);

        try{
            Response<SignUpResponse> loginResponse = usersCall.execute();
            if (loginResponse.isSuccessful() &&
            loginResponse.body().getStatus().equals("Login success!")) {

                Url.token += loginResponse.body().getToken();

                isSuccess = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

}
