package com.sampah.lokasitempatsampah.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.models.User;
import com.sampah.lokasitempatsampah.models.Value;
import com.sampah.lokasitempatsampah.services.APIServices;
import com.sampah.lokasitempatsampah.utils.Utilities;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    EditText etnohp, etpassword;
    RelativeLayout lysignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etnohp = findViewById(R.id.editText);
        etpassword = findViewById(R.id.editText1);
        Button btnMasuk = findViewById(R.id.button);
        LinearLayout llDaftar = findViewById(R.id.ll_daftar);
        lysignin = findViewById(R.id.lysignin);

        llDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                Utilities.hideKeyboard(SignInActivity.this);
                if (TextUtils.isEmpty(etnohp.getText().toString()) || TextUtils.isEmpty(etpassword.getText().toString())) {
                    Snackbar.make(findViewById(android.R.id.content), "no hp atau password kosong",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    signin();
                }
            }
        });
        lysignin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(SignInActivity.this);
            }
        });
    }

    private void signin() {
        final ProgressDialog pDialog = new ProgressDialog(SignInActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<User>> call = api.signin(random, etnohp.getText().toString().trim(), etpassword.getText().toString().trim(), Utilities.getToken());
        call.enqueue(new Callback<Value<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<User>> call, @NonNull Response<Value<User>> response) {
                pDialog.dismiss();
                Utilities.hideKeyboard(SignInActivity.this);
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        for (User user : Objects.requireNonNull(response.body()).getData()) {
                            Utilities.setUser(SignInActivity.this, user);
                        }
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Login berhasil",
                                2000).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Utilities.setLogin(SignInActivity.this);
                            }
                        }, 500);
                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "No HP tidak terdaftar",
                                Snackbar.LENGTH_LONG).show();
                    } else if (success == 3) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Password yang Anda masukkan salah",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal masuk aplikasi. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<User>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
