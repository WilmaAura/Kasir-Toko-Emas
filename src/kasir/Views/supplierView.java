package kasir.Views;

import kasir.Controllers.supplierController;
import kasir.Model.Supplier;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class supplierView {
    private supplierController controller;

    public supplierView() {
        this.controller = new supplierController();
    }

    public void tampilkanMenuSupplier() {
        boolean running = true;
        while (running) {
            String[] opsi = {"1. Tambah Supplier (PT)", "2. Lihat Daftar Supplier", "3. Kembali"};
            String pilihan = (String) JOptionPane.showInputDialog(null, "Pilih Aksi Kelola Supplier:", 
                    "MASTER DATA SUPPLIER", JOptionPane.PLAIN_MESSAGE, null, opsi, opsi[0]);

            if (pilihan == null || pilihan.startsWith("3")) {
                running = false;
                continue;
            }

            if (pilihan.startsWith("1")) {
                formTambahSupplier();
            } else if (pilihan.startsWith("2")) {
                tabelDaftarSupplier();
            }
        }
    }

    private void formTambahSupplier() {
        JTextField txtId = new JTextField(10);
        JTextField txtNama = new JTextField(15); // Diisi nama PT
        JTextField txtTelp = new JTextField(12);
        JTextArea txtAlamat = new JTextArea(3, 15);
        JScrollPane scrollAlamat = new JScrollPane(txtAlamat);

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.add(new JLabel("ID Supplier (cth: SPL01):")); panelForm.add(txtId);
        panelForm.add(new JLabel("Nama PT / Perusahaan:")); panelForm.add(txtNama);
        panelForm.add(new JLabel("No. Telepon Kantor:")); panelForm.add(txtTelp);
        panelForm.add(new JLabel("Alamat Kantor:")); panelForm.add(scrollAlamat);

        int result = JOptionPane.showConfirmDialog(null, panelForm, 
                "TAMBAH SUPPLIER BARU", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String id = txtId.getText().trim();
            String nama = txtNama.getText().trim();
            String telp = txtTelp.getText().trim();
            String alamat = txtAlamat.getText().trim();

            if (id.isEmpty() || nama.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID dan Nama Perusahaan wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Supplier baru = new Supplier(id, nama, telp, alamat);
            if (controller.tambahSupplier(baru)) {
                JOptionPane.showMessageDialog(null, "Sukses menyimpan data supplier baru!");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal simpan! ID mungkin sudah terpakai.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tabelDaftarSupplier() {
        List<Supplier> daftar = controller.getAllSupplier();
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append(String.format("%-10s | %-20s | %-13s\n", "ID SPL", "NAMA PERUSAHAAN (PT)", "NO TELP"));
        sb.append("========================================================\n");
        
        for (Supplier s : daftar) {
            sb.append(String.format("%-10s | %-20s | %-13s\n", 
                    s.getIdSupplier(), s.getNamaSupplier(), s.getNoTelp()));
        }
        sb.append("========================================================\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "DAFTAR SUPPLIER EMAS", JOptionPane.PLAIN_MESSAGE);
    }
}