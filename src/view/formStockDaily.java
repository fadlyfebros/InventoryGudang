package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author GILANG RUBIYANA
 */

    import com.toedter.calendar.JDateChooser;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import javax.swing.*;
    import javax.swing.event.DocumentEvent;
    import javax.swing.event.DocumentListener;
    import javax.swing.table.DefaultTableModel;
    import java.awt.event.FocusAdapter;
    import java.awt.event.FocusEvent;
    import java.awt.event.FocusListener;
    import java.sql.*;
    import java.util.Calendar;

    public class formStockDaily extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection conn;


    /**
     * Creates new form formPurchase
     */
    public formStockDaily() {
        initComponents(); // Memanggil metode untuk menginisialisasi komponen GUI terlebih dahulu
        connectDatabase(); // Koneksi database
        loadComboBoxes(); // Memuat data comboboxes
        loadTableData(); // Memuat data tabel

        // Setelah initComponents() dipanggil, pastikan semua komponen sudah diinisialisasi dengan benar
        txtSearch.setText("Pencarian . . .");

        // Menambahkan listener untuk perubahan fokus pada txtSearch
        txtSearch.addFocusListener(new FocusListener() {
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

        txtSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndLoadData();
            }
        });
    }
    private void searchAndLoadData() {
        String keyword = txtSearch.getText().trim();

        try {
            String sql = "SELECT * FROM tb_stockdaily WHERE kodeSupplier LIKE ? OR NamaSupplier LIKE ? OR kodeBarang LIKE ? OR NamaBarang LIKE ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
            statement.setString(4, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) tblPurchase.getModel();
            tableModel.setRowCount(0);

            while (rs.next()) {
                String kodeSupplier = rs.getString("kodeSupplier");
                String namaSupplier = rs.getString("NamaSupplier");
                String kodeBarang = rs.getString("kodeBarang");
                String namaBarang = rs.getString("NamaBarang");
                int qtyStock = rs.getInt("QtyStock");

                Object[] row = {kodeSupplier, namaSupplier, kodeBarang, namaBarang, qtyStock};
                tableModel.addRow(row);
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading table data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error loading table data: " + ex.getMessage());
        }
    }
    private void loadTableData() {
        try {
            String sql = "SELECT * FROM tb_stockdaily";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) tblPurchase.getModel();
            tableModel.setRowCount(0);

            while (rs.next()) {
                String kodeSupplier = rs.getString("kodeSupplier");
                String namaSupplier = rs.getString("NamaSupplier");
                String kodeBarang = rs.getString("kodeBarang");
                String namaBarang = rs.getString("NamaBarang");
                int qtyStock = rs.getInt("QtyStock");

                Object[] row = {kodeSupplier, namaSupplier, kodeBarang, namaBarang, qtyStock};
                tableModel.addRow(row);
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading table data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error loading table data: " + e.getMessage());
        }
    }
    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to database: " + e.getMessage(), "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }
    private void loadComboBoxes() {
        loadSupplierComboBox();
        loadBarangComboBox();
    }

    private void loadSupplierComboBox() {
        try {
            String sql = "SELECT kodeSuplier FROM tb_suplier";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String kodeSupplier = rs.getString("kodeSuplier");
                comboKodeSupplier.addItem(kodeSupplier);
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading supplier combo box: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error loading supplier combo box: " + e.getMessage());
        }
    }

    private void loadBarangComboBox() {
        try {
            String sql = "SELECT kodeBarang FROM tb_barang";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String kodeBarang = rs.getString("kodeBarang");
                comboKodeBarang.addItem(kodeBarang);
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading barang combo box: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error loading barang combo box: " + e.getMessage());
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
        dataPurchase = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPurchase = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnTambahData = new javax.swing.JButton();
        btnHapusData = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tambahPurchase = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpanPurchase = new javax.swing.JButton();
        btnHapusPurchase = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtQtyStock = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        comboKodeSupplier = new javax.swing.JComboBox<>();
        comboKodeBarang = new javax.swing.JComboBox<>();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        dataPurchase.setBackground(new java.awt.Color(255, 255, 255));

        tblPurchase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Kode Supplier", "Nama Supplier", "Qty Stock"
            }
        ));
        jScrollPane1.setViewportView(tblPurchase);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Stock Daily");

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

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loupe.png"))); // NOI18N

        javax.swing.GroupLayout dataPurchaseLayout = new javax.swing.GroupLayout(dataPurchase);
        dataPurchase.setLayout(dataPurchaseLayout);
        dataPurchaseLayout.setHorizontalGroup(
            dataPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPurchaseLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataPurchaseLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dataPurchaseLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(dataPurchaseLayout.createSequentialGroup()
                        .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addGap(35, 35, 35))))
        );
        dataPurchaseLayout.setVerticalGroup(
            dataPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPurchaseLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(dataPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataPurchaseLayout.createSequentialGroup()
                        .addGroup(dataPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dataPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dataPurchaseLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainpanel.add(dataPurchase, "card2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Stock Daily");

        btnSimpanPurchase.setText("Save");
        btnSimpanPurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPurchaseActionPerformed(evt);
            }
        });

        btnHapusPurchase.setText("Back");
        btnHapusPurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPurchaseActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Kode Supplier");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Nama Supplier");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Kode Barang");

        txtNamaSupplier.setEnabled(false);
        txtNamaSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSupplierActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Nama Barang");

        txtQtyStock.setEnabled(false);
        txtQtyStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyStockActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Qty Stock");

        txtNamaBarang.setEnabled(false);
        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        comboKodeSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Supplier" }));
        comboKodeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboKodeSupplierActionPerformed(evt);
            }
        });

        comboKodeBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Barang" }));
        comboKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboKodeBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboKodeBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNamaBarang)
                    .addComponent(txtNamaSupplier)
                    .addComponent(comboKodeSupplier, 0, 905, Short.MAX_VALUE)
                    .addComponent(txtQtyStock)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(comboKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQtyStock, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tambahPurchaseLayout = new javax.swing.GroupLayout(tambahPurchase);
        tambahPurchase.setLayout(tambahPurchaseLayout);
        tambahPurchaseLayout.setHorizontalGroup(
            tambahPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahPurchaseLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(tambahPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(tambahPurchaseLayout.createSequentialGroup()
                        .addComponent(btnSimpanPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(699, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahPurchaseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tambahPurchaseLayout.setVerticalGroup(
            tambahPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahPurchaseLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tambahPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(tambahPurchase, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDataActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(tambahPurchase);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnTambahDataActionPerformed

    private void btnHapusDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDataActionPerformed
        int selectedRow = tblPurchase.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblPurchase.getModel();
        String kodeSupplier = model.getValueAt(selectedRow, 0).toString();
        String kodeBarang = model.getValueAt(selectedRow, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "DELETE FROM tb_stockdaily WHERE kodeSupplier = ? AND kodeBarang = ?";
                try (PreparedStatement p = c.prepareStatement(sql)) {
                    p.setString(1, kodeSupplier);
                    p.setString(2, kodeBarang);

                    int deleted = p.executeUpdate();
                    if (deleted > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                        loadTableData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Data dengan kode supplier dan kode barang ini tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed

    private void btnSimpanPurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPurchaseActionPerformed
        String kodeSupplier = (String) comboKodeSupplier.getSelectedItem();
        String namaSupplier = txtNamaSupplier.getText();
        String kodeBarang = (String) comboKodeBarang.getSelectedItem();
        String namaBarang = txtNamaBarang.getText();
        int qtyStock = Integer.parseInt(txtQtyStock.getText());

        try {
            String sql = "INSERT INTO tb_stockdaily (kodeSupplier, NamaSupplier, kodeBarang, NamaBarang, QtyStock) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, kodeSupplier);
            statement.setString(2, namaSupplier);
            statement.setString(3, kodeBarang);
            statement.setString(4, namaBarang);
            statement.setInt(5, qtyStock);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            clearInputFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error saving data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSimpanPurchaseActionPerformed
    private void clearInputFields() {
        comboKodeSupplier.setSelectedIndex(0);
        txtNamaSupplier.setText("");
        comboKodeBarang.setSelectedIndex(0);
        txtNamaBarang.setText("");
        txtQtyStock.setText("");
    }
    private void btnHapusPurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPurchaseActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPurchase);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnHapusPurchaseActionPerformed

    private void txtNamaSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSupplierActionPerformed

    private void txtQtyStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyStockActionPerformed

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void comboKodeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboKodeSupplierActionPerformed
        String kodeSupplier = (String) comboKodeSupplier.getSelectedItem();
        try {
            String sql = "SELECT namaSuplier FROM tb_suplier WHERE kodeSuplier = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, kodeSupplier);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String namaSupplier = rs.getString("namaSuplier");
                txtNamaSupplier.setText(namaSupplier);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error loading supplier name: " + e.getMessage());
        }
    }//GEN-LAST:event_comboKodeSupplierActionPerformed

    private void comboKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboKodeBarangActionPerformed
        String kodeBarang = (String) comboKodeBarang.getSelectedItem();
        int stockPagi = 0; // Stock terakhir tgl. Kemaren
        int totalPembelian = 0;
        int totalStokOut = 0;

        try {
            // Ambil Stock terakhir tgl. Kemaren dari tb_pembelian
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            java.util.Date yesterday = cal.getTime();
            java.sql.Date sqlYesterday = new java.sql.Date(yesterday.getTime()); // Konversi java.util.Date ke java.sql.Date

            String sqlStockPagi = "SELECT SUM(`Qty Receive`) AS stockPagi FROM tb_pembelian WHERE Kode_Barang = ? AND `Due Date Receive` < ?";
            PreparedStatement stmtStockPagi = conn.prepareStatement(sqlStockPagi);
            stmtStockPagi.setString(1, kodeBarang);
            stmtStockPagi.setDate(2, sqlYesterday);
            ResultSet rsStockPagi = stmtStockPagi.executeQuery();
            if (rsStockPagi.next()) {
                stockPagi = rsStockPagi.getInt("stockPagi");
            }
            rsStockPagi.close();

            // Hitung total pembelian (Stock in) dari tb_pembelian
            String sqlPembelian = "SELECT SUM(`Qty Receive`) AS totalPembelian FROM tb_pembelian WHERE Kode_Barang = ? AND `Due Date Receive` >= ?";
            PreparedStatement stmtPembelian = conn.prepareStatement(sqlPembelian);
            stmtPembelian.setString(1, kodeBarang);
            stmtPembelian.setDate(2, sqlYesterday); // Pembelian mulai dari hari ini
            ResultSet rsPembelian = stmtPembelian.executeQuery();
            if (rsPembelian.next()) {
                totalPembelian = rsPembelian.getInt("totalPembelian");
            }
            rsPembelian.close();

            // Hitung total stok keluar (Stock out) dari tb_stokout
            String sqlStokOut = "SELECT SUM(Qty) AS totalStokOut FROM tb_stokout WHERE kode_Barang = ? AND dueDate >= ?";
            PreparedStatement stmtStokOut = conn.prepareStatement(sqlStokOut);
            stmtStokOut.setString(1, kodeBarang);
            stmtStokOut.setDate(2, sqlYesterday); // Stok keluar mulai dari hari ini
            ResultSet rsStokOut = stmtStokOut.executeQuery();
            if (rsStokOut.next()) {
                totalStokOut = rsStokOut.getInt("totalStokOut");
            }
            rsStokOut.close();

            // Hitung QtyStock berdasarkan rumus Stock Pagi + Stock in - Stock out
            int qtyStock = stockPagi + totalPembelian - totalStokOut;
            txtQtyStock.setText(String.valueOf(qtyStock));

            // Load NamaBarang
            String sqlBarang = "SELECT namaBarang FROM tb_barang WHERE kodeBarang = ?";
            PreparedStatement statement = conn.prepareStatement(sqlBarang);
            statement.setString(1, kodeBarang);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String namaBarang = rs.getString("namaBarang");
                txtNamaBarang.setText(namaBarang);
            }
            rs.close();

        } catch (SQLException e) {
            System.err.println("Error loading barang name or calculating stock: " + e.getMessage());
        }
    }//GEN-LAST:event_comboKodeBarangActionPerformed
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnHapusPurchase;
    private javax.swing.JButton btnSimpanPurchase;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JComboBox<String> comboKodeBarang;
    private javax.swing.JComboBox<String> comboKodeSupplier;
    private javax.swing.JPanel dataPurchase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel tambahPurchase;
    private javax.swing.JTable tblPurchase;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtQtyStock;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}