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
public class IfrPembayaranSPI extends javax.swing.JInternalFrame {

     ResultSet r;
    Statement s;
    Connection c;
    private Object[][]dataSPI=null;
    private String[]label={"ID SPI","NIS","Nama","Tanggal","Angsuran","Bayar"};
   String vtagihan;
    
    

    
    public IfrPembayaranSPI(String id) {
        initComponents();
        
        bukakoneksi();
        pencarian();
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
       txtBayar.setEnabled(true);
    }
    
    public void ofspp(){
       jtNIS.setEnabled(false);
       jtNama.setEnabled(false);
       jtTanggal.setEnabled(false);
       jtkelas.setEnabled(false);
       jButton1.setEnabled(false);
       jbTambah1.setEnabled(false);
    }
    
    public void transk(){
        try {
            s=c.createStatement();
            String sql="SELECT id_transspi FROM tb_transspi ORDER BY id_transspi DESC";
            r = s.executeQuery(sql);
            if(r.next()){
            String id = r.getString("id_transspi");
          int nomor= Integer.valueOf(id.substring(5,8));
                if(nomor<9){
                jLabel2.setText("SPI-000"+String.valueOf(nomor+1));
                }else if(nomor<99){
                jLabel2.setText("SPI-00"+String.valueOf(nomor+1));
                }else if(nomor<999){
                jLabel2.setText("SPI-0"+String.valueOf(nomor+1));
                }else{
                jLabel2.setText("SPI-"+String.valueOf(nomor+1));
                }
            }
            else{
                jLabel2.setText("SPI-0001");
            }
        } catch (Exception e) {
        }
    }
    
    public void angsur(){
        try {
            s=c.createStatement();
            String sql="SELECT angsuran FROM tb_transspi where nis='"+jtNIS.getText()+"' ORDER BY angsuran DESC ";
            //ORDER BY id_transspi DESC
            r = s.executeQuery(sql);
            if(r.next()){
            String id = r.getString("angsuran");
          int nomor= Integer.valueOf(id.substring(12,13));
                if(nomor<9){
                labelAngsuran.setText("Angsuran ke "+String.valueOf(nomor+1));
                //angsuran ke 
                }
            }
            else{
                labelAngsuran.setText("Angsuran ke 1");
            }
        } catch (Exception e) {
        }
    }
    
     public void pencarian(){
       try 
       {
           s=c.createStatement();
           String sql = "SELECT s.id_transspi, s.nis, n.nm_siswa, s.tanggal, s.angsuran, "
                   + "s.bayar FROM tb_transspi AS s, tb_siswa AS n "
                   + "WHERE s.nis=n.nis AND s.nis LIKE '"+jtNIS.getText()+"'"
                   + "AND s.nis=n.NIS ORDER BY s.id_transspi DESC";
                   
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           while(r.next())
           {
               baris = r.getRow();
           }
           dataSPI= new Object[baris][kolom];
           int x=0;
           r.beforeFirst();
           while(r.next())
           {
               dataSPI[x][0]=r.getString("id_transspi");
               dataSPI[x][1]=r.getString("nis");
               dataSPI[x][2]=r.getString("nm_siswa");
               dataSPI[x][3]=r.getString("tanggal");
               dataSPI[x][4]=r.getString("angsuran");
               dataSPI[x][5]=r.getString("bayar");
               
               
               x++;
           }
           jtbspi.setModel(new DefaultTableModel(dataSPI, label));
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    
        private void setTagihan1(){
         int jumlah = jtbspi.getRowCount();  
         int sumTotal =0;
         for (int i = 0; i < jumlah; i++) {
            
            int dataTotal = Integer.valueOf(jtbspi.getValueAt(i, 5).toString());
            
            sumTotal += dataTotal;
        }
        //vtagihan = jtbspi.getValueAt(0, 5).toString();
        txtThCoba.setText(Integer.toString(sumTotal));  
        
    }
        
           private void setTagihan2(){
        if(txtThCoba.getText().equals("")){
            
            txtKurang.setText(jttagi.getText());
        } else {
            int nomor1 = Integer.parseInt(jttagi.getText());
            int nomor2 = Integer.parseInt(txtThCoba.getText());
            int nomor3 = nomor1-nomor2;
            String nomor4=Integer.toString(nomor3);
            txtKurang.setText(nomor4);
        }
    }
    
       private void simpantrs()
    {
        
        String tanggal ="YYYY-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tanggal);
        String tampil = String.valueOf(format.format(jtTanggal.getDate()));
        
        try 
       {
           String sql = "INSERT INTO tb_transspi VALUES('"+jLabel2.getText()+"',"
                   + "'"+jtNIS.getText()+"','"+tampil+"','"+labelAngsuran.getText()+"',"
                   + "'"+txtBayar.getText()+"','"+txtId.getText()+"')";
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
            String abg = "select * from tb_gol_spi where nama_gol_spi ='"+txtGol.getText()+"'";
            s =c.createStatement();
            r = s.executeQuery(abg);
            r.next();
            String nilai = String.valueOf(r.getInt("nominal_spi"));
            jttagi.setText(nilai);
            r.close();
        } catch (Exception e) {
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

        jLabel4 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jtNIS = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
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
        jttagi = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jbSimpanspp = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbspi = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtThCoba = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        Jbkeluar = new javax.swing.JButton();
        jbTambah1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jbsimpantghn = new javax.swing.JButton();
        jbCetak = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        lblBayar = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        labelAngsuran = new javax.swing.JLabel();
        txtKurang = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setTitle("Form SPI");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/File_32px.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/smkijo48.png"))); // NOI18N

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel99.setText("Form Pembayaran SPI");

        jLabel3.setText("Form ini digunakan untuk mengolah data pembayaran SPI");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("NIS");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 42, -1, -1));

        jtNIS.setEditable(false);
        jtNIS.setEnabled(false);
        jPanel2.add(jtNIS, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 140, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Find User Male_32px.png"))); // NOI18N
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 60, 50));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Kelas");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jtNama.setEditable(false);
        jtNama.setEnabled(false);
        jPanel2.add(jtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 140, -1));

        jtkelas.setEditable(false);
        jtkelas.setEnabled(false);
        jPanel2.add(jtkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 210, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tanggal Pembayaran");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nama Siswa");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("ID SPI");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Detective_16px.png"))); // NOI18N
        jButton2.setText("Lihat Data");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 110, -1));

        txtGol.setEditable(false);
        txtGol.setEnabled(false);
        jPanel2.add(txtGol, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 210, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("SPI-");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        jtTanggal.setEnabled(false);
        jPanel2.add(jtTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, -1, -1));

        lblGol.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGol.setText("Golongan");
        jPanel2.add(lblGol, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jttagi.setEditable(false);
        jttagi.setEnabled(false);
        jPanel2.add(jttagi, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 210, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Biaya Tagihan");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jbSimpanspp.setText("Membayar");
        jbSimpanspp.setEnabled(false);
        jbSimpanspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSimpansppActionPerformed(evt);
            }
        });
        jPanel2.add(jbSimpanspp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 210, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 380, 290));

        jPanel3.setForeground(new java.awt.Color(255, 51, 0));

        jtbspi.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbspi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbspiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbspi);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tabel Transaksi");

        txtId.setEditable(false);
        txtId.setEnabled(false);

        jLabel1.setText("ID User :");

        txtThCoba.setBackground(new java.awt.Color(240, 240, 240));
        txtThCoba.setForeground(new java.awt.Color(240, 240, 240));
        txtThCoba.setBorder(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 418, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtThCoba, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(txtThCoba))
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 530, 490));

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

        jbsimpantghn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Save_32px.png"))); // NOI18N
        jbsimpantghn.setText("Simpan");
        jbsimpantghn.setEnabled(false);
        jbsimpantghn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbsimpantghnActionPerformed(evt);
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

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Angsuran");

        lblBayar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBayar.setText("Bayar");

        txtBayar.setEnabled(false);
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBayarKeyTyped(evt);
            }
        });

        labelAngsuran.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelAngsuran.setText("Angsuran ke");

        txtKurang.setEditable(false);
        txtKurang.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Kekurangan Tagihan");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jbsimpantghn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jbCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBayar)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAngsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtKurang, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                .addComponent(txtBayar)))))
                .addGap(16, 16, 16))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKurang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBayar))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAngsuran)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbsimpantghn)
                    .addComponent(jbCetak))
                .addGap(35, 35, 35))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 380, 190));

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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cSiswa1 cs = new cSiswa1(txtId.getText());
        this.getParent().add(cs);
        cs.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jbSimpanspp.setEnabled(true);
        pencarian();
        setnilai();
        setTagihan1();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jtbspiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbspiMouseClicked
        
    }//GEN-LAST:event_jtbspiMouseClicked

    private void JbkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_JbkeluarActionPerformed

    private void jbTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambah1ActionPerformed
       transk();
        on();
        reset();
        pencarian();
    }//GEN-LAST:event_jbTambah1ActionPerformed

    private void jbsimpantghnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsimpantghnActionPerformed
        if("".equals(txtBayar.getText())){
            JOptionPane.showMessageDialog(null,"Jumlah bayar belum diisi!");
        }
        
        else if ("-- Pilih --".equals(labelAngsuran.getText())){
            JOptionPane.showMessageDialog(null,"Angsuran belum diisi!");
        
        }else if(txtKurang.getText().equals("0")){
                    JOptionPane.showMessageDialog(null,"Pembayaran sudah lunas");
        }else if(Integer.parseInt(txtBayar.getText())>Integer.parseInt(txtKurang.getText())){
                    JOptionPane.showMessageDialog(null,"Jumlah pembayaran melebihi tagihan!");            
        }else{
            simpantrs();
            setnilai();
            pencarian();
            jbsimpantghn.setEnabled(false);
            jbSimpanspp.setEnabled(false);
        }
    }//GEN-LAST:event_jbsimpantghnActionPerformed
    private static String path = System.getProperty("user.dir")+"/src/Laporan/";
    private void jbCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCetakActionPerformed
        String filename = path+"LaporanTRX_1.jrxml";
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
       jbCetak.setEnabled(false);
       jbTambah1.setEnabled(true);
       txtGol.setEnabled(false);
    }//GEN-LAST:event_jbCetakActionPerformed

    private void txtBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyTyped
         char enter=evt.getKeyChar();  
        if(txtBayar.getText().length() == 8){
         evt.consume();
     } else if(!(Character.isDigit(enter))){//hanya bisa menginput angka
        evt.consume();
        }
    }//GEN-LAST:event_txtBayarKeyTyped

    private void jbSimpansppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSimpansppActionPerformed
        if("".equals(jtNIS.getText())||"".equals(jtNama.getText())||"".equals(jtkelas.getText())){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
        }
        else{
            ofspp();
            pencarian();
            jbTambah1.setEnabled(true);
            jbsimpantghn.setEnabled(true);
            jbCetak.setEnabled(true);
            setnilai();
            angsur();
            setTagihan2();
            jButton2.setEnabled(false);
        }
    }//GEN-LAST:event_jbSimpansppActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jbkeluar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JButton jbCetak;
    private javax.swing.JButton jbSimpanspp;
    private javax.swing.JButton jbTambah1;
    private javax.swing.JButton jbsimpantghn;
    private javax.swing.JTextField jtNIS;
    private javax.swing.JTextField jtNama;
    private com.toedter.calendar.JDateChooser jtTanggal;
    private javax.swing.JTable jtbspi;
    private javax.swing.JTextField jtkelas;
    private javax.swing.JTextField jttagi;
    private javax.swing.JLabel labelAngsuran;
    private javax.swing.JLabel lblBayar;
    private javax.swing.JLabel lblGol;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtGol;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKurang;
    private javax.swing.JTextField txtThCoba;
    // End of variables declaration//GEN-END:variables
}
