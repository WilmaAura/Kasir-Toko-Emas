package kasir.Views;

import kasir.Controllers.transaksiController;
import kasir.Model.User;
import javax.swing.*;
import java.awt.*;

public class transaksiPenjualan {
    private transaksiController controller;
    private User kasirAktif;

    public transaksiPenjualan(User kasirAktif) {
        this.controller = new transaksiController();
        this.kasirAktif = kasirAktif;
    }

    public void tampilkanFormPenjualan() {
        JTextField txtIdPenjualan = new JTextField("PJ" + (System.currentTimeMillis() + "").substring(7));
        txtIdPenjualan.setEditable(false); // ID Nota otomatis generate agar tidak bentrok
        JTextField txtIdPelanggan = new JTextField(10);
        JTextField txtIdEmas = new JTextField(10);
        JTextField txtHargaEmasPerGr = new JTextField(12);
        JTextField txtBeratGram = new JTextField(10);
        JTextField txtBiayaProduksi = new JTextField(12);

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.add(new JLabel("No. Nota (Otomatis):")); panelForm.add(txtIdPenjualan);
        panelForm.add(new JLabel("ID Pelanggan / Pembeli:")); panelForm.add(txtIdPelanggan);
        panelForm.add(new JLabel("ID Emas / Barang:")); panelForm.add(txtIdEmas);
        panelForm.add(new JLabel("Harga Emas Hari Ini / Gram (Rp):")); panelForm.add(txtHargaEmasPerGr);
        panelForm.add(new JLabel("Berat Emas (Gram):")); panelForm.add(txtBeratGram);
        panelForm.add(new JLabel("Biaya Produksi / Ongkos (Rp):")); panelForm.add(txtBiayaProduksi);

        int result = JOptionPane.showConfirmDialog(null, panelForm, 
                "TRANSAKSI PENJUALAN EMAS BARU", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String idPenjualan = txtIdPenjualan.getText();
                String idPelanggan = txtIdPelanggan.getText().trim();
                String idEmas = txtIdEmas.getText().trim();
                int hargaEmas = Integer.parseInt(txtHargaEmasPerGr.getText().trim());
                double berat = Double.parseDouble(txtBeratGram.getText().trim());
                int biaya = Integer.parseInt(txtBiayaProduksi.getText().trim());

                if (idPelanggan.isEmpty() || idEmas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ID Pelanggan dan ID Emas tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Hitung Subtotal otomatis berdasarkan logika Toko Emas
                int subtotal = (int) ((hargaEmas * berat) + biaya);
                int totalBayar = subtotal; // Karena via dialog input 1 item, total nota = subtotal

                // Eksekusi transaksi ke database via Controller
                boolean sukses = controller.prosesPenjualan(idPenjualan, kasirAktif.getIdUser(), idPelanggan, idEmas, hargaEmas, subtotal, totalBayar);

                if (sukses) {
                    String notaCetak = "====================================\n" +
                                       "        NOTA PENJUALAN EMAS         \n" +
                                       "====================================\n" +
                                       "Nota    : " + idPenjualan + "\n" +
                                       "Kasir   : " + kasirAktif.getNamaKasir() + "\n" +
                                       "Pembeli : " + idPelanggan + "\n" +
                                       "------------------------------------\n" +
                                       "Kode Emas  : " + idEmas + "\n" +
                                       "Harga/Gram : Rp " + hargaEmas + "\n" +
                                       "Berat (g)  : " + berat + " gram\n" +
                                       "Ongkos     : Rp " + biaya + "\n" +
                                       "------------------------------------\n" +
                                       "TOTAL NOTA : Rp " + totalBayar + "\n" +
                                       "====================================\n";
                    
                    JTextArea taNota = new JTextArea(notaCetak);
                    taNota.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    taNota.setEditable(false);
                    JOptionPane.showMessageDialog(null, new JScrollPane(taNota), "TRANSAKSI SUKSES - NOTA DICETAK", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Gagal! Pastikan ID Pelanggan & ID Emas terdaftar, dan stok emas masih ada.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input nominal angka/berat tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}