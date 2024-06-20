package koneksi;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fadly
 */
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static java.sql.Connection koneksi;
    public static java.sql.Connection getKoneksi(){
        if(koneksi == null) {
            try{
                String url = "jdbc:mysql://localhost:3306/project_inventory";
                String user = "root";
                String password = "";
                // Menggunakan kelas driver yang baru
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Connection Successfully");
            }catch(SQLException | ClassNotFoundException e){
                e.printStackTrace();
                System.out.println("Error!");
            }
        }
        return koneksi;
    }
    public static void main(String args[]){
        getKoneksi();
    }
}