/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PerusahaanDialog.java
 *
 * Created on Sep 26, 2011, 2:24:59 AM
 */
package com.x3.dishub.ui;

import com.x3.dishub.dao.BentukBadanUsahaDAO;
import com.x3.dishub.dao.IjinInsidentilDAO;
import com.x3.dishub.dao.IjinOperasiDAO;
import com.x3.dishub.dao.IjinTrayekDAO;
import com.x3.dishub.dao.IjinUsahaAngkutanBarangDAO;
import com.x3.dishub.dao.IjinUsahaAngkutanOrangDAO;
import com.x3.dishub.dao.KabupatenKotaDAO;
import com.x3.dishub.dao.PerusahaanDAO;
import com.x3.dishub.dao.ProvinsiDAO;
import com.x3.dishub.dao.SkPelaksanaanIjinOperasiDAO;
import com.x3.dishub.dao.SkPelaksanaanIjinTrayekDAO;
import com.x3.dishub.dao.SpioDAO;
import com.x3.dishub.dao.SpitDAO;
import com.x3.dishub.dao.WarnaDAO;
import com.x3.dishub.entity.BentukBadanUsaha;
import com.x3.dishub.entity.IjinInsidentil;
import com.x3.dishub.entity.IjinOperasi;
import com.x3.dishub.entity.IjinTrayek;
import com.x3.dishub.entity.IjinUsahaAngkutanBarang;
import com.x3.dishub.entity.IjinUsahaAngkutanOrang;
import com.x3.dishub.entity.KabupatenKota;
import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.Provinsi;
import com.x3.dishub.entity.SkPelaksanaanIjinOperasi;
import com.x3.dishub.entity.SkPelaksanaanIjinTrayek;
import com.x3.dishub.entity.Spio;
import com.x3.dishub.entity.Spit;
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
public class PerusahaanDialog extends javax.swing.JDialog {

    private WarnaDAO wDao = (WarnaDAO) MainApps.appContext.getBean("warnaDAO");
    private Warna headerColor;
    private Warna backgroudColor;
    private PerusahaanDAO perusaanDao = (PerusahaanDAO) MainApps.appContext.getBean("perusahaanDAO");
    private KabupatenKotaDAO kabDao = (KabupatenKotaDAO) MainApps.appContext.getBean("kabKotaDAO");
    private ProvinsiDAO provinsiDao = (ProvinsiDAO) MainApps.appContext.getBean("provinsiDAO");
    private BentukBadanUsahaDAO bentukDao = (BentukBadanUsahaDAO) MainApps.appContext.getBean("bentukBadanUsahaDAO");
    private Perusahaan selectedPerusahaan;
    private List<Perusahaan> listPerusahaan;

    /** Creates new form PerusahaanDialog */
    public PerusahaanDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        //this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        loadWarna();
        updateWarna();
        loadKabKota();
        loadProvinsi();
        loadPerusahaan();
        loadBadanUsaha();
    }

    private void loadWarna() {
        headerColor = wDao.getByNama("PerusahaanDialogHd");
        if (headerColor == null) {
            headerColor = new Warna();
            headerColor.setName("PerusahaanDialogHd");
            headerColor.setRgb(-16777012);
            wDao.insert(headerColor);
            loadWarna();
        }
        backgroudColor = wDao.getByNama("PerusahaanDialogBg");
        if (backgroudColor == null) {
            backgroudColor = new Warna();
            backgroudColor.setName("PerusahaanDialogBg");
            backgroudColor.setRgb(-13408513);
            wDao.insert(backgroudColor);
            loadWarna();
        }
    }

    private void updateWarna() {
        headerPanel.setBackground(new Color(headerColor.getRgb()));
        this.getContentPane().setBackground(new Color(backgroudColor.getRgb()));
    }

    private void loadPerusahaan() {
        try {
            listPerusahaan = perusaanDao.getAllPerusahaan();
            String title[] = {"Nama Perusahaan", "Nama Pimpinan", "Alamat", "NPWP"};
            Object[][] data = new Object[listPerusahaan.size()][4];
            int row = 0;
            for (Perusahaan p : listPerusahaan) {
                data[row][0] = p.getNama();
                data[row][1] = p.getNamaPimpinan();
                data[row][2] = p.getAlamat();
                data[row][3] = p.getNpwp();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblPerusahaan.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadBadanUsaha() {
        try {
            cmbBadanUsaha.removeAllItems();
            for (BentukBadanUsaha bbu : bentukDao.getAllBentukBadanUsaha()) {
                cmbBadanUsaha.addItem(bbu);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadKabKota() {
        try {
            cmbKabKota.removeAllItems();
            for (KabupatenKota kk : kabDao.getAllKabupatenKota()) {
                cmbKabKota.addItem(kk);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadProvinsi() {
        try {
            cmbProvinsi.removeAllItems();
            for (Provinsi prov : provinsiDao.getAllProvinsi()) {
                cmbProvinsi.addItem(prov);
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

        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPerusahaan = new javax.swing.JTable();
        btnTambahData = new javax.swing.JButton();
        btnHapusData = new javax.swing.JButton();
        btnRefreshTabel = new javax.swing.JButton();
        btnPencarianPerusahaan = new javax.swing.JButton();
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
        txtNama = new javax.swing.JTextField();
        txtPimpinan = new javax.swing.JTextField();
        txtTahunBerdiri = new javax.swing.JTextField();
        txtSkNotaris = new javax.swing.JTextField();
        txtNpwp = new javax.swing.JTextField();
        cmbBadanUsaha = new javax.swing.JComboBox();
        txtAlamat = new javax.swing.JTextField();
        cmbKabKota = new javax.swing.JComboBox();
        cmbProvinsi = new javax.swing.JComboBox();
        txtTelp = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtWebsite = new javax.swing.JTextField();
        btnSimpanData = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane7 = new javax.swing.JTabbedPane();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblIjinAngkutanOrang = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblIjinAngkutanBarang = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSpit = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSpio = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblIjinTrayek = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblIjinOperasi = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblSkPelaksanaanIjinTrayek = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblSkPelaksanaanIjinOperasi = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIjinInsidentil = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuWarna = new javax.swing.JMenu();
        mnuHeader = new javax.swing.JMenuItem();
        mnuBack = new javax.swing.JMenuItem();
        mnuDefault = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Perusahaan");

        headerPanel.setBackground(new java.awt.Color(0, 0, 204));
        headerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Perusahaan");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Menambah, mengubah dan menghapus data perusahaan");

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
                .addContainerGap(611, Short.MAX_VALUE))
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

        jTabbedPane1.setBackground(new java.awt.Color(0, 102, 255));

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));

        tblPerusahaan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPerusahaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPerusahaanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPerusahaan);

        btnTambahData.setText("Tambah Data");
        btnTambahData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahDataActionPerformed(evt);
            }
        });

        btnHapusData.setText("Hapus Data");
        btnHapusData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusDataActionPerformed(evt);
            }
        });

        btnRefreshTabel.setText("Refresh Tabel");
        btnRefreshTabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTabelActionPerformed(evt);
            }
        });

        btnPencarianPerusahaan.setText("Pencarian Berdasarkan Nama");
        btnPencarianPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPencarianPerusahaanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPencarianPerusahaan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 430, Short.MAX_VALUE)
                        .addComponent(btnRefreshTabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahData)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahData)
                    .addComponent(btnHapusData)
                    .addComponent(btnRefreshTabel)
                    .addComponent(btnPencarianPerusahaan))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Daftar Perusahaan", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));

        jTabbedPane2.setBackground(new java.awt.Color(0, 102, 255));

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Perusahaan :");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama Pimpinan :");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bentuk Badan Usaha :");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Alamat :");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kabupaten / Kota :");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Provinsi :");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Telp :");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Email :");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Website :");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("NPWP :");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("No. SK Notaris :");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tahun Berdiri :");

        cmbBadanUsaha.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbKabKota.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbProvinsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSimpanData.setText("Simpan Data");
        btnSimpanData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel14)
                            .addComponent(jLabel5)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtWebsite, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNpwp, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                            .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                            .addComponent(txtPimpinan, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                            .addComponent(txtTahunBerdiri, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSkNotaris, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                            .addComponent(cmbBadanUsaha, 0, 365, Short.MAX_VALUE))
                                        .addGap(410, 410, 410))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txtAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
                                        .addContainerGap())
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cmbKabKota, javax.swing.GroupLayout.Alignment.LEADING, 0, 318, Short.MAX_VALUE)
                                            .addComponent(cmbProvinsi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap(457, Short.MAX_VALUE)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtTelp, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                                    .addContainerGap()))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnSimpanData)
                        .addGap(19, 19, 19))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPimpinan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTahunBerdiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbBadanUsaha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSkNotaris, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtNpwp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbKabKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtWebsite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addComponent(btnSimpanData)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Profil Perusahaan", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 102, 255));

        tblIjinAngkutanOrang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblIjinAngkutanOrang);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane7.addTab("Angkutan Orang", jPanel20);

        tblIjinAngkutanBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblIjinAngkutanBarang);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane7.addTab("Angkutan Barang", jPanel21);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Ijin Usaha", jPanel5);

        jPanel6.setBackground(new java.awt.Color(0, 102, 255));

        tblSpit.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblSpit);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane6.addTab("S P I T", jPanel18);

        tblSpio.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tblSpio);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane6.addTab("S P I O", jPanel19);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Surat Persetujuan", jPanel6);

        jPanel7.setBackground(new java.awt.Color(0, 102, 255));

        tblIjinTrayek.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(tblIjinTrayek);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane5.addTab("Ijin Trayek", jPanel16);

        tblIjinOperasi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tblIjinOperasi);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane5.addTab("Ijin Operasi", jPanel17);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Ijin Trayek", jPanel7);

        jPanel8.setBackground(new java.awt.Color(0, 102, 255));

        tblSkPelaksanaanIjinTrayek.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(tblSkPelaksanaanIjinTrayek);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Pelaksanaan Ijin Trayek", jPanel14);

        tblSkPelaksanaanIjinOperasi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(tblSkPelaksanaanIjinOperasi);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGap(0, 870, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGap(0, 444, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Pelaksanaan Ijin Operasi", jPanel15);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("SK Pelaksanaan", jPanel8);

        jPanel11.setBackground(new java.awt.Color(0, 102, 255));

        tblIjinInsidentil.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblIjinInsidentil);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Ijin Insidentil", jPanel11);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Detail Perusahaan", jPanel3);

        mnuWarna.setText("Seting");

        mnuHeader.setText("Warna Header");
        mnuHeader.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHeaderActionPerformed(evt);
            }
        });
        mnuWarna.add(mnuHeader);

        mnuBack.setText("Warna Background");
        mnuBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBackActionPerformed(evt);
            }
        });
        mnuWarna.add(mnuBack);

        mnuDefault.setText("Warna Default");
        mnuDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDefaultActionPerformed(evt);
            }
        });
        mnuWarna.add(mnuDefault);

        jMenuBar1.add(mnuWarna);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahDataActionPerformed
        InputPerusahaanDialog input = new InputPerusahaanDialog(null, true);
        input.setVisible(true);
    }//GEN-LAST:event_btnTambahDataActionPerformed

    private void btnRefreshTabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTabelActionPerformed
        loadPerusahaan();
    }//GEN-LAST:event_btnRefreshTabelActionPerformed

    private void btnHapusDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusDataActionPerformed
        if (selectedPerusahaan != null) {
            perusaanDao.delete(selectedPerusahaan);
            selectedPerusahaan = null;
            JOptionPane.showMessageDialog(this, "Data terhapus");
            loadPerusahaan();
        }
    }//GEN-LAST:event_btnHapusDataActionPerformed

    private void btnPencarianPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPencarianPerusahaanActionPerformed
        try {
            String nama = JOptionPane.showInputDialog(this, "Nama Perusahaan :");
            listPerusahaan = perusaanDao.getByNama(nama);
            String title[] = {"Nama Perusahaan", "Nama Pimpinan", "Alamat", "NPWP"};
            Object[][] data = new Object[listPerusahaan.size()][4];
            int row = 0;
            for (Perusahaan p : listPerusahaan) {
                data[row][0] = p.getNama();
                data[row][1] = p.getNamaPimpinan();
                data[row][2] = p.getAlamat();
                data[row][3] = p.getNpwp();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblPerusahaan.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnPencarianPerusahaanActionPerformed

    private int getCmbBadanUsahaIndex(BentukBadanUsaha bentuk) {
        int index = 0;
        for (BentukBadanUsaha tmp : bentukDao.getAllBentukBadanUsaha()) {
            if (tmp.getId() == bentuk.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }

    private int getCmbKabKotaIndex(KabupatenKota kab) {
        int index = 0;
        for (KabupatenKota tmp : kabDao.getAllKabupatenKota()) {
            if (tmp.getId() == kab.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }

    private int getCmbProvinsiIndex(Provinsi prov) {
        int index = 0;
        for (Provinsi tmp : provinsiDao.getAllProvinsi()) {
            if (tmp.getId() == prov.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }

    private void tblPerusahaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPerusahaanMouseClicked
        selectedPerusahaan = (Perusahaan) listPerusahaan.get(tblPerusahaan.getSelectedRow());
        txtNama.setText(selectedPerusahaan.getNama());
        txtPimpinan.setText(selectedPerusahaan.getNamaPimpinan());
        txtTahunBerdiri.setText(selectedPerusahaan.getTahunBerdiri());
        cmbBadanUsaha.setSelectedIndex(getCmbBadanUsahaIndex(selectedPerusahaan.getBadanUsaha()));
        txtSkNotaris.setText(selectedPerusahaan.getNoSKNotaris());
        txtNpwp.setText(selectedPerusahaan.getNpwp());
        txtAlamat.setText(selectedPerusahaan.getAlamat());
        cmbKabKota.setSelectedIndex(getCmbKabKotaIndex(selectedPerusahaan.getKabKota()));
        cmbProvinsi.setSelectedIndex(getCmbProvinsiIndex(selectedPerusahaan.getProvinsi()));
        txtTelp.setText(selectedPerusahaan.getTelp());
        txtEmail.setText(selectedPerusahaan.getEmail());
        txtWebsite.setText(selectedPerusahaan.getWebsite());
        loadIjinUsahaAngkOrang();
        loadIjinUsahaAngkBarang();
        loadSpit();
        loadSpio();
        loadIjinTrayek();
        loadIjinOperasi();
        loadSKIjinTrayek();
        loadSKIjinOperasi();
        loadIjinInsidentil();
    }//GEN-LAST:event_tblPerusahaanMouseClicked

    private void btnSimpanDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanDataActionPerformed
        try {
            if (selectedPerusahaan != null) {
                selectedPerusahaan.setNama(txtNama.getText());
                selectedPerusahaan.setNamaPimpinan(txtPimpinan.getText());
                selectedPerusahaan.setBadanUsaha((BentukBadanUsaha) cmbBadanUsaha.getSelectedItem());
                selectedPerusahaan.setAlamat(txtAlamat.getText());
                selectedPerusahaan.setTelp(txtTelp.getText());
                selectedPerusahaan.setEmail(txtEmail.getText());
                selectedPerusahaan.setKabKota((KabupatenKota) cmbKabKota.getSelectedItem());
                selectedPerusahaan.setProvinsi((Provinsi) cmbProvinsi.getSelectedItem());
                selectedPerusahaan.setNpwp(txtNpwp.getText());
                selectedPerusahaan.setNoSKNotaris(txtSkNotaris.getText());
                selectedPerusahaan.setTahunBerdiri(txtTahunBerdiri.getText());
                selectedPerusahaan.setWebsite(txtWebsite.getText());
                perusaanDao.update(selectedPerusahaan);
                JOptionPane.showMessageDialog(this, "Data tersimpan");
                loadPerusahaan();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanDataActionPerformed

    private void mnuHeaderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHeaderActionPerformed
        Color tmp = JColorChooser.showDialog(this, "Ganti Warna Header", headerPanel.getBackground());
        headerColor.setRgb(tmp.getRGB());
        wDao.update(headerColor);
        updateWarna();
    }//GEN-LAST:event_mnuHeaderActionPerformed

    private void mnuBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBackActionPerformed
        Color tmp = JColorChooser.showDialog(this, "Ganti Warna Backgroud", this.getContentPane().getBackground());
        backgroudColor.setRgb(tmp.getRGB());
        wDao.update(backgroudColor);
        updateWarna();
    }//GEN-LAST:event_mnuBackActionPerformed

    private void mnuDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDefaultActionPerformed
        headerColor.setRgb(-16777012);
        backgroudColor.setRgb(-13408513);
        wDao.update(headerColor);
        wDao.update(backgroudColor);
        updateWarna();
    }//GEN-LAST:event_mnuDefaultActionPerformed
    private SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    private void loadIjinUsahaAngkOrang() {
        try {
            IjinUsahaAngkutanOrangDAO orangDao = (IjinUsahaAngkutanOrangDAO) MainApps.appContext.getBean("ijinUsahaAngkutanOrangDAO");
            List<IjinUsahaAngkutanOrang> ijins = orangDao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Tanggal"};
            Object[][] data = new Object[ijins.size()][2];
            int row = 0;
            for (IjinUsahaAngkutanOrang i : ijins) {
                data[row][0] = i.getNomor();
                data[row][1] = formater.format(i.getTanggal());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblIjinAngkutanOrang.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadIjinUsahaAngkBarang() {
        try {
            IjinUsahaAngkutanBarangDAO dao = (IjinUsahaAngkutanBarangDAO) MainApps.appContext.getBean("ijinUsahaAngkutanBarangDAO");
            List<IjinUsahaAngkutanBarang> list = dao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Tanggal"};
            Object[][] data = new Object[list.size()][2];
            int row = 0;
            for (IjinUsahaAngkutanBarang i : list) {
                data[row][0] = i.getNomor();
                data[row][1] = formater.format(i.getTanggal());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblIjinAngkutanBarang.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadSpit() {
        try {
            SpitDAO dao = (SpitDAO) MainApps.appContext.getBean("spitDAO");
            List<Spit> list = dao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Perihal", "Berlaku Sampai"};
            Object[][] data = new Object[list.size()][3];
            int row = 0;
            for (Spit s : list) {
                data[row][0] = s.getNomor();
                data[row][1] = s.getPerihal();
                data[row][2] = formater.format(s.getTanggalBerakhir());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblSpit.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadSpio() {
        try {
            SpioDAO dao = (SpioDAO) MainApps.appContext.getBean("spioDAO");
            List<Spio> list = dao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Perihal", "Berlaku Sampai"};
            Object[][] data = new Object[list.size()][3];
            int row = 0;
            for (Spio s : list) {
                data[row][0] = s.getNomor();
                data[row][1] = s.getPerihal();
                data[row][2] = formater.format(s.getTanggalBerakhir());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblSpio.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadIjinTrayek() {
        try {
            IjinTrayekDAO dao = (IjinTrayekDAO) MainApps.appContext.getBean("ijinTrayekDAO");
            List<IjinTrayek> list = dao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Masa Berlaku"};
            Object[][] data = new Object[list.size()][2];
            int row = 0;
            for (IjinTrayek i : list) {
                data[row][0] = i.getNomor();
                data[row][1] = formater.format(i.getMasaBerlakuMulai()) + " s/d " + formater.format(i.getMasaBerlakuSelesai());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblIjinTrayek.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadIjinOperasi() {
        try {
            IjinOperasiDAO dao = (IjinOperasiDAO) MainApps.appContext.getBean("ijinOperasiDAO");
            List<IjinOperasi> list = dao.getByPersusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Masa Berlaku"};
            Object[][] data = new Object[list.size()][2];
            int row = 0;
            for (IjinOperasi i : list) {
                data[row][0] = i.getNomor();
                data[row][1] = formater.format(i.getMasaBerlakuMulai()) + " s/d " + formater.format(i.getMasaBerlakuSelesai());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblIjinOperasi.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadSKIjinTrayek() {
        try {
            SkPelaksanaanIjinTrayekDAO dao = (SkPelaksanaanIjinTrayekDAO) MainApps.appContext.getBean("skPelaksanaanIjinTrayekDAO");
            List<SkPelaksanaanIjinTrayek> list = dao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Nomor Ijin Trayek"};
            Object[][] data = new Object[list.size()][2];
            int row = 0;
            for (SkPelaksanaanIjinTrayek s : list) {
                data[row][0] = s.getNomor();
                data[row][1] = s.getIjinTrayek().getNomor();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblSkPelaksanaanIjinTrayek.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadSKIjinOperasi() {
        try {
            SkPelaksanaanIjinOperasiDAO dao = (SkPelaksanaanIjinOperasiDAO) MainApps.appContext.getBean("skPelaksanaanIjinOperasiDAO");
            List<SkPelaksanaanIjinOperasi> list = dao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Nomor Ijin Operasi"};
            Object[][] data = new Object[list.size()][2];
            int row = 0;
            for (SkPelaksanaanIjinOperasi s : list) {
                data[row][0] = s.getNomor();
                data[row][1] = s.getIjinOperasi().getNomor();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblSkPelaksanaanIjinOperasi.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadIjinInsidentil() {
        try {
            IjinInsidentilDAO dao = (IjinInsidentilDAO) MainApps.appContext.getBean("ijinInsidentilDAO");
            List<IjinInsidentil> list = dao.getByPerusahaan(selectedPerusahaan);
            String title[] = {"Nomor", "Maksud Perjalanan", "Rute Perjalanan", "Berlaku Sampai"};
            Object[][] data = new Object[list.size()][4];
            int row = 0;
            for (IjinInsidentil i : list) {
                data[row][0] = i.getNomor();
                data[row][1] = i.getMaksudPerjalanan();
                data[row][2] = i.getRutePerjalanan();
                data[row][3] = formater.format(i.getBerlakuSampai());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblIjinInsidentil.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapusData;
    private javax.swing.JButton btnPencarianPerusahaan;
    private javax.swing.JButton btnRefreshTabel;
    private javax.swing.JButton btnSimpanData;
    private javax.swing.JButton btnTambahData;
    private javax.swing.JComboBox cmbBadanUsaha;
    private javax.swing.JComboBox cmbKabKota;
    private javax.swing.JComboBox cmbProvinsi;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTabbedPane jTabbedPane7;
    private javax.swing.JMenuItem mnuBack;
    private javax.swing.JMenuItem mnuDefault;
    private javax.swing.JMenuItem mnuHeader;
    private javax.swing.JMenu mnuWarna;
    private javax.swing.JTable tblIjinAngkutanBarang;
    private javax.swing.JTable tblIjinAngkutanOrang;
    private javax.swing.JTable tblIjinInsidentil;
    private javax.swing.JTable tblIjinOperasi;
    private javax.swing.JTable tblIjinTrayek;
    private javax.swing.JTable tblPerusahaan;
    private javax.swing.JTable tblSkPelaksanaanIjinOperasi;
    private javax.swing.JTable tblSkPelaksanaanIjinTrayek;
    private javax.swing.JTable tblSpio;
    private javax.swing.JTable tblSpit;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNpwp;
    private javax.swing.JTextField txtPimpinan;
    private javax.swing.JTextField txtSkNotaris;
    private javax.swing.JTextField txtTahunBerdiri;
    private javax.swing.JTextField txtTelp;
    private javax.swing.JTextField txtWebsite;
    // End of variables declaration//GEN-END:variables
}
