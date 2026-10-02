package com.mycompany.peminjamanruangan.model;

public class PeminjamEksternal extends Peminjam {
    private String namaInstansi;

    public PeminjamEksternal(String nama, String namaInstansi) {
        super(nama);
        this.namaInstansi = namaInstansi;
    }

    public String getNamaInstansi() { return namaInstansi; }
    public void setNamaInstansi(String namaInstansi) { this.namaInstansi = namaInstansi; }

    @Override
    public String getIdentitas() {
        return getNama() + " dari " + namaInstansi;
    }

    @Override
    public String getKategori() {
        return "Eksternal";
    }
}
