/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Tool.KoneksiDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author user
 */
public class FrMenu extends javax.swing.JFrame {

    KoneksiDB getCnn = new KoneksiDB();
    Connection _Cnn;
    
    String vid_user, vusername, vpassword, vlevel_user;
    String sqlselect;
     
     
    
    public FrMenu() {
        initComponents();
        this.setExtendedState(this.getExtendedState() | FrMenu.MAXIMIZED_BOTH);
        TanggalOtomatis();
        setJam();
        disabledMenu();
        panelLogin.setVisible(true);
        txtIDUser.requestFocus();
    }
    
    private void enableMenu() { 
        mnSistem.setEnabled(true);
        mnUser.setEnabled(false);
        mnKeluar.setEnabled(true);
        mnMaster.setEnabled(true);
        mnLaporan.setEnabled(true);
        mnPembayaran.setEnabled(true);
    }
    
    private void enableMenu1() { 
        mnSistem.setEnabled(true);
        mnUser.setEnabled(true);
        mnKeluar.setEnabled(true);
        mnLaporan.setEnabled(true);
        mnMaster.setEnabled(false);
        mnPembayaran.setEnabled(false);
    }
        
    private void enableMenu2() { 
        mnSistem.setEnabled(true);
        mnUser.setEnabled(false);
        mnMaster.setEnabled(false);
        mnLaporan.setEnabled(true);
        mnPembayaran.setEnabled(true);
    }
    
    private void disabledMenu() { 
        mnSistem.setEnabled(false);
        mnMaster.setEnabled(false);
        mnLaporan.setEnabled(false);
        mnPembayaran.setEnabled(false);
    }
    
    private void clearLogin() {
        txtIDUser.setText("");
        txtPassword.setText("");
    }
    
    private void aksiLogin() {
        if(txtIDUser.getText().equals("")){
            JOptionPane.showMessageDialog(this,"ID. User  harus diisi!");
        }else if (txtPassword.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Password  harus diisi!");
    }else{
            vid_user = txtIDUser.getText().replaceAll("'", "");
            vpassword = txtPassword.getText().replaceAll("'", "");
            try{
                _Cnn = null;    
                _Cnn = getCnn.getConnection();  
                sqlselect = "select * from tb_user where id_user='"+vid_user+"' "
                        + " and password=md5('"+vpassword+"') ";
                Statement stat = _Cnn.createStatement();    
                ResultSet res = stat.executeQuery(sqlselect);   
                if(res.first()) {   
                    vlevel_user = res.getString("level_user");  
                    vusername = res.getString("username");
                    lblKeterangan.setText("ID. User : "+vid_user+" - "+vusername+
                            "   | Lev. User : "+vlevel_user);
                    panelLogin.setVisible(false);
                    
                    if(vlevel_user.equals("Kepala Sekolah")){
                        enableMenu1();
                    }else if(vlevel_user.equals("Bendahara")){
                     enableMenu();   
                    }else if(vlevel_user.equals("Tata Usaha")){
                       enableMenu2(); 
                    }
                    
                }else{
                   JOptionPane.showMessageDialog(this,"Mohon periksa kembali ID. User dan Password !"); 
                }
            }catch (SQLException se){
                JOptionPane.showMessageDialog(this, "Error method aksiLogin() : " + se);
            }
        }
    }
    
    private void aksiKeluar(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin ingin keluar ",
                "Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
        panelMenu.removeAll();
        panelMenu.repaint();
        panelLogin.setVisible(true);
        txtIDUser.setText("");
        txtPassword.setText("");
        txtIDUser.requestFocus(true);
            disabledMenu(); 
            lblKeterangan.setText("ID. User : .....| Lev. User :.....");
        }
    }
    
    public void TanggalOtomatis(){
      Date tanggal = new Date();
      tgl.setText(""+ (String.format("%1$td-%1$tm-%1$tY",tanggal)));
  
} 
    
     public void setJam() {
        ActionListener taskPerformer = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        String nol_jam = "", nol_menit = "", nol_detik = "";

        Date dateTime = new Date();
        int nilai_jam = dateTime.getHours();
        int nilai_menit = dateTime.getMinutes();
        int nilai_detik = dateTime.getSeconds();

        if (nilai_jam <= 9) nol_jam = "0";
        if (nilai_menit <= 9) nol_menit = "0";
        if (nilai_detik <= 9) nol_detik = "0";

        String jam = nol_jam + Integer.toString(nilai_jam);
        String menit = nol_menit + Integer.toString(nilai_menit);
        String detik = nol_detik + Integer.toString(nilai_detik);

        lbljam.setText(jam + ":" + menit + ":" + detik + " ");
        
    }
      };
      new Timer(1000, taskPerformer).start();
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        lblKeterangan = new javax.swing.JLabel();
        tgl = new javax.swing.JLabel();
        lbljam = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelLogin = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        txtIDUser = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnSistem = new javax.swing.JMenu();
        mnUser = new javax.swing.JMenuItem();
        mnKeluar = new javax.swing.JMenuItem();
        mnMaster = new javax.swing.JMenu();
        mnTA = new javax.swing.JMenuItem();
        mnJurusan = new javax.swing.JMenuItem();
        mnKelas = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnSiswa = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnMasterSPP = new javax.swing.JMenuItem();
        mnMasterSPI = new javax.swing.JMenuItem();
        mnMasterQurban = new javax.swing.JMenuItem();
        mnPembayaran = new javax.swing.JMenu();
        mnSPP = new javax.swing.JMenuItem();
        mnSPI = new javax.swing.JMenuItem();
        mnQurban = new javax.swing.JMenuItem();
        mnLaporan = new javax.swing.JMenu();
        mnLapSPPSiswa = new javax.swing.JMenuItem();
        mnLapSPPKelas = new javax.swing.JMenuItem();
        mnLapSPPSeluruh = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnLapSPISiswa = new javax.swing.JMenuItem();
        mnLapSPIKelas = new javax.swing.JMenuItem();
        mnLapSPISeluruh = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnLapQurbanSiswa = new javax.swing.JMenuItem();
        mnLapQurbanKelas = new javax.swing.JMenuItem();
        mnLapQurbanSeluruh = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEM INFORMASI PEMBAYARAN SEKOLAH SMK ISLAM BOJONG");
        setBackground(new java.awt.Color(255, 255, 255));

        panelMenu.setBackground(new java.awt.Color(247, 247, 247));

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblKeterangan.setText("ID. User : .....| Lev. User :.....");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(864, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblKeterangan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tgl.setBackground(new java.awt.Color(255, 255, 255));
        tgl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tgl.setText("Tanggal");

        lbljam.setBackground(new java.awt.Color(255, 255, 255));
        lbljam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbljam.setText("Jam");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/smkijo150px.png"))); // NOI18N

        txtIDUser.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "ID. User :"));
        txtIDUser.setOpaque(false);
        txtIDUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDUserActionPerformed(evt);
            }
        });

        txtPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Password :"));
        txtPassword.setOpaque(false);
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Login As User_32px.png"))); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Cancel_32px.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jLabel2.setText("-{ Pembayaran Sekolah }-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatal)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDUser, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(24, 24, 24)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIDUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        panelLogin.addTab("Login User", new javax.swing.ImageIcon(getClass().getResource("/Icon/User Shield_32px.png")), jPanel2); // NOI18N

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel4.setText("Sistem Informasi Pembayaran Sekolah");

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel5.setText("SMK Islam Bojong");

        mnSistem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Inconsistency_48px.png"))); // NOI18N
        mnSistem.setText("Sistem");
        mnSistem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSistemActionPerformed(evt);
            }
        });

        mnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Add User Group Woman Man_16px.png"))); // NOI18N
        mnUser.setText("User");
        mnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUserActionPerformed(evt);
            }
        });
        mnSistem.add(mnUser);

        mnKeluar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        mnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Delete_20px.png"))); // NOI18N
        mnKeluar.setText("Keluar");
        mnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnKeluarActionPerformed(evt);
            }
        });
        mnSistem.add(mnKeluar);

        jMenuBar1.add(mnSistem);

        mnMaster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/2 - Master.png"))); // NOI18N
        mnMaster.setText("Master");
        mnMaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMasterActionPerformed(evt);
            }
        });

        mnTA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/2 - master small.png"))); // NOI18N
        mnTA.setText("Tahun Angkatan");
        mnTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTAActionPerformed(evt);
            }
        });
        mnMaster.add(mnTA);

        mnJurusan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/2 - master small.png"))); // NOI18N
        mnJurusan.setText("Jurusan");
        mnJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnJurusanActionPerformed(evt);
            }
        });
        mnMaster.add(mnJurusan);

        mnKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/2 - master small.png"))); // NOI18N
        mnKelas.setText("Kelas");
        mnKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnKelasActionPerformed(evt);
            }
        });
        mnMaster.add(mnKelas);
        mnMaster.add(jSeparator1);

        mnSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Student Male_20px.png"))); // NOI18N
        mnSiswa.setText("Siswa");
        mnSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSiswaActionPerformed(evt);
            }
        });
        mnMaster.add(mnSiswa);
        mnMaster.add(jSeparator2);

        mnMasterSPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Coins_20px.png"))); // NOI18N
        mnMasterSPP.setText("Master SPP");
        mnMasterSPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMasterSPPActionPerformed(evt);
            }
        });
        mnMaster.add(mnMasterSPP);

        mnMasterSPI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Coins_20px.png"))); // NOI18N
        mnMasterSPI.setText("Master SPI");
        mnMasterSPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMasterSPIActionPerformed(evt);
            }
        });
        mnMaster.add(mnMasterSPI);

        mnMasterQurban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Coins_20px.png"))); // NOI18N
        mnMasterQurban.setText("Master Qurban");
        mnMasterQurban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnMasterQurbanActionPerformed(evt);
            }
        });
        mnMaster.add(mnMasterQurban);

        jMenuBar1.add(mnMaster);

        mnPembayaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Cash in Hand_48px.png"))); // NOI18N
        mnPembayaran.setText("Pembayaran");

        mnSPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/SPP_20px.png"))); // NOI18N
        mnSPP.setText("SPP");
        mnSPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSPPActionPerformed(evt);
            }
        });
        mnPembayaran.add(mnSPP);

        mnSPI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/SPI_20px.png"))); // NOI18N
        mnSPI.setText("SPI");
        mnSPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSPIActionPerformed(evt);
            }
        });
        mnPembayaran.add(mnSPI);

        mnQurban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Qurban_20px.png"))); // NOI18N
        mnQurban.setText("Qurban");
        mnQurban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnQurbanActionPerformed(evt);
            }
        });
        mnPembayaran.add(mnQurban);

        jMenuBar1.add(mnPembayaran);

        mnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Accounting_48px.png"))); // NOI18N
        mnLaporan.setText("Laporan");
        mnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLaporanActionPerformed(evt);
            }
        });

        mnLapSPPSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapSPPSiswa.setText("Laporan SPP Per Siswa");
        mnLapSPPSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapSPPSiswaActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapSPPSiswa);

        mnLapSPPKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapSPPKelas.setText("Laporan SPP Per Kelas");
        mnLapSPPKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapSPPKelasActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapSPPKelas);

        mnLapSPPSeluruh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapSPPSeluruh.setText("Laporan SPP Seluruh");
        mnLapSPPSeluruh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapSPPSeluruhActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapSPPSeluruh);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        jMenuItem2.setText("Laporan SPP Bulan");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnLaporan.add(jMenuItem2);
        mnLaporan.add(jSeparator3);

        mnLapSPISiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapSPISiswa.setText("Laporan SPI Per Siswa");
        mnLapSPISiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapSPISiswaActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapSPISiswa);

        mnLapSPIKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapSPIKelas.setText("Laporan SPI Per Kelas");
        mnLapSPIKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapSPIKelasActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapSPIKelas);

        mnLapSPISeluruh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapSPISeluruh.setText("Laporan SPI Seluruh");
        mnLapSPISeluruh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapSPISeluruhActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapSPISeluruh);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        jMenuItem1.setText("Laporan Tagihan SPI");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnLaporan.add(jMenuItem1);
        mnLaporan.add(jSeparator4);

        mnLapQurbanSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapQurbanSiswa.setText("Laporan Qurban Per Siswa");
        mnLapQurbanSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapQurbanSiswaActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapQurbanSiswa);

        mnLapQurbanKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapQurbanKelas.setText("Laporan Qurban Per Kelas");
        mnLapQurbanKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapQurbanKelasActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapQurbanKelas);

        mnLapQurbanSeluruh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Print_20px.png"))); // NOI18N
        mnLapQurbanSeluruh.setText("Laporan Qurban Seluruh");
        mnLapQurbanSeluruh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLapQurbanSeluruhActionPerformed(evt);
            }
        });
        mnLaporan.add(mnLapQurbanSeluruh);
        mnLaporan.add(jSeparator5);

        jMenuBar1.add(mnLaporan);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelMenu)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(lbljam, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(43, 43, 43))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(58, 58, 58))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(tgl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbljam)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addComponent(panelMenu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        panelMenu.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUserActionPerformed
        panelMenu.removeAll();
        panelMenu.repaint();
        IfrUser fr = new IfrUser();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnUserActionPerformed

    private void mnTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTAActionPerformed
         panelMenu.removeAll();
        panelMenu.repaint();     
        IfrTahunAngkatan fr = new IfrTahunAngkatan();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnTAActionPerformed

    private void mnJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnJurusanActionPerformed
        panelMenu.removeAll();
        panelMenu.repaint();
        IfrJurusan fr = new IfrJurusan();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnJurusanActionPerformed

    private void mnKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnKelasActionPerformed
        panelMenu.removeAll();
        panelMenu.repaint();
         IfrKelas fr = new IfrKelas();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnKelasActionPerformed

    private void txtIDUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDUserActionPerformed
        txtPassword.requestFocus();
    }//GEN-LAST:event_txtIDUserActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
       aksiLogin();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clearLogin();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void mnMasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMasterActionPerformed
       
    }//GEN-LAST:event_mnMasterActionPerformed

    private void mnSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSiswaActionPerformed
        panelMenu.removeAll();
        panelMenu.repaint();
        IfrSiswa fr = new IfrSiswa();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnSiswaActionPerformed

    private void mnSPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSPPActionPerformed
        panelMenu.removeAll();
        panelMenu.repaint();
        IfrPembayaranSPP fr = new IfrPembayaranSPP(vid_user);
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnSPPActionPerformed

    private void mnSPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSPIActionPerformed
         panelMenu.removeAll();
        panelMenu.repaint();
        IfrPembayaranSPI fr = new IfrPembayaranSPI(vid_user);
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnSPIActionPerformed

    private void mnMasterSPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMasterSPPActionPerformed
          panelMenu.removeAll();
        panelMenu.repaint();
       IfrMasterSPP fr = new IfrMasterSPP();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnMasterSPPActionPerformed

    private void mnMasterSPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMasterSPIActionPerformed
         panelMenu.removeAll();
        panelMenu.repaint();
        IfrMasterSPI fr = new IfrMasterSPI();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnMasterSPIActionPerformed

    private void mnMasterQurbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnMasterQurbanActionPerformed
         panelMenu.removeAll();
        panelMenu.repaint();
        IfrMasterQurban fr = new IfrMasterQurban();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnMasterQurbanActionPerformed

    private void mnQurbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnQurbanActionPerformed
        panelMenu.removeAll();
        panelMenu.repaint();
        IfrPembayaranQurban fr = new IfrPembayaranQurban(vid_user);
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnQurbanActionPerformed

    private void mnSistemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSistemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnSistemActionPerformed

    private void mnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnKeluarActionPerformed
         aksiKeluar();
    }//GEN-LAST:event_mnKeluarActionPerformed

    private void mnLapQurbanKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapQurbanKelasActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanQurbanKelas fr = new laporanQurbanKelas();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapQurbanKelasActionPerformed

    private void mnLapSPPSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapSPPSiswaActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanSPP fr = new laporanSPP();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapSPPSiswaActionPerformed

    private void mnLapSPPKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapSPPKelasActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanSPPKelas fr = new laporanSPPKelas();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapSPPKelasActionPerformed

    private void mnLapSPPSeluruhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapSPPSeluruhActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanSPPSeluruh fr = new laporanSPPSeluruh();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapSPPSeluruhActionPerformed

    private void mnLapSPISiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapSPISiswaActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
       laporanSPI fr = new laporanSPI();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapSPISiswaActionPerformed

    private void mnLapSPIKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapSPIKelasActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanSPIKelas fr = new laporanSPIKelas();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapSPIKelasActionPerformed

    private void mnLapSPISeluruhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapSPISeluruhActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanSPISeluruh fr = new laporanSPISeluruh();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapSPISeluruhActionPerformed

    private void mnLapQurbanSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapQurbanSiswaActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanQurban fr = new laporanQurban();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapQurbanSiswaActionPerformed

    private void mnLapQurbanSeluruhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLapQurbanSeluruhActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanQurbanSeluruh fr = new laporanQurbanSeluruh();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_mnLapQurbanSeluruhActionPerformed

    private void mnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLaporanActionPerformed
       
    }//GEN-LAST:event_mnLaporanActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
         laporanTagihanSPI fr = new laporanTagihanSPI();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       panelMenu.removeAll();
        panelMenu.repaint();
        laporanSPPBulan fr = new laporanSPPBulan();
        panelMenu.add(fr);
        fr.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        aksiLogin();
    }//GEN-LAST:event_txtPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
       public static void main(String args[]) throws UnsupportedLookAndFeelException,
            IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        try{
            com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default","Java Swing","");
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
            SwingUtilities.updateComponentTreeUI(new FrMenu());
            }finally{
                new FrMenu().setVisible(true);
                        
            }
        
            }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JLabel lblKeterangan;
    private javax.swing.JLabel lbljam;
    private javax.swing.JMenuItem mnJurusan;
    private javax.swing.JMenuItem mnKelas;
    private javax.swing.JMenuItem mnKeluar;
    private javax.swing.JMenuItem mnLapQurbanKelas;
    private javax.swing.JMenuItem mnLapQurbanSeluruh;
    private javax.swing.JMenuItem mnLapQurbanSiswa;
    private javax.swing.JMenuItem mnLapSPIKelas;
    private javax.swing.JMenuItem mnLapSPISeluruh;
    private javax.swing.JMenuItem mnLapSPISiswa;
    private javax.swing.JMenuItem mnLapSPPKelas;
    private javax.swing.JMenuItem mnLapSPPSeluruh;
    private javax.swing.JMenuItem mnLapSPPSiswa;
    private javax.swing.JMenu mnLaporan;
    private javax.swing.JMenu mnMaster;
    private javax.swing.JMenuItem mnMasterQurban;
    private javax.swing.JMenuItem mnMasterSPI;
    private javax.swing.JMenuItem mnMasterSPP;
    private javax.swing.JMenu mnPembayaran;
    private javax.swing.JMenuItem mnQurban;
    private javax.swing.JMenuItem mnSPI;
    private javax.swing.JMenuItem mnSPP;
    private javax.swing.JMenu mnSistem;
    private javax.swing.JMenuItem mnSiswa;
    private javax.swing.JMenuItem mnTA;
    private javax.swing.JMenuItem mnUser;
    private javax.swing.JTabbedPane panelLogin;
    private javax.swing.JDesktopPane panelMenu;
    private javax.swing.JLabel tgl;
    private javax.swing.JTextField txtIDUser;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
