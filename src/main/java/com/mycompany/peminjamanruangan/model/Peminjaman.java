package com.mycompany.peminjamanruangan.model;

public class Peminjaman {
    private Peminjam peminjam;   // polimorfisme — bisa Internal atau Eksternal
    private String tujuan;
    private int nomorSurat;
    private Ruangan ruangan;     // polimorfisme — bisa MeetingRoom / Lab / Kelas
    private Jadwal jadwal;

    public Peminjaman(Peminjam peminjam, String tujuan, int nomorSurat,
                      Ruangan ruangan, Jadwal jadwal) {
        this.peminjam   = peminjam;
        this.tujuan     = tujuan;
        this.nomorSurat = nomorSurat;
        this.ruangan    = ruangan;
        this.jadwal     = jadwal;
    }

    public Peminjam getPeminjam()           { return peminjam; }
    public String getTujuan()               { return tujuan; }
    public int getNomorSurat()              { return nomorSurat; }
    public Ruangan getRuangan()             { return ruangan; }
    public Jadwal getJadwal()               { return jadwal; }

    public void setPeminjam(Peminjam peminjam) { this.peminjam = peminjam; }
    public void setRuangan(Ruangan ruangan)    { this.ruangan  = ruangan; }
}
