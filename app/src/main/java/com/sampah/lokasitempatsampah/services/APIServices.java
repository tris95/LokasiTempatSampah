package com.sampah.lokasitempatsampah.services;


import com.sampah.lokasitempatsampah.models.BankSampah;
import com.sampah.lokasitempatsampah.models.Laporan;
import com.sampah.lokasitempatsampah.models.User;
import com.sampah.lokasitempatsampah.models.Value;
import com.sampah.lokasitempatsampah.models.ValueAdd;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServices {


    @FormUrlEncoded
    @POST("getlokasibanksampah.php")
    Call<Value<BankSampah>> getbanksampah(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("signin.php")
    Call<Value<User>> signin(@Field("xkey") String xkey,
                             @Field("nohp") String nohp,
                             @Field("password") String password,
                             @Field("token") String token);

    @FormUrlEncoded
    @POST("signup.php")
    Call<ValueAdd> signup(@Field("xkey") String xkey,
                          @Field("password") String password,
                          @Field("nama") String nama,
                          @Field("nohp") String nohp);


//
//    @FormUrlEncoded
//    @POST("ubahpass.php")
//    Call<ValueAdd> ubahpass(@Field("xkey") String xkey,
//                            @Field("email") String email,
//                            @Field("oldpass") String oldpass,
//                            @Field("newpass") String newpass);
//
//    @FormUrlEncoded
//    @POST("updateprofil.php")
//    Call<Value<User>> updateprofil(@Field("xkey") String xkey,
//                                   @Field("email") String email,
//                                   @Field("nama") String nama,
//                                   @Field("noktp") String noktp,
//                                   @Field("nohp") String nohp,
//                                   @Field("alamat") String alamat);
//
//    @FormUrlEncoded
//    @POST("updateprofilimage.php")
//    Call<Value<User>> updateprofilimage(@Field("xkey") String xkey,
//                                        @Field("id") String id,
//                                        @Field("oldfoto") String oldfoto,
//                                        @Field("foto") String foto);
//
    @FormUrlEncoded
    @POST("kirimlaporan.php")
    Call<ValueAdd> kirimlaporan(@Field("xkey") String xkey,
                                @Field("iduser") String iduser,
                                @Field("ket") String ket,
                                @Field("latitude") String latitude,
                                @Field("longitude") String longitude,
                                @Field("gambar") String gambar);

    @FormUrlEncoded
    @POST("getlaporan.php")
    Call<Value<Laporan>> getlaporan(@Field("xkey") String xkey,
                                    @Field("iduser") String iduser);

//    @FormUrlEncoded
//    @POST("cekime.php")
//    Call<ValueAdd> cekime(@Field("xkey") String xkey,
//                          @Field("ime") String ime,
//                          @Field("idberita") String idberita);
//
    @FormUrlEncoded
    @POST("logout.php")
    Call<ValueAdd> signout(@Field("xkey") String xkey,
                           @Field("email") String email);
//
//    @FormUrlEncoded
//    @POST("setlogin.php")
//    Call<ValueAdd> setlogindb(@Field("xkey") String xkey,
//                              @Field("email") String email,
//                              @Field("idp") String idp);
//
//    @FormUrlEncoded
//    @POST("ceknik.php")
//    Call<ValueAdd> ceknik(@Field("xkey") String xkey,
//                          @Field("nik") String nik);
//
//    @FormUrlEncoded
//    @POST("setperangkat.php")
//    Call<ValueAdd> setperangkat(@Field("xkey") String xkey,
//                                @Field("perangkat") String perangkat,
//                                @Field("token") String token);
//
//    @FormUrlEncoded
//    @POST("ceklogin.php")
//    Call<ValueAdd> ceklogin(@Field("xkey") String xkey,
//                            @Field("email") String email,
//                            @Field("password") String password);
}