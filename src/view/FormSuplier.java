package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author fadly
 */
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import koneksi.Supplier;
import koneksi.SupplierPanel;
// Mendefinisikan class-class di mana terdapat minimal 1 class mengimplementasikan interface 
public class FormSuplier extends javax.swing.JPanel implements SupplierPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private boolean isEditMode = false;

    /**
     * Creates new form FormSuplier
     */
    public FormSuplier() {
        txtSearch = new JTextField("Pencarian . . .");
        tblTampilDataSuplier = new JTable();
        
        initComponents();
        autonumber();
        loadData();
        txtKodeSuplier.setEnabled(false);
        initSearchField();
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
                searchSupplier();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchSupplier();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchSupplier();
            }
        });
    }
    public void searchSupplier() {
        String keyword = txtSearch.getText();
        if (keyword.equals("Pencarian . . .")) {
            loadData();
            return;
        }

        // Menerapkan multithreading dengan menggunakan SwingWorker
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                DefaultTableModel model = new DefaultTableModel(new String[]{"Kode Suplier", "Nama Suplier", "Alamat", "No Telp", "Email"}, 0);
                String sql = "SELECT * FROM tb_suplier WHERE kodeSuplier LIKE ? OR namaSuplier LIKE ?";

                try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement p = c.prepareStatement(sql)) {
                    p.setString(1, "%" + keyword + "%");
                    p.setString(2, "%" + keyword + "%");

                    try (ResultSet r = p.executeQuery()) {
                        while (r.next()) {
                            String kodeSuplier = r.getString("kodeSuplier");
                            String namaSuplier = r.getString("namaSuplier");
                            String alamatSuplier = r.getString("alamatSuplier");
                            String noTelp = r.getString("noTelp");
                            String email = r.getString("Email");
                            model.addRow(new Object[]{kodeSuplier, namaSuplier, alamatSuplier, noTelp, email});
                        }
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }

                // Update GUI di EDT (Event Dispatch Thread)
                SwingUtilities.invokeLater(() -> tblTampilDataSuplier.setModel(model));
                return null;
            }
        };

        // Mulai eksekusi SwingWorker
        worker.execute();
    }
    private void autonumber() {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement()) {
            String sql = "SELECT kodeSuplier FROM tb_suplier ORDER BY kodeSuplier DESC LIMIT 1";
            try (ResultSet r = s.executeQuery(sql)) {
                if (r.next()) {
                    String kodeSup = r.getString("kodeSuplier");
                    int id = Integer.parseInt(kodeSup.substring(3)) + 1;
                    String kode = String.format("SP-%07d", id);
                    txtKodeSuplier.setText(kode);
                } else {
                    txtKodeSuplier.setText("SP-0000001");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    @Override
    public void loadData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Kode Suplier", "Nama Suplier", "Alamat", "No Telp", "Email"}, 0);
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM tb_suplier")) {
            while (r.next()) {
                String kodeSuplier = r.getString("kodeSuplier");
                String namaSuplier = r.getString("namaSuplier");
                String alamatSuplier = r.getString("alamatSuplier");
                String noTelp = r.getString("noTelp");
                String email = r.getString("Email");
                model.addRow(new Object[]{kodeSuplier, namaSuplier, alamatSuplier, noTelp, email});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        tblTampilDataSuplier.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        dataSuplier = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTampilDataSuplier = new javax.swing.JTable();
        btnTambahData = new javax.swing.JButton();
        btnHapusData = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tambahSuplier = new javax.swing.JPanel();
        btnSimpanSuplier = new javax.swing.JButton();
        btnBatalSuplier = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtKodeSuplier = new javax.swing.JTextField();
        txtNamaSuplier = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAlamatSuplier = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNoTelp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.CardLayout());

        dataSuplier.setBackground(new java.awt.Color(255, 255, 255));

        tblTampilDataSuplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Suplier", "Nama Suplier", "Alamat Suplier", "No Telp", "Email"
            }
        ));
        jScrollPane1.setViewportView(tblTampilDataSuplier);

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Data Supplier");

        txtSearch.setText("Pencarian . . .");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loupe.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout dataSuplierLayout = new javax.swing.GroupLayout(dataSuplier);
        dataSuplier.setLayout(dataSuplierLayout);
        dataSuplierLayout.setHorizontalGroup(
            dataSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataSuplierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSuplierLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(dataSuplierLayout.createSequentialGroup()
                        .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(20, 20, 20))
                    .addGroup(dataSuplierLayout.createSequentialGroup()
                        .addGroup(dataSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
                            .addComponent(jLabel1))
                        .addContainerGap())))
        );
        dataSuplierLayout.setVerticalGroup(
            dataSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataSuplierLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(dataSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataSuplierLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(dataSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dataSuplierLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(dataSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(dataSuplier, "card2");

        btnSimpanSuplier.setForeground(new java.awt.Color(0, 102, 102));
        btnSimpanSuplier.setText("Save");
        btnSimpanSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanSuplierActionPerformed(evt);
            }
        });

        btnBatalSuplier.setForeground(new java.awt.Color(0, 102, 102));
        btnBatalSuplier.setText("Back");
        btnBatalSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalSuplierActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 102, 102));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Tambah Supplier");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtKodeSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeSuplierActionPerformed(evt);
            }
        });

        txtNamaSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSuplierActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Kode Supplier");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Nama Supplier");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Alamat Supplier");

        txtAlamatSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatSuplierActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("No Telpon");

        txtNoTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoTelpActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Email");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(txtKodeSuplier)
                        .addComponent(txtNamaSuplier)
                        .addComponent(txtAlamatSuplier, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKodeSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlamatSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tambahSuplierLayout = new javax.swing.GroupLayout(tambahSuplier);
        tambahSuplier.setLayout(tambahSuplierLayout);
        tambahSuplierLayout.setHorizontalGroup(
            tambahSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahSuplierLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(tambahSuplierLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(tambahSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(tambahSuplierLayout.createSequentialGroup()
                        .addComponent(btnSimpanSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatalSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tambahSuplierLayout.setVerticalGroup(
            tambahSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahSuplierLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tambahSuplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatalSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(tambahSuplier, "card2");

        add(mainPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnHapusDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDataActionPerformed
        int selectedRow = tblTampilDataSuplier.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblTampilDataSuplier.getModel();
        String kodeSuplier = model.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus Data", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                // Periksa apakah ada barang yang terhubung dengan supplier ini
                if (cekBarangTerhubungDenganSuplier(kodeSuplier)) {
                    JOptionPane.showMessageDialog(this, "Tidak bisa menghapus Supplier karena ada barang yang terhubung. Hapus barangnya terlebih dahulu.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String sql = "DELETE FROM tb_suplier WHERE kodeSuplier = ?";
                try (PreparedStatement p = c.prepareStatement(sql)) {
                    p.setString(1, kodeSuplier);
                    p.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                    model.removeRow(selectedRow);
                    autonumber();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed
    @Override
    public boolean cekBarangTerhubungDenganSuplier(String kodeSuplier) {
        String query = "SELECT COUNT(*) FROM tb_barang WHERE kodeSupplier = ?";
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement(query)) {
            p.setString(1, kodeSuplier);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    private void btnBatalSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalSuplierActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataSuplier);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnBatalSuplierActionPerformed

    private void btnTambahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDataActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(tambahSuplier);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnTambahDataActionPerformed

    private void txtKodeSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeSuplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeSuplierActionPerformed

    private void txtNamaSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSuplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSuplierActionPerformed

    private void txtAlamatSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatSuplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatSuplierActionPerformed

    private void txtNoTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoTelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoTelpActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed
    private boolean isEditPopupShown = false;
    private void btnSimpanSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanSuplierActionPerformed
        String kodeSuplier = txtKodeSuplier.getText();
        String namaSuplier = txtNamaSuplier.getText();
        String alamatSuplier = txtAlamatSuplier.getText();
        String noTelp = txtNoTelp.getText();
        String email = txtEmail.getText();

        // Input validation
        if (namaSuplier.isEmpty() || alamatSuplier.isEmpty() || noTelp.isEmpty() || email.isEmpty() || !email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi semua data dengan benar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isEditMode) {
            updateDataSupplier(kodeSuplier, namaSuplier, alamatSuplier, noTelp, email);
        } else {
            simpanDataSupplier(kodeSuplier, namaSuplier, alamatSuplier, noTelp, email);
        }

        autonumber();
        loadData();
        switchToDataSupplierPanel();
    }//GEN-LAST:event_btnSimpanSuplierActionPerformed
    private void switchToDataSupplierPanel() {
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        mainPanel.add(dataSuplier);
        mainPanel.repaint();
        mainPanel.revalidate();
    }
    private void simpanDataSupplier(String kodeSuplier, String namaSuplier, String alamatSuplier, String noTelp, String email) {
        String sql = "INSERT INTO tb_suplier (kodeSuplier, namaSuplier, alamatSuplier, noTelp, Email) VALUES (?, ?, ?, ?, ?)";
        executeQuery(sql, kodeSuplier, namaSuplier, alamatSuplier, noTelp, email);
    }

    // Update existing supplier data
    private void updateDataSupplier(String kodeSuplier, String namaSuplier, String alamatSuplier, String noTelp, String email) {
        String sql = "UPDATE tb_suplier SET namaSuplier = ?, alamatSuplier = ?, noTelp = ?, Email = ? WHERE kodeSuplier = ?";
        executeQuery(sql, namaSuplier, alamatSuplier, noTelp, email, kodeSuplier);
    }

    // Execute SQL query with parameters
    private void executeQuery(String sql, String... params) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement p = c.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                p.setString(i + 1, params[i]);
            }
            p.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    // Getter dan Setter untuk memanipulasi nilai dari komponen GUI
    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(JTextField txtSearch) {
        this.txtSearch = txtSearch;
    }

    public JTable getTblTampilDataSuplier() {
        return tblTampilDataSuplier;
    }

    public void setTblTampilDataSuplier(JTable tblTampilDataSuplier) {
        this.tblTampilDataSuplier = tblTampilDataSuplier;
    }
    @Override
    public void clearFields() {
        txtNamaSuplier.setText("");
        txtAlamatSuplier.setText("");
        txtNoTelp.setText("");
        txtEmail.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalSuplier;
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnSimpanSuplier;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JPanel dataSuplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel tambahSuplier;
    private javax.swing.JTable tblTampilDataSuplier;
    private javax.swing.JTextField txtAlamatSuplier;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtKodeSuplier;
    private javax.swing.JTextField txtNamaSuplier;
    private javax.swing.JTextField txtNoTelp;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}