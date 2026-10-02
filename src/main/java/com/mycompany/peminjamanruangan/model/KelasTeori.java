package com.mycompany.peminjamanruangan.model;

public class KelasTeori extends Ruangan {
    private int jumlahKursi;
    private boolean adaProyektor;

    public KelasTeori(int idRuangan, String namaRuangan, int kapasitas,
                      boolean tersedia, int jumlahKursi, boolean adaProyektor) {
        super(idRuangan, namaRuangan, kapasitas, tersedia);
        this.jumlahKursi = jumlahKursi;
        this.adaProyektor = adaProyektor;
    }

    public int getJumlahKursi() { return jumlahKursi; }
    public boolean isAdaProyektor() { return adaProyektor; }

    public void setJumlahKursi(int jumlahKursi) { this.jumlahKursi = jumlahKursi; }
    public void setAdaProyektor(boolean adaProyektor) { this.adaProyektor = adaProyektor; }

    @Override
    public String getJenisRuangan() {
        return "Kelas Teori";
    }

    @Override
    public String getDetailRuangan() {
        return "Jumlah Kursi: " + jumlahKursi
                + " | Proyektor: " + (adaProyektor ? "Ada" : "Tidak Ada");
    }
}
