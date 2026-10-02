package com.mycompany.peminjamanruangan.controller;

import com.mycompany.peminjamanruangan.model.*;
import com.mycompany.peminjamanruangan.View.peminjamanView;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PeminjamanController {
    private final ArrayList<Peminjaman> daftarPeminjaman = new ArrayList<>();
    private final peminjamanView view;

    public PeminjamanController(peminjamanView view) {
        this.view = view;
        dummy();
    }

    private void dummy() {
        // Dummy 1 — Peminjam Eksternal + MeetingRoom
        Peminjam peminjam1 = new PeminjamEksternal("Budi Santoso", "PT Contoh Indonesia");
        Ruangan  ruangan1  = new MeetingRoom(201, "Meeting Room A", 15, false,
                                             "Proyektor dan Smart TV");
        Jadwal   jadwal1   = new Jadwal("2026-09-20", "09:00", "11:00");
        daftarPeminjaman.add(new Peminjaman(peminjam1, "Rapat Evaluasi Bulanan",
                                            1001, ruangan1, jadwal1));

        // Dummy 2 — Peminjam Internal + KelasTeori
        Peminjam peminjam2 = new PeminjamInternal("Siti Rahayu", "D0123456");
        Ruangan  ruangan2  = new KelasTeori(101, "Kelas A1", 40, false, 40, true);
        Jadwal   jadwal2   = new Jadwal("2026-09-21", "13:00", "15:00");
        daftarPeminjaman.add(new Peminjaman(peminjam2, "Seminar Tugas Akhir",
                                            1002, ruangan2, jadwal2));
    }

    public void jalankan() {
        boolean berjalan = true;
        while (berjalan) {
            view.tampilkanMenu();
            switch (view.bacaPilihanMenu()) {
                case 1 -> ajukanSurat();
                case 2 -> tampilkanStatus();
                case 3 -> hapusSurat();
                case 4 -> gantiRuangan();
                case 5 -> {
                    berjalan = false;
                    view.pesan("\n>> Sistem telah dihentikan.");
                }
            }
        }
    }

    // ---------------------------------------------------------------
    private void ajukanSurat() {
        System.out.println("\n=== PENGAJUAN SURAT PEMINJAMAN ===");

        // --- Data Peminjam (polimorfisme: pilih Internal atau Eksternal) ---
        Peminjam peminjam = buatPeminjam();

        // --- Data Ruangan ---
        int idRuangan;
        while (true) {
            idRuangan = view.bacaInt("ID ruangan          (INT): ");
            if (idRuangan <= 0) {
                view.pesan(">> ID ruangan harus lebih dari 0.");
                continue;
            }
            if (idRuanganSudahDigunakan(idRuangan)) {
                view.pesan(">> ID ruangan tersebut sudah digunakan.");
                continue;
            }
            break;
        }

        String namaRuangan = view.bacaString("Nama ruangan             : ");
        int kapasitas;
        while (true) {
            kapasitas = view.bacaInt("Kapasitas ruangan   (INT): ");
            if (kapasitas > 0) break;
            view.pesan(">> Kapasitas harus lebih dari 0.");
        }

        // polimorfisme: tiga pilihan jenis ruangan, semuanya Ruangan
        Ruangan ruangan = buatRuangan(idRuangan, namaRuangan, kapasitas);

        // --- Data Surat ---
        String tujuan = view.bacaString("Tujuan peminjaman        : ");

        int nomorSurat;
        while (true) {
            nomorSurat = view.bacaInt("Nomor Surat         (INT): ");
            if (nomorSurat <= 0) {
                view.pesan(">> Nomor surat harus lebih dari 0.");
                continue;
            }
            if (nomorSuratSudahDigunakan(nomorSurat)) {
                view.pesan(">> Nomor surat tersebut sudah digunakan.");
                continue;
            }
            break;
        }

        String tanggal  = view.bacaTanggal("Tanggal Peminjaman       : ");
        String jamMulai = view.bacaJam("Jam Mulai                : ");
        String jamSelesai;
        while (true) {
            jamSelesai = view.bacaJam("Jam Selesai              : ");
            LocalTime mulai   = LocalTime.parse(jamMulai,   DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime selesai = LocalTime.parse(jamSelesai, DateTimeFormatter.ofPattern("HH:mm"));
            if (selesai.isAfter(mulai)) break;
            view.pesan(">> Jam selesai harus setelah jam mulai.");
        }

        Jadwal jadwal = new Jadwal(tanggal, jamMulai, jamSelesai);
        daftarPeminjaman.add(new Peminjaman(peminjam, tujuan, nomorSurat, ruangan, jadwal));

        view.pesan("\n>> SURAT BERHASIL DIAJUKAN!");
        view.tekanEnter();
    }

    // ---------------------------------------------------------------
    private void tampilkanStatus() {
        System.out.println("\n=== STATUS PEMINJAMAN ===");
        if (daftarPeminjaman.isEmpty()) {
            view.pesan(">> Belum ada data peminjaman.");
        } else {
            for (Peminjaman p : daftarPeminjaman)
                view.tampilkanStatus(p);
        }
        view.tekanEnter();
    }

    // ---------------------------------------------------------------
    private void hapusSurat() {
        System.out.println("\n=== HAPUS PEMINJAMAN ===");
        int nomor = view.bacaInt("Masukkan Nomor Surat: ");
        Peminjaman p = cariPeminjaman(nomor);

        if (p == null) {
            view.pesan(">> Nomor surat tidak ditemukan.");
        } else if (view.bacaKonfirmasi("\nYakin ingin menghapus surat ini? (Y/N): ")
                       .equalsIgnoreCase("Y")) {
            daftarPeminjaman.remove(p);
            view.pesan(">> Surat peminjaman berhasil dihapus!");
        } else {
            view.pesan(">> Penghapusan dibatalkan.");
        }
        view.tekanEnter();
    }

    // ---------------------------------------------------------------
    private void gantiRuangan() {
        System.out.println("\n=== GANTI RUANGAN ===");
        Peminjaman p = cariPeminjaman(view.bacaInt("Masukkan Nomor Surat: "));

        if (p == null) {
            view.pesan(">> Nomor surat tidak ditemukan.");
            view.tekanEnter();
            return;
        }

        view.pesan("\nRuangan saat ini : " + p.getRuangan().getNamaRuangan()
                   + " (" + p.getRuangan().getJenisRuangan() + ")");

        if (!view.bacaKonfirmasi("Apakah ingin mengganti ruangan? (Y/N): ")
                  .equalsIgnoreCase("Y")) {
            view.pesan(">> Penggantian ruangan dibatalkan.");
            view.tekanEnter();
            return;
        }

        int idBaru;
        while (true) {
            idBaru = view.bacaInt("Masukkan ID Ruangan Baru: ");
            if (idBaru <= 0) {
                view.pesan(">> ID ruangan harus lebih dari 0.");
                continue;
            }
            if (idBaruSudahDipakaiOlehPeminjamanLain(idBaru, p)) {
                view.pesan(">> ID ruangan tersebut sudah digunakan.");
                continue;
            }
            break;
        }

        String nama = view.bacaString("Masukkan Nama Ruangan Baru: ");
        int kapasitas;
        while (true) {
            kapasitas = view.bacaInt("Masukkan Kapasitas Ruangan Baru: ");
            if (kapasitas > 0) break;
            view.pesan(">> Kapasitas harus lebih dari 0.");
        }

        // polimorfisme: hasil buatRuangan() bisa berupa tipe apa saja
        Ruangan ruanganBaru = buatRuangan(idBaru, nama, kapasitas);
        p.setRuangan(ruanganBaru);
        view.pesan(">> Ruangan berhasil diganti!");
        view.pesan("Ruangan baru : " + p.getRuangan().getNamaRuangan()
                   + " (" + p.getRuangan().getJenisRuangan() + ")");
        view.tekanEnter();
    }

    // ---------------------------------------------------------------
    // Helper: membuat objek Peminjam (polimorfisme — Internal/Eksternal)
    // ---------------------------------------------------------------
    private Peminjam buatPeminjam() {
        int kategori = view.pilihKategoriPeminjam();
        String nama  = view.bacaString("Nama peminjam            : ");

        if (kategori == 1) {
            String nip = view.bacaString("NIP / NIM                : ");
            return new PeminjamInternal(nama, nip);   // polimorfisme
        } else {
            String instansi = view.bacaString("Nama instansi            : ");
            return new PeminjamEksternal(nama, instansi); // polimorfisme
        }
    }

    // ---------------------------------------------------------------
    // Helper: membuat objek Ruangan (polimorfisme — 3 pilihan)
    // ---------------------------------------------------------------
    private Ruangan buatRuangan(int id, String nama, int kapasitas) {
        int jenis = view.pilihJenisRuangan();
        switch (jenis) {
            case 1 -> {
                String fasilitas = view.bacaString("Fasilitas Meeting Room   : ");
                return new MeetingRoom(id, nama, kapasitas, false, fasilitas);
            }
            case 2 -> {
                String jenisLab = view.bacaString("Jenis Laboratorium       : ");
                return new Laboratorium(id, nama, kapasitas, false, jenisLab);
            }
            default -> {
                int kursi;
                while (true) {
                    kursi = view.bacaInt("Jumlah Kursi        (INT): ");
                    if (kursi > 0) break;
                    view.pesan(">> Jumlah kursi harus lebih dari 0.");
                }
                boolean proyektor = view.bacaKonfirmasi(
                        "Ada Proyektor? (Y/N)     : ").equalsIgnoreCase("Y");
                return new KelasTeori(id, nama, kapasitas, false, kursi, proyektor);
            }
        }
    }

    // ---------------------------------------------------------------
    // Helpers pencarian & validasi
    // ---------------------------------------------------------------
    private boolean nomorSuratSudahDigunakan(int nomor) {
        for (Peminjaman p : daftarPeminjaman)
            if (p.getNomorSurat() == nomor) return true;
        return false;
    }

    private boolean idRuanganSudahDigunakan(int id) {
        for (Peminjaman p : daftarPeminjaman)
            if (p.getRuangan().getIdRuangan() == id) return true;
        return false;
    }

    private boolean idBaruSudahDipakaiOlehPeminjamanLain(int id, Peminjaman sekarang) {
        for (Peminjaman p : daftarPeminjaman)
            if (p != sekarang && p.getRuangan().getIdRuangan() == id) return true;
        return false;
    }

    private Peminjaman cariPeminjaman(int nomor) {
        for (Peminjaman p : daftarPeminjaman)
            if (p.getNomorSurat() == nomor) return p;
        return null;
    }
}
