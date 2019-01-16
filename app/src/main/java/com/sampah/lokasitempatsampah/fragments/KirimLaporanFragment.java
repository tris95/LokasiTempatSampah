package com.sampah.lokasitempatsampah.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sampah.lokasitempatsampah.R;
import com.sampah.lokasitempatsampah.activities.SignUpActivity;
import com.sampah.lokasitempatsampah.models.ValueAdd;
import com.sampah.lokasitempatsampah.services.APIServices;
import com.sampah.lokasitempatsampah.utils.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KirimLaporanFragment extends Fragment {
    Button btnKirim;
    ImageView imgLaporan;
    EditText txtket;
    String latitude, longitude, gambar;
    private static final int CAMERA_REQUEST = 188, FILE_REQUES = 189;
    FusedLocationProviderClient mFusedLocationClient;

    public KirimLaporanFragment() {
    }

    public static KirimLaporanFragment newInstance() {
        return new KirimLaporanFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kirim_laporan, container, false);

        btnKirim = view.findViewById(R.id.btnKirim);
        imgLaporan = view.findViewById(R.id.imgLaporan);
        txtket = view.findViewById(R.id.txtket);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Kirim Laporan");
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude=String.valueOf(location.getLatitude());
                            longitude=String.valueOf(location.getLongitude());

                        } else {
                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Lokasi tidak terdeteksi",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (gambar.equals("")) {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silakan masukkan foto",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    kirimlaporan();
                }
            }
        });

        imgLaporan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                dialogAmbilGambar();
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void dialogAmbilGambar() {
        final CharSequence[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Ambil foto dari ?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera")) {

                    if (Build.VERSION.SDK_INT >= 24) {
                        try {
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (options[item].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, FILE_REQUES);
                }
            }
        }).

                setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap imageBitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    imageBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    int w = imageBitmap.getWidth();
//                    int h = imageBitmap.getHeight();
//                    w=w/2;h=h/2;
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 480, 480, true);
                    gambar = Utilities.getArrayByteFromBitmap(imageBitmap);
                    imgLaporan.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == FILE_REQUES) {
                Uri imageUri = data.getData();
                InputStream imageStream = null;
                Bitmap imageBitmap;
                try {
                    imageStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(Objects.requireNonNull(imageUri));
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    int w = imageBitmap.getWidth();
//                    int h = imageBitmap.getHeight();
//                    w=w/2;h=h/2;
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 480, 480, true);
                    gambar = Utilities.getArrayByteFromBitmap(imageBitmap);
                    imgLaporan.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (imageStream != null) {
                        try {
                            imageStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void kirimlaporan() {
        final ProgressDialog pDialog = new ProgressDialog(getContext());
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
        Call<ValueAdd> call = api.kirimlaporan(random, Utilities.getUser(getContext()).getId_user(),
                txtket.getText().toString().trim().toUpperCase(), latitude, longitude, gambar);
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        txtket.setText("");
                        imgLaporan.setImageDrawable(getResources().getDrawable(R.drawable.defaultimage));
                        latitude = "";
                        longitude = "";
                        gambar = "";
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Laporan berhasil dikirim", 3500).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {

                            }
                        }, 500);

                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Nomor HP sudah terdaftar. Silakan masuk aplikasi",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal menyimpan data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
