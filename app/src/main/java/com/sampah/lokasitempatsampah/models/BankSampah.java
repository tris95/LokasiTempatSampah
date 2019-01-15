package com.sampah.lokasitempatsampah.models;

public class BankSampah {
    private String id, nama_lokasi, latitude, longitude, alamat;

    public BankSampah(String id, String nama_lokasi, String latitude, String longitude, String alamat) {
        this.id = id;
        this.nama_lokasi = nama_lokasi;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_lokasi() {
        return nama_lokasi;
    }

    public void setNama_lokasi(String nama_lokasi) {
        this.nama_lokasi = nama_lokasi;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latiitude) {
        this.latitude = latiitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
