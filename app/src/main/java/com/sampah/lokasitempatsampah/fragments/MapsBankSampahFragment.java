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

//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }

    private void lokasibanksampah() {
//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        String random = Utilities.getRandom(5);
//
//        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Utilities.getBaseURLUser())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        APIServices api = retrofit.create(APIServices.class);
//        Call<Value<BankSampah>> call = api.getbanksampah(random);
//        call.enqueue(new Callback<Value<BankSampah>>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(@NonNull Call<Value<BankSampah>> call, @NonNull Response<Value<BankSampah>> response) {
//                pDialog.dismiss();
//                if (response.body() != null) {
//                    int success = Objects.requireNonNull(response.body()).getSuccess();
//                    if (success == 1) {
//                        ArrayList<BankSampah> mListBankSampah = (ArrayList<BankSampah>) Objects.requireNonNull(response.body()).getData();
//
//                        lat = mListBankSampah.get(0).getLat();
//                        lng = mListBankSampah.get(0).getLng();
//                        namaLokasi = mListBankSampah.get(0).getNama_bank_sampah();

                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                gMap = googleMap;
                                if (gMap != null) gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//                if (ContextCompat.checkSelfPermission(getActivity(),
//                        android.Manifest.permission.ACCESS_FINE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {


//                    requestPermissions(new String[]{
//                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                } else {
//                    mFusedLocationClient.getLastLocation()
//                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                                @Override
//                                public void onSuccess(Location location) {
//                                    if (location != null) {
//                                        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                                        gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));
//
//                                    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
//                                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                                //LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                LatLng destlatLng = new LatLng(-3.3571023,104.3231384);
                                gMap.addMarker(new MarkerOptions().position(destlatLng).title("Bank Sampah KI")).showInfoWindow();
                                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));

//                                    if (currentLatLng != null && destlatLng != null) {
//                                        String url = getUrl(currentLatLng, destlatLng);
//                                        DownloadTask FetchUrl = new DownloadTask();
//                                        FetchUrl.execute(url);
//                                    }
//                                    } else {
////                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silakan hidupkan GPS Anda",
////                                                Snackbar.LENGTH_LONG).show();
//
//                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi)).showInfoWindow();
//                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
//                                    }
//                                }
//                            });
//                }
                            }
                        });
//                    } else {
////                        rl_none.setVisibility(View.GONE);
//                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
//                                Snackbar.LENGTH_LONG).show();
//                    }
//                } else {
////                    rl_none.setVisibility(View.GONE);
//                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
//                            Snackbar.LENGTH_LONG).show();
//                }
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onFailure(@NonNull Call<Value<BankSampah>> call, @NonNull Throwable t) {
//                System.out.println("Retrofit Error:" + t.getMessage());
//                pDialog.dismiss();
////                rl_none.setVisibility(View.GONE);
//                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
//                        Snackbar.LENGTH_LONG).show();
//            }
//        });
    }

    private String getUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
//            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);

            }

            try {
                gMap.addPolyline(lineOptions);
            } catch (Exception e) {
                Log.e("polyline", e.toString());
            }
        }
    }
}
