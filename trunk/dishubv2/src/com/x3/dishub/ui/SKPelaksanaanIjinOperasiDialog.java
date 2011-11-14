/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IjinTrayekDialog.java
 *
 * Created on Sep 26, 2011, 7:02:38 AM
 */
package com.x3.dishub.ui;

import com.x3.dishub.dao.IjinOperasiDAO;
import com.x3.dishub.dao.KelasPelayananDAO;
import com.x3.dishub.dao.KendaraanDAO;
import com.x3.dishub.dao.PerusahaanDAO;
import com.x3.dishub.dao.SkPelaksanaanIjinOperasiDAO;
import com.x3.dishub.dao.TrayekDAO;
import com.x3.dishub.dao.WarnaDAO;
import com.x3.dishub.entity.Armada;
import com.x3.dishub.entity.IjinOperasi;
import com.x3.dishub.entity.KelasPelayanan;
import com.x3.dishub.entity.Kendaraan;
import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.SkPelaksanaanIjinOperasi;
import com.x3.dishub.entity.Trayek;
import com.x3.dishub.entity.Warna;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

/**
 *
 * @author Hendro Steven
 */
public class SKPelaksanaanIjinOperasiDialog extends javax.swing.JDialog {

    private WarnaDAO wDao = (WarnaDAO) MainApps.appContext.getBean("warnaDAO");
    private Warna headerColor;
    private Warna backgroudColor;
    private SkPelaksanaanIjinOperasiDAO dao = (SkPelaksanaanIjinOperasiDAO) MainApps.appContext.getBean("skPelaksanaanIjinOperasiDAO");
    private List<SkPelaksanaanIjinOperasi> list;
    private SkPelaksanaanIjinOperasi selected;
    private SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
    private PerusahaanDAO pDao = (PerusahaanDAO) MainApps.appContext.getBean("perusahaanDAO");
    private List<IjinOperasi> listIjinOperasi;
    private IjinOperasi ijin;
    private IjinOperasiDAO iDao = (IjinOperasiDAO) MainApps.appContext.getBean("ijinOperasiDAO");
    private KendaraanDAO kDao = (KendaraanDAO) MainApps.appContext.getBean("kendaraanDAO");
    private List<Kendaraan> listKendaraan;
    private Kendaraan kendaraan;
    private KelasPelayananDAO kpDao = (KelasPelayananDAO) MainApps.appContext.getBean("kelasPelayananDAO");
    private TrayekDAO tDao = (TrayekDAO) MainApps.appContext.getBean("trayekDAO");

    /** Creates new form IjinTrayekDialog */
    public SKPelaksanaanIjinOperasiDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        //this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        loadWarna();
        updateWarna();
        reloadAll();
        loadKelasPelayanan();
        loadTrayek();
    }

    private void loadWarna() {
        headerColor = wDao.getByNama("SKPelaksanaanIjinOperasiDialogHd");
        if (headerColor == null) {
            headerColor = new Warna();
            headerColor.setName("SKPelaksanaanIjinOperasiDialogHd");
            headerColor.setRgb(-16777012);
            wDao.insert(headerColor);
            loadWarna();
        }
        backgroudColor = wDao.getByNama("SKPelaksanaanIjinOperasiDialogBg");
        if (backgroudColor == null) {
            backgroudColor = new Warna();
            backgroudColor.setName("SKPelaksanaanIjinOperasiDialogBg");
            backgroudColor.setRgb(-13408513);
            wDao.insert(backgroudColor);
            loadWarna();
        }
    }

    private void updateWarna() {
        headerPanel.setBackground(new Color(headerColor.getRgb()));
        this.getContentPane().setBackground(new Color(backgroudColor.getRgb()));
    }

    private void loadTrayek() {
        try {
            cmbTrayek.removeAllItems();
            for (Trayek t : tDao.getAllTrayek()) {
                cmbTrayek.addItem(t);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadKelasPelayanan() {
        try {
            cmbKelasPelayanan.removeAllItems();
            for (KelasPelayanan kp : kpDao.getAllKelasPelayanan()) {
                cmbKelasPelayanan.addItem(kp);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void reloadAll() {
        try {
            list = dao.getAllIjin();
            bindTabel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void bindTabel() {
        try {
            String title[] = {"No.SK Pelasanaan", "Tgl SK", "No.Ijin Operasi", "Masa Berlaku Ijin Operasi", "Perusahaan"};
            Object[][] data = new Object[list.size()][5];
            int row = 0;
            for (SkPelaksanaanIjinOperasi sk : list) {
                data[row][0] = sk.getNomor();
                data[row][1] = formater.format(sk.getTanggal());
                data[row][2] = sk.getIjinOperasi().getNomor();
                data[row][3] = formater.format(sk.getIjinOperasi().getMasaBerlakuMulai()) + " s/d "
                        + formater.format(sk.getIjinOperasi().getMasaBerlakuSelesai());
                data[row][4] = sk.getIjinOperasi().getPerusahaan().getNama();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblSK.setModel(model);
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

        pilihPerusahaan = new javax.swing.JDialog();
        jLabel7 = new javax.swing.JLabel();
        cmbPerusahaan = new javax.swing.JComboBox();
        btnPencarianPerusahaan = new javax.swing.JButton();
        pilihIjinOperasi = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNoIjinOperasi = new javax.swing.JTextField();
        btnCariIjin = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblIjin = new javax.swing.JTable();
        createArmada = new javax.swing.JDialog();
        jLabel13 = new javax.swing.JLabel();
        txtNoPol = new javax.swing.JTextField();
        btnBrowseKendaraan = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmbTrayek = new javax.swing.JComboBox();
        cmbKelasPelayanan = new javax.swing.JComboBox();
        btnSimpanArmada = new javax.swing.JButton();
        browseKendaraan = new javax.swing.JDialog();
        jLabel22 = new javax.swing.JLabel();
        txtCariNomorPolisi = new javax.swing.JTextField();
        btnCariKendaraan1 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblBrowseKendaraan = new javax.swing.JTable();
        headerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tabpane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSK = new javax.swing.JTable();
        btnCariNomor = new javax.swing.JButton();
        btnCariPerusahaan = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tglSk = new org.jdesktop.swingx.JXDatePicker();
        txtNomor = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnBuatBaru = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtIjinOperasi = new javax.swing.JTextField();
        btnBrowseIjinOperasi = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPerusahaan = new javax.swing.JTextField();
        btnCetak = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblArmada = new javax.swing.JTable();
        btnTambahArmada = new javax.swing.JButton();
        btnHapusArmada = new javax.swing.JButton();
        btnCetakLampiran = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuWarna = new javax.swing.JMenu();
        mnuHeader = new javax.swing.JMenuItem();
        mnuBack = new javax.swing.JMenuItem();
        mnuDefault = new javax.swing.JMenuItem();

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Pilih Perusahaan :");

        cmbPerusahaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnPencarianPerusahaan.setText("Cari");
        btnPencarianPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPencarianPerusahaanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pilihPerusahaanLayout = new javax.swing.GroupLayout(pilihPerusahaan.getContentPane());
        pilihPerusahaan.getContentPane().setLayout(pilihPerusahaanLayout);
        pilihPerusahaanLayout.setHorizontalGroup(
            pilihPerusahaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pilihPerusahaanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pilihPerusahaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPerusahaan, 0, 408, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(btnPencarianPerusahaan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        pilihPerusahaanLayout.setVerticalGroup(
            pilihPerusahaanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pilihPerusahaanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(btnPencarianPerusahaan)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(0, 0, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Daftar Ijin Operi");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Pilih Ijin Operasi untuk SK Pelaksanaan");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9))
                    .addComponent(jLabel8))
                .addContainerGap(488, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setText("Nomor :");

        btnCariIjin.setText("Cari");
        btnCariIjin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariIjinActionPerformed(evt);
            }
        });

        tblIjin.setModel(new javax.swing.table.DefaultTableModel(
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
        tblIjin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblIjinMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblIjin);

        javax.swing.GroupLayout pilihIjinOperasiLayout = new javax.swing.GroupLayout(pilihIjinOperasi.getContentPane());
        pilihIjinOperasi.getContentPane().setLayout(pilihIjinOperasiLayout);
        pilihIjinOperasiLayout.setHorizontalGroup(
            pilihIjinOperasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pilihIjinOperasiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNoIjinOperasi, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCariIjin)
                .addContainerGap(322, Short.MAX_VALUE))
            .addGroup(pilihIjinOperasiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );
        pilihIjinOperasiLayout.setVerticalGroup(
            pilihIjinOperasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pilihIjinOperasiLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pilihIjinOperasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNoIjinOperasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariIjin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        createArmada.setTitle("Tambah Armada");

        jLabel13.setText("Kendaraan :");

        txtNoPol.setEditable(false);

        btnBrowseKendaraan.setText("....");
        btnBrowseKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseKendaraanActionPerformed(evt);
            }
        });

        jLabel14.setText("Trayek :");

        jLabel15.setText("Kelas Pelayanan :");

        cmbTrayek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbKelasPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSimpanArmada.setText("Simpan");
        btnSimpanArmada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanArmadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout createArmadaLayout = new javax.swing.GroupLayout(createArmada.getContentPane());
        createArmada.getContentPane().setLayout(createArmadaLayout);
        createArmadaLayout.setHorizontalGroup(
            createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createArmadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbKelasPelayanan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbTrayek, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNoPol, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBrowseKendaraan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createArmadaLayout.createSequentialGroup()
                .addContainerGap(398, Short.MAX_VALUE)
                .addComponent(btnSimpanArmada)
                .addContainerGap())
        );
        createArmadaLayout.setVerticalGroup(
            createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createArmadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseKendaraan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cmbTrayek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cmbKelasPelayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btnSimpanArmada)
                .addContainerGap())
        );

        jLabel22.setText("Nomor Polisi :");

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
        jScrollPane6.setViewportView(tblBrowseKendaraan);

        javax.swing.GroupLayout browseKendaraanLayout = new javax.swing.GroupLayout(browseKendaraan.getContentPane());
        browseKendaraan.getContentPane().setLayout(browseKendaraanLayout);
        browseKendaraanLayout.setHorizontalGroup(
            browseKendaraanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(browseKendaraanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(browseKendaraanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                    .addGroup(browseKendaraanLayout.createSequentialGroup()
                        .addComponent(jLabel22)
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
                    .addComponent(jLabel22)
                    .addComponent(txtCariNomorPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariKendaraan1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SK Pelaksanaan Ijin Operasi");

        headerPanel.setBackground(new java.awt.Color(0, 0, 204));
        headerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SK Pelaksanaan Ijin Operasi");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pembuatan SK Pelaksanaan Ijin Opeasi Taksi ");

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
                .addContainerGap(557, Short.MAX_VALUE))
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

        tblSK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nomor", "Perusahaan", "Masa Berlaku"
            }
        ));
        tblSK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSKMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSK);

        btnCariNomor.setText("Cari Berdarkan Nomor");
        btnCariNomor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariNomorActionPerformed(evt);
            }
        });

        btnCariPerusahaan.setText("Cari Bersarkan Perusahaan");
        btnCariPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPerusahaanActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh Tabel");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCariNomor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariPerusahaan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 406, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCariNomor)
                    .addComponent(btnCariPerusahaan)
                    .addComponent(btnRefresh))
                .addContainerGap())
        );

        tabpane.addTab("Daftar SK Pelaksanaan Ijin Operasi", jPanel2);

        jLabel3.setText("Nomor :");

        jLabel5.setText("Tanggal :");

        jLabel6.setText("Ijin Operasi :");

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBuatBaru.setText("Buat Baru");
        btnBuatBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuatBaruActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtIjinOperasi.setEditable(false);

        btnBrowseIjinOperasi.setText("....");
        btnBrowseIjinOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseIjinOperasiActionPerformed(evt);
            }
        });

        jLabel4.setText("Perusahaan :");

        txtPerusahaan.setEditable(false);

        btnCetak.setText("Cetak SK Pelaksanaan Ijin Operasi");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCetak)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuatBaru)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tglSk, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(txtIjinOperasi, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnBrowseIjinOperasi, 0, 0, Short.MAX_VALUE)))
                            .addComponent(txtPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tglSk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIjinOperasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseIjinOperasi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBuatBaru)
                    .addComponent(btnHapus)
                    .addComponent(btnCetak))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Surat Keputusan", jPanel4);

        tblArmada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nomor Polisi", "Nomor Uji", "Merk / Type", "Tahun", "Daya Angkut", "Wilayah Operasi"
            }
        ));
        jScrollPane2.setViewportView(tblArmada);

        btnTambahArmada.setText("Tambah Armada");
        btnTambahArmada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahArmadaActionPerformed(evt);
            }
        });

        btnHapusArmada.setText("Hapus Armada");
        btnHapusArmada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusArmadaActionPerformed(evt);
            }
        });

        btnCetakLampiran.setText("Cetak Lampiran Armada");
        btnCetakLampiran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakLampiranActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnCetakLampiran)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                        .addComponent(btnHapusArmada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahArmada)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahArmada)
                    .addComponent(btnHapusArmada)
                    .addComponent(btnCetakLampiran))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Lampiran Armada", jPanel5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabpane.addTab("Detai SK Pelaksanaan Ijin Operasi / Buat Baru", jPanel3);

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
                .addComponent(tabpane, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabpane, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPerusahaanActionPerformed
        pilihPerusahaan.setLocationRelativeTo(this);
        pilihPerusahaan.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        loadPerusahaan();
        cmbPerusahaan.setSelectedItem(null);
        pilihPerusahaan.setSize(300, 200);
        pilihPerusahaan.setModal(true);
        pilihPerusahaan.setVisible(true);
    }//GEN-LAST:event_btnCariPerusahaanActionPerformed

    private void loadPerusahaan() {
        try {
            cmbPerusahaan.removeAllItems();
            for (Perusahaan p : pDao.getAllPerusahaan()) {
                cmbPerusahaan.addItem(p);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        reloadAll();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCariNomorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariNomorActionPerformed
        try {
            String nomor = JOptionPane.showInputDialog(this, "Nomor SK : ");
            list = dao.getByNomor(nomor);
            bindTabel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnCariNomorActionPerformed

    private void btnPencarianPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPencarianPerusahaanActionPerformed
        try {
            Perusahaan p = (Perusahaan) cmbPerusahaan.getSelectedItem();
            list = dao.getByPerusahaan(p);
            bindTabel();
            pilihPerusahaan.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnPencarianPerusahaanActionPerformed

    private void loadArmada() {
        try {
            List<Armada> armadas = selected.getArmadas();
            String title[] = {"No. Polisi", "No. Uji", "Merk/Type", "Daya Angkut", "Trayek"};
            Object[][] data = new Object[armadas.size()][5];
            int row = 0;
            for (Armada a : armadas) {
                data[row][0] = a.getKendaraan().getNomorPolisi();
                data[row][1] = a.getKendaraan().getNomorUji();
                data[row][2] = a.getKendaraan().getMerk().getNama() + "/" + a.getKendaraan().getType();
                data[row][3] = a.getKendaraan().getDayaAngkutOrang() + " org, " + a.getKendaraan().getDayaAngkutBarang() + " kg";
                data[row][4] = a.getTrayek().getNama();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblArmada.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void tblSKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSKMouseClicked
        selected = list.get(tblSK.getSelectedRow());
        txtNomor.setText(selected.getNomor());
        tglSk.setDate(selected.getTanggal());
        txtIjinOperasi.setText(selected.getIjinOperasi().getNomor());
        txtPerusahaan.setText(selected.getIjinOperasi().getPerusahaan().getNama());
        loadArmada();
    }//GEN-LAST:event_tblSKMouseClicked

    private void bindIjinOperasi() {
        try {
            String title[] = {"Nomor", "Perusahaan", "Masa Berlaku"};
            Object data[][] = new Object[listIjinOperasi.size()][3];
            int row = 0;
            for (IjinOperasi i : listIjinOperasi) {
                data[row][0] = i.getNomor();
                data[row][1] = i.getPerusahaan().getNama();
                data[row][2] = formater.format(i.getMasaBerlakuMulai()) + " s/d "
                        + formater.format(i.getMasaBerlakuSelesai());
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblIjin.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void btnBrowseIjinOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseIjinOperasiActionPerformed
        try {
            listIjinOperasi = iDao.getAllIjinOperasi();
            bindIjinOperasi();
            pilihIjinOperasi.setLocationRelativeTo(null);
            pilihIjinOperasi.getContentPane().setBackground(new java.awt.Color(51, 102, 255));

            pilihIjinOperasi.setSize(600, 500);
            pilihIjinOperasi.setModal(true);
            pilihIjinOperasi.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnBrowseIjinOperasiActionPerformed

    private void btnCariIjinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariIjinActionPerformed
        try {
            listIjinOperasi = iDao.getByNomor(txtNoIjinOperasi.getText());
            bindIjinOperasi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnCariIjinActionPerformed

    private void tblIjinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIjinMouseClicked
        ijin = listIjinOperasi.get(tblIjin.getSelectedRow());
        txtIjinOperasi.setText(ijin.getNomor());
        txtPerusahaan.setText(ijin.getPerusahaan().getNama());
        pilihIjinOperasi.setVisible(false);
}//GEN-LAST:event_tblIjinMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
            if (!txtNomor.getText().isEmpty() && ijin != null) {
                if (selected == null) {
                    SkPelaksanaanIjinOperasi sk = new SkPelaksanaanIjinOperasi();
                    sk.setNomor(txtNomor.getText());
                    sk.setTanggal(tglSk.getDate());
                    sk.setIjinOperasi(ijin);
                    List<Armada> armadas = ijin.getArmadas();
                    sk.setArmadas(armadas);
                    dao.insert(sk);
                } else {
                    selected.setNomor(txtNomor.getText());
                    selected.setTanggal(tglSk.getDate());
                    selected.setIjinOperasi(ijin);
                    dao.update(selected);
                    selected = null;
                }
                reloadAll();
                ijin = null;
                txtNomor.setText("");
                tglSk.setDate(new Date());
                txtIjinOperasi.setText("");
                txtPerusahaan.setText("");
                JOptionPane.showMessageDialog(this, "Data tersimpan");
                tabpane.setSelectedIndex(0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBuatBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuatBaruActionPerformed
        ijin = null;
        selected = null;
        txtNomor.setText("");
        tglSk.setDate(new Date());
        txtIjinOperasi.setText("");
        txtPerusahaan.setText("");
        txtNomor.requestFocus();
    }//GEN-LAST:event_btnBuatBaruActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        try {
            if (selected != null) {
                dao.delete(selected);
                selected = null;
                reloadAll();
                ijin = null;
                txtNomor.setText("");
                tglSk.setDate(new Date());
                txtIjinOperasi.setText("");
                txtPerusahaan.setText("");
                JOptionPane.showMessageDialog(this, "Data terhapus");
                tabpane.setSelectedIndex(0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBrowseKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseKendaraanActionPerformed
        try {
            listKendaraan = kDao.getAll();
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
            tblBrowseKendaraan.setModel(model);
            browseKendaraan.setSize(800, 600);
            browseKendaraan.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
            browseKendaraan.setModal(true);
            browseKendaraan.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnBrowseKendaraanActionPerformed

    private void btnSimpanArmadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanArmadaActionPerformed
        if (kendaraan != null && cmbTrayek.getSelectedItem() != null && cmbKelasPelayanan.getSelectedItem() != null) {
            Armada a = new Armada();
            a.setKendaraan(kendaraan);
            a.setTrayek((Trayek) cmbTrayek.getSelectedItem());
            a.setKelasPelayanan((KelasPelayanan) cmbKelasPelayanan.getSelectedItem());
            selected.getArmadas().add(a);
            dao.update(selected);
            createArmada.setVisible(false);
            loadArmada();
        }
}//GEN-LAST:event_btnSimpanArmadaActionPerformed

    private void btnCariKendaraan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKendaraan1ActionPerformed
        try {
            listKendaraan = kDao.getByNomorPolisi(txtCariNomorPolisi.getText());
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
            tblBrowseKendaraan.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnCariKendaraan1ActionPerformed

    private void tblBrowseKendaraanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBrowseKendaraanMouseClicked
        kendaraan = (Kendaraan) listKendaraan.get(tblBrowseKendaraan.getSelectedRow());
        txtNoPol.setText(kendaraan.getNomorPolisi());
        browseKendaraan.setVisible(false);
}//GEN-LAST:event_tblBrowseKendaraanMouseClicked

    private void btnTambahArmadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahArmadaActionPerformed
        createArmada.setLocationRelativeTo(this);
        createArmada.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        txtNoPol.setText("");
        cmbTrayek.setSelectedItem(null);
        cmbKelasPelayanan.setSelectedItem(null);
        createArmada.setSize(550, 250);
        createArmada.setModal(true);
        createArmada.setVisible(true);
    }//GEN-LAST:event_btnTambahArmadaActionPerformed

    private void btnHapusArmadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusArmadaActionPerformed
        if (tblArmada.getSelectedRow() > -1) {
            Armada a = selected.getArmadas().get(tblArmada.getSelectedRow());
            selected.getArmadas().remove(a);
            dao.update(selected);
            JOptionPane.showMessageDialog(this, "Armada terhapus");
            loadArmada();
        }
    }//GEN-LAST:event_btnHapusArmadaActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed

        try {
            if (selected != null) {
                //ambil parameter
                Map mp = new HashMap();
                mp.put("nomor", selected.getNomor());

                DataSource ds = (DataSource) MainApps.appContext.getBean("dataSource");
                JDialog printView = new JDialog();
                JasperPrint print = JasperFillManager.fillReport("print/skPelaksanaanIjinOperasi.jasper",
                        mp, ds.getConnection());
                JRViewer viewer = new JRViewer(print);
                printView.getContentPane().add(viewer);
                printView.setModal(false);
                printView.setTitle("Print Ijin Trayek");
                printView.setSize(800, 500);
                printView.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnCetakActionPerformed

    private void btnCetakLampiranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakLampiranActionPerformed
        try {
            if (selected != null) {
                //ambil parameter
                Map mp = new HashMap();
                mp.put("nomor_ijin", selected.getNomor());

                DataSource ds = (DataSource) MainApps.appContext.getBean("dataSource");
                JDialog printView = new JDialog();
                JasperPrint print = JasperFillManager.fillReport("print/lampiranSkPelaksanaanIjinOperasi.jasper",
                        mp, ds.getConnection());
                JRViewer viewer = new JRViewer(print);
                printView.getContentPane().add(viewer);
                printView.setModal(false);
                printView.setTitle("Print Ijin Trayek");
                printView.setSize(800, 500);
                printView.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnCetakLampiranActionPerformed

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog browseKendaraan;
    private javax.swing.JButton btnBrowseIjinOperasi;
    private javax.swing.JButton btnBrowseKendaraan;
    private javax.swing.JButton btnBuatBaru;
    private javax.swing.JButton btnCariIjin;
    private javax.swing.JButton btnCariKendaraan1;
    private javax.swing.JButton btnCariNomor;
    private javax.swing.JButton btnCariPerusahaan;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnCetakLampiran;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnHapusArmada;
    private javax.swing.JButton btnPencarianPerusahaan;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpanArmada;
    private javax.swing.JButton btnTambahArmada;
    private javax.swing.JComboBox cmbKelasPelayanan;
    private javax.swing.JComboBox cmbPerusahaan;
    private javax.swing.JComboBox cmbTrayek;
    private javax.swing.JDialog createArmada;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JMenuItem mnuBack;
    private javax.swing.JMenuItem mnuDefault;
    private javax.swing.JMenuItem mnuHeader;
    private javax.swing.JMenu mnuWarna;
    private javax.swing.JDialog pilihIjinOperasi;
    private javax.swing.JDialog pilihPerusahaan;
    private javax.swing.JTabbedPane tabpane;
    private javax.swing.JTable tblArmada;
    private javax.swing.JTable tblBrowseKendaraan;
    private javax.swing.JTable tblIjin;
    private javax.swing.JTable tblSK;
    private org.jdesktop.swingx.JXDatePicker tglSk;
    private javax.swing.JTextField txtCariNomorPolisi;
    private javax.swing.JTextField txtIjinOperasi;
    private javax.swing.JTextField txtNoIjinOperasi;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtNomor;
    private javax.swing.JTextField txtPerusahaan;
    // End of variables declaration//GEN-END:variables
}
