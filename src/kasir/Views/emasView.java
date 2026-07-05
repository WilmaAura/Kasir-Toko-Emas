package kasir.Views;

import kasir.Controllers.emasController;
import kasir.Model.Emas;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class emasView {
    private emasController controller;

    public emasView() {
        this.controller = new emasController();
    }

    public void tampilkanMenuEmas() {
        boolean running = true;
        while (running) {
            String[] opsi = {"1. Tambah Data Emas", "2. Lihat Daftar Emas", "3. Kembali ke Menu Utama"};
            String pilihan = (String) JOptionPane.showInputDialog(null, "Pilih Aksi Kelola Emas:", 
                    "MASTER DATA EMAS", JOptionPane.PLAIN_MESSAGE, null, opsi, opsi[0]);

            if (pilihan == null || pilihan.startsWith("3")) {
                running = false;
                continue;
            }

            if (pilihan.startsWith("1")) {
                formTambahEmas();
            } else if (pilihan.startsWith("2")) {
                tabelDaftarEmas();
            }
        }
    }

    private void formTambahEmas() {
        // Membuat kolom inputan dalam satu kotak sekaligus
        JTextField txtId = new JTextField(10);
        JTextField txtJenis = new JTextField(15); // Contoh: Cincin, Kalung
        JTextField txtKadar = new JTextField(10); // Contoh: 16K, 24K
        JTextField txtBerat = new JTextField(10);
        JTextField txtBiaya = new JTextField(12);
        JTextField txtStok = new JTextField(5);

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.add(new JLabel("ID Emas / Kode:")); panelForm.add(txtId);
        panelForm.add(new JLabel("Jenis Perhiasan:")); panelForm.add(txtJenis);
        panelForm.add(new JLabel("Kadar (Karat):")); panelForm.add(txtKadar);
        panelForm.add(new JLabel("Berat (Gram):")); panelForm.add(txtBerat);
        panelForm.add(new JLabel("Biaya Produksi (Rp):")); panelForm.add(txtBiaya);
        panelForm.add(new JLabel("Jumlah Stok awal:")); panelForm.add(txtStok);

        int result = JOptionPane.showConfirmDialog(null, panelForm, 
                "TAMBAH DATA EMAS BARU", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Konversi data string inputan ke tipe data database
                String id = txtId.getText().trim();
                String jenis = txtJenis.getText().trim();
                String kadar = txtKadar.getText().trim();
                double berat = Double.parseDouble(txtBerat.getText().trim());
                int biaya = Integer.parseInt(txtBiaya.getText().trim());
                int stok = Integer.parseInt(txtStok.getText().trim());

                if (id.isEmpty() || jenis.isEmpty() || kadar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Semua data teks wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Emas baru = new Emas(id, jenis, kadar, berat, biaya, stok);
                if (controller.tambahEmas(baru)) {
                    JOptionPane.showMessageDialog(null, "Sukses menyimpan data perhiasan baru!");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal simpan! Periksa apakah ID sudah terpakai.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Format angka pada Berat/Biaya/Stok salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tabelDaftarEmas() {
        List<Emas> daftar = controller.getAllEmas();
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append(String.format("%-10s | %-12s | %-6s | %-8s | %-6s\n", "ID EMAS", "JENIS", "KADAR", "BERAT(g)", "STOK"));
        sb.append("========================================================\n");
        
        for (Emas e : daftar) {
            sb.append(String.format("%-10s | %-12s | %-6s | %-8.2f | %-6d\n", 
                    e.getIdEmas(), e.getJenis(), e.getKadar(), e.getBeratGram(), e.getStok()));
        }
        sb.append("========================================================\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "DAFTAR STOK EMAS SAAT INI", JOptionPane.PLAIN_MESSAGE);
    }
}