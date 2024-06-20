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
    import java.sql.*;
    import javax.swing.table.TableRowSorter;

    public class formStockMonthly extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection conn;
    private DefaultTableModel tableModel;


    /**
     * Creates new form formPurchase
     */
    public formStockMonthly() {
        txtSearch = new JTextField("Pencarian . . .");
        connectDatabase();
        initComponents();
        loadComboBoxes();
        loadStockMonthlyData();
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

        txtSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txtSearch.getText().trim();
                searchStockMonthly(keyword);
            }
        });
    }
    private void searchStockMonthly(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblStockMonthly.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblStockMonthly.setRowSorter(sorter);
        if (keyword.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        }
    }
    private void loadStockMonthlyData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Kode Barang", "Nama Barang", "Kode Supplier", "Nama Supplier", "Total Stock In", "Total Stock Out", "Stock End"}, 0);
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM tb_stockmonthly")) {

            while (r.next()) {
                String kodeBarang = r.getString("kodeBarang");
                String namaBarang = r.getString("namaBarang");
                String kodeSupplier = r.getString("kodeSupplier");
                String namaSupplier = r.getString("namaSupplier");
                int totalStockIn = r.getInt("totalStockIn");
                int totalStockOut = r.getInt("totalStockOut");
                int stockEnd = r.getInt("stockEnd");
                model.addRow(new Object[]{kodeBarang, namaBarang, kodeSupplier, namaSupplier, totalStockIn, totalStockOut, stockEnd});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        tblStockMonthly.setModel(model);
    }
    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }
    private void loadComboBoxes() {
        // Load data into combo boxes from database tables
        loadSupplierComboBox();
        loadBarangComboBox();
    }
    private void loadSupplierComboBox() {
        // Load data into combo boxes from database tables
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
            System.err.println("Error loading supplier combo box: " + e.getMessage());
        }
    }

    private void loadBarangComboBox() {
        // Load data into combo boxes from database tables
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
        tblStockMonthly = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnTambahData = new javax.swing.JButton();
        btnHapusData = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tambahPurchase = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpanStockDaily = new javax.swing.JButton();
        btnBackPurchase = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        comboKodeSupplier = new javax.swing.JComboBox<>();
        comboKodeBarang = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtTotalStockIn = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtStockOut = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtStockEnd = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        dataPurchase.setBackground(new java.awt.Color(255, 255, 255));

        tblStockMonthly.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Kode Supplier", "Nama Supplier", "Total Stock In", "Total Stock Out", "Stock end"
            }
        ));
        jScrollPane1.setViewportView(tblStockMonthly);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Stock Monthly");

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
                        .addGroup(dataPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainpanel.add(dataPurchase, "card2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Stock Monthly");

        btnSimpanStockDaily.setText("Save");
        btnSimpanStockDaily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanStockDailyActionPerformed(evt);
            }
        });

        btnBackPurchase.setText("Back");
        btnBackPurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackPurchaseActionPerformed(evt);
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

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Total Stock In");

        txtTotalStockIn.setEnabled(false);
        txtTotalStockIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalStockInActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 102));
        jLabel12.setText("Total Stock Out");

        txtStockOut.setEnabled(false);
        txtStockOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockOutActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText("Stock End");

        txtStockEnd.setEnabled(false);
        txtStockEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockEndActionPerformed(evt);
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
                    .addComponent(txtTotalStockIn)
                    .addComponent(txtStockOut)
                    .addComponent(txtStockEnd)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
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
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotalStockIn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStockOut, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStockEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
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
                        .addComponent(btnSimpanStockDaily, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBackPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(btnSimpanStockDaily, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBackPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        int selectedRow = tblStockMonthly.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tblStockMonthly.getModel();
            String kodeBarang = (String) model.getValueAt(selectedRow, 0);

            try {
                String sql = "DELETE FROM tb_stockmonthly WHERE kodeBarang = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, kodeBarang);
                statement.executeUpdate();

                // Remove the row from the table model
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed

    private void btnSimpanStockDailyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanStockDailyActionPerformed
        try {
            // Validasi input pengguna
            if (comboKodeBarang.getSelectedItem() == null || comboKodeSupplier.getSelectedItem() == null ||
                txtNamaSupplier.getText().trim().isEmpty() || txtNamaBarang.getText().trim().isEmpty() ||
                txtTotalStockIn.getText().trim().isEmpty() || txtStockOut.getText().trim().isEmpty() ||
                txtStockEnd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua kolom harus diisi.", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
                // Insert into tb_stockmonthly or update if already exists
                String kodeBarang = (String) comboKodeBarang.getSelectedItem();
            String kodeSupplier = (String) comboKodeSupplier.getSelectedItem();
            String namaSupplier = txtNamaSupplier.getText().trim();
            String namaBarang = txtNamaBarang.getText().trim();
            int totalStockIn = Integer.parseInt(txtTotalStockIn.getText().trim());
            int totalStockOut = Integer.parseInt(txtStockOut.getText().trim());
            int stockEnd = Integer.parseInt(txtStockEnd.getText().trim());

             if (conn == null) {
                JOptionPane.showMessageDialog(this, "Koneksi ke database tidak tersedia.", "Kesalahan Koneksi", JOptionPane.ERROR_MESSAGE);
                return;
            }

                String sql = "INSERT INTO tb_stockmonthly (kodeBarang, namaBarang, kodeSupplier, namaSupplier, totalStockIn, totalStockOut, stockEnd) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE " +
                         "namaBarang = VALUES(namaBarang), " +
                         "namaSupplier = VALUES(namaSupplier), " +
                         "totalStockIn = VALUES(totalStockIn), " +
                         "totalStockOut = VALUES(totalStockOut), " +
                         "stockEnd = VALUES(stockEnd)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, kodeBarang);
            statement.setString(2, namaBarang);
            statement.setString(3, kodeSupplier);
            statement.setString(4, namaSupplier);
            statement.setInt(5, totalStockIn);
            statement.setInt(6, totalStockOut);
            statement.setInt(7, stockEnd);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Sukses", JOptionPane.INFORMATION_MESSAGE);


                // Optionally, update tblStockMonthly if it's a JTable

            } catch (SQLException e) {
            System.err.println("Error saving data to tb_stockmonthly: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Total Stock In, Stock Out, dan Stock End harus berupa angka.", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanStockDailyActionPerformed

    private void btnBackPurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackPurchaseActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPurchase);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnBackPurchaseActionPerformed

    private void txtNamaSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSupplierActionPerformed

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
        try {
            // Fetch Nama_Barang from tb_pembelian
            String sqlNamaBarang = "SELECT Nama_Barang FROM tb_pembelian WHERE Kode_Barang = ? LIMIT 1";
            PreparedStatement stmtNamaBarang = conn.prepareStatement(sqlNamaBarang);
            stmtNamaBarang.setString(1, kodeBarang);
            ResultSet rsNamaBarang = stmtNamaBarang.executeQuery();
            if (rsNamaBarang.next()) {
                String namaBarang = rsNamaBarang.getString("Nama_Barang");
                txtNamaBarang.setText(namaBarang);
            }
            rsNamaBarang.close();

            // Calculate txtTotalStockIn from tb_pembelian
            String sqlPembelian = "SELECT SUM(`Qty Receive`) AS totalPembelian FROM tb_pembelian WHERE Kode_Barang = ?";
            PreparedStatement stmtPembelian = conn.prepareStatement(sqlPembelian);
            stmtPembelian.setString(1, kodeBarang);
            ResultSet rsPembelian = stmtPembelian.executeQuery();
            int totalPembelian = 0;
            if (rsPembelian.next()) {
                totalPembelian = rsPembelian.getInt("totalPembelian");
            }
            rsPembelian.close();

            // Calculate txtStockOut from tb_stokout
            String sqlStokOut = "SELECT SUM(Qty) AS totalStokOut FROM tb_stokout WHERE kode_Barang = ?";
            PreparedStatement stmtStokOut = conn.prepareStatement(sqlStokOut);
            stmtStokOut.setString(1, kodeBarang);
            ResultSet rsStokOut = stmtStokOut.executeQuery();
            int totalStokOut = 0;
            if (rsStokOut.next()) {
                totalStokOut = rsStokOut.getInt("totalStokOut");
            }
            rsStokOut.close();

            // Set txtTotalStockIn, txtStockOut, and txtStockEnd
            txtTotalStockIn.setText(String.valueOf(totalPembelian));
            txtStockOut.setText(String.valueOf(totalStokOut));
            txtStockEnd.setText(String.valueOf(totalPembelian - totalStokOut));

        } catch (SQLException e) {
            System.err.println("Error calculating stock: " + e.getMessage());
        }
    }//GEN-LAST:event_comboKodeBarangActionPerformed

    private void txtTotalStockInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalStockInActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalStockInActionPerformed

    private void txtStockOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockOutActionPerformed

    private void txtStockEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockEndActionPerformed
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackPurchase;
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnSimpanStockDaily;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JComboBox<String> comboKodeBarang;
    private javax.swing.JComboBox<String> comboKodeSupplier;
    private javax.swing.JPanel dataPurchase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel tambahPurchase;
    private javax.swing.JTable tblStockMonthly;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStockEnd;
    private javax.swing.JTextField txtStockOut;
    private javax.swing.JTextField txtTotalStockIn;
    // End of variables declaration//GEN-END:variables
}