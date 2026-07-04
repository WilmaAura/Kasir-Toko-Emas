package kasir.Config;
import java.sql.*;
// Library JBDC
/* login.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
*/

public class databaseConfig {
    private static final String URL = "jdbc:mariadb://localhost:3306/kasir_toko_emas";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi Database Berhasil!!");
        } catch(ClassNotFoundException e){
            System.out.println("Driver tidak ditemukan:" + e.getMessage());
        } catch (SQLException e){
            System.err.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}
