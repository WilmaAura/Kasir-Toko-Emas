package kasir.Model;

public class Pelanggan {
    private String idPelanggan;
    private String namaPelanggan;
    private String noTelp;
    private String alamat;

    public Pelanggan() {}

    public Pelanggan(String idPelanggan, String namaPelanggan, String noTelp, String alamat) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    // Getter dan Setter
    public String getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(String idPelanggan) { this.idPelanggan = idPelanggan; }
    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }
    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}