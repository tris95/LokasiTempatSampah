package com.sampah.lokasitempatsampah.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.fragments.DaftarLaporanFragment;
import com.sampah.lokasitempatsampah.fragments.KirimLaporanFragment;
import com.sampah.lokasitempatsampah.fragments.MapsBankSampahFragment;
import com.sampah.lokasitempatsampah.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationViewEx bottomNavigationView;
    static FragmentManager fragmentManager;
    Fragment fragment;
    static int currentFragment = 0, nowFragment = 0;
    static Fragment lastFragment;
    public static final int PHONE_REQUEST = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        bottomNavigationView.enableAnimation(true);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setIconSize(27, 27);
        bottomNavigationView.setTextSize(11);

        fragment = MapsBankSampahFragment.newInstance();
        currentFragment = 0;
        nowFragment = 0;
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PHONE_REQUEST);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_lokasi:
                        currentFragment = 0;
                        nowFragment = 0;
                        fragment = MapsBankSampahFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_laporan:
                        currentFragment = 1;
                        nowFragment = 1;
                        fragment = DaftarLaporanFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_kirimlaporan:
                        currentFragment = 2;
                        nowFragment = 2;
                        fragment = KirimLaporanFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_profil:
                        currentFragment = 4;
                        nowFragment = 4;
                        fragment = ProfileFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                }
                return true;
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_REQUEST: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PHONE_REQUEST);
                }
            }
        }
    }
}
