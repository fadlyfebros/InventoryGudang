package koneksi;

public class Supplier {
    private String kodeSupplier; // Enkapsulasi
    private String namaSupplier;
    private String alamat;
    private String noTelp;
    private String email;

    public Supplier(String kodeSupplier, String namaSupplier, String alamat, String noTelp, String email) {
        this.kodeSupplier = kodeSupplier;
        this.namaSupplier = namaSupplier;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.email = email;
    }

    public String getKodeSupplier() {
        return kodeSupplier;
    }

    public void setKodeSupplier(String kodeSupplier) {
        this.kodeSupplier = kodeSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displaySupplierInfo() {
        System.out.println("Kode Supplier: " + kodeSupplier);
        System.out.println("Nama Supplier: " + namaSupplier);
        System.out.println("Alamat: " + alamat);
        System.out.println("No. Telepon: " + noTelp);
        System.out.println("Email: " + email);
    }
}