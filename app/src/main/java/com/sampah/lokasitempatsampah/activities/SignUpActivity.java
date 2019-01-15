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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.models.ValueAdd;
import com.sampah.lokasitempatsampah.services.APIServices;
import com.sampah.lokasitempatsampah.utils.Utilities;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    EditText etNama, etTelp, etPassword;
    ProgressDialog pDialog;
    int APP_REQUEST_CODE = 99;
    RelativeLayout lysignup;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etNama = findViewById(R.id.editText5);
        etPassword = findViewById(R.id.editText6);
        etTelp = findViewById(R.id.editText4);
        lysignup = findViewById(R.id.lysignup);

        Button btnDaftar = findViewById(R.id.button);
        LinearLayout llMasuk = findViewById(R.id.ll_masuk);

        llMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        etTelp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pDialog = new ProgressDialog(SignUpActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    phoneLogin();
                }
            }
        });

        etTelp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pDialog = new ProgressDialog(SignUpActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    phoneLogin();
                    return true;
                }
                return false;
            }
        });

        lysignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(SignUpActivity.this);
            }
        });
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etNama.getText().toString().trim()) || TextUtils.isEmpty(etTelp.getText().toString().trim())) {
                    Snackbar.make(findViewById(android.R.id.content), "Harap lengkapi semua data yang dibutuhkan",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    signup();
                }
            }
        });

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void phoneLogin() {
        final Intent intent = new Intent(SignUpActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            pDialog.dismiss();
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal verifikasi nomor Hp",
                        Snackbar.LENGTH_LONG).show();
            } else {
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(final Account account) {
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        String phoneNumberString = "";
                        if (phoneNumber != null) {
                            phoneNumberString = phoneNumber.toString();
                        }
                        etTelp.setText(phoneNumberString);
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal verifikasi nomor Hp",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void signup() {
        final ProgressDialog pDialog = new ProgressDialog(SignUpActivity.this);
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
        Call<ValueAdd> call = api.signup(random, etPassword.getText().toString().trim(),
                etNama.getText().toString().trim().toUpperCase(), etTelp.getText().toString().trim());
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Akun berhasil terdaftar. Silakan login aplikasi", 3500).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 500);
                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Alamat email sudah terdaftar. Silakan masuk aplikasi",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal menyimpan data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
