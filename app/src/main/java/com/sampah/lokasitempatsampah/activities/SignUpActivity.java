package com.sampah.lokasitempatsampah.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.sampah.lokasitempatsampah.utils.Utilities;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    EditText etEmail, etNama, etTelp;
    ProgressDialog pDialog;
    int APP_REQUEST_CODE = 99;
    RelativeLayout lysignup;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etEmail = findViewById(R.id.editText);
        etNama = findViewById(R.id.editText5);
        etTelp = findViewById(R.id.editText4);
        lysignup=findViewById(R.id.lysignup);

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
                if (TextUtils.isEmpty(etEmail.getText().toString().trim()) || TextUtils.isEmpty(etNama.getText().toString().trim()) || TextUtils.isEmpty(etTelp.getText().toString().trim())){
                    Snackbar.make(findViewById(android.R.id.content), "Harap lengkapi semua data yang dibutuhkan",
                            Snackbar.LENGTH_LONG).show();
                }else {
                    if (!Utilities.isValidEmail(etEmail.getText().toString())) {
                        requestFocus(etEmail);
                        Snackbar.make(findViewById(android.R.id.content), "Alamat email tidak valid",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
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
}
