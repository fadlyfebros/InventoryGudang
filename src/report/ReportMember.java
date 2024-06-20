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
public class ReportMember extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    

    /**
     * Creates new form ReportBarang
     */
    public ReportMember() {
        initComponents();
    }
    private void loadData() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Kode Memnber", "Nama Member", "Alamat", "Email"}, 0);
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM tb_Member")) {
            while (r.next()) {
                String kodeMember = r.getString("kodeMember");
                String namaMember = r.getString("namaMember");
                String alamat = r.getString("alamat");
                String email = r.getString("Email");
                model.addRow(new Object[]{kodeMember, namaMember, alamat, email});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        tblReportMember.setModel(model);
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
        jTextField1 = new javax.swing.JTextField();
        btnPencarian = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReportMember = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(953, 796));
        setLayout(new java.awt.CardLayout());

        panel_report.setBackground(new java.awt.Color(153, 153, 153));

        btnPencarian.setText("Pencarian");
        btnPencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPencarianActionPerformed(evt);
            }
        });

        jButton2.setText("Cetak");

        tblReportMember.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblReportMember);

        jLabel3.setBackground(new java.awt.Color(51, 51, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Report Member");
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
                    .addGroup(panel_reportLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_reportLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnPencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_reportLayout.setVerticalGroup(
            panel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_reportLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnPencarian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(panel_report, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnPencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPencarianActionPerformed
        String keyword = jTextField1.getText().trim(); // Ambil kata kunci pencarian dari JTextField
        DefaultTableModel model = new DefaultTableModel(new String[]{"Kode Member", "Nama Member", "Alamat", "Email"}, 0);

        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement s = c.createStatement()) {

            String query = "SELECT * FROM tb_member WHERE namaMember LIKE '%" + keyword + "%'";
            ResultSet r = s.executeQuery(query);

            while (r.next()) {
                String kodeMember = r.getString("kodeMember");
                String namaMember = r.getString("namaMember");
                String alamat = r.getString("alamat");
                String email = r.getString("Email");
                model.addRow(new Object[]{kodeMember, namaMember, alamat, email});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        tblReportMember.setModel(model); // Set model tabel dengan data hasil pencarian
    }//GEN-LAST:event_btnPencarianActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPencarian;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panel_report;
    private javax.swing.JTable tblReportMember;
    // End of variables declaration//GEN-END:variables
}