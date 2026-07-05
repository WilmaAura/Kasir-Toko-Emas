package kasir.Controllers;

import kasir.Config.databaseConfig;
import kasir.Model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class supplierController {

    public boolean tambahSupplier(Supplier sp) {
        Connection conn = databaseConfig.getConnection();
        if (conn == null) return false;

        String sql = "INSERT INTO tabel_supplier (id_supplier, nama_supplier, no_telp, alamat) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getIdSupplier());
            ps.setString(2, sp.getNamaSupplier());
            ps.setString(3, sp.getNoTelp());
            ps.setString(4, sp.getAlamat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal tambah supplier: " + e.getMessage());
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    public List<Supplier> getAllSupplier() {
        List<Supplier> list = new ArrayList<>();
        Connection conn = databaseConfig.getConnection();
        if (conn == null) return list;

        String sql = "SELECT * FROM tabel_supplier";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Supplier(
                    rs.getString("id_supplier"),
                    rs.getString("nama_supplier"),
                    rs.getString("no_telp"),
                    rs.getString("alamat")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Gagal ambil data supplier: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
        return list;
    }
}