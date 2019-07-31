/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Tool.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class IfrMasterSPP extends javax.swing.JInternalFrame {

    KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vid_gol_spp, vnm_jur, vnama_gol_spp, vnominal_spp;
    
    DefaultTableModel tblSPP;
    
    public IfrMasterSPP() {
        initComponents();
        
        clearForm();
        disableForm();
        setTabelSPP();
        showDataSPP();
        listJurusan();
    }
    
     private void clearForm(){
       
        txtIDSPP.setText("");
        txtNamaSPP.setText("");
        txtNominalSPP.setText("");
        cmbJur.setSelectedIndex(0);
    }
     
     private void disableForm(){
        txtIDSPP.setEnabled(false);
        txtNamaSPP.setEnabled(false);
        txtNominalSPP.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
        cmbJur.setEnabled(false);
    }
     
      private void enableForm(){
        txtIDSPP.setEnabled(false);
        txtNamaSPP.setEnabled(true);
        txtNominalSPP.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
        cmbJur.setEnabled(true);
    }
     
     private void setTabelSPP(){
        String[]kolom1 = {"ID. Gol. SPP","Jurusan", "Nama Gol. SPP", "Nominal SPP"};
        tblSPP = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
                    
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            // agar tabel tidak bisa diedit
            public boolean isCellEditable(int row, int col) {
                int cola = tblSPP.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataSPP.setModel(tblSPP);
        tbDataSPP.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataSPP.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataSPP.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataSPP.getColumnModel().getColumn(3).setPreferredWidth(75);
    }
     
     private void clearTabelSPP(){
        int row = tblSPP.getRowCount();
        for (int i = 0;i < row;i++){
             tblSPP.removeRow(0);
        }
    }
     
      private void showDataSPP(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelSPP();
            sqlselect =  "select * from tb_gol_spp a, tb_jurusan b "
                    + " where a.id_jur=b.id_jur"
                    + " order by a.id_gol_spp asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_gol_spp = res.getString("id_gol_spp");
                vnm_jur = res.getString("nama_jurusan");
                vnama_gol_spp = res.getString("nama_gol_spp");
                vnominal_spp = res.getString("nominal_spp");
                Object[]data = {vid_gol_spp, vnm_jur, vnama_gol_spp, vnominal_spp};
                tblSPP.addRow(data);
            }
                 btnTambah.setText("Tambah");
            lblRecord.setText("Record : "+tbDataSPP.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method : " + ex);
            }
    }
      
     
      
      private void aksiSimpan(){
          vid_gol_spp = txtIDSPP.getText();
          vnm_jur = KeyJurusan[cmbJur.getSelectedIndex()];
          vnama_gol_spp = txtNamaSPP.getText();
          vnominal_spp = txtNominalSPP.getText();
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
           if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tb_gol_spp values "
                    + " ('"+vid_gol_spp+"','"+vnama_gol_spp+"', '"+vnominal_spp+"','"+vnm_jur+ "') ";
            
            
           }else{
               sqlinsert = "update tb_gol_spp set "
                        + " nama_gol_spp='"+vnama_gol_spp+"', "
                       + "nominal_spp='"+vnominal_spp+"', "
                       + "id_jur='"+vnm_jur+"'"
                            +"where id_gol_spp='"+vid_gol_spp+"'";
               
           }
           
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(this, "Data Berhasil disimpan");
            clearForm(); disableForm(); showDataSPP();
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/Icon/Add User Group Woman Man_32px.png")));
        }catch(SQLException ex){
            //JOptionPane.showMessageDialog(this, "Error Method aksiSimpan() : "+ex);
            JOptionPane.showMessageDialog(this, "Mohon tidak memasukkan Nama Golongan SPP yang sama!");
        } 
    }
      
      private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? ID : "+vid_gol_spp,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from tb_gol_spp where id_gol_spp= '"+vid_gol_spp+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showDataSPP(); 
                btnHapus.setEnabled(false);
                btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/Icon/Add User Group Woman Man_32px.png")));
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }
        
    }   
      
          private void createAutoID(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select max(id_gol_spp) from tb_gol_spp";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.first()){
                Integer no = res.getInt(1)+1;
                vid_gol_spp = no.toString();
                txtIDSPP.setText(vid_gol_spp);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error method createAutoID() : " 
                    + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
          
              String[] KeyJurusan;
    private void listJurusan(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM tb_jurusan";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbJur.removeAllItems();
            cmbJur.repaint();
            cmbJur.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbJur.addItem(res.getString("nama_jurusan"));
                i++;
            }
            res.first();
            KeyJurusan = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyJurusan[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listJurusan" +ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNominalSPP = new javax.swing.JTextField();
        txtNamaSPP = new javax.swing.JTextField();
        txtIDSPP = new javax.swing.JTextField();
        cmbJur = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataSPP = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Form Master SPP");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/File_32px.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/smkijo48.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Form Master SPP");

        jLabel3.setText("Form ini digunakan untuk mengolah master SPP");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Form input :"));
        jPanel1.setOpaque(false);

        txtNominalSPP.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nominal SPP"));
        txtNominalSPP.setOpaque(false);
        txtNominalSPP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNominalSPPKeyTyped(evt);
            }
        });

        txtNamaSPP.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Gol. SPP"));
        txtNamaSPP.setOpaque(false);
        txtNamaSPP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNamaSPPKeyTyped(evt);
            }
        });

        txtIDSPP.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "ID Gol. SPP"));
        txtIDSPP.setOpaque(false);
        txtIDSPP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDSPPKeyTyped(evt);
            }
        });

        cmbJur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbJur.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Jurusan:"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtIDSPP, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbJur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNamaSPP, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNominalSPP, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIDSPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbJur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNominalSPP, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(txtNamaSPP)))
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel14.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Plus_17px.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Clear Symbol_32px.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Save_32px.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tabel Data Kelas : klik 2x untuk mengubah/menghapus data "));

        tbDataSPP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "ID. Gol. SPP", "Nama Gol. SPP", "Nominal SPP"
            }
        ));
        tbDataSPP.setRowHeight(25);
        tbDataSPP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSPPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataSPP);

        lblRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecord.setText("Record : 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRecord)
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1))
                .addGap(126, 126, 126)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(62, 62, 62)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(347, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNominalSPPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNominalSPPKeyTyped
         char enter=evt.getKeyChar(); 
        if(txtNominalSPP.getText().length() == 6){
         evt.consume();
     }
        else if(!(Character.isDigit(enter))){//hanya bisa menginput angka
        evt.consume();
        
        
        
        }
    }//GEN-LAST:event_txtNominalSPPKeyTyped

    private void txtNamaSPPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaSPPKeyTyped
        if(txtNamaSPP.getText().length() == 20){
         evt.consume();
     }
    }//GEN-LAST:event_txtNamaSPPKeyTyped

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed

         
        if(btnTambah.getText().equals("Tambah")){
            enableForm();
            createAutoID();
            btnHapus.setEnabled(false);
            txtNamaSPP.requestFocus(true);
        btnSimpan.setText("Simpan");
            btnTambah.setText("Batal");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icon/Cancel_17px.png")));
        }else if(btnTambah.getText().equals("Batal")){
        clearForm();
        disableForm();
        btnTambah.setText("Tambah");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icon/Plus_17px.png")));
        }
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
         if(txtIDSPP.getText().equals("")){
            JOptionPane.showMessageDialog(this, "ID Golongan SPP harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbJur.getSelectedItem().equals("-- Pilih --")){
            JOptionPane.showMessageDialog(this, "Nama Jurusan harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else if(txtNamaSPP.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Golongan SPP harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else if(txtNominalSPP.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nominal SPP harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed

         if(txtIDSPP.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataSPPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSPPMouseClicked
        if(evt.getClickCount()==2){
              
              int row = tbDataSPP.getSelectedRow();
            
            vid_gol_spp = tbDataSPP.getValueAt(row, 0).toString();
            String vjurusan = tbDataSPP.getValueAt(row, 1).toString();
            vnama_gol_spp = tbDataSPP.getValueAt(row, 2).toString();
            vnominal_spp = tbDataSPP.getValueAt(row, 3).toString();
            
              txtIDSPP.setText(vid_gol_spp);
              cmbJur.setSelectedItem(vjurusan);
            txtNamaSPP.setText(vnama_gol_spp); 
            txtNominalSPP.setText(vnominal_spp); 
               txtNamaSPP.requestFocus();
            
            btnSimpan.setText("Ubah");
            
            
            txtIDSPP.setEnabled(false);
        txtNamaSPP.setEnabled(true);
        txtNominalSPP.setEnabled(true);
        btnSimpan.setEnabled(true);
        cmbJur.setEnabled(false);
        btnHapus.setEnabled(true);
            
            
            btnTambah.setText("Batal");
            btnTambah.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/Icon/Cancel_17px.png")));
        }
    }//GEN-LAST:event_tbDataSPPMouseClicked

    private void txtIDSPPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDSPPKeyTyped
         if(txtIDSPP.getText().length() == 4){
         evt.consume();
     }
    }//GEN-LAST:event_txtIDSPPKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbJur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tbDataSPP;
    private javax.swing.JTextField txtIDSPP;
    private javax.swing.JTextField txtNamaSPP;
    private javax.swing.JTextField txtNominalSPP;
    // End of variables declaration//GEN-END:variables
}
