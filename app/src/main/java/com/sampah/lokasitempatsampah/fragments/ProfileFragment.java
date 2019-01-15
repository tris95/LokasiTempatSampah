package com.sampah.lokasitempatsampah.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.activities.MainActivity;
import com.sampah.lokasitempatsampah.activities.SignInActivity;
import com.sampah.lokasitempatsampah.activities.SplashScreenActivity;
import com.sampah.lokasitempatsampah.models.User;
import com.sampah.lokasitempatsampah.utils.Utilities;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ProfileFragment extends Fragment {
    Button btnAction;
    EditText etNama,etNoHp,etAlamat;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnAction= view.findViewById(R.id.btnAction);
        etNama= view.findViewById(R.id.etNama);
        etNoHp= view.findViewById(R.id.etNoHp);
        etAlamat= view.findViewById(R.id.etAlamat);

        User users = Utilities.getUser(getActivity());;
        etNama.setText(users.getNama_user());
        etNoHp.setText(users.getNo_hp());
        etAlamat.setText(users.getAlamat());

        btnAction.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "LogOut berhasil",
                        2000).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), SignInActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        getActivity().finish();
                        Utilities.signOutUser(getContext());
                    }
                }, 500);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
