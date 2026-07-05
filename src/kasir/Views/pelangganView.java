package kasir.Views;

import kasir.Controllers.pelangganController;
import kasir.Model.Pelanggan;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class pelangganView {
    private pelangganController controller;

    public pelangganView() {
        this.controller = new pelangganController();
    }

    public void tampilkanMenuPelanggan() {
        boolean running = true;
        while (running) {
            String[] opsi = {"1. Tambah Pelanggan", "2. Lihat Daftar Pelanggan", "3. Kembali"};
            String pilihan = (String) JOptionPane.showInputDialog(null, "Pilih Aksi Kelola Pelanggan:", 
                    "MASTER DATA PELANGGAN", JOptionPane.PLAIN_MESSAGE, null, opsi, opsi[0]);

            if (pilihan == null || pilihan.startsWith("3")) {
                running = false;
                continue;
            }

            if (pilihan.startsWith("1")) {
                formTambahPelanggan();
            } else if (pilihan.startsWith("2")) {
                tabelDaftarPelanggan();
            }
        }
    }

    private void formTambahPelanggan() {
        JTextField txtId = new JTextField(10);
        JTextField txtNama = new JTextField(15);
        JTextField txtTelp = new JTextField(12);
        JTextArea txtAlamat = new JTextArea(3, 15);
        JScrollPane scrollAlamat = new JScrollPane(txtAlamat);

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.add(new JLabel("ID Pelanggan (cth: PLG01):")); panelForm.add(txtId);
        panelForm.add(new JLabel("Nama Pelanggan:")); panelForm.add(txtNama);
        panelForm.add(new JLabel("No. Telepon:")); panelForm.add(txtTelp);
        panelForm.add(new JLabel("Alamat:")); panelForm.add(scrollAlamat);

        int result = JOptionPane.showConfirmDialog(null, panelForm, 
                "TAMBAH PELANGGAN BARU", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String id = txtId.getText().trim();
            String nama = txtNama.getText().trim();
            String telp = txtTelp.getText().trim();
            String alamat = txtAlamat.getText().trim();

            if (id.isEmpty() || nama.isEmpty() || telp.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID, Nama, dan No Telp wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pelanggan baru = new Pelanggan(id, nama, telp, alamat);
            if (controller.tambahPelanggan(baru)) {
                JOptionPane.showMessageDialog(null, "Sukses menyimpan data pelanggan baru!");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal simpan! ID mungkin sudah terpakai.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tabelDaftarPelanggan() {
        List<Pelanggan> daftar = controller.getAllPelanggan();
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append(String.format("%-10s | %-15s | %-13s | %-15s\n", "ID PLG", "NAMA PELANGGAN", "NO TELP", "ALAMAT"));
        sb.append("========================================================\n");
        
        for (Pelanggan p : daftar) {
            sb.append(String.format("%-10s | %-15s | %-13s | %-15s\n", 
                    p.getIdPelanggan(), p.getNamaPelanggan(), p.getNoTelp(), p.getAlamat()));
        }
        sb.append("========================================================\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "DAFTAR PELANGGAN TOKO", JOptionPane.PLAIN_MESSAGE);
    }
}