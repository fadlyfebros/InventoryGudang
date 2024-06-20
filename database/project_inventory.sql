-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Jun 2024 pada 19.02
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project_inventory`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `login`
--

CREATE TABLE `login` (
  `id` varchar(50) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `tipeLogin` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `login`
--

INSERT INTO `login` (`id`, `username`, `password`, `tipeLogin`) VALUES
('AD001', 'admin', 'admin', 'admin'),
('AD002', 'Fadly', '12345678', 'Cashier'),
('AD003', 'Keisha', '12345678', 'Purchasing'),
('AD004', 'Fadly', '123456778', 'Purchasing'),
('AD005', 'Xaviant', 'bayarwoi', 'Purchasing'),
('AD006', 'Gilang', '12345678', 'Admin'),
('AD007', 'fadly', 'admin', 'Admin');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_barang`
--

CREATE TABLE `tb_barang` (
  `kodeBarang` varchar(30) NOT NULL,
  `namaBarang` varchar(30) NOT NULL,
  `berat` varchar(10) NOT NULL,
  `kodeSupplier` varchar(30) NOT NULL,
  `namaSupplier` varchar(40) NOT NULL,
  `StokAwal` int(11) DEFAULT NULL,
  `Satuan` varchar(10) NOT NULL,
  `hargaBeli` varchar(10) NOT NULL,
  `hargaJual` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_barang`
--

INSERT INTO `tb_barang` (`kodeBarang`, `namaBarang`, `berat`, `kodeSupplier`, `namaSupplier`, `StokAwal`, `Satuan`, `hargaBeli`, `hargaJual`) VALUES
('B-000000001', 'Wafer Tango', '21 Gram', 'SP-0000001', 'PT. MAYORA INDAH TBK.', 100, 'Pcs', '1000.0', '1200.0'),
('B-000000002', 'DIAMOND MILK FRESH LOW ', '946ml', 'SP-0000004', 'PT Diamond Food Indonesia Tbk ', 100, 'Pcs', '25000.0', '30000.0');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_member`
--

CREATE TABLE `tb_member` (
  `kodeMember` varchar(40) NOT NULL,
  `namaMember` varchar(40) NOT NULL,
  `alamat` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_member`
--

INSERT INTO `tb_member` (`kodeMember`, `namaMember`, `alamat`, `email`) VALUES
('M-0000001', 'Fadly Febro', 'Jln Pegangsaan Dua', 'fadly@gmail.com'),
('M-0000002', 'Reyhan', 'Jln Sunter Jaya', 'reyy@gmail.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_pembelian`
--

CREATE TABLE `tb_pembelian` (
  `PO_No` varchar(255) DEFAULT NULL,
  `Kode_Supplier` varchar(255) DEFAULT NULL,
  `Nama_Supplier` varchar(255) DEFAULT NULL,
  `Kode_Barang` varchar(255) DEFAULT NULL,
  `Nama_Barang` varchar(255) DEFAULT NULL,
  `Due_Date_Order` date DEFAULT NULL,
  `Qty_Order` int(11) DEFAULT NULL,
  `Due Date Receive` date NOT NULL,
  `Qty Receive` int(11) NOT NULL DEFAULT 0,
  `Total_Stock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_pembelian`
--

INSERT INTO `tb_pembelian` (`PO_No`, `Kode_Supplier`, `Nama_Supplier`, `Kode_Barang`, `Nama_Barang`, `Due_Date_Order`, `Qty_Order`, `Due Date Receive`, `Qty Receive`, `Total_Stock`) VALUES
('PA0100001', 'SP-0000001', 'PT. MAYORA INDAH TBK.', 'B-000000001', 'Wafer Tango', '2024-06-30', 100, '2024-06-20', 100, 100),
('PA0100001', 'SP-0000001', 'PT. MAYORA INDAH TBK.', 'B-000000001', 'Wafer Tango', '2024-06-30', 100, '2024-06-20', 346, 446),
('PA0100001', 'SP-0000001', 'PT. MAYORA INDAH TBK.', 'B-000000001', 'Wafer Tango', '2024-06-30', 100, '2024-06-20', 100, 200),
('PA0100002', 'SP-0000004', 'PT Diamond Food Indonesia Tbk', 'B-000000002', 'DIAMOND MILK FRESH LOW', '2024-06-30', 100, '2024-06-20', 200, 290);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_purchase`
--

CREATE TABLE `tb_purchase` (
  `PO No` varchar(30) NOT NULL,
  `Kode Supplier` varchar(30) DEFAULT NULL,
  `Nama Supplier` varchar(100) DEFAULT NULL,
  `Kode Barang` varchar(30) DEFAULT NULL,
  `Nama Barang` varchar(100) DEFAULT NULL,
  `Due Date Order` date DEFAULT NULL,
  `Qty Order` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_purchase`
--

INSERT INTO `tb_purchase` (`PO No`, `Kode Supplier`, `Nama Supplier`, `Kode Barang`, `Nama Barang`, `Due Date Order`, `Qty Order`) VALUES
('PA0100001', 'SP-0000001', 'PT. MAYORA INDAH TBK.', 'B-000000001', 'Wafer Tango', '2024-06-30', 100),
('PA0100002', 'SP-0000004', 'PT Diamond Food Indonesia Tbk ', 'B-000000002', 'DIAMOND MILK FRESH LOW ', '2024-06-30', 100);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_stockdaily`
--

CREATE TABLE `tb_stockdaily` (
  `kodeSupplier` varchar(255) NOT NULL,
  `NamaSupplier` varchar(255) DEFAULT NULL,
  `kodeBarang` varchar(50) DEFAULT NULL,
  `NamaBarang` varchar(255) DEFAULT NULL,
  `QtyStock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_stockdaily`
--

INSERT INTO `tb_stockdaily` (`kodeSupplier`, `NamaSupplier`, `kodeBarang`, `NamaBarang`, `QtyStock`) VALUES
('SP-0000001', 'PT. MAYORA INDAH TBK.', 'B-000000001', 'Wafer Tango', 446),
('SP-0000004', 'PT Diamond Food Indonesia Tbk ', 'B-000000002', 'DIAMOND MILK FRESH LOW ', 190);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_stockmonthly`
--

CREATE TABLE `tb_stockmonthly` (
  `kodeBarang` varchar(30) NOT NULL,
  `namaBarang` varchar(50) DEFAULT NULL,
  `kodeSupplier` varchar(30) DEFAULT NULL,
  `namaSupplier` varchar(50) DEFAULT NULL,
  `totalStockIn` int(11) DEFAULT NULL,
  `totalStockOut` int(11) DEFAULT NULL,
  `stockEnd` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_stockmonthly`
--

INSERT INTO `tb_stockmonthly` (`kodeBarang`, `namaBarang`, `kodeSupplier`, `namaSupplier`, `totalStockIn`, `totalStockOut`, `stockEnd`) VALUES
('B-000000001', 'Wafer Tango', 'SP-0000001', 'PT. MAYORA INDAH TBK.', 546, 100, 446),
('B-000000002', 'DIAMOND MILK FRESH LOW', 'SP-0000004', 'PT Diamond Food Indonesia Tbk', 200, 10, 190);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_stokout`
--

CREATE TABLE `tb_stokout` (
  `noinvoice` varchar(50) NOT NULL,
  `dueDate` date DEFAULT NULL,
  `kode_Barang` varchar(50) DEFAULT NULL,
  `nama_barang` varchar(100) DEFAULT NULL,
  `Qty` int(11) DEFAULT NULL,
  `Total_Harga` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_stokout`
--

INSERT INTO `tb_stokout` (`noinvoice`, `dueDate`, `kode_Barang`, `nama_barang`, `Qty`, `Total_Harga`) VALUES
('8.860.770.728', '2024-06-20', 'B-000000002', 'DIAMOND MILK FRESH LOW ', 10, 300000),
('9.406.339.897', '2024-06-20', 'B-000000001', 'Wafer Tango', 100, 120000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_suplier`
--

CREATE TABLE `tb_suplier` (
  `kodeSuplier` varchar(10) NOT NULL,
  `namaSuplier` varchar(225) NOT NULL,
  `alamatSuplier` varchar(225) NOT NULL,
  `noTelp` varchar(225) NOT NULL,
  `Email` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_suplier`
--

INSERT INTO `tb_suplier` (`kodeSuplier`, `namaSuplier`, `alamatSuplier`, `noTelp`, `Email`) VALUES
('SP-0000001', 'PT. MAYORA INDAH TBK.', 'Gedung Mayora Jl. Tomang Raya  Jakarta Barat', '+62 (21) 806 377 04', 'consumer@mayora.co.id'),
('SP-0000003', 'PT Pencari cinta Sejati', 'Keramat Jati', '+62 838 349 3929', 'customer@gmail.com'),
('SP-0000004', 'PT Diamond Food Indonesia Tbk ', 'Pegangsaan Dua', '086228378842', 'customer@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `tb_barang`
--
ALTER TABLE `tb_barang`
  ADD PRIMARY KEY (`kodeBarang`),
  ADD KEY `fk_tb_barang_tb_suplier` (`kodeSupplier`);

--
-- Indeks untuk tabel `tb_member`
--
ALTER TABLE `tb_member`
  ADD PRIMARY KEY (`kodeMember`);

--
-- Indeks untuk tabel `tb_pembelian`
--
ALTER TABLE `tb_pembelian`
  ADD KEY `fk_pembelian_supplier` (`Kode_Supplier`);

--
-- Indeks untuk tabel `tb_purchase`
--
ALTER TABLE `tb_purchase`
  ADD PRIMARY KEY (`PO No`),
  ADD KEY `Kode Supplier` (`Kode Supplier`),
  ADD KEY `Kode Barang` (`Kode Barang`);

--
-- Indeks untuk tabel `tb_stockdaily`
--
ALTER TABLE `tb_stockdaily`
  ADD PRIMARY KEY (`kodeSupplier`),
  ADD KEY `fk_barang` (`kodeBarang`);

--
-- Indeks untuk tabel `tb_stockmonthly`
--
ALTER TABLE `tb_stockmonthly`
  ADD PRIMARY KEY (`kodeBarang`),
  ADD KEY `fk_tb_stockmonthly_kodeSupplier` (`kodeSupplier`);

--
-- Indeks untuk tabel `tb_stokout`
--
ALTER TABLE `tb_stokout`
  ADD PRIMARY KEY (`noinvoice`),
  ADD KEY `fk_kode_barang` (`kode_Barang`);

--
-- Indeks untuk tabel `tb_suplier`
--
ALTER TABLE `tb_suplier`
  ADD PRIMARY KEY (`kodeSuplier`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_barang`
--
ALTER TABLE `tb_barang`
  ADD CONSTRAINT `fk_tb_barang_tb_suplier` FOREIGN KEY (`kodeSupplier`) REFERENCES `tb_suplier` (`kodeSuplier`);

--
-- Ketidakleluasaan untuk tabel `tb_pembelian`
--
ALTER TABLE `tb_pembelian`
  ADD CONSTRAINT `fk_pembelian_supplier` FOREIGN KEY (`Kode_Supplier`) REFERENCES `tb_purchase` (`Kode Supplier`);

--
-- Ketidakleluasaan untuk tabel `tb_purchase`
--
ALTER TABLE `tb_purchase`
  ADD CONSTRAINT `tb_purchase_ibfk_1` FOREIGN KEY (`Kode Supplier`) REFERENCES `tb_suplier` (`kodeSuplier`),
  ADD CONSTRAINT `tb_purchase_ibfk_2` FOREIGN KEY (`Kode Barang`) REFERENCES `tb_barang` (`kodeBarang`);

--
-- Ketidakleluasaan untuk tabel `tb_stockdaily`
--
ALTER TABLE `tb_stockdaily`
  ADD CONSTRAINT `fk_stockdaily_supplier` FOREIGN KEY (`kodeSupplier`) REFERENCES `tb_pembelian` (`Kode_Supplier`);

--
-- Ketidakleluasaan untuk tabel `tb_stockmonthly`
--
ALTER TABLE `tb_stockmonthly`
  ADD CONSTRAINT `fk_tb_stockmonthly_kodeSupplier` FOREIGN KEY (`kodeSupplier`) REFERENCES `tb_pembelian` (`Kode_Supplier`);

--
-- Ketidakleluasaan untuk tabel `tb_stokout`
--
ALTER TABLE `tb_stokout`
  ADD CONSTRAINT `fk_kode_barang` FOREIGN KEY (`kode_Barang`) REFERENCES `tb_barang` (`kodeBarang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
