package com.mycompany.peminjamanruangan.model;

public class Jadwal {
    private String tanggal;
    private String jamMulai;
    private String jamSelesai;

    public Jadwal(String tanggal,String jamMulai,String jamSelesai) {
        this.tanggal=tanggal;
        this.jamMulai=jamMulai;
        this.jamSelesai=jamSelesai;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }
}