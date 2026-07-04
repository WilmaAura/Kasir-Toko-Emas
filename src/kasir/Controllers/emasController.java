package kasir.Controllers;

import kasir.Config.databaseConfig;
import kasir.Model.Emas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class emasController {

    // 1. Fungsi Tambah Barang
    public boolean tambahEmas(Emas em) {
        Connection conn = databaseConfig.getConnection();
        if (conn == null) return false;

        String sql = "INSERT INTO emas (id_emas, jenis, kadar, berat_gram, biaya_produksi, stok) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, em.getIdEmas());
            ps.setString(2, em.getJenis());
            ps.setString(3, em.getKadar());
            ps.setDouble(4, em.getBeratGram());
            ps.setInt(5, em.getBiayaProduksi());
            ps.setInt(6, em.getStok());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal tambah emas: " + e.getMessage());
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    // 2. Fungsi Tampilkan Semua Barang
    public List<Emas> getAllEmas() {
        List<Emas> listEmas = new ArrayList<>();
        Connection conn = databaseConfig.getConnection();
        if (conn == null) return listEmas;

        String sql = "SELECT * FROM emas";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Emas em = new Emas(
                    rs.getString("id_emas"),
                    rs.getString("jenis"),
                    rs.getString("kadar"),
                    rs.getDouble("berat_gram"),
                    rs.getInt("biaya_produksi"),
                    rs.getInt("stok")
                );
                listEmas.add(em);
            }
        } catch (SQLException e) {
            System.err.println("Gagal ambil data emas: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
        return listEmas;
    }
}