/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * KendaraanDialog.java
 *
 * Created on Sep 25, 2011, 10:05:35 PM
 */
package com.x3.dishub.ui;

import com.x3.dishub.dao.BahanBakarDAO;
import com.x3.dishub.dao.JenisDAO;
import com.x3.dishub.dao.KartuJamPerjalananDAO;
import com.x3.dishub.dao.KartuPengawasanOtobisUmumDAO;
import com.x3.dishub.dao.KartuPengawasanTaksiDAO;
import com.x3.dishub.dao.KendaraanDAO;
import com.x3.dishub.dao.MerkDAO;
import com.x3.dishub.dao.PemilikDAO;
import com.x3.dishub.dao.WarnaDAO;
import com.x3.dishub.entity.BahanBakar;
import com.x3.dishub.entity.Jenis;
import com.x3.dishub.entity.KartuJamPerjalanan;
import com.x3.dishub.entity.KartuPengawasanOtobisUmum;
import com.x3.dishub.entity.KartuPengawasanTaksi;
import com.x3.dishub.entity.Kendaraan;
import com.x3.dishub.entity.Merk;
import com.x3.dishub.entity.Pemilik;
import com.x3.dishub.entity.Warna;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Hendro Steven
 */
public class KendaraanDialog extends javax.swing.JDialog {

    private WarnaDAO wDao = (WarnaDAO) MainApps.appContext.getBean("warnaDAO");
    private Warna headerColor;
    private Warna backgroudColor;
    private KendaraanDAO kendaraanDao = (KendaraanDAO) MainApps.appContext.getBean("kendaraanDAO");
    private PemilikDAO pemilikDao = (PemilikDAO) MainApps.appContext.getBean("pemilikDAO");
    private List<Kendaraan> listKendaraan;
    private Kendaraan selectedKendaraan;
    private Pemilik selectedPemilik;
    private Kendaraan peremajaan;
    private SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
    private MerkDAO merkDao = (MerkDAO) MainApps.appContext.getBean("merkDAO");
    private JenisDAO jenisDao = (JenisDAO) MainApps.appContext.getBean("jenisDAO");
    private BahanBakarDAO bahanDao = (BahanBakarDAO) MainApps.appContext.getBean("bahanBakarDAO");
    private List<Kendaraan> listKendaraanBrowse;

    /** Creates new form KendaraanDialog */
    public KendaraanDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        loadWarna();
        updateWarna();
        //this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        loadKendaraan();
        loadMerk();
        loadJenis();
        loadBahanBakar();
    }

    private void loadWarna() {
        headerColor = wDao.getByNama("KendaraanDialogHd");
        if (headerColor == null) {
            headerColor = new Warna();
            headerColor.setName("KendaraanDialogHd");
            headerColor.setRgb(-16777012);
            wDao.insert(headerColor);
            loadWarna();
        }
        backgroudColor = wDao.getByNama("KendaraanDialogBg");
        if (backgroudColor == null) {
            backgroudColor = new Warna();
            backgroudColor.setName("KendaraanDialogBg");
            backgroudColor.setRgb(-13408513);
            wDao.insert(backgroudColor);
            loadWarna();
        }
    }

    private void updateWarna() {
        headerPanel.setBackground(new Color(headerColor.getRgb()));
        this.getContentPane().setBackground(new Color(backgroudColor.getRgb()));
    }

    private void loadKendaraan() {
        try {
            listKendaraan = kendaraanDao.getAll();
            String title[] = {"No. Polisi", "No.Uji", "Merk/Type", "Tahun"};
            Object[][] data = new Object[listKendaraan.size()][4];
            int row = 0;
            for (Kendaraan k : listKendaraan) {
                data[row][0] = k.getNomorPolisi();
                data[row][1] = k.getNomorUji();
                data[row][2] = k.getMerk() + k.getType();
                data[row][3] = k.getTahunPembuatan();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblKendaraan.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadMerk() {
        try {
            cmbMerk.removeAllItems();
            for (Merk m : merkDao.getAllMerk()) {
                cmbMerk.addItem(m);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadJenis() {
        try {
            cmbJenis.removeAllItems();
            for (Jenis j : jenisDao.getAllJenis()) {
                cmbJenis.addItem(j);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadBahanBakar() {
        try {
            cmbBahanBakar.removeAllItems();
            for (BahanBakar bb : bahanDao.getAllBahanBakar()) {
                cmbBahanBakar.addItem(bb);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        browsePemilik = new javax.swing.JDialog();
        jLabel22 = new javax.swing.JLabel();
        txtNamaPemilik = new javax.swing.JTextField();
        btnCariPemilik = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblBrowsePemilik = new javax.swing.JTable();
        browseKendaraan = new javax.swing.JDialog();
        jLabel24 = new javax.swing.JLabel();
        txtCariNomorPolisi = new javax.swing.JTextField();
        btnCariKendaraan1 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblBrowseKendaraan = new javax.swing.JTable();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKendaraan = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnCariKendaraan = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnHapusKendaraan = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtThnPembuatan = new javax.swing.JTextField();
        txtNoPol = new javax.swing.JTextField();
        txtNoUji = new javax.swing.JTextField();
        txtNoMesin = new javax.swing.JTextField();
        txtNoRangka = new javax.swing.JTextField();
        txtThnPerakitan = new javax.swing.JTextField();
        cmbMerk = new javax.swing.JComboBox();
        txtType = new javax.swing.JTextField();
        cmbJenis = new javax.swing.JComboBox();
        txtModel = new javax.swing.JTextField();
        txtDayaAngkutBarang = new javax.swing.JTextField();
        txtDayaAngkutOrang = new javax.swing.JTextField();
        txtNoBpkb = new javax.swing.JTextField();
        txtThnRegistrasi = new javax.swing.JTextField();
        cmbBahanBakar = new javax.swing.JComboBox();
        txtIsiSilinder = new javax.swing.JTextField();
        txtWarna = new javax.swing.JTextField();
        txtWarnaTnkb = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtPeremajaanUntuk = new javax.swing.JTextField();
        btnBrowseKendaraan = new javax.swing.JButton();
        chkAktif = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPemilik = new javax.swing.JTable();
        btnTambahPemilik = new javax.swing.JButton();
        btnHapusPemilik = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblKPU = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblKPT = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKJP = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuUbahWarna = new javax.swing.JMenu();
        mnuWarnaHeader = new javax.swing.JMenuItem();
        mnuWarnaBack = new javax.swing.JMenuItem();
        mnuWarnaDefault = new javax.swing.JMenuItem();

        jLabel22.setText("Nama :");

        btnCariPemilik.setText("Cari");
        btnCariPemilik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPemilikActionPerformed(evt);
            }
        });

        tblBrowsePemilik.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBrowsePemilik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBrowsePemilikMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblBrowsePemilik);

        javax.swing.GroupLayout browsePemilikLayout = new javax.swing.GroupLayout(browsePemilik.getContentPane());
        browsePemilik.getContentPane().setLayout(browsePemilikLayout);
        browsePemilikLayout.setHorizontalGroup(
            browsePemilikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(browsePemilikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(browsePemilikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                    .addGroup(browsePemilikLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNamaPemilik, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariPemilik)))
                .addContainerGap())
        );
        browsePemilikLayout.setVerticalGroup(
            browsePemilikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(browsePemilikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(browsePemilikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtNamaPemilik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariPemilik))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );

        browseKendaraan.setBackground(new java.awt.Color(0, 51, 255));

        jLabel24.setText("Nomor Polisi :");

        btnCariKendaraan1.setText("Cari");
        btnCariKendaraan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKendaraan1ActionPerformed(evt);
            }
        });

        tblBrowseKendaraan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBrowseKendaraan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBrowseKendaraanMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblBrowseKendaraan);

        javax.swing.GroupLayout browseKendaraanLayout = new javax.swing.GroupLayout(browseKendaraan.getContentPane());
        browseKendaraan.getContentPane().setLayout(browseKendaraanLayout);
        browseKendaraanLayout.setHorizontalGroup(
            browseKendaraanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(browseKendaraanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(browseKendaraanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                    .addGroup(browseKendaraanLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCariNomorPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariKendaraan1)))
                .addContainerGap())
        );
        browseKendaraanLayout.setVerticalGroup(
            browseKendaraanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(browseKendaraanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(browseKendaraanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtCariNomorPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariKendaraan1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Kendaraan");

        headerPanel.setBackground(new java.awt.Color(0, 0, 204));
        headerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Kendaraaan");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Menambah, mengubah dan menghapus data kendaraan");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addComponent(jLabel1))
                .addContainerGap(573, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 102, 255));

        tblKendaraan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblKendaraan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKendaraanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKendaraan);

        btnTambah.setText("Tambah Data Kendaraan");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnCariKendaraan.setText("Cari Berdasarkan Nomor Polisi");
        btnCariKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKendaraanActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh Data Tabel");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnHapusKendaraan.setText("Hapus Data Kendaraan");
        btnHapusKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKendaraanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCariKendaraan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 250, Short.MAX_VALUE)
                        .addComponent(btnRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusKendaraan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambah)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnCariKendaraan)
                    .addComponent(btnHapusKendaraan)
                    .addComponent(btnRefresh))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Daftar Kendaraan", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));

        jTabbedPane2.setBackground(new java.awt.Color(0, 102, 255));

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nomor Polisi :");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nomor Uji :");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nomor Mesin :");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nomor Rangka :");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tahun Pembuatan :");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tahun Perakitan :");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Merk :");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Type :");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Jenis :");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Model :");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Isi Silinder :");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Bahan Bakar :");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Warna :");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Warna TNKB :");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tahun Registrasi :");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Nomor BPKB :");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Daya Angkut Orang :");

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Daya Angkut Barang :");

        cmbMerk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbBahanBakar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSimpan.setText("Simpan Data Kendaraan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Peremajaan Untuk :");

        txtPeremajaanUntuk.setEditable(false);

        btnBrowseKendaraan.setText("....");
        btnBrowseKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseKendaraanActionPerformed(evt);
            }
        });

        chkAktif.setBackground(new java.awt.Color(0, 102, 255));
        chkAktif.setForeground(new java.awt.Color(255, 255, 255));
        chkAktif.setText("Beroperasi");

        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Aktif :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtModel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(cmbJenis, 0, 282, Short.MAX_VALUE)
                            .addComponent(txtType, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(txtNoPol, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(txtThnPembuatan, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(txtNoUji, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(txtNoMesin, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(txtNoRangka, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(txtThnPerakitan, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                            .addComponent(cmbMerk, 0, 282, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkAktif)
                            .addComponent(txtDayaAngkutOrang, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(txtDayaAngkutBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(txtNoBpkb, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(txtThnRegistrasi, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(cmbBahanBakar, 0, 323, Short.MAX_VALUE)
                            .addComponent(txtIsiSilinder, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(txtWarna, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(txtWarnaTnkb, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtPeremajaanUntuk, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBrowseKendaraan, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))))
                    .addComponent(btnSimpan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtIsiSilinder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cmbBahanBakar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtWarna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtWarnaTnkb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtThnRegistrasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtNoBpkb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtDayaAngkutOrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtDayaAngkutBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtPeremajaanUntuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBrowseKendaraan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkAktif)
                            .addComponent(jLabel23)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNoUji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtNoMesin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtNoRangka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtThnPembuatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtThnPerakitan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cmbMerk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cmbJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(btnSimpan)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Profil Kendaraan", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 102, 255));

        tblPemilik.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPemilik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPemilikMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPemilik);

        btnTambahPemilik.setText("Tambah Pemilik Baru");
        btnTambahPemilik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPemilikActionPerformed(evt);
            }
        });

        btnHapusPemilik.setText("Hapus Data Pemilik");
        btnHapusPemilik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPemilikActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnHapusPemilik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahPemilik)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahPemilik)
                    .addComponent(btnHapusPemilik))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Riwayat Pemilik", jPanel5);

        jPanel6.setBackground(new java.awt.Color(0, 102, 255));

        tblKPU.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblKPU);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Kartu Pengawasan Otobis Umum", jPanel6);

        jPanel7.setBackground(new java.awt.Color(0, 102, 255));

        tblKPT.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblKPT);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Kartu Pengawasan Taksi", jPanel7);

        jPanel8.setBackground(new java.awt.Color(0, 102, 255));

        tblKJP.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblKJP);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Kartu Jam Perjalanan", jPanel8);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Detail Kendaraan", jPanel3);

        mnuUbahWarna.setText("Seting");

        mnuWarnaHeader.setText("Warna Header");
        mnuWarnaHeader.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuWarnaHeaderActionPerformed(evt);
            }
        });
        mnuUbahWarna.add(mnuWarnaHeader);

        mnuWarnaBack.setText("Warna Background");
        mnuWarnaBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuWarnaBackActionPerformed(evt);
            }
        });
        mnuUbahWarna.add(mnuWarnaBack);

        mnuWarnaDefault.setText("Warna Default");
        mnuWarnaDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuWarnaDefaultActionPerformed(evt);
            }
        });
        mnuUbahWarna.add(mnuWarnaDefault);

        jMenuBar1.add(mnuUbahWarna);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        InputKendaraanDialog input = new InputKendaraanDialog(null, true);
        input.setVisible(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadKendaraan();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCariKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKendaraanActionPerformed
        try {
            String nopol = JOptionPane.showInputDialog(this, "Nomor Polisi :");
            listKendaraan = kendaraanDao.getByNomorPolisi(nopol);
            String title[] = {"No. Polisi", "No.Uji", "Merk/Type", "Tahun"};
            Object[][] data = new Object[listKendaraan.size()][4];
            int row = 0;
            for (Kendaraan k : listKendaraan) {
                data[row][0] = k.getNomorPolisi();
                data[row][1] = k.getNomorUji();
                data[row][2] = k.getMerk() + k.getType();
                data[row][3] = k.getTahunPembuatan();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblKendaraan.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnCariKendaraanActionPerformed

    private int getCmbMerkIndex(Merk merk) {
        int index = 0;
        for (Merk tmp : merkDao.getAllMerk()) {
            if (tmp.getId() == merk.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }

    private int getCmbJenisIndex(Jenis jenis) {
        int index = 0;
        for (Jenis tmp : jenisDao.getAllJenis()) {
            if (tmp.getId() == jenis.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }

    private int getCmbBahanBakarIndex(BahanBakar bb) {
        int index = 0;
        for (BahanBakar tmp : bahanDao.getAllBahanBakar()) {
            if (tmp.getId() == bb.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }
    private void tblKendaraanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKendaraanMouseClicked
        selectedKendaraan = listKendaraan.get(tblKendaraan.getSelectedRow());
        txtNoPol.setText(selectedKendaraan.getNomorPolisi());
        txtNoUji.setText(selectedKendaraan.getNomorUji());
        txtNoMesin.setText(selectedKendaraan.getNomorMesin());
        txtNoRangka.setText(selectedKendaraan.getNomorRangka());
        txtThnPembuatan.setText(selectedKendaraan.getTahunPembuatan());
        txtThnPerakitan.setText(selectedKendaraan.getTahunPerakitan());
        cmbMerk.setSelectedIndex(getCmbMerkIndex(selectedKendaraan.getMerk()));
        txtType.setText(selectedKendaraan.getType());
        cmbJenis.setSelectedIndex(getCmbJenisIndex(selectedKendaraan.getJenis()));
        txtModel.setText(selectedKendaraan.getModel());
        txtIsiSilinder.setText(selectedKendaraan.getIsiSilinder());
        cmbBahanBakar.setSelectedIndex(getCmbBahanBakarIndex(selectedKendaraan.getBahanBakar()));
        txtWarna.setText(selectedKendaraan.getWarna());
        txtWarnaTnkb.setText(selectedKendaraan.getWarnaTNKB());
        txtThnRegistrasi.setText(selectedKendaraan.getTahunRegistrasi());
        txtNoBpkb.setText(selectedKendaraan.getNomorBPKB());
        txtDayaAngkutBarang.setText(selectedKendaraan.getDayaAngkutBarang() + "");
        txtDayaAngkutOrang.setText(selectedKendaraan.getDayaAngkutOrang() + "");
        peremajaan = selectedKendaraan.getPeremajaanUntuk();
        if (peremajaan != null) {
            txtPeremajaanUntuk.setText(peremajaan.getNomorPolisi());
        }
        chkAktif.setSelected(selectedKendaraan.isIsAktif());
        loadPemilik();
        loadKPU();
        loadKPT();
        loadKJP();
    }//GEN-LAST:event_tblKendaraanMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
            if (selectedKendaraan != null) {
                selectedKendaraan.setNomorPolisi(txtNoPol.getText());
                selectedKendaraan.setNomorRangka(txtNoRangka.getText());
                selectedKendaraan.setNomorMesin(txtNoMesin.getText());
                selectedKendaraan.setNomorUji(txtNoUji.getText());
                selectedKendaraan.setTahunPembuatan(txtThnPembuatan.getText());
                selectedKendaraan.setTahunPerakitan(txtThnPerakitan.getText());
                selectedKendaraan.setIsiSilinder(txtIsiSilinder.getText());
                selectedKendaraan.setWarna(txtWarna.getText());
                selectedKendaraan.setBahanBakar((BahanBakar) cmbBahanBakar.getSelectedItem());
                selectedKendaraan.setNomorBPKB(txtNoBpkb.getText());
                selectedKendaraan.setWarnaTNKB(txtWarnaTnkb.getText());
                selectedKendaraan.setTahunRegistrasi(txtThnRegistrasi.getText());
                selectedKendaraan.setMerk((Merk) cmbMerk.getSelectedItem());
                selectedKendaraan.setType(txtType.getText());
                selectedKendaraan.setJenis((Jenis) cmbJenis.getSelectedItem());
                selectedKendaraan.setModel(txtModel.getText());
                selectedKendaraan.setDayaAngkutBarang(Integer.valueOf(txtDayaAngkutBarang.getText()));
                selectedKendaraan.setDayaAngkutOrang(Integer.valueOf(txtDayaAngkutOrang.getText()));
                selectedKendaraan.setPeremajaanUntuk(peremajaan);
                selectedKendaraan.setIsAktif(chkAktif.isSelected());
                kendaraanDao.update(selectedKendaraan);
                peremajaan = null;
                JOptionPane.showMessageDialog(this, "Data tersimpan");
                loadKendaraan();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKendaraanActionPerformed
        if (selectedKendaraan != null) {
            kendaraanDao.delete(selectedKendaraan);
            selectedKendaraan = null;
            loadKendaraan();
        }
    }//GEN-LAST:event_btnHapusKendaraanActionPerformed

    private void btnCariPemilikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPemilikActionPerformed
        try {
            listPemilikBrowse = pemilikDao.getByName(txtNamaPemilik.getText());
            String title[] = {"Nama Depan", "Nama Belakang", "Alamat"};
            Object[][] data = new Object[listPemilikBrowse.size()][3];
            int row = 0;
            for (Pemilik p : listPemilikBrowse) {
                data[row][0] = p.getNamaDepan();
                data[row][1] = p.getNamaBelakang();
                data[row][2] = p.getAlamat();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblBrowsePemilik.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnCariPemilikActionPerformed
    private Pemilik pemilik;
    private void tblBrowsePemilikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBrowsePemilikMouseClicked
        if (listPemilikBrowse.size() > 0) {
            pemilik = (Pemilik) listPemilikBrowse.get(tblBrowsePemilik.getSelectedRow());
            selectedKendaraan.getPemilik().add(pemilik);
            pemilik.getKendaraans().add(selectedKendaraan);
            kendaraanDao.update(selectedKendaraan);
            pemilikDao.update(pemilik);
            loadPemilik();
        }
        browsePemilik.setVisible(false);
}//GEN-LAST:event_tblBrowsePemilikMouseClicked

    private void btnBrowseKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseKendaraanActionPerformed
        try {
            listKendaraanBrowse = kendaraanDao.getAll();
            String title[] = {"No. Polisi", "No.Uji", "Merk/Type", "Tahun"};
            Object[][] data = new Object[listKendaraanBrowse.size()][4];
            int row = 0;
            for (Kendaraan k : listKendaraanBrowse) {
                data[row][0] = k.getNomorPolisi();
                data[row][1] = k.getNomorUji();
                data[row][2] = k.getMerk() + k.getType();
                data[row][3] = k.getTahunPembuatan();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblBrowseKendaraan.setModel(model);
            browseKendaraan.setSize(800, 600);
            browseKendaraan.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
            browseKendaraan.setModal(true);
            browseKendaraan.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnBrowseKendaraanActionPerformed

    private void mnuWarnaHeaderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuWarnaHeaderActionPerformed
        Color tmp = JColorChooser.showDialog(this, "Ganti Warna Header", headerPanel.getBackground());
        headerColor.setRgb(tmp.getRGB());
        wDao.update(headerColor);
        updateWarna();
    }//GEN-LAST:event_mnuWarnaHeaderActionPerformed

    private void mnuWarnaBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuWarnaBackActionPerformed
        Color tmp = JColorChooser.showDialog(this, "Ganti Warna Background", this.getContentPane().getBackground());
        backgroudColor.setRgb(tmp.getRGB());
        wDao.update(backgroudColor);
        updateWarna();
    }//GEN-LAST:event_mnuWarnaBackActionPerformed

    private void mnuWarnaDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuWarnaDefaultActionPerformed
        headerColor.setRgb(-16777012);
        backgroudColor.setRgb(-13408513);
        wDao.update(headerColor);
        wDao.update(backgroudColor);
        updateWarna();
    }//GEN-LAST:event_mnuWarnaDefaultActionPerformed

    private void btnHapusPemilikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPemilikActionPerformed
        if (selectedKendaraan != null) {
            if (selectedPemilik != null) {
                selectedKendaraan.getPemilik().remove(selectedPemilik);
                selectedPemilik.getKendaraans().remove(selectedKendaraan);
                kendaraanDao.update(selectedKendaraan);
                pemilikDao.update(selectedPemilik);
                selectedPemilik = null;
                loadPemilik();
            }
        }
    }//GEN-LAST:event_btnHapusPemilikActionPerformed

    private void tblPemilikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPemilikMouseClicked
        if (selectedKendaraan != null && selectedKendaraan.getPemilik().size() > 0) {
            selectedPemilik = selectedKendaraan.getPemilik().get(tblPemilik.getSelectedRow());
        }
    }//GEN-LAST:event_tblPemilikMouseClicked
    private List<Pemilik> listPemilikBrowse;
    private void btnTambahPemilikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPemilikActionPerformed
        try {
            if (selectedKendaraan != null) {
                listPemilikBrowse = pemilikDao.getAllPemilik();
                String[] title = {"Nama Depan", "Nama Belakang", "Alamat"};
                Object[][] data = new Object[listPemilikBrowse.size()][3];
                int row = 0;
                for (Pemilik p : listPemilikBrowse) {
                    data[row][0] = p.getNamaDepan();
                    data[row][1] = p.getNamaBelakang();
                    data[row][2] = p.getAlamat();
                    ++row;
                }
                TableModel model = new DefaultTableModel(data, title);
                tblBrowsePemilik.setModel(model);
                browsePemilik.setSize(800, 600);
                browsePemilik.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
                browsePemilik.setModal(true);
                browsePemilik.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnTambahPemilikActionPerformed

    private void btnCariKendaraan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKendaraan1ActionPerformed
        try {
            listKendaraanBrowse = kendaraanDao.getByNomorPolisi(txtCariNomorPolisi.getText());
            String title[] = {"No. Polisi", "No.Uji", "Merk/Type", "Tahun"};
            Object[][] data = new Object[listKendaraanBrowse.size()][4];
            int row = 0;
            for (Kendaraan k : listKendaraanBrowse) {
                data[row][0] = k.getNomorPolisi();
                data[row][1] = k.getNomorUji();
                data[row][2] = k.getMerk() + k.getType();
                data[row][3] = k.getTahunPembuatan();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblBrowseKendaraan.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnCariKendaraan1ActionPerformed

    private void tblBrowseKendaraanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBrowseKendaraanMouseClicked
        peremajaan = (Kendaraan) listKendaraanBrowse.get(tblBrowseKendaraan.getSelectedRow());
        txtPeremajaanUntuk.setText(peremajaan.getNomorPolisi());
        browseKendaraan.setVisible(false);
}//GEN-LAST:event_tblBrowseKendaraanMouseClicked

    private void loadPemilik() {
        try {
            if (selectedKendaraan != null) {
                List<Pemilik> list = selectedKendaraan.getPemilik();
                String[] title = {"Nama", "Alamat", "Telp", "Email"};
                Object[][] data = new Object[list.size()][4];
                int row = 0;
                for (Pemilik p : list) {
                    data[row][0] = p.getNamaBelakang() + " " + p.getNamaDepan();
                    data[row][1] = p.getAlamat();
                    data[row][2] = p.getTlp();
                    data[row][3] = p.getEmail();
                    ++row;
                }
                TableModel model = new DefaultTableModel(data, title);
                tblPemilik.setModel(model);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadKPU() {
        try {
            if (selectedKendaraan != null) {
                KartuPengawasanOtobisUmumDAO kpuDao = (KartuPengawasanOtobisUmumDAO) MainApps.appContext.getBean("kartuPengawasanOtobisUmumDAO");
                List<KartuPengawasanOtobisUmum> list = kpuDao.getByKendaraan(selectedKendaraan);
                String title[] = {"Nomor", "Trayek", "Masa Berlaku"};
                Object[][] data = new Object[list.size()][3];
                int row = 0;
                for (KartuPengawasanOtobisUmum k : list) {
                    data[row][0] = k.getNomor();
                    data[row][1] = k.getArmada().getTrayek();
                    data[row][2] = formater.format(k.getTanggalBerlakuMulai()) + " s/d " + formater.format(k.getTanggalBerlakuSelesai());
                    ++row;
                }
                TableModel model = new DefaultTableModel(data, title);
                tblKPU.setModel(model);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadKPT() {
        try {
            if (selectedKendaraan != null) {
                KartuPengawasanTaksiDAO kptDao = (KartuPengawasanTaksiDAO) MainApps.appContext.getBean("kartuPengawasanTaksiDAO");
                List<KartuPengawasanTaksi> list = kptDao.getByKendaraan(selectedKendaraan);
                String title[] = {"Nomor", "Trayek", "Masa Berlaku"};
                Object[][] data = new Object[list.size()][3];
                int row = 0;
                for (KartuPengawasanTaksi k : list) {
                    data[row][0] = k.getNomor();
                    data[row][1] = k.getArmada().getTrayek();
                    data[row][2] = formater.format(k.getTanggalBerlakuMulai()) + " s/d " + formater.format(k.getTanggalBerlakuSelesai());
                    ++row;
                }
                TableModel model = new DefaultTableModel(data, title);
                tblKPT.setModel(model);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadKJP() {
        try {
            if (selectedKendaraan != null) {
                KartuJamPerjalananDAO kjpDao = (KartuJamPerjalananDAO) MainApps.appContext.getBean("kartuJamPerjalananDAO");
                List<KartuJamPerjalanan> list = kjpDao.getByKendraan(selectedKendaraan);
                String title[] = {"Nomor", "Trayek", "Masa Berlaku"};
                Object[][] data = new Object[list.size()][3];
                int row = 0;
                for (KartuJamPerjalanan k : list) {
                    data[row][0] = k.getNomor();
                    data[row][1] = k.getArmada().getTrayek();
                    data[row][2] = formater.format(k.getTanggalMulaiBerlaku()) + " s/d " + formater.format(k.getTanggalMulaiBerakhir());
                    ++row;
                }
                TableModel model = new DefaultTableModel(data, title);
                tblKJP.setModel(model);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog browseKendaraan;
    private javax.swing.JDialog browsePemilik;
    private javax.swing.JButton btnBrowseKendaraan;
    private javax.swing.JButton btnCariKendaraan;
    private javax.swing.JButton btnCariKendaraan1;
    private javax.swing.JButton btnCariPemilik;
    private javax.swing.JButton btnHapusKendaraan;
    private javax.swing.JButton btnHapusPemilik;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnTambahPemilik;
    private javax.swing.JCheckBox chkAktif;
    private javax.swing.JComboBox cmbBahanBakar;
    private javax.swing.JComboBox cmbJenis;
    private javax.swing.JComboBox cmbMerk;
    private javax.swing.JPanel headerPanel;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JMenu mnuUbahWarna;
    private javax.swing.JMenuItem mnuWarnaBack;
    private javax.swing.JMenuItem mnuWarnaDefault;
    private javax.swing.JMenuItem mnuWarnaHeader;
    private javax.swing.JTable tblBrowseKendaraan;
    private javax.swing.JTable tblBrowsePemilik;
    private javax.swing.JTable tblKJP;
    private javax.swing.JTable tblKPT;
    private javax.swing.JTable tblKPU;
    private javax.swing.JTable tblKendaraan;
    private javax.swing.JTable tblPemilik;
    private javax.swing.JTextField txtCariNomorPolisi;
    private javax.swing.JTextField txtDayaAngkutBarang;
    private javax.swing.JTextField txtDayaAngkutOrang;
    private javax.swing.JTextField txtIsiSilinder;
    private javax.swing.JTextField txtModel;
    private javax.swing.JTextField txtNamaPemilik;
    private javax.swing.JTextField txtNoBpkb;
    private javax.swing.JTextField txtNoMesin;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtNoRangka;
    private javax.swing.JTextField txtNoUji;
    private javax.swing.JTextField txtPeremajaanUntuk;
    private javax.swing.JTextField txtThnPembuatan;
    private javax.swing.JTextField txtThnPerakitan;
    private javax.swing.JTextField txtThnRegistrasi;
    private javax.swing.JTextField txtType;
    private javax.swing.JTextField txtWarna;
    private javax.swing.JTextField txtWarnaTnkb;
    // End of variables declaration//GEN-END:variables
}
