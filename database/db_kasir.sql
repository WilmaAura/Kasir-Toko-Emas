-- Active: 1765006836152@@localhost@3306@kasir_toko_emas
CREATE DATABASE kasir_toko_emas;

use kasir_toko_emas;


CREATE TABLE table_user(
    id_user VARCHAR (10) PRIMARY KEY,
    nama_kasir VARCHAR (100),
    username VARCHAR (50) NOT NULL,
    password varchar(50),
    status ENUM ('Aktif', 'Nonaktif') DEFAULT 'Aktif'
);

CREATE TABLE emas (
    id_emas VARCHAR (10) PRIMARY KEY,
    jenis VARCHAR (100),
    kadar VARCHAR (10) NOT NULL,
    berat_gram DECIMAL(5,2) NOT NULL,
    biaya_produksi INT NOT NULL,
    STOK INT
);

CREATE TABLE pelanggan (
    id_pelanggan VARCHAR(10) PRIMARY KEY,
    nama_pelanggan VARCHAR(100),
    no_telp VARCHAR (15) NOT NULL,
    alamat TEXT
);

CREATE TABLE supplier (
    id_supplier VARCHAR(10) PRIMARY KEY,
    nama_supplier VARCHAR(100) NOT NULL,
    no_telp VARCHAR(15),
    alamat TEXT
);


CREATE TABLE penjualan_emas (
    id_penjualan VARCHAR(20) PRIMARY KEY,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_user VARCHAR(10),
    id_pelanggan VARCHAR(10),
    total_bayar INT,
    FOREIGN KEY (id_user) REFERENCES table_user(id_user),
    FOREIGN KEY (id_pelanggan) REFERENCES pelanggan(id_pelanggan)
);

CREATE TABLE detail_penjualan(
    id_detail varchar(10) PRIMARY KEY,
    id_penjualan varchar(20),
    id_emas varchar(10),
    harga_emas INT,
    subtotal INT,
    FOREIGN KEY (id_penjualan) REFERENCES penjualan_emas(id_penjualan),
    FOREIGN KEY (id_emas) REFERENCES emas(id_emas)
);

CREATE TABLE pembelian_emas(
    id_pembelian VARCHAR (20) PRIMARY KEY,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    jenis_pembelian ENUM ('KULAKAN', 'BUYBACK') NOT NULL,
    id_supplier VARCHAR(10),
    id_user VARCHAR (10),
    id_pelanggan VARCHAR(10),
    total_pengeluaran INT NOT NULL,
    FOREIGN KEY (id_supplier) REFERENCES supplier(id_supplier),
    FOREIGN KEY (id_user) REFERENCES table_user(id_user),
    FOREIGN KEY (id_pelanggan) REFERENCES pelanggan(id_pelanggan)
);

CREATE TABLE detail_pembelian(
    id_detail VARCHAR(10) PRIMARY KEY,
    id_pembelian VARCHAR(20),
    id_emas VARCHAR(10),
    harga_beli_perGr INT NOT NULL,
    berat_beli DECIMAL(5,2) NOT NULL,
    jumlah INT DEFAULT 1,
    subtotal INT NOT NULL,
    FOREIGN KEY (id_pembelian) REFERENCES pembelian_emas(id_pembelian),
    FOREIGN KEY (id_emas) REFERENCES emas(id_emas)
);

INSERT INTO table_user  
VALUES 
('USR001', 'Wilma', 'wilma', 'wilma123', 'Aktif'),
('USR002', 'Lili', 'lili', 'lili123', 'Aktif'),
('USR003', 'Budi Santoso', 'budi', 'budi123', 'Nonaktif');