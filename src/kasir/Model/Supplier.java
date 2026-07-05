package kasir.Model;

public class Supplier {
    private String idSupplier;
    private String namaSupplier;
    private String noTelp;
    private String alamat;

    public Supplier() {}

    public Supplier(String idSupplier, String namaSupplier, String noTelp, String alamat) {
        this.idSupplier = idSupplier;
        this.namaSupplier = namaSupplier;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    // Getter & Setter
    public String getIdSupplier() { return idSupplier; }
    public void setIdSupplier(String idSupplier) { this.idSupplier = idSupplier; }
    public String getNamaSupplier() { return namaSupplier; }
    public void setNamaSupplier(String namaSupplier) { this.namaSupplier = namaSupplier; }
    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}