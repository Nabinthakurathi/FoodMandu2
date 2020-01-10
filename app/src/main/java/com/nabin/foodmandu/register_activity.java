package com.nabin.foodmandu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nabin.foodmandu.api.UsersAPI;
import com.nabin.foodmandu.model.User;
import com.nabin.foodmandu.serverresponse.ImageResponse;
import com.nabin.foodmandu.serverresponse.SignUpResponse;
import com.nabin.foodmandu.strictmode.strictmodeclass;
import com.nabin.foodmandu.url.Url;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register_activity extends AppCompatActivity {
    private ImageView imgProfile;
    private EditText etFirstName, etLastName, etSignUpUsername, etSignUpPassword, etConfirmPassword;
    private Button btnSignup;
    String imagePath="";
    private String imageName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        imgProfile = findViewById(R.id.imgProfile);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etSignUpUsername = findViewById(R.id.etSignUpUsername);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSignUpPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    if (validate()) {
                        SaveImageOnly();
                        SignUp();
                    }

                }else {
                    Toast.makeText(register_activity.this, "Password Does Not Match",Toast.LENGTH_SHORT).show();
                    etSignUpPassword.requestFocus();
                    return;
                }
            }
        });
    }
    private boolean validate(){
        boolean status=true;
        if (etSignUpUsername.getText().toString().length()< 6){
            etSignUpUsername.setError("Minimum 6 Characters");
            status=false;
        }
        return status;
    }
    private void BrowseImage(){
        checkPrimission();
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);

    }
    private void checkPrimission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=getPackageManager().PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RESULT_OK){

            if (data==null){
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
        try {
            Uri uri=data.getData();
            imgProfile.setImageURI(uri);
            imagePath=getRealPathFromUri(uri);
        }catch (Exception e){
            Toast.makeText(this, "Excep"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();

    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void SaveImageOnly() {
        File file=new File(imagePath);
        RequestBody requestBody= RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("imageFile",
                file.getName(),requestBody);
        ;
        UsersAPI userApi= Url.getInstance().create(UsersAPI.class);
        Call<ImageResponse> responseBodyCall= userApi.uploadImage(body);

        strictmodeclass.StrictMode();

        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    private void SignUp(){
        String fname = etFirstName.getText().toString();
        String lname = etLastName.getText().toString();
        String username = etSignUpUsername.getText().toString();
        String password = etSignUpPassword.getText().toString();
        String conpassword = etConfirmPassword.getText().toString();

        User user=new User(fname,lname,username,password,conpassword, imageName);

        UsersAPI userApi= Url.getInstance().create(UsersAPI.class);
        Call<SignUpResponse> signUpCall= userApi.registerUser(user);

        signUpCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(register_activity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(register_activity.this, "Registered", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(register_activity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }
}
