/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Menerapkan interface
package koneksi;
import javax.swing.JTable;
import javax.swing.JTextField;

public interface SupplierPanel {
    JTextField getTxtSearch();
    JTable getTblTampilDataSuplier();
    void loadData();
    void searchSupplier();
    void clearFields();
    boolean cekBarangTerhubungDenganSuplier(String kodeSuplier);
}