package com.sampah.lokasitempatsampah.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sampah.lokasitempatsampah.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationViewEx bottomNavigationView;
    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

//        bottomNavigationView.enableAnimation(true);
//        bottomNavigationView.enableShiftingMode(false);
//        bottomNavigationView.enableItemShiftingMode(false);
//        bottomNavigationView.setIconSize(27, 27);
//        bottomNavigationView.setTextSize(11);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
//                        currentFragment = 0;
//                        nowFragment = 0;
//                        fragment = HomeFragment.newInstance();
//                        lastFragment = fragment;
//                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_kontak:
//                        currentFragment = 1;
//                        nowFragment = 1;
//                        fragment = KontakFragment.newInstance();
//                        lastFragment = fragment;
//                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_event:
//                        currentFragment = 2;
//                        nowFragment = 2;
//                        fragment = EventFragment.newInstance();
//                        lastFragment = fragment;
//                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_laporan:
//                        currentFragment = 3;
//                        nowFragment = 3;
//                        fragment = LaporanFragment.newInstance();
//                        lastFragment = fragment;
//                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_profil:
//                        currentFragment = 4;
//                        nowFragment = 4;
//                        fragment = ProfilFragment.newInstance();
//                        lastFragment = fragment;
//                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                }
                return true;
            }
        });
    }
}
