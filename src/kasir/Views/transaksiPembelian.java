package kasir.Views;

import kasir.Controllers.transaksiController;
import kasir.Model.User;
import javax.swing.*;
import java.awt.*;

public class transaksiPembelian {
    private transaksiController controller;
    private User kasirAktif;

    public transaksiPembelian(User kasirAktif) {
        this.controller = new transaksiController();
        this.kasirAktif = kasirAktif;
    }

    public void tampilkanFormPembelian() {
        // Dropdown Jenis Pembelian
        String[] jenisOpsi = {"KULAKAN", "BUYBACK"};
        JComboBox<String> cbJenis = new JComboBox<>(jenisOpsi);

        JTextField txtIdPembelian = new JTextField("PB" + (System.currentTimeMillis() + "").substring(7));
        txtIdPembelian.setEditable(false);
        
        JTextField txtIdSupplier = new JTextField(10); // Diisi jika KULAKAN
        JTextField txtIdPelanggan = new JTextField(10); // Diisi jika BUYBACK
        JTextField txtIdEmas = new JTextField(10);
        JTextField txtHargaBeli = new JTextField(12);
        JTextField txtBeratBeli = new JTextField(10);

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        panelForm.add(new JLabel("No. Nota Masuk (Auto):")); panelForm.add(txtIdPembelian);
        panelForm.add(new JLabel("Jenis Transaksi:")); panelForm.add(cbJenis);
        panelForm.add(new JLabel("ID Supplier (Untuk Kulakan):")); panelForm.add(txtIdSupplier);
        panelForm.add(new JLabel("ID Pelanggan (Untuk Buyback):")); panelForm.add(txtIdPelanggan);
        panelForm.add(new JLabel("ID Emas / Barang:")); panelForm.add(txtIdEmas);
        panelForm.add(new JLabel("Harga Beli / Gram (Rp):")); panelForm.add(txtHargaBeli);
        panelForm.add(new JLabel("Berat Barang (Gram):")); panelForm.add(txtBeratBeli);

        int result = JOptionPane.showConfirmDialog(null, panelForm, 
                "TRANSAKSI PEMBELIAN EMAS", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String idPembelian = txtIdPembelian.getText();
                String jenisPembelian = (String) cbJenis.getSelectedItem();
                String idSupplier = txtIdSupplier.getText().trim();
                String idPelanggan = txtIdPelanggan.getText().trim();
                String idEmas = txtIdEmas.getText().trim();
                int hargaBeli = Integer.parseInt(txtHargaBeli.getText().trim());
                double beratBeli = Double.parseDouble(txtBeratBeli.getText().trim());

                // Validasi logis relasi aktor
                if (jenisPembelian.equals("KULAKAN") && idSupplier.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Untuk KULAKAN, ID Supplier wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (jenisPembelian.equals("BUYBACK") && idPelanggan.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Untuk BUYBACK, ID Pelanggan warga wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (idEmas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ID Emas tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Rumus subtotal pembelian toko: harga beli x berat gram
                int subtotal = (int) (hargaBeli * beratBeli);
                int totalPengeluaran = subtotal;

                // Kosongkan salah satu ID aktor agar di database bernilai NULL sesuai jalurnya
                if (jenisPembelian.equals("KULAKAN")) idPelanggan = null;
                else idSupplier = null;

                boolean sukses = controller.prosesPembelian(idPembelian, jenisPembelian, idSupplier, 
                        kasirAktif.getIdUser(), idPelanggan, idEmas, hargaBeli, beratBeli, subtotal, totalPengeluaran);

                if (sukses) {
                    String pihakPemberi = (jenisPembelian.equals("KULAKAN")) ? "Supplier : " + idSupplier : "Warga    : " + idPelanggan;
                    
                    String notaCetak = "====================================\n" +
                                       "      NOTA MASUK (KULAKAN/BUYBACK)   \n" +
                                       "====================================\n" +
                                       "Nota    : " + idPembelian + "\n" +
                                       "Jenis   : " + jenisPembelian + "\n" +
                                       "Kasir   : " + kasirAktif.getNamaKasir() + "\n" +
                                       pihakPemberi + "\n" +
                                       "------------------------------------\n" +
                                       "Kode Emas  : " + idEmas + "\n" +
                                       "Harga Beli : Rp " + hargaBeli + " /gr\n" +
                                       "Berat (g)  : " + beratBeli + " gram\n" +
                                       "------------------------------------\n" +
                                       "TOTAL KELUAR : Rp " + totalPengeluaran + "\n" +
                                       "====================================\n";
                    
                    JTextArea taNota = new JTextArea(notaCetak);
                    taNota.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    taNota.setEditable(false);
                    JOptionPane.showMessageDialog(null, new JScrollPane(taNota), "PEMBELIAN SUKSES - STOK BERTAMBAH", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Gagal! Pastikan ID Emas sudah terdaftar di database master.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Format angka nominal inputan salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}