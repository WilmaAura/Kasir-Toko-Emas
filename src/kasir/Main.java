package kasir;

import kasir.Views.login;
import kasir.Views.menuUtama;
import kasir.Model.User;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Memulai Aplikasi Kasir Toko Emas...");
        
        login formLogin = new login();
        User kasirAktif = formLogin.tampilkanFormLogin();
        
        if (kasirAktif != null) {
            System.out.println("Kasir aktif saat ini: " + kasirAktif.getNamaKasir());
                
            menuUtama menu = new menuUtama(kasirAktif);
            menu.tampilkanMenu();
            
            JOptionPane.showMessageDialog(null, "Sesi kerja selesai. Sampai jumpa!");
            System.exit(0);
        } else {
            System.out.println("Aplikasi ditutup dari halaman login.");
            System.exit(0);
        }
    }
}