# Aplikasi Inventory Gudang

User Interface Login

User Interface Login
<img align="justify" alt="Web-developer" width="50%" src="https://github.com/fadlyfebros/InventoryGudang/blob/main/Login.png">


User Interface Menu Utama

![Menu Utama](https://github.com/fadlyfebros/InventoryGudang/blob/main/menuUtama.png)


User Interface Menu Laporan Barang

![Menu Laporan Barang Keluar](https://github.com/fadlyfebros/InventoryGudang/blob/main/Report%20Barang.png)

**Deskripsi Singkat:**
Aplikasi Inventory Gudang adalah sebuah sistem yang dibangun menggunakan Java dan MySQL untuk mengelola inventaris di sebuah gudang. Aplikasi ini menyediakan berbagai fitur untuk memantau dan mengelola data barang, pemasok, serta laporan stok dengan efisien.

**Fitur Utama:**
- **Data Barang:** Mengelola informasi lengkap tentang barang yang tersedia di gudang.
- **Data Supplier:** Menyimpan dan mengelola informasi pemasok barang.
- **Purchasing Order:** Pencatatan dan manajemen pesanan pembelian barang.
- **Stock In:** Pencatatan penerimaan barang ke gudang.
- **Stock Out:** Pencatatan pengeluaran barang dari gudang.
- **Stock Harian:** Pemantauan stok harian secara detail.
- **Stock Bulanan:** Analisis stok secara bulanan untuk perencanaan.
- **Laporan Barang:** Pembuatan laporan detail tentang status dan pergerakan barang.
- **Laporan Supplier:** Laporan mengenai performa dan transaksi dengan pemasok.
- **Laporan Stock In:** Laporan tentang penerimaan barang ke gudang.
- **Laporan Stock Out:** Laporan tentang pengeluaran barang dari gudang.

**Teknologi yang Digunakan:**
- **Bahasa Pemrograman:** Java
- **Database:** MySQL (Konfigurasi koneksi diperlukan)
- **IDE:** NetBeans (disarankan)
- **Framework:** JavaFX untuk antarmuka pengguna (opsional)

**Persyaratan Sistem:**
- **JDK:** Java Development Kit (JDK) versi 22 atau lebih baru
- **IDE:** NetBeans IDE versi terbaru
- **Database Server:** MySQL versi 5.7 atau lebih baru
- **Sistem Operasi:** Windows, macOS, atau Linux

**Panduan Instalasi:**
1. **Clone Repository:**
   ```bash
   git clone https://github.com/fadlyfebros/InventoryGudang.git
2. **Import Project ke NetBeans:**
   - Buka NetBeans.
   - Pilih `File > Open Project`.
   - Arahkan ke folder hasil clone repository dan buka.
3. **Konfigurasi Database:**
   - Buat database baru di MySQL dengan nama `project_inventory`.
   - Import skema database dari file `project_inventory.sql` yang terdapat di dalam folder `SQL`.
   - Sesuaikan konfigurasi koneksi database di file `src/koneksi/Koneksi.java`.
4. **Jalankan Aplikasi:**
   - Pilih proyek di NetBeans.
   - Klik kanan dan pilih `Run`.

**Penggunaan:**
1. **Login:**
   - Masukkan username dan password yang telah terdaftar.
2. **Dashboard:**
   - Melihat ringkasan stok ban dan aktivitas terakhir.
3. **Manajemen Stok:**
   - Menambah, mengurangi, dan memperbarui data stok ban.
4. **Laporan:**
   - Generate laporan stok dan transaksi.
