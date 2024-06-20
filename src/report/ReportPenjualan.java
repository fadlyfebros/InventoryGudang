/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fadly
 */
public class ReportPenjualan extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private Connection conn;

    /**
     * Creates new form ReportSupplier
     */
    public ReportPenjualan() {
        initComponents();
        loadData();
    }
    private void loadData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"No Invoice", "Kode Barang", "Nama Barang", "Due Date", "Qty", "Total Harga"}, 0);
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM tb_stokout")) {
            while (r.next()) {
                String noInvoice = r.getString("noinvoice");
                String kodeBarang = r.getString("kode_Barang");
                String namaBarang = r.getString("nama_barang");
                String dueDate = r.getString("dueDate");
                int qty = r.getInt("Qty");
                int totalHarga = r.getInt("Total_Harga");
                model.addRow(new Object[]{noInvoice, kodeBarang, namaBarang, dueDate, qty, totalHarga});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        tblpenjualan.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_report = new javax.swing.JPanel();
        txtPencarian = new javax.swing.JTextField();
        btnPencarian = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpenjualan = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        panel_report.setBackground(new java.awt.Color(153, 153, 153));

        btnPencarian.setText("Pencarian");
        btnPencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPencarianActionPerformed(evt);
            }
        });

        jButton2.setText("Cetak");

        tblpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Barang ", "Nama Barang ", "Berat", "Kode Supplier ", "Nama Supplier", "QTY", "Harga"
            }
        ));
        jScrollPane1.setViewportView(tblpenjualan);

        jLabel3.setBackground(new java.awt.Color(51, 51, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Report Stock Out");
        jLabel3.setMaximumSize(new java.awt.Dimension(161, 29));
        jLabel3.setMinimumSize(new java.awt.Dimension(161, 29));
        jLabel3.setPreferredSize(new java.awt.Dimension(161, 29));

        javax.swing.GroupLayout panel_reportLayout = new javax.swing.GroupLayout(panel_report);
        panel_report.setLayout(panel_reportLayout);
        panel_reportLayout.setHorizontalGroup(
            panel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_reportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
                    .addGroup(panel_reportLayout.createSequentialGroup()
                        .addComponent(txtPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_reportLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_reportLayout.setVerticalGroup(
            panel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_reportLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnPencarian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(txtPencarian, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(panel_report, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnPencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPencarianActionPerformed
        String keyword = txtPencarian.getText().trim(); // Ambil kata kunci pencarian dari JTextField
        DefaultTableModel model = new DefaultTableModel(new String[]{"No Invoice", "Kode Barang", "Nama Barang", "Due Date", "Qty", "Total Harga"}, 0);

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement()) {

            String query = "SELECT * FROM tb_penjualan " +
                           "WHERE Nama_Barang LIKE '%" + keyword + "%'"; // Ubah kolom dan kondisi sesuai kebutuhan pencarian

            ResultSet r = s.executeQuery(query);

            while (r.next()) {
                String noInvoice = r.getString("noinvoice");
                String kodeBarang = r.getString("kode_Barang");
                String namaBarang = r.getString("nama_barang");
                String dueDate = r.getString("dueDate");
                int qty = r.getInt("Qty");
                int totalHarga = r.getInt("Total_Harga");
                model.addRow(new Object[]{noInvoice, kodeBarang, namaBarang, dueDate, qty, totalHarga});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        tblpenjualan.setModel(model); // Set model tabel dengan data hasil pencarian
    }//GEN-LAST:event_btnPencarianActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPencarian;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_report;
    private javax.swing.JTable tblpenjualan;
    private javax.swing.JTextField txtPencarian;
    // End of variables declaration//GEN-END:variables
}