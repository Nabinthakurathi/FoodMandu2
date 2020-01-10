package com.nabin.foodmandu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nabin.foodmandu.bll.LoginBll;
import com.nabin.foodmandu.strictmode.strictmodeclass;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You can register here", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this, register_activity.class);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                login();
            }
        });

    }
    private void login(){
        String username=etUsername.getText().toString();
        String password=etPassword.getText().toString();

        LoginBll loginBLL=new LoginBll();

        strictmodeclass.StrictMode();
        if (loginBLL.checkUser(username, password)) {
//            Toast.makeText(getApplicationContext(), "Welcome to dashboard", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DashBoard_activity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();
            etUsername.requestFocus();
        }

    }
}
