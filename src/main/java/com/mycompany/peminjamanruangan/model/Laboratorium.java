package com.mycompany.peminjamanruangan.model;

public class Laboratorium extends Ruangan {
    private String jenisLaboratorium;

    public Laboratorium(int idRuangan,String namaRuangan,int kapasitas,boolean tersedia,String jenisLaboratorium) {
        super(idRuangan,namaRuangan,kapasitas,tersedia);
        this.jenisLaboratorium=jenisLaboratorium;
    }

    public String getJenisLaboratorium() {
        return jenisLaboratorium;
    }

    public void setJenisLaboratorium(String jenisLaboratorium) {
        this.jenisLaboratorium=jenisLaboratorium;
    }

    @Override
    public String getJenisRuangan() {
        return "Laboratorium";
    }

    @Override
    public String getDetailRuangan() {
        return "Jenis: "+jenisLaboratorium;
    }
}