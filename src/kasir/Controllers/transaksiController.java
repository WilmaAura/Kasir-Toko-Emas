package kasir.Controllers;

import kasir.Config.databaseConfig;
import java.sql.*;

public class transaksiController {

    // Fungsi untuk memproses transaksi penjualan emas
    public boolean prosesPenjualan(String idPenjualan, String idUser, String idPelanggan, 
                                   String idEmas, int hargaEmas, int subtotal, int totalBayar) {
        Connection conn = databaseConfig.getConnection();
        if (conn == null) return false;

        PreparedStatement psNota = null;
        PreparedStatement psDetail = null;
        PreparedStatement psStok = null;

        try {
            // MATIKAN AUTO-COMMIT agar jika salah satu query gagal, semuanya dibatalkan (Transaction Management)
            conn.setAutoCommit(false);

            // 1. Insert ke tabel induk penjualan_emas
            String sqlNota = "INSERT INTO penjualan_emas (id_penjualan, id_user, id_pelanggan, total_bayar) VALUES (?, ?, ?, ?)";
            psNota = conn.prepareStatement(sqlNota);
            psNota.setString(1, idPenjualan);
            psNota.setString(2, idUser);
            psNota.setString(3, idPelanggan);
            psNota.setInt(4, totalBayar);
            psNota.executeUpdate();

            // 2. Insert ke tabel detail_penjualan
            // Kita generate ID Detail secara acak sederhana untuk primary key-nya
            String idDetail = "DTL" + Long.toString(System.currentTimeMillis()).substring(8);
            String sqlDetail = "INSERT INTO detail_penjualan (id_detail, id_penjualan, id_emas, harga_emas, subtotal) VALUES (?, ?, ?, ?, ?)";
            psDetail = conn.prepareStatement(sqlDetail);
            psDetail.setString(1, idDetail);
            psDetail.setString(2, idPenjualan);
            psDetail.setString(3, idEmas);
            psDetail.setInt(4, hargaEmas);
            psDetail.setInt(5, subtotal);
            psDetail.executeUpdate();

            // 3. UPDATE POTONG STOK EMAS
            String sqlStok = "UPDATE emas SET STOK = STOK - 1 WHERE id_emas = ? AND STOK > 0";
            psStok = conn.prepareStatement(sqlStok);
            psStok.setString(1, idEmas);
            int barisTerpengaruh = psStok.executeUpdate();

            // Jika stok ternyata 0 atau emas tidak ditemukan, batalkan transaksi
            if (barisTerpengaruh == 0) {
                throw new SQLException("Stok emas habis atau ID Emas tidak valid!");
            }

            // JIKA SEMUA KUERI SUKSES, COMMIT KE DATABASE
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Transaksi Penjualan Gagal, Rollback dijalankan: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {}
            return false;
        } finally {
            // Kembalikan ke normal dan tutup semua resource
            try {
                if (psNota != null) psNota.close();
                if (psDetail != null) psDetail.close();
                if (psStok != null) psStok.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {}
        }
    }

    public boolean prosesPembelian(String idPembelian, String jenisPembelian, String idSupplier, 
                               String idUser, String idPelanggan, String idEmas, 
                               int hargaBeli, double beratBeli, int subtotal, int totalPengeluaran) {
    Connection conn = databaseConfig.getConnection();
    if (conn == null) return false;

    PreparedStatement psNota = null;
    PreparedStatement psDetail = null;
    PreparedStatement psStok = null;

    try {
        conn.setAutoCommit(false); // Nyalakan transaction management

        // 1. Insert ke tabel induk pembelian_emas
        String sqlNota = "INSERT INTO pembelian_emas (id_pembelian, jenis_pembelian, id_supplier, id_user, id_pelanggan, total_pengeluaran) VALUES (?, ?, ?, ?, ?, ?)";
        psNota = conn.prepareStatement(sqlNota);
        psNota.setString(1, idPembelian);
        psNota.setString(2, jenisPembelian);
        
        // PENTING: Handle nilai NULL jika supplier atau pelanggan kosong tergantung jenis transaksi
        if (idSupplier == null || idSupplier.isEmpty()) psNota.setNull(3, java.sql.Types.VARCHAR);
        else psNota.setString(3, idSupplier);
        
        psNota.setString(4, idUser);
        
        if (idPelanggan == null || idPelanggan.isEmpty()) psNota.setNull(5, java.sql.Types.VARCHAR);
        else psNota.setString(5, idPelanggan);
        
        psNota.setInt(6, totalPengeluaran);
        psNota.executeUpdate();

        // 2. Insert ke tabel detail_pembelian
        String idDetail = "DTB" + (System.currentTimeMillis() + "").substring(8);
        String sqlDetail = "INSERT INTO detail_pembelian (id_detail, id_pembelian, id_emas, harga_beli_perGr, berat_beli, jumlah, subtotal) VALUES (?, ?, ?, ?, ?, 1, ?)";
        psDetail = conn.prepareStatement(sqlDetail);
        psDetail.setString(1, idDetail);
        psDetail.setString(2, idPembelian);
        psDetail.setString(3, idEmas);
        psDetail.setInt(4, hargaBeli);
        psDetail.setDouble(5, beratBeli);
        psDetail.setInt(6, subtotal);
        psDetail.executeUpdate();

        // 3. UPDATE TAMBAH STOK EMAS (Karena toko membeli barang masuk)
        String sqlStok = "UPDATE emas SET STOK = STOK + 1 WHERE id_emas = ?";
        psStok = conn.prepareStatement(sqlStok);
        psStok.setString(1, idEmas);
        psStok.executeUpdate();

        conn.commit(); // Eksekusi sukses total
        return true;

    } catch (SQLException e) {
        System.err.println("Transaksi Pembelian Gagal, Rollback dijalankan: " + e.getMessage());
        try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
        return false;
    } finally {
        try {
            if (psNota != null) psNota.close();
            if (psDetail != null) psDetail.close();
            if (psStok != null) psStok.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {}
    }
}

    // Tambahkan dua method ini di dalam class transaksiController kamu

// 1. Ambil Rekap Penjualan
public String getRekapPenjualan() {
    StringBuilder sb = new StringBuilder();
    sb.append("====================================================================\n");
    sb.append(String.format("%-14s | %-12s | %-10s | %-15s\n", "NO NOTA", "PELANGGAN", "KASIR", "TOTAL BAYAR"));
    sb.append("====================================================================\n");

    String sql = "SELECT p.id_penjualan, p.id_pelanggan, u.nama_kasir, p.total_bayar " +
                 "FROM penjualan_emas p " +
                 "JOIN table_user u ON p.id_user = u.id_user ORDER BY p.tanggal DESC";

    Connection conn = databaseConfig.getConnection();
    if (conn == null) return "Gagal terhubung database.";

    try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        int grandTotal = 0;
        while (rs.next()) {
            sb.append(String.format("%-14s | %-12s | %-10s | Rp %-12d\n",
                    rs.getString("id_penjualan"),
                    rs.getString("id_pelanggan"),
                    rs.getString("nama_kasir"),
                    rs.getInt("total_bayar")));
            grandTotal += rs.getInt("total_bayar");
        }
        sb.append("====================================================================\n");
        sb.append(String.format(" TOTAL PENDAPATAN TOKO MAS : Rp %d\n", grandTotal));
        sb.append("====================================================================\n");
    } catch (SQLException e) {
        return "Error ambil laporan: " + e.getMessage();
    } finally {
        try { if (conn != null) conn.close(); } catch (SQLException ex) {}
    }
    return sb.toString();
}

// 2. Ambil Rekap Pembelian/Buyback
public String getRekapPembelian() {
    StringBuilder sb = new StringBuilder();
    sb.append("====================================================================\n");
    sb.append(String.format("%-14s | %-10s | %-10s | %-15s\n", "NO NOTA", "JENIS", "PIHAK LUAR", "TOTAL KELUAR"));
    sb.append("====================================================================\n");

    String sql = "SELECT id_pembelian, jenis_pembelian, id_supplier, id_pelanggan, total_pengeluaran FROM pembelian_emas ORDER BY tanggal DESC";

    Connection conn = databaseConfig.getConnection();
    if (conn == null) return "Gagal terhubung database.";

    try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        int grandTotal = 0;
        while (rs.next()) {
            String pihakLuar = rs.getString("jenis_pembelian").equals("KULAKAN") ? rs.getString("id_supplier") : rs.getString("id_pelanggan");
            sb.append(String.format("%-14s | %-10s | %-10s | Rp %-12d\n",
                    rs.getString("id_pembelian"),
                    rs.getString("jenis_pembelian"),
                    pihakLuar,
                    rs.getInt("total_pengeluaran")));
            grandTotal += rs.getInt("total_pengeluaran");
        }
        sb.append("====================================================================\n");
        sb.append(String.format(" TOTAL PENGELUARAN TOKO MAS: Rp %d\n", grandTotal));
        sb.append("====================================================================\n");
    } catch (SQLException e) {
        return "Error ambil laporan: " + e.getMessage();
    } finally {
        try { if (conn != null) conn.close(); } catch (SQLException ex) {}
    }
    return sb.toString();
}
}