/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Aries
 */
public class laporanSPPSeluruh extends javax.swing.JInternalFrame {

    /**
     * Creates new form laporanSPP
     */
    ResultSet r;
    Statement s;
    Connection c;
    public laporanSPPSeluruh() {
        initComponents();
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtmulai = new com.toedter.calendar.JDateChooser();
        jtsampai = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        setTitle("Input Parameter Cetak");

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Tanggal Mulai");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 32, -1, -1));
        jPanel2.add(jtmulai, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 130, -1));
        jPanel2.add(jtsampai, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 130, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_17px.png"))); // NOI18N
        jButton1.setText("Cetak");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 90, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Cancel_17px.png"))); // NOI18N
        jButton2.setText("Keluar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 90, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Keterangan");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Sampai Tanggal");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sudah Bayar", "Belum Bayar" }));
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 120, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 380, 160));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private static String path = System.getProperty("user.dir")+"/src/Laporan/";
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String tanggal ="YYYY-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tanggal);
        String Mulai = String.valueOf(format.format(jtmulai.getDate()));
        String Selesai = String.valueOf(format.format(jtsampai.getDate()));
        if("".equals(Mulai) || "".equals(Selesai)){
            JOptionPane.showMessageDialog(null, "Lengkapi data");
        }else{
            String filename = path+"LaporanSPP_1.jrxml";
            String filename1 = path+"LaporanSPP_1_1_1.jrxml";
            JasperReport jasrep;
            JasperPrint japri;
            JasperViewer javie = null;
            HashMap param = new HashMap(2);
            JasperDesign jasdes;
            try {
                param.put("tglmulai",Mulai);
                param.put("tglsampai",Selesai);
                
                if(jComboBox1.getSelectedIndex()==0){
                    File report = new File(filename);
                    jasdes = JRXmlLoader.load(report);
                    jasrep = JasperCompileManager.compileReport(jasdes);
                    japri = JasperFillManager.fillReport(jasrep,param,Komponen.koneksi.GetConnection());
                    javie.viewReport(japri,false);
                }else{
                    File report = new File(filename1);
                    jasdes = JRXmlLoader.load(report);
                    jasrep = JasperCompileManager.compileReport(jasdes);
                    japri = JasperFillManager.fillReport(jasrep,param,Komponen.koneksi.GetConnection());
                    javie.viewReport(japri,false);
                }
            } catch (Exception e) {
                System.out.print("gagal ngprint");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser jtmulai;
    private com.toedter.calendar.JDateChooser jtsampai;
    // End of variables declaration//GEN-END:variables
}
