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

    public class formPurchase extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    /**
     * Creates new form formPurchase
     */
    public formPurchase() {
        txtSearch = new JTextField("Pencarian . . .");
        tblPurchase = new JTable();
        dateDueDate = new JDateChooser();
        comboKodeBarang = new JComboBox<>();
        comboKodeSupplier = new JComboBox<>();
        txtKodePurchase = new JTextField();
        txtNamaSupplier = new JTextField();
        txtNamaBarang = new JTextField();
        txtQty = new JTextField();
        mainpanel = new JPanel();
        tambahPurchase = new JPanel();

        initComponents();
        autonumber();
        loadData();
        loadComboKodeBarang();
        loadComboKodeSupplier();

        txtKodePurchase.setEnabled(false);
        txtNamaSupplier.setEnabled(false);
        txtNamaBarang.setEnabled(false);

        initSearchField();

         lblPencarian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPencarianMouseClicked(evt);
            }
        });
         // overriding kodebarang
        comboKodeBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNamaBarang();
            }
        });
        // overriding combokODESupplier
        comboKodeSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNamaSupplier();
            }
        });
    }
    private void lblPencarianMouseClicked(java.awt.event.MouseEvent evt) {
        searchPurchase();
    }
    private void savePurchase() {
        String kodePurchase = txtKodePurchase.getText();
        String kodeSupplier = (String) comboKodeSupplier.getSelectedItem();
        String namaSupplier = txtNamaSupplier.getText();
        String kodeBarang = (String) comboKodeBarang.getSelectedItem();
        String namaBarang = txtNamaBarang.getText();
        java.util.Date dueDate = dateDueDate.getDate();
        int qty = 0;

        // Validasi Kode Supplier
        if ("Pilih Kode Supplier".equals(kodeSupplier)) {
            JOptionPane.showMessageDialog(this, "Pilih Kode Supplier terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi Kode Barang
        if ("Pilih Kode Barang".equals(kodeBarang)) {
            JOptionPane.showMessageDialog(this, "Pilih Kode Barang terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi Tanggal Due Date
        if (dueDate == null) {
            JOptionPane.showMessageDialog(this, "Masukkan Tanggal terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi Qty
        String qtyText = txtQty.getText().trim();
        if (qtyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan Qty terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi bahwa Qty merupakan angka
        try {
            qty = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check if the supplier exists
            String checkSupplierSql = "SELECT * FROM tb_suplier WHERE kodeSuplier = ?";
            try (PreparedStatement checkStmt = c.prepareStatement(checkSupplierSql)) {
                checkStmt.setString(1, kodeSupplier);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Kode Supplier tidak valid", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String sql = "INSERT INTO tb_purchase (`PO No`, `Kode Supplier`, `Nama Supplier`, `Kode Barang`, `Nama Barang`, `Due Date Order`, `Qty Order`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, kodePurchase);
                p.setString(2, kodeSupplier);
                p.setString(3, namaSupplier);
                p.setString(4, kodeBarang);
                p.setString(5, namaBarang);
                p.setDate(6, new java.sql.Date(dueDate.getTime()));
                p.setInt(7, qty);

                int inserted = p.executeUpdate();
                if (inserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                    loadData(); // Memuat ulang tabel setelah penyimpanan berhasil
                    clearFields(); // Mengosongkan input setelah penyimpanan berhasil
                    autonumber(); // Mengatur ulang nomor otomatis
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePurchase() {
        String kodePurchase = txtKodePurchase.getText();
        String kodeSupplier = (String) comboKodeSupplier.getSelectedItem();
        String namaSupplier = txtNamaSupplier.getText();
        String kodeBarang = (String) comboKodeBarang.getSelectedItem();
        String namaBarang = txtNamaBarang.getText();
        java.util.Date dueDate = dateDueDate.getDate();
        int qty = 0;

        // Validasi tanggal
        if (dueDate == null) {
            JOptionPane.showMessageDialog(this, "Masukkan tanggal terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi qty
        String qtyText = txtQty.getText().trim();
        if (qtyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan QTY terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi bahwa qty merupakan angka
        try {
            qty = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check if the supplier exists
            String checkSupplierSql = "SELECT * FROM tb_suplier WHERE kodeSuplier = ?";
            try (PreparedStatement checkStmt = c.prepareStatement(checkSupplierSql)) {
                checkStmt.setString(1, kodeSupplier);
                ResultSet rs = checkStmt.executeQuery();
                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Kode Supplier tidak valid", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String sql = "UPDATE tb_purchase SET `Kode Supplier` = ?, `Nama Supplier` = ?, `Kode Barang` = ?, `Nama Barang` = ?, `Due Date Order` = ?, `Qty Order` = ? WHERE `PO No` = ?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, kodeSupplier);
                p.setString(2, namaSupplier);
                p.setString(3, kodeBarang);
                p.setString(4, namaBarang);
                p.setDate(5, new java.sql.Date(dueDate.getTime()));
                p.setInt(6, qty);
                p.setString(7, kodePurchase);

                int updated = p.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil diupdate");
                    loadData(); // Memuat ulang tabel setelah update berhasil
                    clearFields(); // Mengosongkan input setelah update berhasil
                    btnSimpanPurchase.setText("Save"); // Kembalikan teks tombol ke "Save" setelah update berhasil

                    // Pindah kembali ke panel dataPurchase
                    mainpanel.removeAll();
                    mainpanel.repaint();
                    mainpanel.revalidate();
                    mainpanel.add(dataPurchase);
                    mainpanel.repaint();
                    mainpanel.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengupdate data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                searchPurchase();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchPurchase();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchPurchase();
            }
        });
    }
    
    private void searchPurchase() {
        String keyword = txtSearch.getText();
        if (keyword.equals("Pencarian . . .")) {
            loadData();
            return;
        }

        DefaultTableModel model = new DefaultTableModel(new String[]{"PO No", "Kode Supplier", "Nama Supplier", "Kode Barang", "Nama Barang", "Due Date Order", "Qty Order"}, 0);
        String sql = "SELECT * FROM tb_purchase WHERE `Kode Supplier` LIKE ? OR `Nama Supplier` LIKE ?";

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, "%" + keyword + "%");
            p.setString(2, "%" + keyword + "%");

            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    String PO = r.getString("PO No");
                    String kodeSupplier = r.getString("Kode Supplier");
                    String namaSupplier = r.getString("Nama Supplier");
                    String kodeBarang = r.getString("Kode Barang");
                    String namaBarang = r.getString("Nama Barang");
                    Date dueDate = r.getDate("Due Date Order");
                    int Qty = r.getInt("Qty Order");
                    model.addRow(new Object[]{PO, kodeSupplier, namaSupplier, kodeBarang, namaBarang, dueDate, Qty});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        tblPurchase.setModel(model);
    }
    private void autonumber() {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement()) {
            String sql = "SELECT `PO No` FROM tb_purchase ORDER BY `PO No` DESC LIMIT 1";
            try (ResultSet r = s.executeQuery(sql)) {
                if (r.next()) {
                    String PONo = r.getString("PO No");
                    int id = Integer.parseInt(PONo.substring(2)) + 1;
                    String kode = String.format("PA%07d", id);
                    txtKodePurchase.setText(kode);
                } else {
                    txtKodePurchase.setText("PA0100001");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    private void loadData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"PO No", "Kode Supplier", "Nama Supplier", "Kode Barang", "Nama Barang", "Due Date Order", "Qty Order"}, 0);
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM tb_purchase")) {
            while (r.next()) {
                String PO = r.getString("PO No");
                String kodeSupplier = r.getString("Kode Supplier");
                String namaSupplier = r.getString("Nama Supplier");
                String kodeBarang = r.getString("Kode Barang");
                String namaBarang = r.getString("Nama Barang");
                Date dueDate = r.getDate("Due Date Order");
                int Qty = r.getInt("Qty Order");
                model.addRow(new Object[]{PO, kodeSupplier, namaSupplier, kodeBarang, namaBarang, dueDate, Qty});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        tblPurchase.setModel(model);
    }


    private void loadComboKodeBarang() {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT kodeBarang FROM tb_barang")) {
            while (r.next()) {
                comboKodeBarang.addItem(r.getString("kodeBarang"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void loadComboKodeSupplier() {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT kodeSuplier FROM tb_suplier")) {
            while (r.next()) {
                comboKodeSupplier.addItem(r.getString("kodeSuplier"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void updateNamaBarang() {
        String kodeBarang = (String) comboKodeBarang.getSelectedItem();
        if (kodeBarang != null) {
            try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement p = c.prepareStatement("SELECT namaBarang FROM tb_barang WHERE kodeBarang = ?")) {
                p.setString(1, kodeBarang);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        txtNamaBarang.setText(r.getString("namaBarang"));
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    private void updateNamaSupplier() {
        String kodeSupplier = (String) comboKodeSupplier.getSelectedItem();
        if (kodeSupplier != null) {
            try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement p = c.prepareStatement("SELECT namaSuplier FROM tb_suplier WHERE kodeSuplier = ?")) {
                p.setString(1, kodeSupplier);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        txtNamaSupplier.setText(r.getString("namaSuplier"));
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }
    private void clearFields() {
        txtNamaSupplier.setText("");
        txtNamaBarang.setText("");
        dateDueDate.setDate(null);
        txtQty.setText("");
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
        lblPencarian = new javax.swing.JLabel();
        tambahPurchase = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpanPurchase = new javax.swing.JButton();
        btnHapusPurchase = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKodePurchase = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        dateDueDate = new com.toedter.calendar.JDateChooser();
        comboKodeSupplier = new javax.swing.JComboBox<>();
        comboKodeBarang = new javax.swing.JComboBox<>();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        dataPurchase.setBackground(new java.awt.Color(255, 255, 255));

        tblPurchase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PO No", "Kode Supplier", "Nama Supplier", "Kode Barang", "Nama Barang", "Due Date Order", "Qty Order", "Due Date Receive", "Qty Receive"
            }
        ));
        jScrollPane1.setViewportView(tblPurchase);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Data Purchase");

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

        lblPencarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loupe.png"))); // NOI18N

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
                        .addComponent(lblPencarian)
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
                    .addComponent(lblPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainpanel.add(dataPurchase, "card2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Data Purchase");

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("PO No");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Kode Supplier");

        txtKodePurchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePurchaseActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Nama Supplier");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Kode Barang");

        txtNamaSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSupplierActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Nama Barang");

        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Qty Order");

        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Due Date Order");

        comboKodeSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Supplier" }));
        comboKodeSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboKodeSupplierActionPerformed(evt);
            }
        });

        comboKodeBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Barang" }));

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
                    .addComponent(dateDueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboKodeSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtKodePurchase)
                    .addComponent(txtQty)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKodePurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
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
        int row = tblPurchase.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String kodePurchase = (String) tblPurchase.getValueAt(row, 0);
        String kodeSupplier = (String) tblPurchase.getValueAt(row, 1);

        if (!canDeletePurchase(kodeSupplier)) {
            JOptionPane.showMessageDialog(this, "Data masih digunakan di tb_pembelian, tidak dapat dihapus", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM tb_purchase WHERE `PO No` = ?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, kodePurchase);
                int deleted = p.executeUpdate();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                    loadData(); // Memuat ulang tabel setelah penghapusan berhasil
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed
    private boolean canDeletePurchase(String kodeSupplier) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT COUNT(*) FROM tb_pembelian WHERE `Kode_Supplier` = ?";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setString(1, kodeSupplier);
                ResultSet rs = p.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    private void btnSimpanPurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPurchaseActionPerformed
        if (btnSimpanPurchase.getText().equals("Save")) {
            savePurchase();
        } else if (btnSimpanPurchase.getText().equals("Update")) {
            updatePurchase();
        }
    }//GEN-LAST:event_btnSimpanPurchaseActionPerformed

    private void btnHapusPurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPurchaseActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPurchase);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnHapusPurchaseActionPerformed

    private void txtKodePurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePurchaseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodePurchaseActionPerformed

    private void txtNamaSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSupplierActionPerformed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void comboKodeSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboKodeSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboKodeSupplierActionPerformed
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnHapusPurchase;
    private javax.swing.JButton btnSimpanPurchase;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JComboBox<String> comboKodeBarang;
    private javax.swing.JComboBox<String> comboKodeSupplier;
    private javax.swing.JPanel dataPurchase;
    private com.toedter.calendar.JDateChooser dateDueDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPencarian;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel tambahPurchase;
    private javax.swing.JTable tblPurchase;
    private javax.swing.JTextField txtKodePurchase;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}