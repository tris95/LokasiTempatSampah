package com.sampah.lokasitempatsampah.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.models.BankSampah;
import com.sampah.lokasitempatsampah.models.Value;
import com.sampah.lokasitempatsampah.services.APIServices;
import com.sampah.lokasitempatsampah.services.DirectionsJSONParser;
import com.sampah.lokasitempatsampah.utils.Utilities;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsBankSampahFragment extends Fragment {
    private String lat, lng, namaLokasi;
    MapView mapView;
    GoogleMap gMap;

    public MapsBankSampahFragment() {
    }

    public static MapsBankSampahFragment newInstance() {
        MapsBankSampahFragment fragment = new MapsBankSampahFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps_bank_sampah, container, false);

        mapView = v.findViewById(R.id.maps);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        lokasibanksampah();

        return v;
    }

    private void lokasibanksampah() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
        Call<Value<BankSampah>> call = api.getbanksampah(random);
        call.enqueue(new Callback<Value<BankSampah>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<BankSampah>> call, @NonNull Response<Value<BankSampah>> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        final ArrayList<BankSampah> mListBankSampah = (ArrayList<BankSampah>) Objects.requireNonNull(response.body()).getData();

                            mapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    gMap = googleMap;
                                    for (int i = 0; i < mListBankSampah.size(); i++) {
                                        lat = mListBankSampah.get(i).getLatitude();
                                        lng = mListBankSampah.get(i).getLongitude();
                                        namaLokasi = mListBankSampah.get(i).getNama_lokasi();
                                        LatLng destlatLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
                                    }
                                }
                            });
                    } else {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<BankSampah>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
