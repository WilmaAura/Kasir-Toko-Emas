package kasir.Controllers;

import kasir.Config.databaseConfig;

import kasir.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {
    // Function untuk validasi berdasarkan data database
    public User autentication(String username, String password){
        Connection conn = databaseConfig.getConnection();
        if (conn == null){
            return null;
        }
        String sql = "SELECT * FROM table_user WHERE username = ? AND password = ? AND status = 'Aktif'";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // jika ditemukan, buat objek User dan kembalikan ke View
                    User userAutenticated = new User();
                    userAutenticated.setIdUser(rs.getString("id_user"));
                    userAutenticated.setNamaKasir(rs.getString("nama_kasir"));
                    userAutenticated.setUsername(rs.getString("username"));
                    userAutenticated.setStatus(rs.getString("status"));
                    return userAutenticated;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat autentikasi login: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close(); // Selalu tutup koneksi setelah digunakan
            } catch (SQLException ex) {
                System.err.println("Gagal menutup koneksi: " + ex.getMessage());
            }
        }
        return null;
    }
}
