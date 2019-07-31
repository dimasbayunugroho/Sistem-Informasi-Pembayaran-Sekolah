/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tool;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author user
 */
public class KoneksiDB {
    
    public Connection getConnection() throws SQLException{ 
        Connection cnn; 
        try{
            String server = "jdbc:mysql://localhost/db_ijo"; 
            String drever = "com.mysql.jdbc.Driver"; 
            Class.forName(drever); 
            cnn = DriverManager.getConnection(server, "root", ""); 
            return cnn;
        }catch(SQLException | ClassNotFoundException se){ 
            JOptionPane.showMessageDialog(null,"Error koneksi database" + se);
            return null;
        }
    }
}
