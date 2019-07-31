package Form;

import Tool.KoneksiDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrSiswa extends javax.swing.JInternalFrame {
KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    
    String sqlselect, sqlinsert, sqldelete;
    String vid_jur, vid_kelas, vnm_siswa, vjns_kel, vnis, vtmp_lahir, vtgl_lahir, 
           valamat, vno_telepon , vstatus, vgol_spp, vgol_spi, vid_ta ,vgolqurban;
    
    DefaultTableModel tblsiswa;
    int batas = 6;
    int halaman = 1;
    int posisi = 0;
    int totalHal = 0;
    
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    public IfrSiswa() {
        initComponents();
        
        clearForm();
        disableForm();
        enableForm();
        setTabelSiswa();  
        showDataSiswa();
        listJurusan(cmbJur);
        listSPI();
        listQurban();
        listTA();
        
        btnHapus.setEnabled(false);
    }
    private void clearForm(){
        txtNis.setText("");
        txtNmSiswa.setText("");
        txtTmpLahir.setText("");
        cmbJnsKel.setSelectedIndex(0);
        cmbKelas.setSelectedIndex(0);
        txtAlamat.setText("");
        txtNoHp.setText("");
        cmbStatus.setSelectedIndex(0);
        cmbSPP.setSelectedIndex(0);
        cmbSPI.setSelectedIndex(0);
        cmbJur.setSelectedIndex(0);
        cmbQurban.setSelectedIndex(0);
        cmbTA.setSelectedIndex(0);
    }
      private void disableForm(){
        txtNis.setEnabled(false);
        txtNmSiswa.setEnabled(false);
        cmbJnsKel.setEnabled(false);
        cmbKelas.setSelectedIndex(0);
        cmbJur.setSelectedIndex(0);
        txtTmpLahir.setEnabled(false);
        dtTglLahir.setEnabled(false);
        btnUbah.setEnabled(false);
        txtAlamat.setEnabled(false);
        txtNoHp.setEnabled(false);
        btnHapus.setEnabled(false);
    }
      
       private void enableForm(){
        txtNis.setEnabled(true);
        txtNmSiswa.setEnabled(true);
        cmbJnsKel.setEnabled(true);
        txtTmpLahir.setEnabled(true);
        dtTglLahir.setEnabled(true);
        txtAlamat.setEnabled(true);
        txtNoHp.setEnabled(true);
    }
      
       private void setTabelSiswa(){
        String[]kolom1 = {"NIS", "Jurusan", "Kelas", "Nama Siswa", "JK", "Tempat Lahir","Tgl Lahir", 
            "No Telepon", "Alamat", "Status", "Gol. SPP", "Gol. SPI","Qurban", "Tahun Angkatan"};
        tblsiswa= new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
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
                int cola = tblsiswa.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataSiswa.setModel(tblsiswa);
        tbDataSiswa.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(3).setPreferredWidth(150);
        tbDataSiswa.getColumnModel().getColumn(4).setPreferredWidth(25);
        tbDataSiswa.getColumnModel().getColumn(5).setPreferredWidth(90);
        tbDataSiswa.getColumnModel().getColumn(6).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(7).setPreferredWidth(100);
        tbDataSiswa.getColumnModel().getColumn(8).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(9).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(10).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(11).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(12).setPreferredWidth(75);
        tbDataSiswa.getColumnModel().getColumn(13).setPreferredWidth(75);
       
    }
    private void clearTabelSiswa(){
        int row = tblsiswa.getRowCount();
        for (int i = 0;i < row;i++){
             tblsiswa.removeRow(0);
        }
    }
    
    private void showDataSiswa(){
         try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            clearTabelSiswa();
            sqlselect =  "select * from tb_siswa a, tb_kelas b,  tb_gol_spp d, "
                    + "tb_gol_spi e, tb_thangkatan f, tb_gol_qurban g , tb_jurusan h"
                    + " where a.id_kelas=b.id_kelas "
                    + " and a.id_jur=h.id_jur "
                    + " and a.id_gol_spp=d.id_gol_spp"
                    + " and a.id_gol_spi=e.id_gol_spi"
                    + " and a.id_gol_qurban=g.id_gol_qurban "
                    + " and a.id_ta=f.id_ta order by a.nis";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vnis = res.getString("nis");
                vid_jur = res.getString("nama_jurusan");
                vid_kelas = res.getString("nama_kelas");
                vnm_siswa = res.getString("nm_siswa");
                vjns_kel = res.getString("jns_kelamin");
                vtmp_lahir = res.getString("tmp_lahir");
                vtgl_lahir = res.getString("tgl_lahir");
                vno_telepon = res.getString("no_telp");
                valamat = res.getString("alamat");
                vstatus = res.getString("status");
                vgol_spp = res.getString("nama_gol_spp");
                vgol_spi = res.getString("nama_gol_spi");
                vid_ta = res.getString("tahun_angkatan");
                vgolqurban = res.getString("nominal_qurban");
                //baru sampai sini
                Object[]data = {vnis, vid_jur,  vid_kelas, vnm_siswa, vjns_kel, vtmp_lahir, vtgl_lahir, 
                vno_telepon, valamat, vstatus, vgol_spp, vgol_spi,vgolqurban, vid_ta };
                tblsiswa.addRow(data); 
                btnTambah.setText("Tambah");
            }
            lblRecord.setText("Record : "+tbDataSiswa.getRowCount());
        }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showdataSiswa : " + ex);
            }
         
 }
    
    private void aksiSimpan(){
          vnis = txtNis.getText();
          vid_jur = KeyJurusan[cmbJur.getSelectedIndex()];
          vid_kelas = KeyKelas[cmbKelas.getSelectedIndex()];
          vnm_siswa = txtNmSiswa.getText();
          vjns_kel = cmbJnsKel.getSelectedItem().toString();
          vtmp_lahir = txtTmpLahir.getText();
          vtgl_lahir = tglinput.format(dtTglLahir.getDate());
          vno_telepon = txtNoHp.getText();
          valamat = txtAlamat.getText();
          vstatus = cmbStatus.getSelectedItem().toString();
          vgol_spp = KeySPP[cmbSPP.getSelectedIndex()];
           vgol_spi = KeySPI[cmbSPI.getSelectedIndex()];
           vgolqurban = KeyQurban[cmbQurban.getSelectedIndex()];
            vid_ta = KeyTA[cmbTA.getSelectedIndex()];
           
           try{                
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            if(btnSimpan2.getText().equals("Simpan")){
            sqlinsert = "insert into tb_siswa values "
                    + " ('"+vnis+"','"+vid_jur+"', '"+vid_kelas+"', '"+vnm_siswa+"', '"+vjns_kel+"', '"+vtmp_lahir+"', '"+vtgl_lahir+"', "
                    + " '"+vno_telepon+"', '"+valamat+"','"+vstatus+"','"+vgol_spp+"',"
                    + "'"+vgol_spi+"','"+vgolqurban+"','"+vid_ta+"') ";

             }else{
                sqlinsert = "update tb_siswa set id_jur='"+vid_jur+"' , id_kelas='"+vid_kelas+"' ,nm_siswa ='"+vnm_siswa+"', jns_kelamin ='"+vjns_kel+"', "
                       + " tmp_lahir='"+vtmp_lahir+"', tgl_lahir='"+vtgl_lahir+"', "
                       + " no_telp='"+vno_telepon+"', alamat ='"+valamat+"' ,"
                       + " status = '"+vstatus+"',id_gol_spp ='"+vgol_spp+"',"
                       + " id_gol_spi = '"+vgol_spi+"', id_gol_qurban = '"+vgolqurban+"',"
                       + " id_ta = '"+vid_ta+"' where nis='"+vnis+"' ";
               InputSiswa.dispose();
            }
             txtNis.requestFocus();
            Statement state = _Cnn.createStatement();
            state.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            clearForm(); enableForm(); showDataSiswa();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Mohon tidak memasukkan nis yang sama!");
            //JOptionPane.showMessageDialog(this, "Error method aksiSimpan : " + ex);
            
        } 
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? NIS : "+vnis,
                "Konfirmasi ",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
             try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqldelete = "delete from tb_siswa where nis= '"+vnis+"'"; 
            java.sql.Statement state = _Cnn.createStatement();
            state.executeUpdate(sqldelete);
           JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
           clearForm();disableForm();showDataSiswa();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiHapus : " + ex);
        }
        
    }   
    }
    
    private void getDataSiswa() {
             try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqlselect = "select * from tb_siswa "
                    + " where nis='"+vnis+"'"
                    + " order by nis asc ";        
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                if(res.first()){
                    txtNis.setText(res.getString("nis"));
                    cmbJur.setSelectedItem(res.getString("id_jur"));
                    cmbKelas.setSelectedItem(res.getString("id_kelas"));
                    txtNmSiswa.setText(res.getString("nm_siswa"));
                    txtTmpLahir.setText(res.getString("tmp_lahir"));
                    dtTglLahir.setDate(res.getDate("tgl_lahir"));
                    txtNoHp.setText(res.getString("no_telp"));
                    txtAlamat.setText(res.getString("alamat"));
                    
                    int row = tbDataSiswa.getSelectedRow();
                    String vjur = tbDataSiswa.getValueAt(row, 1).toString();
                    String vkelas = tbDataSiswa.getValueAt(row, 2).toString();
                    String vjk = tbDataSiswa.getValueAt(row, 4).toString();
                    String vstatus = tbDataSiswa.getValueAt(row, 9).toString();
                    String vspp = tbDataSiswa.getValueAt(row, 10).toString();
                    String vspi = tbDataSiswa.getValueAt(row, 11).toString();
                    String vqurban = tbDataSiswa.getValueAt(row, 12).toString();
                    String vta = tbDataSiswa.getValueAt(row, 13).toString();
                    cmbJur.setSelectedItem(vjur);
                    cmbKelas.setSelectedItem(vkelas);
                    cmbJnsKel.setSelectedItem(vjk);
                    cmbStatus.setSelectedItem(vstatus);
                    cmbSPP.setSelectedItem(vspp);
                    cmbSPI.setSelectedItem(vspi);
                    cmbQurban.setSelectedItem(vqurban);
                    cmbTA.setSelectedItem(vta);
                }   
                btnSimpan2.setText("Ubah Data");
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method GetDataPaien : " + ex);
            }
             
    }
    
    
    private void cariSiswa(){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                clearTabelSiswa();
                sqlselect ="select * from tb_siswa a, tb_kelas b,  tb_gol_spp d,"
                        + " tb_gol_spi e, tb_thangkatan f, tb_gol_qurban g, tb_jurusan h"
                    + " where a.id_kelas=b.id_kelas "
                    + " and a.id_jur=h.id_jur"    
                    + " and a.id_gol_spp=d.id_gol_spp"
                    + " and a.id_gol_spi=e.id_gol_spi"
                    + " and a.id_gol_qurban=g.id_gol_qurban "
                    + " and a.id_ta=f.id_ta "
                    + " and nm_siswa like '%"+txtCari.getText()+"%' order by nis asc ";     
                Statement stat = _Cnn.createStatement();
                ResultSet res = stat.executeQuery(sqlselect);
                while(res.next()){
                    
                  vnis = res.getString("nis");
                vid_jur = res.getString("nama_jurusan");
                vid_kelas = res.getString("nama_kelas");
                vnm_siswa = res.getString("nm_siswa");
                vjns_kel = res.getString("jns_kelamin");
                vtmp_lahir = res.getString("tmp_lahir");
                vtgl_lahir = res.getString("tgl_lahir");
                vno_telepon = res.getString("no_telp");
                valamat = res.getString("alamat");
                vstatus = res.getString("status");
                vgol_spp = res.getString("nama_gol_spp");
                vgol_spi = res.getString("nama_gol_spi");
                vid_ta = res.getString("tahun_angkatan");
                vgolqurban = res.getString("nominal_qurban");
                //baru sampai sini
                Object[]data = {vnis, vid_jur, vid_kelas, vnm_siswa, vjns_kel, vtmp_lahir, vtgl_lahir, 
                vno_telepon, valamat, vstatus, vgol_spp, vgol_spi,vgolqurban, vid_ta };
                tblsiswa.addRow(data);  
                }
                lblRecord.setText("Record : "+tbDataSiswa.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Eror Method showDataPasien : " + ex);
            }
    }
    String[] KeyKelas;
    private void listKelas(JComboBox cmb, String jur){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            //sqlselect = "SELECT * FROM tb_gol_spp a, tb_jurusan b where a.id_jur=b.id_jur"
             //       + " and b.nama_jurusan = '"+kel+"' order by a.id_jur";
            sqlselect = "SELECT * FROM tb_kelas a, tb_jurusan b where a.id_jur=b.id_jur"
                    + " and nama_jurusan = '"+jur+"' order by a.id_jur";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbKelas.removeAllItems();
            cmbKelas.repaint();
            cmbKelas.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbKelas.addItem(res.getString("nama_kelas"));
                i++;
            }
            res.first();
            KeyKelas = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyKelas[x] = res.getString("id_kelas");
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listKategori " +ex);
        }
    }
    
    String[] KeyJurusan, jurusan;
    private void listJurusan(JComboBox cmb){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM tb_jurusan order by id_jur asc";
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
   
    String[] KeySPP;
    private void listSPP(JComboBox cmb, String kel){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM tb_gol_spp a, tb_jurusan b where a.id_jur=b.id_jur"
                    + " and b.nama_jurusan = '"+kel+"' order by a.id_jur";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbSPP.removeAllItems();
            cmbSPP.repaint();
            cmbSPP.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbSPP.addItem(res.getString("nama_gol_spp"));
                i++;
            }
            res.first();
            KeySPP = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeySPP[x] = res.getString("id_gol_spp");
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listKategori " +ex);
        }
    }
    
    
     String[] KeySPI;
    private void listSPI(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM tb_gol_spi order by id_gol_spi asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbSPI.removeAllItems();
            cmbSPI.repaint();
            cmbSPI.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbSPI.addItem(res.getString("nama_gol_spi"));
                i++;
            }
            res.first();
            KeySPI = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeySPI[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listKategori " +ex);
        }
    }
    
     String[] KeyQurban;
    private void listQurban(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM tb_gol_qurban order by id_gol_qurban asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbQurban.removeAllItems();
            cmbQurban.repaint();
            cmbQurban.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbQurban.addItem(res.getString("nominal_qurban"));
                i++;
            }
            res.first();
            KeyQurban = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyQurban[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listKategori " +ex);
        }
    }
    
    String[] KeyTA;
    private void listTA(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "SELECT * FROM tb_thangkatan";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbTA.removeAllItems();
            cmbTA.repaint();
            cmbTA.addItem("-- Pilih --");
            int i = 1;
            while(res.next()){
                cmbTA.addItem(res.getString("tahun_angkatan"));
                i++;
            }
            res.first();
            KeyTA = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyTA[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listTA" +ex);
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InputSiswa = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        txtNmSiswa = new javax.swing.JTextField();
        txtNoHp = new javax.swing.JTextField();
        cmbJnsKel = new javax.swing.JComboBox<>();
        txtTmpLahir = new javax.swing.JTextField();
        dtTglLahir = new com.toedter.calendar.JDateChooser();
        cmbStatus = new javax.swing.JComboBox<>();
        cmbSPI = new javax.swing.JComboBox<>();
        cmbSPP = new javax.swing.JComboBox<>();
        cmbTA = new javax.swing.JComboBox<>();
        cmbQurban = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        cmbJur = new javax.swing.JComboBox<>();
        cmbKelas = new javax.swing.JComboBox<>();
        txtNis = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblRecord2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnSimpan2 = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblRecord = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        btnUbah = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbDataSiswa = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();

        InputSiswa.setTitle("Data Siswa");
        InputSiswa.setSize(new java.awt.Dimension(0, 0));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Form Input"));

        txtNmSiswa.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Nama Siswa"));
        txtNmSiswa.setOpaque(false);
        txtNmSiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNmSiswaKeyTyped(evt);
            }
        });

        txtNoHp.setToolTipText("Gunakan format semua angka (085...)");
        txtNoHp.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "No.Telepon"));
        txtNoHp.setOpaque(false);
        txtNoHp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoHpKeyTyped(evt);
            }
        });

        cmbJnsKel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "L", "P" }));
        cmbJnsKel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Jenis Kelamin"));

        txtTmpLahir.setToolTipText("Ketikan 3 huruf lalu tekan enter untuk  Kota cepat");
        txtTmpLahir.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tempat Lahir"));
        txtTmpLahir.setOpaque(false);
        txtTmpLahir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTmpLahirMouseEntered(evt);
            }
        });
        txtTmpLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTmpLahirActionPerformed(evt);
            }
        });
        txtTmpLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTmpLahirKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTmpLahirKeyTyped(evt);
            }
        });

        dtTglLahir.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tanggal Lahir"));
        dtTglLahir.setOpaque(false);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Aktif", "Mengundurkan Diri", "Drop Out" }));
        cmbStatus.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Status"));
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        cmbSPI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbSPI.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Gol. SPI"));

        cmbSPP.setMaximumRowCount(10);
        cmbSPP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbSPP.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Gol. SPP"));

        cmbTA.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbTA.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "TahunAngkatan"));

        cmbQurban.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbQurban.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Qurban"));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Alamat"));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        txtAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAlamatKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtAlamat);

        cmbJur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbJur.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Jurusan:"));
        cmbJur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJurItemStateChanged(evt);
            }
        });
        cmbJur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJurActionPerformed(evt);
            }
        });

        cmbKelas.setMaximumRowCount(12);
        cmbKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --" }));
        cmbKelas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Kelas"));
        cmbKelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbKelasItemStateChanged(evt);
            }
        });

        txtNis.setToolTipText("kode A untuk jurusan Akuntansi, \nkode I untuk jurusan RPL, \nkode O untuk jurusan TKR");
        txtNis.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "NIS"));
        txtNis.setOpaque(false);
        txtNis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNisKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbJnsKel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbKelas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNmSiswa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
                        .addComponent(cmbJur, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNis, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbStatus, 0, 218, Short.MAX_VALUE)
                    .addComponent(dtTglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNoHp)
                    .addComponent(txtTmpLahir)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSPP, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSPI, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbQurban, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTA, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 23, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cmbSPP, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbSPI, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbQurban)
                        .addGap(18, 18, 18)
                        .addComponent(cmbTA, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtNoHp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cmbJur, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTmpLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNmSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbJnsKel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jLabel8.setText("Form ini digunakan untuk mengolah data siswa");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("DATA SISWA");

        lblRecord2.setText("Record 0");

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Save_32px.png"))); // NOI18N
        btnSimpan2.setText("Simpan");
        btnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan2ActionPerformed(evt);
            }
        });

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Clear Symbol_32px.png"))); // NOI18N
        btnBatal.setText("Keluar");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(214, 214, 214))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout InputSiswaLayout = new javax.swing.GroupLayout(InputSiswa.getContentPane());
        InputSiswa.getContentPane().setLayout(InputSiswaLayout);
        InputSiswaLayout.setHorizontalGroup(
            InputSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InputSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(InputSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InputSiswaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblRecord2)
                .addGap(35, 35, 35))
        );
        InputSiswaLayout.setVerticalGroup(
            InputSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InputSiswaLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord2))
        );

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Form Siswa");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/File_32px.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DATA SISWA");

        jLabel3.setText("Form ini digunakan untuk mengolah data siswa");

        lblRecord.setText("Record 0");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));

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

        txtCari.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Cari Nama Siswa:"));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Edit_17px.png"))); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jScrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tabel Data Siswa : klik untuk mengubah/menghapus data "));

        tbDataSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NIS", "ID.Kelas", "Nama Siswa", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "No.Telp", "Alamat", "Status", "Gol SPP", "Gol SPI", "Gol Qurban"
            }
        ));
        tbDataSiswa.setRowHeight(25);
        tbDataSiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSiswaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tbDataSiswa);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/smkijo48.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane8)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(495, 695, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRecord)
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel12))
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        
        InputSiswa.setVisible(true);
        InputSiswa.setSize(750, 450);
        InputSiswa.setLocationRelativeTo(this);
        enableForm();
        clearForm();
        cmbJur.requestFocus(true);
        btnSimpan2.setText("Simpan");
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan2ActionPerformed
        if(txtNis.getText().equals("")){
            JOptionPane.showMessageDialog(this, "NIS harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE); 
            txtNis.requestFocus();
        }else if(cmbJur.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Jurusan harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbJur.requestFocus();
        }else if(cmbKelas.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Kelas harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbKelas.requestFocus();
        }else if(txtNmSiswa.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Siswa harus diisi ! ",
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtNmSiswa.requestFocus();
        }else if(cmbJnsKel.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Jenis Kelamin harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbJnsKel.requestFocus();
        }else if(txtTmpLahir.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Tempat lahir harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtTmpLahir.requestFocus();
        }else if(dtTglLahir.getDate().equals("")){
            JOptionPane.showMessageDialog(this, "Tanggal lahir harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            dtTglLahir.requestFocus();
        /*
        }else if(txtNoHp.getText().equals("")){
            JOptionPane.showMessageDialog(this, "No.Telp harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtNoHp.requestFocus();
            */
        }else if(txtAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Alamat harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            txtAlamat.requestFocus();
        }else if(cmbStatus.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Status harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbStatus.requestFocus();
        }else if(cmbSPP.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Golongan SPP harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbSPP.requestFocus();
        }else if(cmbSPI.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Golongan SPI harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbSPI.requestFocus();
        }else if(cmbQurban.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Golongan Qurban harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbQurban.requestFocus();
        }else if(cmbTA.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Tahun Angkatan harus diisi",  
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
            cmbTA.requestFocus();
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpan2ActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
         if(txtNis.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih ! ",
            "Informasi",JOptionPane.INFORMATION_MESSAGE );  
         
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        
           InputSiswa.setVisible(false); 
          
    }//GEN-LAST:event_btnBatalActionPerformed

    private void tbDataSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSiswaMouseClicked
    if(evt.getClickCount()==1){
            vnis = tbDataSiswa.getValueAt(tbDataSiswa.getSelectedRow(), 0).toString();
            btnHapus.setEnabled(true);
            btnTambah.setEnabled(true);
            btnUbah.setEnabled(true);
            getDataSiswa();
            enableForm();
             
           }
    }//GEN-LAST:event_tbDataSiswaMouseClicked

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        cariSiswa();
    }//GEN-LAST:event_txtCariKeyTyped

    private void txtNmSiswaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmSiswaKeyTyped
        char enter=evt.getKeyChar(); 
        if(txtNmSiswa.getText().replaceAll("'", "").length() == 45){
         evt.consume();
     }  else if((Character.isDigit(enter))){//hanya bisa menginput huruf
        evt.consume();
        
        
        
        }    
    }//GEN-LAST:event_txtNmSiswaKeyTyped

    private void txtNoHpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoHpKeyTyped
        char enter=evt.getKeyChar(); 
        if(txtNoHp.getText().length() == 15){
         evt.consume();
     }else if(!(Character.isDigit(enter))){//hanya bisa menginput angka
        evt.consume();
        }
    }//GEN-LAST:event_txtNoHpKeyTyped

    private void txtTmpLahirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTmpLahirKeyTyped
        if(txtTmpLahir.getText().length() == 20){
            evt.consume();
        }
    }//GEN-LAST:event_txtTmpLahirKeyTyped

    private void txtNisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNisKeyTyped
         if(txtNis.getText().length() == 9){
         evt.consume();
     }
    }//GEN-LAST:event_txtNisKeyTyped

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
       
            btnSimpan2.setText("Simpan Data");
            InputSiswa.setVisible(true);
        InputSiswa.setSize(750, 450);
        InputSiswa.setLocationRelativeTo(this);
        enableForm();
        cmbJur.setEnabled(false);
        txtNis.setEnabled(false);
        cmbKelas.requestFocus(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        
    }//GEN-LAST:event_btnUbahActionPerformed

    private void txtTmpLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTmpLahirActionPerformed
        if(txtTmpLahir.getText().equals("Pek")){
            txtTmpLahir.setText("Pekalongan");
        }else if(txtTmpLahir.getText().equals("Jak")){
            txtTmpLahir.setText("Jakarta");
    }else if(txtTmpLahir.getText().equals("Pem")){
            txtTmpLahir.setText("Pemalang");
      }else if(txtTmpLahir.getText().equals("Bat")){
            txtTmpLahir.setText("Batang");  
    }else{
            
      }     
    }//GEN-LAST:event_txtTmpLahirActionPerformed

    private void txtTmpLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTmpLahirKeyPressed
         
    }//GEN-LAST:event_txtTmpLahirKeyPressed

    private void txtTmpLahirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTmpLahirMouseEntered
      
    }//GEN-LAST:event_txtTmpLahirMouseEntered

    private void txtAlamatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatKeyTyped
        if(txtAlamat.getText().length() == 100){
            evt.consume();
        }
    }//GEN-LAST:event_txtAlamatKeyTyped

    private void cmbKelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbKelasItemStateChanged
        
    }//GEN-LAST:event_cmbKelasItemStateChanged

    private void cmbJurItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJurItemStateChanged
       
        if (cmbJur.getSelectedIndex() > 0) {
            listSPP(cmbSPP, cmbJur.getSelectedItem().toString());
            listKelas(cmbKelas, cmbJur.getSelectedItem().toString());
        
    }
     
  
    }//GEN-LAST:event_cmbJurItemStateChanged

    private void cmbJurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJurActionPerformed
         
    }//GEN-LAST:event_cmbJurActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame InputSiswa;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan2;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cmbJnsKel;
    private javax.swing.JComboBox<String> cmbJur;
    private javax.swing.JComboBox<String> cmbKelas;
    private javax.swing.JComboBox<String> cmbQurban;
    private javax.swing.JComboBox<String> cmbSPI;
    private javax.swing.JComboBox<String> cmbSPP;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbTA;
    private com.toedter.calendar.JDateChooser dtTglLahir;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JLabel lblRecord2;
    private javax.swing.JTable tbDataSiswa;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNis;
    private javax.swing.JTextField txtNmSiswa;
    private javax.swing.JTextField txtNoHp;
    private javax.swing.JTextField txtTmpLahir;
    // End of variables declaration//GEN-END:variables
}
