package kasir.Model;

public class Emas {
    private String idEmas;
    private String jenis;
    private String kadar;
    private double beratGram;
    private int biayaProduksi;
    private int stok;

    public Emas() {}

    public Emas(String idEmas, String jenis, String kadar, double beratGram, int biayaProduksi, int stok) {
        this.idEmas = idEmas;
        this.jenis = jenis;
        this.kadar = kadar;
        this.beratGram = beratGram;
        this.biayaProduksi = biayaProduksi;
        this.stok = stok;
    }

    // Getter dan Setter
    public String getIdEmas() { return idEmas; }
    public void setIdEmas(String idEmas) { this.idEmas = idEmas; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public String getKadar() { return kadar; }
    public void setKadar(String kadar) { this.kadar = kadar; }

    public double getBeratGram() { return beratGram; }
    public void setBeratGram(double beratGram) { this.beratGram = beratGram; }

    public int getBiayaProduksi() { return biayaProduksi; }
    public void setBiayaProduksi(int biayaProduksi) { this.biayaProduksi = biayaProduksi; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}