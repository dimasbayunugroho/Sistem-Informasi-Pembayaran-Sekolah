/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author user
 */
public class IfrPembayaranQurban extends javax.swing.JInternalFrame {

     ResultSet r;
    Statement s;
    Connection c;
    private Object[][]dataSPP=null;
    private String[]label={"ID Qurban","NIS","Nama","Tanggal","Jumlah Bulan","Total Bayar"};
    
    private Object[][]dataSPP1=null;
    private String[]label1={"ID Qurban Detail","ID Qurban","NIS","Harga","Bulan","Tahun"};

    String vtahun;
    
    public IfrPembayaranQurban(String id) {
        initComponents();
        
        bukakoneksi();
        pencarian();
        pencarian1();
        jtTanggal.setDate(new java.util.Date());
        txtId.setText(id);
    }
    
    private void bukakoneksi()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ijo","root","");
            System.out.println("Koneksi Sukses");
        } 
        catch (Exception e) 
        {
            System.out.println("Koneksi Sukses");
        }
    }
    
    public JTextField GETextField(){
        return jtNIS;
        
        
    }
    public JTextField GETextField1(){
        return jtNama;
    }
    public JTextField GETextField2(){
        return jtkelas;
    }
    
    public JTextField GETextField3(){
        return txtGol;
    }
    
    public void reset(){
       jtNIS.setText("");
       jtNama.setText("");
       //jtTanggal.setText("");
       jtkelas.setText("");
       jtNIS.requestFocus();
       txtGol.setText("");
       
    }
    public void on(){
       jtNIS.setEnabled(true);
       jtNama.setEnabled(true);
       jtkelas.setEnabled(true);
       jButton1.setEnabled(true);
    }
    public void ontghn(){
       jttagi.setEnabled(true);
       jbsimpantghn.setEnabled(true);
       jbCetak.setEnabled(true);
    }
    public void ofspp(){
       jtNIS.setEnabled(false);
       jtNama.setEnabled(false);
       jtTanggal.setEnabled(false);
       jtkelas.setEnabled(false);
       jbSimpanspp.setEnabled(false);
       jButton1.setEnabled(false);
       jbTambah1.setEnabled(false);
    }
    
    public void transk(){
        try {
            s=c.createStatement();
            String sql="SELECT id_transqurban FROM tb_transqurban ORDER BY id_transqurban DESC";
            r = s.executeQuery(sql);
            if(r.next()){
            String id = r.getString("id_transqurban");
          int nomor= Integer.valueOf(id.substring(5,8));
                if(nomor<9){
                jLabel2.setText("QRB-000"+String.valueOf(nomor+1));
                }else if(nomor<99){
                jLabel2.setText("QRB-00"+String.valueOf(nomor+1));
                }else if(nomor<999){
                jLabel2.setText("QRB-0"+String.valueOf(nomor+1));
                }else{
                jLabel2.setText("QRB-"+String.valueOf(nomor+1));
                }
            }
            else{
                jLabel2.setText("QRB-0001");
            }
        } catch (Exception e) {
        }
    }
    
     public void pencarian(){
       try 
       {
           s=c.createStatement();
           String sql = "SELECT s.id_transqurban, s.nis, n.nm_siswa, s.tanggal, s.jumlahbln, "
                   + "s.totalbayar FROM tb_transqurban AS s, tb_siswa AS n "
                   + "WHERE s.nis=n.nis AND s.nis LIKE '"+jtNIS.getText()+"'"
                   + "AND s.nis=n.NIS ORDER BY s.id_transqurban DESC";
                   
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           while(r.next())
           {
               baris = r.getRow();
           }
           dataSPP= new Object[baris][kolom];
           int x=0;
           r.beforeFirst();
           while(r.next())
           {
               dataSPP[x][0]=r.getString("id_transqurban");
               dataSPP[x][1]=r.getString("nis");
               dataSPP[x][2]=r.getString("nm_siswa");
               dataSPP[x][3]=r.getString("tanggal");
               dataSPP[x][4]=r.getString("jumlahbln");
               dataSPP[x][5]=r.getString("totalbayar");
               
               
               x++;
           }
           jtbspp.setModel(new DefaultTableModel(dataSPP, label));
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    
    public void pencarian1(){
       try 
       {
           s=c.createStatement();
           String sql = "SELECT a.id_transqurbandet, a.id_transqurban,b.nis, a.harga, a.bulan ,a.tahun"
                   + " FROM tb_transqurbandet a,tb_transqurban b  "
                   + "WHERE a.id_transqurban=b.id_transqurban AND b.nis='"+jtNIS.getText()+"'"
                   + " ORDER BY a.id_transqurbandet desc";
                   
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           while(r.next())
           {
               baris = r.getRow();
           }
           dataSPP1= new Object[baris][kolom];
           int x=0;
           r.beforeFirst();
           while(r.next())
           {
               dataSPP1[x][0]=r.getString("id_transqurbandet");
               dataSPP1[x][1]=r.getString("id_transqurban");
               dataSPP1[x][2]=r.getString("NIS");
               dataSPP1[x][3]=r.getString("harga");
               dataSPP1[x][4]=r.getString("bulan");
               dataSPP1[x][5]=r.getString("tahun");
               
               x++;
           }
           jtbspp1.setModel(new DefaultTableModel(dataSPP1, label1));
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    
   private void setTahun(){
        
       String tanggal2 ="YYYY";
            SimpleDateFormat format = new SimpleDateFormat(tanggal2);
            String tampil2 = String.valueOf(format.format(jtTanggal.getDate()));
       
        if(txtThCoba.getText().equals("")){
            
            txtTahun.setText(tampil2);
        } else {
            
            txtTahun.setText(txtThCoba.getText());
            txtTahun.setEditable(false);
        }
    }
      
       private void simpantrs()
    {
        String tanggal ="YYYY-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tanggal);
        String tampil = String.valueOf(format.format(jtTanggal.getDate()));
        
        try 
       {
           String sql = "INSERT INTO tb_transqurban VALUES('"+jLabel2.getText()+"','"+jtNIS.getText()+"','"+tampil+"',0,0,'"+txtId.getText()+"')";
           s.executeUpdate(sql);
           s.close();
           //JOptionPane.showMessageDialog(null, "Tambahkan data Pembayaran");
       }
       catch (SQLException e) 
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
       
       private void updatetrs(){
        try 
       {
           String sql = "UPDATE tb_transqurban SET totalbayar='"+jttot.getText()+"', jumlahbln='"+jljumlah.getText()+"' WHERE id_transqurban='"+jLabel2.getText()+"'";
           s.executeUpdate(sql);
       }
       catch (SQLException e) 
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
       
       private void simpantghn()
    {
        try 
       {
           String sql = "INSERT INTO tb_transqurbandet VALUES(null,'"+jLabel2.getText()+"',"
                   + "'"+jttagi.getText()+"','"+lbBulan.getText()+"','"+txtTahun.getText()+"')";
           s.executeUpdate(sql);
           s.close();
           JOptionPane.showMessageDialog(null, "Biaya sukses ditambahkan");
       }
       catch (SQLException e) 
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
       
       private void setnilai(){
        try {
            String abg = "select * from tb_gol_qurban where id_gol_qurban ='"+txtGol.getText()+"'";
            s =c.createStatement();
            r = s.executeQuery(abg);
            r.next();
            String nilai = String.valueOf(r.getInt("nominal_qurban"));
            jttagi.setText(nilai);
            r.close();
        } catch (Exception e) {
        }
    }
       
       public void JumlahBulan(){
        try {
            String abg = "SELECT COUNT(harga) AS total FROM tb_transqurbandet WHERE id_transqurban ='"+jLabel2.getText()+"'";
            s =c.createStatement();
            r = s.executeQuery(abg);
            r.next();
            String nilai = String.valueOf(r.getInt("total"));
            jljumlah.setText(nilai);
            r.close();
        } catch (Exception e) {
        }
    }
       
        public void totalbayar(){
        try {
            String abg = "SELECT SUM(harga) AS total FROM tb_transqurbandet WHERE id_transqurban ='"+jLabel2.getText()+"'";
            s =c.createStatement();
            r = s.executeQuery(abg);
            r.next();
            String nilai = String.valueOf(r.getInt("total"));
            jttot.setText(nilai);
            r.close();
        } catch (Exception e) {
        }
    }
        
        public void setBulan(){
        try {
            s=c.createStatement();
            String sql="SELECT bulan FROM tb_transqurbandet a, tb_transqurban b "
                    + "where a.id_transqurban=b.id_transqurban and b.nis='"+jtNIS.getText()+"' "
                    + "ORDER BY a.id_transqurbandet DESC ";
            r = s.executeQuery(sql);
            if(r.next()){
            String id = r.getString("bulan");
                if(null != id)switch (id) {
                    case "Juli":
                        lbBulan.setText("Agustus");
                        break;
                    case "Agustus":
                        lbBulan.setText("September");
                        break;
                    case "September":
                        lbBulan.setText("Oktober");
                        break;
                    case "Oktober":
                        lbBulan.setText("November");
                        break;
                    case "November":
                        lbBulan.setText("Desember");
                        break;
                    case "Desember":
                        lbBulan.setText("Januari");
                        String nomor1 = txtTahun.getText();
                        int nomor11 = Integer.parseInt(nomor1);
                        int nomor111=nomor11+1;
                        String nomor1111=Integer.toString(nomor111);
                        txtTahun.setText(nomor1111);
                        break;
                    case "Januari":
                        lbBulan.setText("Februari");
                        break;
                    case "Februari":
                        lbBulan.setText("Maret");
                        break;
                    case "Maret":
                        lbBulan.setText("April");
                        break;
                    case "April":
                        lbBulan.setText("Mei");
                        break;
                    case "Mei":
                        lbBulan.setText("Juni");
                        break;
                    case "Juni":
                        lbBulan.setText("Juli");
                        break;
                    default:
                        break;
                }
                
            }else{
                lbBulan.setText("Juli");
            }
        } catch (Exception e) {
        }
    }
        
          private void setTahunCoba(){
        vtahun = jtbspp1.getValueAt(0, 5).toString();
        txtThCoba.setText(vtahun);
        
    }
          
          public void batasPembayaran(){
         String tanggal2 ="YYYY";
            SimpleDateFormat format = new SimpleDateFormat(tanggal2);
            String tampil2 = String.valueOf(format.format(jtTanggal.getDate()));
            
                        int lebih2 = Integer.parseInt(tampil2);
                        int lebih3=lebih2+1;
                        String lebih4=Integer.toString(lebih3);
                        //2019
                        
                        String hasil1=lbBulan.getText()+txtTahun.getText();
                        String hasil2="Juli"+lebih4;
         
                        txtHasil.setText(hasil1);
                        txtHasil2.setText(hasil2);
        }
        
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtNIS = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jbSimpanspp = new javax.swing.JButton();
        jtNama = new javax.swing.JTextField();
        jtkelas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtGol = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtTanggal = new com.toedter.calendar.JDateChooser();
        lblGol = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbspp = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbspp1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Jbkeluar = new javax.swing.JButton();
        jbTambah1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jttagi = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jbsimpantghn = new javax.swing.JButton();
        jbTambahtgn = new javax.swing.JButton();
        jbCetak = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jljumlah = new javax.swing.JLabel();
        jttot = new javax.swing.JLabel();
        jljumlah1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTahun = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lbBulan = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHasil = new javax.swing.JTextField();
        txtThCoba = new javax.swing.JTextField();
        txtHasil2 = new javax.swing.JTextField();

        setTitle("Form SPP");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/File_32px.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/smkijo48.png"))); // NOI18N

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel99.setText("Form Pembayaran Qurban");

        jLabel3.setText("Form ini digunakan untuk mengolah data pembayaran Qurban");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("NIS");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 42, -1, -1));

        jtNIS.setEditable(false);
        jtNIS.setEnabled(false);
        jPanel2.add(jtNIS, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 42, 150, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Find User Male_32px.png"))); // NOI18N
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 60, 50));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Kelas");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jbSimpanspp.setText("Membayar");
        jbSimpanspp.setEnabled(false);
        jbSimpanspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSimpansppActionPerformed(evt);
            }
        });
        jPanel2.add(jbSimpanspp, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 220, 40));

        jtNama.setEditable(false);
        jtNama.setEnabled(false);
        jPanel2.add(jtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 150, -1));

        jtkelas.setEnabled(false);
        jPanel2.add(jtkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 220, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tanggal Pembayaran");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nama Siswa");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("ID Qurban");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Detective_16px.png"))); // NOI18N
        jButton2.setText("Lihat Data");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 120, -1));

        txtGol.setEnabled(false);
        jPanel2.add(txtGol, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 220, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("QRB");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        jtTanggal.setEnabled(false);
        jPanel2.add(jtTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, -1, -1));

        lblGol.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGol.setText("Golongan");
        jPanel2.add(lblGol, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 420, 250));

        jtbspp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtbspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbsppMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbspp);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tabel Transaksi");

        jtbspp1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Qurban Detail", "ID Qurban", "Nis", "Harga", "Bulan", "Tahun"
            }
        ));
        jtbspp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbspp1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtbspp1);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Tabel Transaksi Detail");

        txtId.setEditable(false);
        txtId.setEnabled(false);

        jLabel1.setText("ID User :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel17))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(10, 10, 10))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 490, 490));

        Jbkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Cancel_32px.png"))); // NOI18N
        Jbkeluar.setText("Keluar");
        Jbkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbkeluarActionPerformed(evt);
            }
        });

        jbTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Plus_32px.png"))); // NOI18N
        jbTambah1.setText("Tambah");
        jbTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTambah1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbTambah1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Jbkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbTambah1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jbkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 490));

        jttagi.setEditable(false);
        jttagi.setEnabled(false);
        jttagi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jttagiKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Biaya Tagihan");

        jbsimpantghn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Save_32px.png"))); // NOI18N
        jbsimpantghn.setText("Simpan");
        jbsimpantghn.setEnabled(false);
        jbsimpantghn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbsimpantghnActionPerformed(evt);
            }
        });

        jbTambahtgn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Plus_17px.png"))); // NOI18N
        jbTambahtgn.setText("Tambah");
        jbTambahtgn.setEnabled(false);
        jbTambahtgn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTambahtgnActionPerformed(evt);
            }
        });

        jbCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_17px.png"))); // NOI18N
        jbCetak.setText("Cetak");
        jbCetak.setEnabled(false);
        jbCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCetakActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Ket : ");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Jumlah Bulan :");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Total Bayar    :");

        jljumlah.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jljumlah.setText("0");

        jttot.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jttot.setText("0");

        jljumlah1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jljumlah1.setText("Bulan");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Bulan");

        txtTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTahunActionPerformed(evt);
            }
        });
        txtTahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTahunKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Tahun");

        lbBulan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbBulan.setText("Bulan");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jttagi, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jbTambahtgn)
                                .addGap(81, 81, 81)
                                .addComponent(jbsimpantghn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                .addComponent(jbCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(jLabel15))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jljumlah)
                                .addGap(9, 9, 9)
                                .addComponent(jljumlah1))
                            .addComponent(jttot))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jttagi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(lbBulan))
                        .addGap(18, 18, 18)
                        .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel18))
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jljumlah1)
                    .addComponent(jljumlah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jttot))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbTambahtgn)
                    .addComponent(jbsimpantghn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbCetak))
                .addContainerGap())
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 420, 230));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/smkijo48.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtHasil.setEditable(false);
        txtHasil.setBackground(new java.awt.Color(204, 204, 204));
        txtHasil.setForeground(new java.awt.Color(204, 204, 204));
        txtHasil.setBorder(null);
        jPanel1.add(txtHasil, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 570, 21, -1));

        txtThCoba.setEditable(false);
        txtThCoba.setBackground(new java.awt.Color(204, 204, 204));
        txtThCoba.setForeground(new java.awt.Color(204, 204, 204));
        txtThCoba.setBorder(null);
        jPanel1.add(txtThCoba, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 570, 20, -1));

        txtHasil2.setEditable(false);
        txtHasil2.setBackground(new java.awt.Color(204, 204, 204));
        txtHasil2.setForeground(new java.awt.Color(204, 204, 204));
        txtHasil2.setBorder(null);
        jPanel1.add(txtHasil2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 570, 21, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(621, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1086, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel99)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(543, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cSiswa2 cs = new cSiswa2(txtId.getText());
        this.getParent().add(cs);
        cs.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbSimpansppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSimpansppActionPerformed
        if("".equals(jtNIS.getText())||"".equals(jtNama.getText())||"".equals(jtkelas.getText())){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
        }
        else{
            simpantrs();
            ontghn();
            ofspp();
            pencarian();
            pencarian1();
            jbTambah1.setEnabled(true);
            setnilai();
            setTahun();
            setBulan();
            jButton2.setEnabled(false);
            batasPembayaran();
        }
    }//GEN-LAST:event_jbSimpansppActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        jbSimpanspp.setEnabled(true);
        pencarian();
        pencarian1();
        setTahunCoba();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtbsppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbsppMouseClicked
        
    }//GEN-LAST:event_jtbsppMouseClicked

    private void jtbspp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbspp1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtbspp1MouseClicked

    private void JbkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_JbkeluarActionPerformed

    private void jbTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambah1ActionPerformed
       transk();
        on();
        reset();
        pencarian();
        pencarian1();
    }//GEN-LAST:event_jbTambah1ActionPerformed

    private void jbsimpantghnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsimpantghnActionPerformed
String tanggal2 ="YYYY";
            SimpleDateFormat format = new SimpleDateFormat(tanggal2);
            String tampil2 = String.valueOf(format.format(jtTanggal.getDate()));
            String ambiltahun = txtTahun.getText();
            int ambiltahun2 = Integer.parseInt(ambiltahun);
            int ambiltahun3 = Integer.parseInt(tampil2);
            int ambiltahun4 = ambiltahun3+1;
            
              
      
      batasPembayaran();
      
      
           if(txtHasil.getText() == null ? txtHasil2.getText() == null : txtHasil.getText().equals(txtHasil2.getText()) ){
            JOptionPane.showMessageDialog(null,"Pembayaran harus sesuai dengan tahun ajaran!");
                    
       // if("Juli".equals(lbBulan.getText())){
         //  JOptionPane.showMessageDialog(null,"Bulan masih kosong !");
           }else if(ambiltahun2<2018){
               JOptionPane.showMessageDialog(null,"Harap masukkan tahun dengan benar!");
          }else if(ambiltahun2>ambiltahun4){
               JOptionPane.showMessageDialog(null,"Pembayaran harus sesuai dengan tahun ajaran!");
        }else{
            try {
            s=c.createStatement();
            String sql="SELECT * FROM tb_transqurban a inner join tb_transqurbandet b on a.id_transqurban =b.id_transqurban where a.nis='"+jtNIS.getText()+"' "
                    + " and b.bulan='"+lbBulan.getText()+"' and b.tahun='"+txtTahun.getText()+"' ";
            r = s.executeQuery(sql);
            if(r.first()){
            JOptionPane.showMessageDialog(this, "Pembayaran Qurban dengan nis "+jtNIS.getText()+" bulan"
                    + " "+lbBulan.getText()+" tahun "+txtTahun.getText()+" sudah dibayar! ");
            }
            else{
            simpantghn();
            setnilai();
            totalbayar();
            JumlahBulan();
            updatetrs();
            pencarian();
            pencarian1();
            jbsimpantghn.setEnabled(false);
            jbTambahtgn.setEnabled(true);
            txtTahun.setEditable(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Eror Method GetDataJur : " + e);
        }
        }
    }//GEN-LAST:event_jbsimpantghnActionPerformed

    private void jbTambahtgnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambahtgnActionPerformed
       pencarian();
        pencarian1();
        setBulan();
        jbTambahtgn.setEnabled(false);
        jbsimpantghn.setEnabled(true);
        batasPembayaran();
    }//GEN-LAST:event_jbTambahtgnActionPerformed
    private static String path = System.getProperty("user.dir")+"/src/Laporan/";
    private void jbCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCetakActionPerformed
        String filename = path+"LaporanTRX_2.jrxml";
            JasperReport jasrep;
            JasperPrint japri;
            JasperViewer javie = null;
            HashMap param = new HashMap(3);
            JasperDesign jasdes;
            try {
                param.put("Transaksi",jLabel2.getText());
                File report = new File(filename);
                jasdes = JRXmlLoader.load(report);
                jasrep = JasperCompileManager.compileReport(jasdes);
                japri = JasperFillManager.fillReport(jasrep,param,Komponen.koneksi.GetConnection());
                javie.viewReport(japri,false);
            } catch (Exception e) {
                System.out.print("gagal ngprint");
            }
        
        
        
       jttagi.setEnabled(false);
       jbsimpantghn.setEnabled(false);
       jbTambahtgn.setEnabled(false);
       jbCetak.setEnabled(false);
       jbTambah1.setEnabled(true);
       txtGol.setEnabled(false);
    }//GEN-LAST:event_jbCetakActionPerformed

    private void txtTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTahunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTahunActionPerformed

    private void jttagiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jttagiKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jttagiKeyTyped

    private void txtTahunKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTahunKeyTyped
         char enter=evt.getKeyChar();  
        if(txtTahun.getText().length() == 4){
         evt.consume();
     } else if(!(Character.isDigit(enter))){//hanya bisa menginput angka
        evt.consume();
        }
    }//GEN-LAST:event_txtTahunKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jbkeluar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbCetak;
    private javax.swing.JButton jbSimpanspp;
    private javax.swing.JButton jbTambah1;
    private javax.swing.JButton jbTambahtgn;
    private javax.swing.JButton jbsimpantghn;
    private javax.swing.JLabel jljumlah;
    private javax.swing.JLabel jljumlah1;
    private javax.swing.JTextField jtNIS;
    private javax.swing.JTextField jtNama;
    private com.toedter.calendar.JDateChooser jtTanggal;
    private javax.swing.JTable jtbspp;
    private javax.swing.JTable jtbspp1;
    private javax.swing.JTextField jtkelas;
    private javax.swing.JTextField jttagi;
    private javax.swing.JLabel jttot;
    private javax.swing.JLabel lbBulan;
    private javax.swing.JLabel lblGol;
    private javax.swing.JTextField txtGol;
    private javax.swing.JTextField txtHasil;
    private javax.swing.JTextField txtHasil2;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTahun;
    private javax.swing.JTextField txtThCoba;
    // End of variables declaration//GEN-END:variables
}
