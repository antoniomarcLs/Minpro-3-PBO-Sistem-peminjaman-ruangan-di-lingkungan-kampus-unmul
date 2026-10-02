package com.mycompany.peminjamanruangan.model;

public class PeminjamInternal extends Peminjam {
    private String nip; // Nomor Induk Pegawai / NIM

    public PeminjamInternal(String nama, String nip) {
        super(nama);
        this.nip = nip;
    }

    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }

    @Override
    public String getIdentitas() {
        return getNama() + " (NIP/NIM: " + nip + ")";
    }

    @Override
    public String getKategori() {
        return "Internal";
    }
}
