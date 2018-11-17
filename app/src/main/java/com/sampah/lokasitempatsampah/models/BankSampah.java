package com.sampah.lokasitempatsampah.models;

public class BankSampah {
    private String id_bank_sampah, nama_bank_sampah, lokasi, lat, lng, nohp;

    public BankSampah(String id_bank_sampah, String nama_bank_sampah, String lokasi, String lat, String lng, String nohp) {
        this.id_bank_sampah = id_bank_sampah;
        this.nama_bank_sampah = nama_bank_sampah;
        this.lokasi = lokasi;
        this.lat = lat;
        this.lng = lng;
        this.nohp = nohp;
    }

    public String getId_bank_sampah() {
        return id_bank_sampah;
    }

    public void setId_bank_sampah(String id_bank_sampah) {
        this.id_bank_sampah = id_bank_sampah;
    }

    public String getNama_bank_sampah() {
        return nama_bank_sampah;
    }

    public void setNama_bank_sampah(String nama_bank_sampah) {
        this.nama_bank_sampah = nama_bank_sampah;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }
}
