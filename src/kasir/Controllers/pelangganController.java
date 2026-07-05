package kasir.Controllers;

import kasir.Config.databaseConfig;
import kasir.Model.Pelanggan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class pelangganController {

    public boolean tambahPelanggan(Pelanggan pl) {
        Connection conn = databaseConfig.getConnection();
        if (conn == null) return false;

        String sql = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, no_telp, alamat) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pl.getIdPelanggan());
            ps.setString(2, pl.getNamaPelanggan());
            ps.setString(3, pl.getNoTelp());
            ps.setString(4, pl.getAlamat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal tambah pelanggan: " + e.getMessage());
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    public List<Pelanggan> getAllPelanggan() {
        List<Pelanggan> list = new ArrayList<>();
        Connection conn = databaseConfig.getConnection();
        if (conn == null) return list;

        String sql = "SELECT * FROM pelanggan";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Pelanggan(
                    rs.getString("id_pelanggan"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("no_telp"),
                    rs.getString("alamat")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Gagal ambil data pelanggan: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
        return list;
    }
}