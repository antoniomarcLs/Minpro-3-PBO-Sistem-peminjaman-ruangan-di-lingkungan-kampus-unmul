package com.mycompany.peminjamanruangan.model;

public abstract class Peminjam {
    private String nama;

    public Peminjam(String nama) {
        this.nama = nama;
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public abstract String getIdentitas();

    public abstract String getKategori();
}
