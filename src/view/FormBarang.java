package view;

import java.awt.List;
import view.MenuUtama;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import koneksi.Barang;
/**
 *
 * @author fadly
 */
public class FormBarang extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private Connection getConnection() throws SQLException {
         return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    /**
     * Creates new form FormBarang
     */
    public FormBarang() {
        initComponents();
        txtKodeBarang.setEnabled(false);
        txtNamaSuplier.setEnabled(false);
        txtHargaJual.setEnabled(false);
        autonumber();
        refreshTable();
        fillComboKodeSupplier();

        txtSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtSearch.getText().equals("Pencarian . . .")) {
                    txtSearch.setText("");
                }
            }

            public void focusLost(FocusEvent evt) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Pencarian . . .");
                }
            }
        });

        lblPencarian.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                searchItem();
            }
        });

        txtHargaBeli.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateHargaJual();
            }
            public void removeUpdate(DocumentEvent e) {
                updateHargaJual();
            }
            public void insertUpdate(DocumentEvent e) {
                updateHargaJual();
            }
        });
    }
    // Menerapkan overloading
    private void updateHargaJual() {
        try {
            double hargaBeli = Double.parseDouble(txtHargaBeli.getText());
            double hargaJual = hargaBeli + (hargaBeli * 0.20);
            DecimalFormat df = new DecimalFormat("#");
            txtHargaJual.setText(df.format(hargaJual));
        } catch (NumberFormatException e) {
            txtHargaJual.setText("");
        }
    }

    private void searchItem() {
        String searchText = txtSearch.getText();
        if (searchText.equals("Pencarian . . .")) {
            searchText = "";
        }
        DefaultTableModel model = (DefaultTableModel) tblBarang.getModel();
        model.setRowCount(0);

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM tb_barang WHERE kodeBarang LIKE ? OR namaBarang LIKE ?")) {
            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("kodeBarang"),
                        rs.getString("namaBarang"),
                        rs.getString("berat"),
                        rs.getString("kodeSupplier"),
                        rs.getString("namaSupplier"),
                        rs.getString("satuan"),
                        rs.getString("hargaBeli"),
                        rs.getString("hargaJual"),
                        rs.getInt("StokAwal")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    private void autonumber() {
        try (Connection c = getConnection();
             Statement s = c.createStatement()) {
            String sql = "SELECT kodeBarang FROM tb_barang ORDER BY kodeBarang DESC LIMIT 1";
            try (ResultSet r = s.executeQuery(sql)) {
                if (r.next()) {
                    String kodeBarang = r.getString("kodeBarang");
                    int numberPart = Integer.parseInt(kodeBarang.substring(2));
                    String newKodeBarang = String.format("B-%09d", numberPart + 1);
                    txtKodeBarang.setText(newKodeBarang);
                } else {
                    txtKodeBarang.setText("B-000000001");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    private void fillComboKodeSupplier() {
        comboKodeSup.removeAllItems();
        comboKodeSup.addItem("Pilih Kode Supplier");
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT kodeSuplier FROM tb_suplier";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                comboKodeSup.addItem(rs.getString("kodeSuplier"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
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

        mainPanel = new javax.swing.JPanel();
        dataBarang = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBarang = new javax.swing.JTable();
        btnTambahData = new javax.swing.JButton();
        btnHapusData = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblPencarian = new javax.swing.JLabel();
        tambahBarang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtKodeBarang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBeratBarang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNamaSuplier = new javax.swing.JTextField();
        comboKodeSup = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtHargaBeli = new javax.swing.JTextField();
        comboSatuan = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtHargaJual = new javax.swing.JTextField();
        txtStokAwal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnSimpanBarang = new javax.swing.JButton();
        btnBatalBarang = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        mainPanel.setLayout(new java.awt.CardLayout());

        dataBarang.setBackground(new java.awt.Color(255, 255, 255));

        tblBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Berat", "Kode Supplier", "Nama Supplier", "Stok Awal", "Harga Beli", "Harga Jual", "Satuan"
            }
        ));
        jScrollPane1.setViewportView(tblBarang);

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
        jLabel1.setText("Data Barang");

        txtSearch.setText("Pencarian . . .");
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        lblPencarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/loupe.png"))); // NOI18N

        javax.swing.GroupLayout dataBarangLayout = new javax.swing.GroupLayout(dataBarang);
        dataBarang.setLayout(dataBarangLayout);
        dataBarangLayout.setHorizontalGroup(
            dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataBarangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataBarangLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(dataBarangLayout.createSequentialGroup()
                        .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPencarian)
                        .addGap(23, 23, 23))))
            .addGroup(dataBarangLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dataBarangLayout.setVerticalGroup(
            dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataBarangLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataBarangLayout.createSequentialGroup()
                        .addGroup(dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambahData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapusData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
                    .addGroup(dataBarangLayout.createSequentialGroup()
                        .addGroup(dataBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        mainPanel.add(dataBarang, "card2");

        tambahBarang.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Tambah Barang");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Kode Barang");

        txtKodeBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodeBarangActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Nama Barang");

        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Berat");

        txtBeratBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBeratBarangActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Kode Suplier");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Nama Suplier");

        txtNamaSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSuplierActionPerformed(evt);
            }
        });

        comboKodeSup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kode Suplier" }));
        comboKodeSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboKodeSupActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Satuan Barang");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Harga Beli");

        txtHargaBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaBeliActionPerformed(evt);
            }
        });

        comboSatuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Satuan Barang", "Pcs", "Box", "Lusin" }));
        comboSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSatuanActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Harga Jual");

        txtHargaJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaJualActionPerformed(evt);
            }
        });

        txtStokAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStokAwalActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("Stok Awal");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKodeBarang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBeratBarang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtHargaBeli)
                    .addComponent(comboSatuan, 0, 896, Short.MAX_VALUE)
                    .addComponent(comboKodeSup, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtHargaJual)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtStokAwal)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNamaSuplier))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBeratBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboKodeSup, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNamaSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStokAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        btnSimpanBarang.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnSimpanBarang.setForeground(new java.awt.Color(0, 102, 102));
        btnSimpanBarang.setText("Save");
        btnSimpanBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanBarangActionPerformed(evt);
            }
        });

        btnBatalBarang.setForeground(new java.awt.Color(0, 102, 102));
        btnBatalBarang.setText("Back");
        btnBatalBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tambahBarangLayout = new javax.swing.GroupLayout(tambahBarang);
        tambahBarang.setLayout(tambahBarangLayout);
        tambahBarangLayout.setHorizontalGroup(
            tambahBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambahBarangLayout.createSequentialGroup()
                .addGroup(tambahBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tambahBarangLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(tambahBarangLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(tambahBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tambahBarangLayout.createSequentialGroup()
                                .addComponent(btnSimpanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnBatalBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tambahBarangLayout.setVerticalGroup(
            tambahBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambahBarangLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tambahBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatalBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(tambahBarang, "card2");

        add(mainPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnHapusDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDataActionPerformed
        int selectedRow = tblBarang.getSelectedRow();
        if (selectedRow != -1) {
            String kodeBarang = (String) tblBarang.getValueAt(selectedRow, 0);

            try {
                // Periksa apakah ada purchasing yang terhubung dengan barang ini
                boolean adaPurchasingTerhubung = cekPurchasingTerhubungDenganBarang(kodeBarang);

                if (adaPurchasingTerhubung) {
                    JOptionPane.showMessageDialog(this, "Tidak bisa menghapus barang karena ada purchasing yang terhubung. Hapus purchasingnya terlebih dahulu.");
                    return;
                }

                // Periksa apakah ada stock monthly yang terhubung dengan barang ini
                boolean adaStockMonthlyTerhubung = cekStockMonthlyTerhubungDenganBarang(kodeBarang);

                if (adaStockMonthlyTerhubung) {
                    JOptionPane.showMessageDialog(this, "Tidak bisa menghapus barang karena ada stock monthly yang terhubung. Hapus stock monthly terlebih dahulu.");
                    return;
                }

                // Lanjutkan untuk menghapus barang di tb_barang
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement("DELETE FROM tb_barang WHERE kodeBarang = ?")) {
                    stmt.setString(1, kodeBarang);
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Barang dengan kode " + kodeBarang + " berhasil dihapus.");
                        refreshTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Gagal menghapus barang dengan kode " + kodeBarang);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Pilih barang yang ingin dihapus.");
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed
    private boolean cekPurchasingTerhubungDenganBarang(String kodeBarang) throws SQLException {
        String sql = "SELECT COUNT(*) AS jumlah FROM tb_purchase WHERE `Kode Barang` = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kodeBarang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("jumlah") > 0;
                }
            }
        }
        return false;
    }
    private boolean cekStockMonthlyTerhubungDenganBarang(String kodeBarang) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM tb_stockmonthly WHERE kodeBarang = ?")) {
            stmt.setString(1, kodeBarang);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
    private void btnSimpanBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanBarangActionPerformed
        // Retrieve input data from form fields
        String kodeBarang = txtKodeBarang.getText();
        String namaBarang = txtNamaBarang.getText();
        String berat = txtBeratBarang.getText();
        String kodeSupplier = (String) comboKodeSup.getSelectedItem();
        String namaSupplier = txtNamaSuplier.getText();
        String stokAwalText = txtStokAwal.getText();
        String satuan = (String) comboSatuan.getSelectedItem();
        String hargaBeliText = txtHargaBeli.getText();

        // Validate input data
        if (namaBarang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan Nama Barang Terlebih dahulu");
            return;
        }

        if (berat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan Berat Terlebih dahulu");
            return;
        }

        if (kodeSupplier.equals("Pilih Kode Supplier")) {
            JOptionPane.showMessageDialog(this, "Pilih Kode Supplier Terlebih dahulu");
            return;
        }

        if (stokAwalText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan Stok Awal Terlebih dahulu");
            return;
        }

        try {
            int stokAwal = Integer.parseInt(stokAwalText);
            if (stokAwal <= 0) {
                JOptionPane.showMessageDialog(this, "Stok Awal Harus Berupa Nomor dan lebih dari 0");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok Awal Harus Berupa Nomor");
            return;
        }

        if (satuan.equals("Pilih Satuan")) {
            JOptionPane.showMessageDialog(this, "Pilih Satuan Barang Terlebih dahulu");
            return;
        }

        if (hargaBeliText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan Harga Beli Terlebih dahulu");
            return;
        }

        try {
            // Format and parse input data
            DecimalFormat df = new DecimalFormat("#.##");
            double hargaBeli = Double.parseDouble(hargaBeliText);
            int stokAwal = Integer.parseInt(stokAwalText);

            if (stokAwal <= 0) {
                JOptionPane.showMessageDialog(this, "Stok awal harus lebih dari 0");
                return;
            }

            // Calculate hargaJual with 20% markup
            double hargaJual = hargaBeli * 1.20;
            String hargaBeliFormatted = df.format(hargaBeli);
            String hargaJualFormatted = df.format(hargaJual);

            // Create Barang object with input data
            Barang barang = new Barang(kodeBarang, namaBarang, berat, kodeSupplier, namaSupplier, stokAwal, satuan, hargaBeli, hargaJual);

            // Save data using simpanDataBarang method
            boolean berhasilDisimpan = simpanDataBarang(barang);

            // Show success or failure message
            if (berhasilDisimpan) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
                refreshTable();
                autonumber();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk harga beli atau stok awal");
        }
    }//GEN-LAST:event_btnSimpanBarangActionPerformed
    private boolean simpanDataBarang(Barang barang) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO tb_barang (kodeBarang, namaBarang, berat, kodeSupplier, namaSupplier, StokAwal, Satuan, hargaBeli, hargaJual) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, barang.getKodeBarang());
            stmt.setString(2, barang.getNamaBarang());
            stmt.setString(3, barang.getBerat());
            stmt.setString(4, barang.getKodeSupplier());
            stmt.setString(5, barang.getNamaSupplier());
            stmt.setInt(6, barang.getStokAwal());
            stmt.setString(7, barang.getSatuan());
            stmt.setDouble(8, barang.getHargaBeli());
            stmt.setDouble(9, barang.getHargaJual());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Kode Barang sudah ada dalam database");
            } else {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
            return false;
        }
    }
    private void clearForm() {
        txtNamaBarang.setText("");
        txtBeratBarang.setText("");
        comboKodeSup.setSelectedIndex(0);
        txtNamaSuplier.setText("");
        txtStokAwal.setText("");
        comboSatuan.setSelectedIndex(0);
        txtHargaBeli.setText("");
        txtHargaJual.setText("");
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblBarang.getModel();
        model.setRowCount(0); // Clear the table before loading new data

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tb_barang")) {

            DecimalFormat df = new DecimalFormat("#");

            while (rs.next()) {
                Barang barang = new Barang(
                    rs.getString("kodeBarang"),
                    rs.getString("namaBarang"),
                    rs.getString("berat"),
                    rs.getString("kodeSupplier"),
                    rs.getString("namaSupplier"),
                    rs.getInt("StokAwal"),
                    rs.getString("Satuan"),
                    Double.parseDouble(rs.getString("hargaBeli")),
                    Double.parseDouble(rs.getString("hargaJual"))
                );

                // Add the row to the table model
                model.addRow(new Object[]{
                    barang.getKodeBarang(),
                    barang.getNamaBarang(),
                    barang.getBerat(),
                    barang.getKodeSupplier(),
                    barang.getNamaSupplier(),
                    barang.getStokAwal(), // Ensure StokAwal is correctly fetched
                    df.format(barang.getHargaBeli()),
                    df.format(barang.getHargaJual()),
                    barang.getSatuan()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void btnBatalBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalBarangActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(dataBarang);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnBatalBarangActionPerformed

    private void txtKodeBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodeBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodeBarangActionPerformed

    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtBeratBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBeratBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBeratBarangActionPerformed

    private void txtNamaSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSuplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSuplierActionPerformed

    private void txtHargaBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaBeliActionPerformed

    private void btnTambahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDataActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(tambahBarang);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnTambahDataActionPerformed

    private void comboKodeSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboKodeSupActionPerformed
        String kodeSupplier = (String) comboKodeSup.getSelectedItem();
        if (!"Pilih Kode Supplier".equals(kodeSupplier)) {
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                String sql = "SELECT * FROM tb_suplier WHERE kodeSuplier = '" + kodeSupplier + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    txtNamaSuplier.setText(rs.getString("namaSuplier"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            txtNamaSuplier.setText("");
        }
    }//GEN-LAST:event_comboKodeSupActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed
    
    private void comboSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSatuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboSatuanActionPerformed

    private void txtHargaJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaJualActionPerformed

    private void txtStokAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStokAwalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStokAwalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatalBarang;
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnSimpanBarang;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JComboBox<String> comboKodeSup;
    private javax.swing.JComboBox<String> comboSatuan;
    private javax.swing.JPanel dataBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPencarian;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel tambahBarang;
    private javax.swing.JTable tblBarang;
    private javax.swing.JTextField txtBeratBarang;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtHargaJual;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaSuplier;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStokAwal;
    // End of variables declaration//GEN-END:variables

    private MenuUtama getMenuUtamaInstance() {
        return null;
    }
}