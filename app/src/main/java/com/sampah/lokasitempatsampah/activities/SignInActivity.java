package com.sampah.lokasitempatsampah.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.models.User;
import com.sampah.lokasitempatsampah.utils.Utilities;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    EditText etPhoneEmail;
    public static List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etPhoneEmail = findViewById(R.id.editText);
        Button btnMasuk = findViewById(R.id.button);
        LinearLayout llDaftar = findViewById(R.id.ll_daftar);

        llDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                String phoneEmail = etPhoneEmail.getText().toString();
                Utilities.hideKeyboard(SignInActivity.this);
                if (TextUtils.isEmpty(phoneEmail)) {
                    etPhoneEmail.setError("Silahkan masukan email atau no. HP");
                }else {
                    if (etPhoneEmail.getText().toString().substring(0,1).equals("0")){
                        if (phoneEmail.substring(0,1).equals("0")){
                            phoneEmail = "+62"+phoneEmail.substring(1,phoneEmail.length());
                        }
//                        signin(phoneEmail, "");
                    }else if (etPhoneEmail.getText().toString().substring(0,1).equals("+")){
//                        signin(phoneEmail, "");
                    }else {
//                        signin("", phoneEmail);
                    }
                }
            }
        });

    }
}
