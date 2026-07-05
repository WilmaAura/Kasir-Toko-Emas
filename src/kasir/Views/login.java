package kasir.Views;

import kasir.Controllers.loginController;
import kasir.Model.User;
import javax.swing.*;
import java.awt.*;

public class login {
    private loginController controller;

    public login() {
        this.controller = new loginController();
    }

    public User tampilkanFormLogin() {
        while (true) {
            //Komponen input
            JTextField txtUsername = new JTextField(15);
            JPasswordField txtPassword = new JPasswordField(15);

            //Menyusun tampilan di dalam JPanel dengan GridBagLayout
            JPanel panelInput = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Jarak antar komponen
            gbc.anchor = GridBagConstraints.WEST;

            //Username
            gbc.gridx = 0; gbc.gridy = 0;
            panelInput.add(new JLabel("Username Kasir :"), gbc);
            gbc.gridx = 1;
            panelInput.add(txtUsername, gbc);

            //Password
            gbc.gridx = 0; gbc.gridy = 1;
            panelInput.add(new JLabel("Password       :"), gbc);
            gbc.gridx = 1;
            panelInput.add(txtPassword, gbc);

            //Menampilkan panel tersebut di dalam ShowConfirmDialog
            int pilihan = JOptionPane.showConfirmDialog(null, 
                    panelInput, 
                    "SISTEM KASIR TOKO EMAS - LOGIN", 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE);

            //Jika tombol 'Cancel' atau tanda silang diklik
            if (pilihan != JOptionPane.OK_OPTION) {
                int konfirmasi = JOptionPane.showConfirmDialog(null, 
                        "Apakah Anda ingin keluar dari aplikasi?", 
                        "Konfirmasi Keluar", 
                        JOptionPane.YES_NO_OPTION);
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    return null;
                }
                continue;
            }

            //Mengambil teks dari inputan
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword()); // Mengubah char[] menjadi String

            //Validasi input kosong
            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                        "Username dan Password tidak boleh kosong!", 
                        "Peringatan", 
                        JOptionPane.WARNING_MESSAGE);
                continue;
            }

            // Autentikasi ke database dengan Controller
            User userAtutenticated = controller.autentication(username, password);

            if (userAtutenticated != null) {
                JOptionPane.showMessageDialog(null, 
                        "Login Berhasil!\nSelamat Bekerja, " + userAtutenticated.getNamaKasir() + ".", 
                        "Sukses", 
                        JOptionPane.INFORMATION_MESSAGE);
                return userAtutenticated;
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Username / Password Salah atau Status Akun Nonaktif!", 
                        "Login Gagal", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}