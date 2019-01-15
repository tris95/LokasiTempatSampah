package com.sampah.lokasitempatsampah.models;

public class User {
    private String id_user, nama_user, password, no_hp, alamat;

    public User(){

    }
    public User(String id_user, String nama_user, String password, String no_hp, String alamat) {
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.password = password;
        this.no_hp = no_hp;
        this.alamat = alamat;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
