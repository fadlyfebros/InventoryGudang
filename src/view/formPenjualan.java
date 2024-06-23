package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author GILANG RUBIYANA
 */
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class formPenjualan extends javax.swing.JPanel {
     private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    /**
     * Creates new form formPenjualan
     */
    public formPenjualan() {
        txtSearch = new JTextField("Pencarian . . .");
        tblpenjualan = new JTable();
        initComponents();
        loadData();
        autonumber();
        initSearchField();
        setTanggalOtomatis();
        loadComboKodeBarang();
        txtNoInvoice.setEnabled(false);
        txtDueDate.setEnabled(false);
        txtNamaBarang.setEnabled(false);
        txtHargaBarang.setEnabled(false);
        txtTotalPembayaran.setEnabled(false);

        comboKodeBarang.addActionListener(e -> fillBarangName());

        // Tambahkan listener untuk Qty
        txtQty.addActionListener(e -> calculateTotalPayment());
    }
    private void loadComboKodeBarang() {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT kodeBarang FROM tb_barang")) {
            comboKodeBarang.removeAllItems();
            comboKodeBarang.addItem("Pilih Kode Barang");

            while (r.next()) {
                comboKodeBarang.addItem(r.getString("kodeBarang"));
            }

            if (comboKodeBarang.getItemCount() == 1) {
                JOptionPane.showMessageDialog(this, "Tidak ada data kode barang yang ditemukan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data into combo box: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setTanggalOtomatis() {
        DateFormat tanggal = new SimpleDateFormat("yyyy-MM-dd");
        String htgl = tanggal.format(Calendar.getInstance().getTime());
        txtDueDate.setText(htgl);
    }

    private void fillBarangName() {
        Object selectedKodeBarang = comboKodeBarang.getSelectedItem();
        if (selectedKodeBarang instanceof String && !selectedKodeBarang.equals("Pilih Kode Barang")) {
            String kodeBarang = (String) selectedKodeBarang;
            try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT namaBarang, hargaJual FROM tb_barang WHERE kodeBarang = ?";
                try (PreparedStatement p = c.prepareStatement(sql)) {
                    p.setString(1, kodeBarang);
                    try (ResultSet r = p.executeQuery()) {
                        if (r.next()) {
                            String namaBarang = r.getString("namaBarang");
                            int hargaJual = r.getInt("hargaJual");
                            txtNamaBarang.setText(namaBarang);
                            txtHargaBarang.setText(String.valueOf(hargaJual));
                        } else {
                            txtNamaBarang.setText("");
                            txtHargaBarang.setText("");
                            JOptionPane.showMessageDialog(this, "Kode Barang tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    private void calculateTotalPayment() {
        try {
            int qty = Integer.parseInt(txtQty.getText());
            int hargaBarang = Integer.parseInt(txtHargaBarang.getText());
            int totalPembayaran = qty * hargaBarang;
            txtTotalPembayaran.setText(String.valueOf(totalPembayaran));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty dan Harga Barang harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //instanceof saveData
    private void saveData() {
        String dueDateStr = txtDueDate.getText();
        Object selectedKodeBarang = comboKodeBarang.getSelectedItem();
        String kodeBarang = null;

        if (selectedKodeBarang instanceof String) {
            kodeBarang = (String) selectedKodeBarang;
        } else {
            JOptionPane.showMessageDialog(this, "Kode Barang harus dipilih", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String namaBarang = txtNamaBarang.getText();
        String noInvoice = txtNoInvoice.getText();
        String qtyStr = txtQty.getText();
        String totalHargaStr = txtTotalPembayaran.getText();

        if (dueDateStr.isEmpty() || kodeBarang.isEmpty() || namaBarang.isEmpty() || noInvoice.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi semua kolom data terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int qty;
        int totalHarga;

        try {
            qty = Integer.parseInt(qtyStr);
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Qty harus lebih dari 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            totalHarga = Integer.parseInt(totalHargaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Total Harga harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.sql.Date dueDate;
        try {
            dueDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr).getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isNoInvoiceExists(noInvoice)) {
            noInvoice = generateUniqueInvoiceNumber();
            txtNoInvoice.setText(noInvoice);
        }

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Update Total_Stock di tb_pembelian
            String updateStockSql = "UPDATE tb_pembelian SET `Total_Stock` = `Total_Stock` - ? WHERE `Kode_Barang` = ?";
            try (PreparedStatement updateStockStmt = c.prepareStatement(updateStockSql)) {
                updateStockStmt.setInt(1, qty);
                updateStockStmt.setString(2, kodeBarang);
                updateStockStmt.executeUpdate();
            }

            // Insert ke tb_stokout
            String insertSql = "INSERT INTO tb_stokout (noinvoice, dueDate, kode_Barang, nama_barang, Qty, Total_Harga) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = c.prepareStatement(insertSql)) {
                insertStmt.setString(1, noInvoice);
                insertStmt.setDate(2, dueDate);
                insertStmt.setString(3, kodeBarang);
                insertStmt.setString(4, namaBarang);
                insertStmt.setInt(5, qty);
                insertStmt.setInt(6, totalHarga);
                insertStmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
            }

            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isNoInvoiceExists(String invoiceNumber) {
        String sql = "SELECT COUNT(*) AS count FROM tb_stokout WHERE noinvoice = ?";
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, invoiceNumber);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    int count = r.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking existing invoice number: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    private void initSearchField() {
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Pencarian . . .")) {
                    txtSearch.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Pencarian . . .");
                }
            }
        });
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchpenjualan();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchpenjualan();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchpenjualan();
            }
        });
    }
    private void searchpenjualan() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty() || keyword.equals("Pencarian . . .")) {
            loadData();
            return;
        }
        DefaultTableModel model = new DefaultTableModel(new String[]{"No Invoice", "Kode Barang", "Nama Barang", "Due Date", "Qty", "Total Harga"}, 0);
        String sql = "SELECT * FROM tb_stokout WHERE noinvoice LIKE ? OR kode_Barang LIKE ? OR nama_barang LIKE ?";

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            p.setString(1, likeKeyword);
            p.setString(2, likeKeyword);
            p.setString(3, likeKeyword);

            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    String noInvoice = r.getString("noinvoice");
                    String kodeBarang = r.getString("kode_Barang");
                    String namaBarang = r.getString("nama_barang");
                    String dueDate = r.getString("dueDate");
                    int qty = r.getInt("Qty");
                    int totalHarga = r.getInt("Total_Harga");
                    model.addRow(new Object[]{noInvoice, kodeBarang, namaBarang, dueDate, qty, totalHarga});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        tblpenjualan.setModel(model);
    }
    private void autonumber() {
        String newInvoiceNumber = generateUniqueInvoiceNumber();
        txtNoInvoice.setText(newInvoiceNumber);
    }

    private String generateUniqueInvoiceNumber() {
        Random random = new Random();
        String randomInvoiceNumber;
        do {
            int randomNumber1 = random.nextInt(10); // Generate single digit random number between 0 and 9
            int randomNumber2 = random.nextInt(1000); // Generate random number between 0 and 999
            int randomNumber3 = random.nextInt(1000); // Generate random number between 0 and 999
            int randomNumber4 = random.nextInt(1000); // Generate random number between 0 and 999

            randomInvoiceNumber = String.format("%d.%03d.%03d.%03d", randomNumber1, randomNumber2, randomNumber3, randomNumber4);
        } while (isNoInvoiceExists(randomInvoiceNumber)); // Cek jika nomor invoice sudah ada di database

        return randomInvoiceNumber;
    }
    private void loadData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No Invoice");
        model.addColumn("Due Date");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Qty");
        model.addColumn("Total Harga");

        String sql = "SELECT * FROM tb_stokout";
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {
            while (r.next()) {
                model.addRow(new Object[]{
                        r.getString("noinvoice"),
                        r.getString("dueDate"),
                        r.getString("kode_Barang"),
                        r.getString("nama_barang"),
                        r.getInt("Qty"),
                        r.getInt("Total_Harga")
                });
            }
            tblpenjualan.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainpanel = new javax.swing.JPanel();
        dataPenjualan = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpenjualan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnTambahData = new javax.swing.JButton();
        btnHapusData = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tambahPenjualan = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatalData1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        comboKodeBarang = new javax.swing.JComboBox<>();
        txtDueDate = new javax.swing.JTextField();
        txtNoInvoice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtHargaBarang = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTotalPembayaran = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        dataPenjualan.setBackground(new java.awt.Color(255, 255, 255));

        tblpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No Invoice", "Due Date", "Kode Barang", "Nama Barang", "Harga Barang", "QTY", "Total Pembayaran"
            }
        ));
        jScrollPane1.setViewportView(tblpenjualan);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Stock out");
        jLabel1.setMaximumSize(new java.awt.Dimension(161, 29));
        jLabel1.setMinimumSize(new java.awt.Dimension(161, 29));
        jLabel1.setPreferredSize(new java.awt.Dimension(161, 29));

        btnTambahData.setText("Add");
        btnTambahData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahDataActionPerformed(evt);
            }
        });

        btnHapusData.setText("Delete");
        btnHapusData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusDataActionPerformed(evt);
            }
        });

        txtSearch.setText("Pencarian . . .");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loupe.png"))); // NOI18N

        javax.swing.GroupLayout dataPenjualanLayout = new javax.swing.GroupLayout(dataPenjualan);
        dataPenjualan.setLayout(dataPenjualanLayout);
        dataPenjualanLayout.setHorizontalGroup(
            dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataPenjualanLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dataPenjualanLayout.createSequentialGroup()
                        .addGroup(dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 919, Short.MAX_VALUE)
                            .addGroup(dataPenjualanLayout.createSequentialGroup()
                                .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)))
                        .addContainerGap())))
        );
        dataPenjualanLayout.setVerticalGroup(
            dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPenjualanLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataPenjualan, "card2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Stock Out");
        jLabel2.setMaximumSize(new java.awt.Dimension(161, 29));
        jLabel2.setMinimumSize(new java.awt.Dimension(161, 29));
        jLabel2.setPreferredSize(new java.awt.Dimension(161, 29));

        btnSimpan.setText("Save");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatalData1.setText("Back");
        btnBatalData1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalData1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Kode Barang");

        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Nama Barang");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Due Date");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("Qty");

        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        comboKodeBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Barang" }));

        txtDueDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDueDateActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("No. Invoice");

        txtHargaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaBarangActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 102));
        jLabel12.setText("Harga Barang");

        txtTotalPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPembayaranActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText("Total Pembayaran");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoInvoice)
                    .addComponent(txtDueDate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(comboKodeBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNamaBarang)
                    .addComponent(txtQty)
                    .addComponent(txtHargaBarang)
                    .addComponent(txtTotalPembayaran)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(46, 46, 46))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(36, 36, 36))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(61, 61, 61))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(31, 31, 31))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(99, 99, 99))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(30, 30, 30))
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(771, 771, 771)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNoInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(comboKodeBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHargaBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalPembayaran, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout tambahPenjualanLayout = new javax.swing.GroupLayout(tambahPenjualan);
        tambahPenjualan.setLayout(tambahPenjualanLayout);
        tambahPenjualanLayout.setHorizontalGroup(
            tambahPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahPenjualanLayout.createSequentialGroup()
                .addGroup(tambahPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tambahPenjualanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(tambahPenjualanLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(tambahPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tambahPenjualanLayout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatalData1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tambahPenjualanLayout.setVerticalGroup(
            tambahPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahPenjualanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tambahPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatalData1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(tambahPenjualan, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDataActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(tambahPenjualan);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnTambahDataActionPerformed

    private void btnHapusDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDataActionPerformed
        int selectedRow = tblpenjualan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblpenjualan.getModel();
        String noInvoice = model.getValueAt(selectedRow, 0).toString(); // Ubah sesuai dengan nama kolom yang benar

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "DELETE FROM tb_stokout WHERE noinvoice = ?"; // Sesuaikan dengan nama kolom yang benar di tb_stokout
                try (PreparedStatement p = c.prepareStatement(sql)) {
                    p.setString(1, noInvoice);
                    int deleted = p.executeUpdate();
                    if (deleted > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                        loadData(); // Memuat ulang data setelah penghapusan berhasil
                    } else {
                        JOptionPane.showMessageDialog(this, "Data dengan nomor invoice ini tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed
    //instaceof Jbutton
    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (evt.getSource() instanceof JButton) {
            JButton btn = (JButton) evt.getSource();
            if (btn.getText().equals("Add")) {
                // Lakukan operasi simpan data
                saveData();
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed
    
    private void btnBatalData1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalData1ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPenjualan);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnBatalData1ActionPerformed

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        int qtyStock = Integer.parseInt(txtQty.getText());
        // Ambil Kode Barang dari combo box atau dari mana pun yang diperlukan
        String kodeBarang = (String) comboKodeBarang.getSelectedItem();

        // Query untuk mengambil Qty Receive dari tb_pembelian
        String sql = "SELECT `Qty Receive` FROM tb_pembelian WHERE Kode_Barang = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kodeBarang);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int qtyReceive = rs.getInt("Qty Receive");

                // Cek kondisi Qty Stock versus Qty Receive
                if (qtyStock > qtyReceive) {
                    JOptionPane.showMessageDialog(this, "Stok Tidak Sesuai", "Error", JOptionPane.ERROR_MESSAGE);
                    // Clear or reset the fields as needed
                    txtQty.setText(""); // Clear Qty text field
                    txtQty.requestFocus(); // Set focus back to Qty field or handle focus as appropriate
                } else {
                    // Continue with other logic or actions if needed
                }
            } else {
                JOptionPane.showMessageDialog(this, "Data pembelian tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                // Handle if no data found, e.g., clear fields or reset UI state
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtDueDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDueDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDueDateActionPerformed

    private void txtHargaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBarangActionPerformed

    private void txtTotalPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPembayaranActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalData1;
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JComboBox<String> comboKodeBarang;
    private javax.swing.JPanel dataPenjualan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel tambahPenjualan;
    private javax.swing.JTable tblpenjualan;
    private javax.swing.JTextField txtDueDate;
    private javax.swing.JTextField txtHargaBarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNoInvoice;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTotalPembayaran;
    // End of variables declaration//GEN-END:variables
}