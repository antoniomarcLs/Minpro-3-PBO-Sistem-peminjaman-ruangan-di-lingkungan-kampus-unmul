package com.mycompany.peminjamanruangan.model;

public class MeetingRoom extends Ruangan {
    private String fasilitas;

    public MeetingRoom(int idRuangan,String namaRuangan,int kapasitas,boolean tersedia,String fasilitas) {
        super(idRuangan,namaRuangan,kapasitas,tersedia);
        this.fasilitas=fasilitas;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas=fasilitas;
    }

    @Override
    public String getJenisRuangan() {
        return "Meeting Room";
    }

    @Override
    public String getDetailRuangan() {
        return "Fasilitas: "+fasilitas;
    }
}