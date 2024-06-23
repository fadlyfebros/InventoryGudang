package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import java.awt.Dimension;
import report.ReportBarang;
import view.content_bg;
import view.formPembelian;
import view.formPenjualan;
import view.formPurchase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import report.ReportMember;
import report.ReportPembelian;
import report.ReportPenjualan;
import report.ReportSupplier;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
/**
 *
 * @author GILANG RUBIYANA
 */
public class MenuUtama extends javax.swing.JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private float volume = 0.1f; // Volume dalam rentang 0.0 (mute) hingga 1.0 (full volume)
    private String audioFilePath = "D:/Febro/Website/Project Netbeans/Project Inventory/Project Inventory/src/audio/lofi.mp3";
    
    /**
     * Creates new form Menu_Utama
     */
    public MenuUtama() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension panelSize = new Dimension(250, 100);
        showDashboard();
        playAudio();
        excute();
    }
    /**
    * Memainkan file audio dalam loop. Metode ini berjalan dalam thread terpisah
    * untuk memastikan GUI tetap responsif saat audio diputar.
    * Jika pemutaran selesai, audio akan diputar ulang.
    */
   private void playAudio() {
       new Thread(() -> {
           while (true) { // Loop untuk memutar ulang audio
               try {
                   FileInputStream fis = new FileInputStream(audioFilePath);
                   AdvancedPlayer player = new AdvancedPlayer(fis);

                   player.setPlayBackListener(new PlaybackListener() {
                       @Override
                       public void playbackFinished(PlaybackEvent evt) {
                           System.out.println("Playback selesai, memulai ulang...");
                       }
                   });

                   player.play();
               } catch (JavaLayerException | IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();
   }

   /**
    * Menyesuaikan volume file audio yang ditentukan dengan faktor pengurangan
    * yang diberikan dan memutar audio yang sudah disesuaikan. Metode ini tidak
    * memutar audio dalam loop.
    *
    * @param audioFilePath Jalur ke file audio yang akan diputar.
    * @param volumeReductionFactor Faktor pengurangan volume.
    * @throws IOException Jika terjadi kesalahan I/O.
    * @throws LineUnavailableException Jika jalur audio tidak tersedia.
    * @throws UnsupportedAudioFileException Jika format file audio tidak didukung.
    */
   private void adjustVolume(String audioFilePath, float volumeReductionFactor) 
           throws IOException, LineUnavailableException, UnsupportedAudioFileException {
       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
       AudioFormat format = audioInputStream.getFormat();

       DataLine.Info info = new DataLine.Info(Clip.class, format);
       Clip audioClip = (Clip) AudioSystem.getLine(info);
       audioClip.open(audioInputStream);

       FloatControl volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
       float volume = volumeControl.getValue();
       volumeControl.setValue(volume * volumeReductionFactor);

       audioClip.start();
   }
    public void showDashboard() {
        panel_utama.removeAll(); // Menghapus semua komponen dari panel utama
        panel_utama.add(new content_bg()); // Menambahkan panel dashboard
        panel_utama.repaint(); // Melakukan repaint panel utama
        panel_utama.revalidate(); // Me-validate ulang tata letak panel utama
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_atas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panel_kiri = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel_menu = new javax.swing.JPanel();
        panel_dasar = new javax.swing.JPanel();
        panel_utama = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panel_atas.setBackground(new java.awt.Color(0, 102, 102));
        panel_atas.setPreferredSize(new java.awt.Dimension(793, 70));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Aplikasi Inventory Gudang PokeMart");

        javax.swing.GroupLayout panel_atasLayout = new javax.swing.GroupLayout(panel_atas);
        panel_atas.setLayout(panel_atasLayout);
        panel_atasLayout.setHorizontalGroup(
            panel_atasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
        );
        panel_atasLayout.setVerticalGroup(
            panel_atasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_atasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        getContentPane().add(panel_atas, java.awt.BorderLayout.PAGE_START);

        panel_kiri.setBackground(new java.awt.Color(0, 102, 102));
        panel_kiri.setFocusTraversalPolicyProvider(true);
        panel_kiri.setPreferredSize(new java.awt.Dimension(250, 495));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        panel_menu.setBackground(new java.awt.Color(34, 45, 50));
        panel_menu.setLayout(new javax.swing.BoxLayout(panel_menu, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panel_menu);

        javax.swing.GroupLayout panel_kiriLayout = new javax.swing.GroupLayout(panel_kiri);
        panel_kiri.setLayout(panel_kiriLayout);
        panel_kiriLayout.setHorizontalGroup(
            panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panel_kiriLayout.setVerticalGroup(
            panel_kiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        getContentPane().add(panel_kiri, java.awt.BorderLayout.LINE_START);

        panel_dasar.setBackground(new java.awt.Color(255, 255, 255));

        panel_utama.setBackground(new java.awt.Color(255, 255, 255));
        panel_utama.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 543, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );

        panel_utama.add(jPanel1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout panel_dasarLayout = new javax.swing.GroupLayout(panel_dasar);
        panel_dasar.setLayout(panel_dasarLayout);
        panel_dasarLayout.setHorizontalGroup(
            panel_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_utama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_dasarLayout.setVerticalGroup(
            panel_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_utama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(panel_dasar, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(807, 504));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        panel_utama.add(new content_bg());
        panel_utama.repaint();
        panel_utama.revalidate();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                java.awt.EventQueue.invokeLater(() -> new MenuUtama().setVisible(true));
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_atas;
    private javax.swing.JPanel panel_dasar;
    private javax.swing.JPanel panel_kiri;
    private javax.swing.JPanel panel_menu;
    private javax.swing.JPanel panel_utama;
    // End of variables declaration//GEN-END:variables

    private void excute() {
        //bagian menu
        ImageIcon dashboard = new ImageIcon(getClass().getResource("/img/home.png"));
        ImageIcon dataMaster = new ImageIcon(getClass().getResource("/img/master-data.png"));
        ImageIcon purchasing = new ImageIcon(getClass().getResource("/img/order.png"));
        ImageIcon transaksi = new ImageIcon(getClass().getResource("/img/transaction.png"));
        ImageIcon inventory = new ImageIcon(getClass().getResource("/img/inventory.png"));
        ImageIcon report = new ImageIcon(getClass().getResource("/img/report.png"));

        //sub Menu
        ImageIcon dataSuplier = new ImageIcon(getClass().getResource("/img/group.png"));
        ImageIcon dataBarang = new ImageIcon(getClass().getResource("/img/boxes.png"));
        ImageIcon claim = new ImageIcon(getClass().getResource("/img/claim.png"));
        ImageIcon pembeli = new ImageIcon(getClass().getResource("/img/market.png"));
        ImageIcon penjual = new ImageIcon(getClass().getResource("/img/seller.png"));
        ImageIcon stok = new ImageIcon(getClass().getResource("/img/stock.png"));
         
        // Define MenuItems with ActionListener implementations
        MenuItem Master1 = new MenuItem(dataBarang, true, null, "Data Barang", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new FormBarang());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Master2 = new MenuItem(dataSuplier, true, null, "Data Supplier", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new FormSuplier());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Purchasing1 = new MenuItem(purchasing, true, null, "Purchase Order", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new formPurchase());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Transaksi1 = new MenuItem(pembeli, true, null, "Stock In", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new formPembelian());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Transaksi2 = new MenuItem(penjual, true, null, "Stock Out", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new formPenjualan());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Inventory1 = new MenuItem(stok, true, null, "Stock Daily", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new formStockDaily());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Inventory2 = new MenuItem(stok, true, null, "Stock Monthly", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new formStockMonthly());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Report1 = new MenuItem(dataBarang, true, null, "Report Barang", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new ReportBarang());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Report2 = new MenuItem(dataSuplier, true, null, "Report Supplier", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new ReportSupplier());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Report3 = new MenuItem(pembeli, true, null, "Stock In", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new ReportPembelian());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem Report4 = new MenuItem(penjual, true, null, "Stock Out", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new ReportPenjualan());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem menuDashboard = new MenuItem(dashboard, false, null, "Home", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_utama.removeAll();
                panel_utama.add(new content_bg());
                panel_utama.repaint();
                panel_utama.revalidate();
            }
        });

        MenuItem menuMaster = new MenuItem(dataMaster, false, null, "Data Master", null, Master1, Master2);
        MenuItem menuPurchasing = new MenuItem(purchasing, false, null, "Purchasing", null, Purchasing1);
        MenuItem menuTransaksi = new MenuItem(transaksi, false, null, "Transaksi", null, Transaksi1, Transaksi2);
        MenuItem menuInventory = new MenuItem(inventory, false, null, "Inventory", null, Inventory1, Inventory2);
        MenuItem menuReport = new MenuItem(report, false, null, "Report", null, Report1, Report2, Report3, Report4);

        addMenu(menuDashboard, menuMaster, menuPurchasing, menuTransaksi, menuInventory, menuReport);
    }
    private void addMenu(MenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            panel_menu.add(menu[i]);
            ArrayList<MenuItem> subMenu = menu[i].getSubMenu();
            for (MenuItem m : subMenu) {
                addMenu(m);
            }
        }
        panel_menu.revalidate();
    }

    void setVisible() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
