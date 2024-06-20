// Menerapkan konsep class, constructor, dan method
package koneksi;
/**
 * Representasi sebuah produk (barang) dengan detail seperti kode, nama, berat, informasi supplier, stok awal, satuan, harga beli, dan harga jual.
 * Kelas ini menyediakan getter dan setter untuk mengakses serta mengubah detail produk.
 */
public class Barang {
    //Menerapkan Encapsulation
    private String kodeBarang;
    private String namaBarang;
    private String berat;
    private String kodeSupplier;
    private String namaSupplier;
    private int stokAwal;
    private String satuan;
    private double hargaBeli;
    private double hargaJual;
    
    /**
     * Konstruktor untuk membuat objek Barang dengan detail yang ditentukan.
     *
     * @param kodeBarang Kode produk.
     * @param namaBarang Nama produk.
     * @param berat Berat produk.
     * @param kodeSupplier Kode supplier yang menyediakan produk.
     * @param namaSupplier Nama supplier yang menyediakan produk.
     * @param stokAwal Jumlah stok awal produk.
     * @param satuan Satuan produk.
     * @param hargaBeli Harga beli produk.
     * @param hargaJual Harga jual produk.
     */
    // Constructor
    public Barang(String kodeBarang, String namaBarang, String berat, String kodeSupplier, String namaSupplier, int stokAwal, String satuan, double hargaBeli, double hargaJual) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.berat = berat;
        this.kodeSupplier = kodeSupplier;
        this.namaSupplier = namaSupplier;
        this.stokAwal = stokAwal;
        this.satuan = satuan;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }
     // Getters and setters (methods)
    /**
     * Mengembalikan kode barang.
     *
     * @return Kode barang.
     */
    public String getKodeBarang() {
        return kodeBarang;
    }

    /**
     * Mengatur kode barang.
     *
     * @param kodeBarang Kode barang yang akan diatur.
     */
    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    /**
     * Mengembalikan nama barang.
     *
     * @return Nama barang.
     */
    public String getNamaBarang() {
        return namaBarang;
    }

    /**
     * Mengatur nama barang.
     *
     * @param namaBarang Nama barang yang akan diatur.
     */
    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    /**
     * Mengembalikan berat barang.
     *
     * @return Berat barang.
     */
    public String getBerat() {
        return berat;
    }

    /**
     * Mengatur berat barang.
     *
     * @param berat Berat barang yang akan diatur.
     */
    public void setBerat(String berat) {
        this.berat = berat;
    }

    /**
     * Mengembalikan kode supplier yang menyediakan barang.
     *
     * @return Kode supplier.
     */
    public String getKodeSupplier() {
        return kodeSupplier;
    }

    /**
     * Mengatur kode supplier yang menyediakan barang.
     *
     * @param kodeSupplier Kode supplier yang akan diatur.
     */
    public void setKodeSupplier(String kodeSupplier) {
        this.kodeSupplier = kodeSupplier;
    }

    /**
     * Mengembalikan nama supplier yang menyediakan barang.
     *
     * @return Nama supplier.
     */
    public String getNamaSupplier() {
        return namaSupplier;
    }

    /**
     * Mengatur nama supplier yang menyediakan barang.
     *
     * @param namaSupplier Nama supplier yang akan diatur.
     */
    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    /**
     * Mengembalikan stok awal barang.
     *
     * @return Stok awal barang.
     */
    public int getStokAwal() {
        return stokAwal;
    }

    /**
     * Mengatur stok awal barang.
     *
     * @param stokAwal Stok awal barang yang akan diatur.
     */
    public void setStokAwal(int stokAwal) {
        this.stokAwal = stokAwal;
    }

    /**
     * Mengembalikan satuan barang.
     *
     * @return Satuan barang.
     */
    public String getSatuan() {
        return satuan;
    }

    /**
     * Mengatur satuan barang.
     *
     * @param satuan Satuan barang yang akan diatur.
     */
    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    /**
     * Mengembalikan harga beli barang.
     *
     * @return Harga beli barang.
     */
    public double getHargaBeli() {
        return hargaBeli;
    }

    /**
     * Mengatur harga beli barang.
     *
     * @param hargaBeli Harga beli barang yang akan diatur.
     */
    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    /**
     * Mengembalikan harga jual barang.
     *
     * @return Harga jual barang.
     */
    public double getHargaJual() {
        return hargaJual;
    }

    /**
     * Mengatur harga jual barang.
     *
     * @param hargaJual Harga jual barang yang akan diatur.
     */
    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }
}