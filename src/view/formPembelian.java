package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author GILANG RUBIYANA
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class formPembelian extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    /**
     * Creates new form formPembelian
     */
    public formPembelian() {
        txtSearch = new JTextField("Pencarian . . .");
        tblpembelian = new JTable();
        
        initComponents();
        loadData();
        loadPoNo();
        setTanggalOtomatis();
        // Disable text fields initially
        disableTextFields();
        // Listener untuk combo box PO No
        comboPoNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ambil PO No yang dipilih
                String selectedPoNo = (String) comboPoNo.getSelectedItem();
                if (selectedPoNo != null) {
                    loadPurchaseData(selectedPoNo);
                }
            }
        });
    }
    private void disableTextFields() {
        txtNamaBarang.setEnabled(false);
        txtNamaSuplier.setEnabled(false);
        txtDueDateRec.setEnabled(false);
        txtKodeBarang.setEnabled(false);
        txtKodeSupplier.setEnabled(false);
        txtDateOrder.setEnabled(false);
        txtQty.setEnabled(false);
    }
    private void loadPurchaseData(String poNo) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement("SELECT * FROM tb_purchase WHERE `PO No` = ?")) {
            p.setString(1, poNo);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    // Ambil data dari hasil kueri
                    String kodeSupplier = r.getString("Kode Supplier");
                    String namaSupplier = r.getString("Nama Supplier");
                    String kodeBarang = r.getString("Kode Barang");
                    String namaBarang = r.getString("Nama Barang");
                    Date dueDateOrder = r.getDate("Due Date Order");
                    int qtyOrder = r.getInt("Qty Order");

                    // Set nilai ke dalam JTextField yang sesuai
                    txtKodeSupplier.setText(kodeSupplier);
                    txtNamaSuplier.setText(namaSupplier);
                    txtKodeBarang.setText(kodeBarang);
                    txtNamaBarang.setText(namaBarang);
                    txtDateOrder.setText(formatDate(dueDateOrder));
                    txtQty.setText(String.valueOf(qtyOrder));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    private void setTanggalOtomatis() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        txtDueDateRec.setText(currentDate);
    }

    private void loadPoNo() {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT `PO No` FROM tb_purchase")) {
            while (r.next()) {
                comboPoNo.addItem(r.getString("PO No"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    private void initSearchField() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { search(txtSearch.getText()); }
            public void removeUpdate(DocumentEvent e) { search(txtSearch.getText()); }
            public void insertUpdate(DocumentEvent e) { search(txtSearch.getText()); }
        });
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
    }

    private void search(String keyword) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Stok In", "PO No", "Line", "Kode Supplier", "Nama Supplier", "Kode Barang", "Nama Barang", "Due Date", "Qty", "Rec"}, 0);
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement("SELECT * FROM tb_pembelian WHERE `Stok_In` LIKE ? OR `PO_No` LIKE ? OR `Line` LIKE ? OR `Kode_Supplier` LIKE ? OR `Nama_Supplier` LIKE ? OR `Kode_Barang` LIKE ? OR `Nama_Barang` LIKE ? OR `Due_Date` LIKE ? OR `Qty` LIKE ? OR `Rec` LIKE ?")) {
            String query = "%" + keyword + "%";
            for (int i = 1; i <= 10; i++) {
                p.setString(i, query);
            }
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    model.addRow(new Object[]{
                            r.getInt("Stok_In"),
                            r.getString("PO_No"),
                            r.getInt("Line"),
                            r.getString("Kode_Supplier"),
                            r.getString("Nama_Supplier"),
                            r.getString("Kode_Barang"),
                            r.getString("Nama_Barang"),
                            r.getDate("Due_Date"),
                            r.getInt("Qty"),
                            r.getString("Rec")
                    });
                }
                tblpembelian.setModel(model);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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
        dataPembelian = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpembelian = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnTambahData = new javax.swing.JButton();
        btnHapusData = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tambahPembelian = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpanData = new javax.swing.JButton();
        btnBatalData1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNamaSuplier = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDueDateRec = new javax.swing.JTextField();
        comboPoNo = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtRec = new javax.swing.JTextField();
        txtKodeSupplier = new javax.swing.JTextField();
        txtKodeBarang = new javax.swing.JTextField();
        txtDateOrder = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        mainpanel.setLayout(new java.awt.CardLayout());

        dataPembelian.setBackground(new java.awt.Color(255, 255, 255));

        tblpembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PO No", "Kode Supplier", "Nama Supplier", "Kode Barang", "Nama Barang", "Due Date Order", "Qty Order", "Due Date Receive", "Qty Receive", "Total Stock"
            }
        ));
        jScrollPane1.setViewportView(tblpembelian);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Stock In");
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

        javax.swing.GroupLayout dataPembelianLayout = new javax.swing.GroupLayout(dataPembelian);
        dataPembelian.setLayout(dataPembelianLayout);
        dataPembelianLayout.setHorizontalGroup(
            dataPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPembelianLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataPembelianLayout.createSequentialGroup()
                        .addGroup(dataPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
                            .addGroup(dataPembelianLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(dataPembelianLayout.createSequentialGroup()
                        .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(24, 24, 24))))
        );
        dataPembelianLayout.setVerticalGroup(
            dataPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPembelianLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(dataPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(dataPembelian, "card2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Stok In");
        jLabel2.setMaximumSize(new java.awt.Dimension(161, 29));
        jLabel2.setMinimumSize(new java.awt.Dimension(161, 29));
        jLabel2.setPreferredSize(new java.awt.Dimension(161, 29));

        btnSimpanData.setText("Save");
        btnSimpanData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanDataActionPerformed(evt);
            }
        });

        btnBatalData1.setText("Back");
        btnBatalData1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalData1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("PO No");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Kode Supplier");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Nama Supplier");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Kode Barang");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Nama Barang");

        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("Due Date Order");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 102));
        jLabel12.setText("Qty Order");

        txtNamaSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSuplierActionPerformed(evt);
            }
        });

        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 102));
        jLabel13.setText("Due Date Receive");

        txtDueDateRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDueDateRecActionPerformed(evt);
            }
        });

        comboPoNo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih PreOrder Nomer" }));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 102));
        jLabel14.setText("Qty Receive");

        txtRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecActionPerformed(evt);
            }
        });

        txtDateOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPoNo, 0, 905, Short.MAX_VALUE)
                    .addComponent(txtQty)
                    .addComponent(txtDueDateRec)
                    .addComponent(txtNamaSuplier)
                    .addComponent(txtNamaBarang)
                    .addComponent(txtRec)
                    .addComponent(txtKodeSupplier)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtKodeBarang)
                    .addComponent(txtDateOrder))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboPoNo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(3, 3, 3)
                .addComponent(txtKodeSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(4, 4, 4)
                .addComponent(txtDateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDueDateRec, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRec, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tambahPembelianLayout = new javax.swing.GroupLayout(tambahPembelian);
        tambahPembelian.setLayout(tambahPembelianLayout);
        tambahPembelianLayout.setHorizontalGroup(
            tambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahPembelianLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(tambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tambahPembelianLayout.createSequentialGroup()
                        .addComponent(btnSimpanData, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatalData1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 705, Short.MAX_VALUE))
            .addGroup(tambahPembelianLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tambahPembelianLayout.setVerticalGroup(
            tambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahPembelianLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tambahPembelianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatalData1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainpanel.add(tambahPembelian, "card2");

        add(mainpanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDataActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(tambahPembelian);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnTambahDataActionPerformed

    private void btnHapusDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDataActionPerformed
        int selectedRow = tblpembelian.getSelectedRow();
        if (selectedRow != -1) {
            String poNo = tblpembelian.getValueAt(selectedRow, 0).toString();
            String kodeSupplier = tblpembelian.getValueAt(selectedRow, 1).toString();

            if (hasRelatedStockIn(kodeSupplier)) {
                JOptionPane.showMessageDialog(this, "Tidak bisa menghapus Data Stock In karena ada Data Stock Daily yang terhubung. Hapus Stock Daily terlebih dahulu.");
                return;
            }   

            int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement p = c.prepareStatement("DELETE FROM tb_pembelian WHERE `PO_No` = ?")) {
                    p.setString(1, poNo);
                    int rowsAffected = p.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                        loadData(); // Reload the table data
                    } else {
                        JOptionPane.showMessageDialog(this, "Data tidak ditemukan di database.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus.");
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed
    private boolean hasRelatedStockIn(String kodeSupplier) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM tb_stockdaily WHERE `kodeSupplier` = ?")) {
            p.setString(1, kodeSupplier);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        return false;
    }
    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed
        private boolean isEditMode = false;
    private void btnSimpanDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanDataActionPerformed
        saveData();
    }//GEN-LAST:event_btnSimpanDataActionPerformed
    private void saveData() {
        // Pengambilan data dari form
        String poNo = comboPoNo.getSelectedItem().toString();
        String kodeSupplier = txtKodeSupplier.getText().trim();
        String namaSupplier = txtNamaSuplier.getText().trim();
        String kodeBarang = txtKodeBarang.getText().trim();
        String namaBarang = txtNamaBarang.getText().trim();
        String dateOrder = txtDateOrder.getText().trim();
        String dueDateRec = txtDueDateRec.getText().trim();
        String qtyOrderStr = txtQty.getText().trim();
        String qtyReceiveStr = txtRec.getText().trim();

        // Validasi input kosong
        if (poNo.isEmpty() || kodeSupplier.isEmpty() || namaSupplier.isEmpty() || kodeBarang.isEmpty() ||
                namaBarang.isEmpty() || dateOrder.isEmpty() || dueDateRec.isEmpty() || qtyOrderStr.isEmpty() ||
                qtyReceiveStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int qtyOrder, qtyReceive;
        try {
            qtyOrder = Integer.parseInt(qtyOrderStr);
            qtyReceive = Integer.parseInt(qtyReceiveStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Qty harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse tanggal ke dalam bentuk java.sql.Date
        java.sql.Date dateOrderParsed = null, dueDateRecParsed = null;
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (!dateOrder.isEmpty()) {
                java.util.Date dateOrderUtil = dateFormatInput.parse(dateOrder);
                dateOrderParsed = new java.sql.Date(dateOrderUtil.getTime());
            }
            java.util.Date dueDateRecUtil = dateFormatInput.parse(dueDateRec);
            dueDateRecParsed = new java.sql.Date(dueDateRecUtil.getTime());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simpan data ke dalam database
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Hitung Total_Stock
            int totalStock = calculateTotalStock(c, kodeBarang, qtyReceive);

            // Insert ke tb_pembelian
            String insertSql = "INSERT INTO tb_pembelian (PO_No, Kode_Supplier, Nama_Supplier, Kode_Barang, Nama_Barang, Due_Date_Order, Qty_Order, `Due Date Receive`, `Qty Receive`, Total_Stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertData = c.prepareStatement(insertSql)) {
                insertData.setString(1, poNo);
                insertData.setString(2, kodeSupplier);
                insertData.setString(3, namaSupplier);
                insertData.setString(4, kodeBarang);
                insertData.setString(5, namaBarang);
                insertData.setDate(6, dateOrderParsed);
                insertData.setInt(7, qtyOrder);
                insertData.setDate(8, dueDateRecParsed);
                insertData.setInt(9, qtyReceive);
                insertData.setInt(10, totalStock); // Masukkan Total_Stock

                int rowsAffected = insertData.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
                    loadData(); // Reload table data
                } else {
                    JOptionPane.showMessageDialog(this, "Data gagal disimpan.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private int calculateTotalStock(Connection c, String kodeBarang, int qtyReceive) throws SQLException {
        int stockAwal = 0;
        String query = "SELECT StokAwal FROM tb_barang WHERE kodeBarang = ?";
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setString(1, kodeBarang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stockAwal = rs.getInt("StokAwal");
            }
        }
        return stockAwal + qtyReceive; // Hitung Total_Stock sebagai StockAwal + Qty Receive
    }
    private void clearForm() {
        // Kosongkan semua JTextField atau reset form sesuai kebutuhan
        txtKodeSupplier.setText("");
        txtNamaSuplier.setText("");
        txtKodeBarang.setText("");
        txtNamaBarang.setText("");
        txtDateOrder.setText("");
        txtQty.setText("");
        txtDueDateRec.setText("");
        txtRec.setText("");
    }

    private void loadData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"PO_No", "Kode_Supplier", "Nama_Supplier", "Kode_Barang", "Nama_Barang", "Due_Date_Order", "Qty_Order", "Due Date Receive", "Qty Receive", "Total_Stock"}, 0);
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT PO_No, Kode_Supplier, Nama_Supplier, Kode_Barang, Nama_Barang, Due_Date_Order, Qty_Order, `Due Date Receive`, `Qty Receive`, Total_Stock " +
                                          "FROM tb_pembelian")) {
            while (r.next()) {
                model.addRow(new Object[]{
                        r.getString("PO_No"),
                        r.getString("Kode_Supplier"),
                        r.getString("Nama_Supplier"),
                        r.getString("Kode_Barang"),
                        r.getString("Nama_Barang"),
                        r.getDate("Due_Date_Order"),
                        r.getString("Qty_Order"),
                        r.getDate("Due Date Receive"),
                        r.getString("Qty Receive"),
                        r.getInt("Total_Stock")
                });
            }
            tblpembelian.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    private void btnBatalData1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalData1ActionPerformed
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(dataPembelian);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_btnBatalData1ActionPerformed

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtNamaSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSuplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSuplierActionPerformed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtDueDateRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDueDateRecActionPerformed
        
    }//GEN-LAST:event_txtDueDateRecActionPerformed

    private void txtRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecActionPerformed

    private void txtDateOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateOrderActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalData1;
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnSimpanData;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JComboBox<String> comboPoNo;
    private javax.swing.JPanel dataPembelian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel tambahPembelian;
    private javax.swing.JTable tblpembelian;
    private javax.swing.JTextField txtDateOrder;
    private javax.swing.JTextField txtDueDateRec;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtKodeSupplier;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaSuplier;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtRec;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}