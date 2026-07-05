package kasir.Views;

import kasir.Model.User;
import javax.swing.*;
import java.awt.*;
import kasir.Views.emasView;

public class menuUtama {
    private User kasirAktif;
    public menuUtama(User kasirAktif){
        this.kasirAktif = kasirAktif;
    }

    public void tampilkanMenu(){
        boolean running = true;
        while(running){
            String infoSesi = "=========================================\n" +
                              "         KASIR TOKO EMAS BIMA SAKTI          \n" +
                              "=========================================\n" +
                              " Kasir Aktif : " + kasirAktif.getNamaKasir() + " (" + kasirAktif.getIdUser() + ")\n" +
                              " Status Sesi : Terautentikasi (Aman)\n" +
                              "=========================================\n" +
                              " Silakan Pilih Menu Aksi di Bawah Ini:\n";
            String [] opsiMenu = {
                "1. Kelola Data emas (CRUD)",
                "2. Kelola Pelanggan (CRUD)",
                "3. Kelola Supplier (CRUD)",
                "4. Transaksi Penjualan",
                "5. Transaksi Pembelian",
                "6. Lihat Laporan Keuangan",
                "7. Keluar / Log Out"
            };
            String pilihan = (String) JOptionPane.showInputDialog(
                    null, 
                    infoSesi, 
                    "MENU UTAMA - TOKO EMAS", 
                    JOptionPane.PLAIN_MESSAGE, 
                    null, 
                    opsiMenu, 
                    opsiMenu[0]
            );
            if (pilihan == null){
               int konfirmasi = JOptionPane.showConfirmDialog(null, 
                        "Apakah Anda yakin ingin menutup aplikasi?", 
                        "Konfirmasi Keluar", 
                        JOptionPane.YES_NO_OPTION);
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    running = false;
                }
                continue;
            }

            switch (pilihan.substring(0,1)){
                case "1":
                    kasir.Views.emasView viewEmas = new kasir.Views.emasView();
                    viewEmas.tampilkanMenuEmas();
                    break;
                case "2":
                    kasir.Views.pelangganView viewPlg = new kasir.Views.pelangganView();
                    viewPlg.tampilkanMenuPelanggan();
                    break;
                case "3":
                    kasir.Views.supplierView viewSpl = new kasir.Views.supplierView();
                    viewSpl.tampilkanMenuSupplier();
                    break;
                case "4":
                    kasir.Views.transaksiPenjualan jual = new kasir.Views.transaksiPenjualan(kasirAktif);
                    jual.tampilkanFormPenjualan();
                    break;
                case "5":
                    kasir.Views.transaksiPembelian beli = new kasir.Views.transaksiPembelian(kasirAktif);
                    beli.tampilkanFormPembelian();
                    break;
                case "6":
                   kasir.Views.formLaporan viewLapor = new kasir.Views.formLaporan();
                   viewLapor.tampilkanMenuLaporan();
                   break;
                case "7":
                    int logout = JOptionPane.showConfirmDialog(null, 
                            "Apakah Anda ingin Log Out dari akun " + kasirAktif.getNamaKasir() + "?", 
                            "Konfirmasi Log Out", 
                            JOptionPane.YES_NO_OPTION);
                    if (logout == JOptionPane.YES_OPTION) {
                        running = false;
                    }
                    break;
            }
        }
    }
}
