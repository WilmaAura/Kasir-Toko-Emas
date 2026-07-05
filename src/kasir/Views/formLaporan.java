package kasir.Views;

import kasir.Controllers.transaksiController;
import javax.swing.*;
import java.awt.*;

public class formLaporan {
    private transaksiController controller;

    public formLaporan() {
        this.controller = new transaksiController();
    }

    public void tampilkanMenuLaporan() {
        boolean running = true;
        while (running) {
            String[] opsi = {"1. Laporan Penjualan", "2. Laporan Pembelian / Buyback", "3. Kembali"};
            String pilihan = (String) JOptionPane.showInputDialog(null, "Pilih Jenis Laporan:", 
                    "LAPORAN KEUANGAN TOKO EMAS", JOptionPane.PLAIN_MESSAGE, null, opsi, opsi[0]);

            if (pilihan == null || pilihan.startsWith("3")) {
                running = false;
                continue;
            }

            String teksLaporan = "";
            String judulLaporan = "";

            if (pilihan.startsWith("1")) {
                teksLaporan = "               LAPORAN TRANSAKSI PENJUALAN TOKO EMAS\n" + controller.getRekapPenjualan();
                judulLaporan = "REKAP PENJUALAN KASIR";
            } else if (pilihan.startsWith("2")) {
                teksLaporan = "          LAPORAN TRANSAKSI PEMBELIAN & BUYBACK TOKO EMAS\n" + controller.getRekapPembelian();
                judulLaporan = "REKAP PEMBELIAN KASIR";
            }

            // Tampilkan laporan ke dalam JTextArea
            JTextArea textArea = new JTextArea(teksLaporan);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(650, 350));

            // Tombol opsi diubah menjadi Export ke File Nota dan Tutup
            Object[] opsiDialog = {"📄 Export ke Notepad (.txt)", "❌ Tutup"};
            int aksi = JOptionPane.showOptionDialog(null, 
                    scrollPane, 
                    judulLaporan,
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.PLAIN_MESSAGE, 
                    null, 
                    opsiDialog, 
                    opsiDialog[0]);

            // Jika kasir memilih tombol Export ke Txt (Indeks 0)
            if (aksi == 0) {
                // Generate nama file otomatis yang unik
                String namaFile = judulLaporan.toLowerCase().replace(" ", "_") + "_" + System.currentTimeMillis() + ".txt";
                
                try (java.io.FileWriter writer = new java.io.FileWriter(namaFile)) {
                    writer.write(teksLaporan);
                    JOptionPane.showMessageDialog(null, 
                            "Laporan berhasil diexport secara fisik!\nFile tersimpan di root project sebagai:\n" + namaFile,
                            "Sukses Export", 
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (java.io.IOException e) {
                    JOptionPane.showMessageDialog(null, "Gagal menulis file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}