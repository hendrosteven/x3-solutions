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
import com.x3.dishub.dao.SpioDAO;
import com.x3.dishub.dao.TrayekDAO;
import com.x3.dishub.dao.WarnaDAO;
import com.x3.dishub.entity.Armada;
import com.x3.dishub.entity.IjinOperasi;
import com.x3.dishub.entity.KelasPelayanan;
import com.x3.dishub.entity.Kendaraan;
import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.Spio;
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
public class IjinOperasiDialog extends javax.swing.JDialog {

    private WarnaDAO wDao = (WarnaDAO) MainApps.appContext.getBean("warnaDAO");
    private Warna headerColor;
    private Warna backgroudColor;
    private IjinOperasiDAO dao = (IjinOperasiDAO) MainApps.appContext.getBean("ijinOperasiDAO");
    private List<IjinOperasi> list;
    private IjinOperasi selected;
    private SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
    private PerusahaanDAO pDao = (PerusahaanDAO) MainApps.appContext.getBean("perusahaanDAO");
    private SpioDAO sDao = (SpioDAO) MainApps.appContext.getBean("spioDAO");
    private TrayekDAO tDao = (TrayekDAO) MainApps.appContext.getBean("trayekDAO");
    private KelasPelayananDAO kpDao = (KelasPelayananDAO) MainApps.appContext.getBean("kelasPelayananDAO");
    private KendaraanDAO kDao = (KendaraanDAO) MainApps.appContext.getBean("kendaraanDAO");
    private List<Kendaraan> listKendaraan;
    private Kendaraan kendaraan;

    /** Creates new form IjinTrayekDialog */
    public IjinOperasiDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        loadWarna();
        updateWarna();
        //this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        reloadAllIjin();
        loadPerusahaan();
        loadSpio();
        loadKelasPelayanan();
        loadTrayek();
    }

    private void loadWarna() {
        headerColor = wDao.getByNama("IjinOperasiDialogHd");
        if (headerColor == null) {
            headerColor = new Warna();
            headerColor.setName("IjinOperasiDialogHd");
            headerColor.setRgb(-16777012);
            wDao.insert(headerColor);
            loadWarna();
        }
        backgroudColor = wDao.getByNama("IjinOperasiDialogBg");
        if (backgroudColor == null) {
            backgroudColor = new Warna();
            backgroudColor.setName("IjinOperasiDialogBg");
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

    private void reloadAllIjin() {
        try {
            list = dao.getAllIjinOperasi();
            bindTabel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void bindTabel() {
        try {
            String title[] = {"Nomor", "Perusahaan", "Masa Berlaku"};
            Object[][] data = new Object[list.size()][3];
            int row = 0;
            for (IjinOperasi i : list) {
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cariTanggalDialog = new javax.swing.JDialog();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tglCariMulai = new org.jdesktop.swingx.JXDatePicker();
        jLabel22 = new javax.swing.JLabel();
        tglCariSelesai = new org.jdesktop.swingx.JXDatePicker();
        btnCari = new javax.swing.JButton();
        createArmada = new javax.swing.JDialog();
        jLabel23 = new javax.swing.JLabel();
        txtNoPol = new javax.swing.JTextField();
        btnBrowseKendaraan = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cmbTrayek = new javax.swing.JComboBox();
        cmbKelasPelayanan = new javax.swing.JComboBox();
        btnSimpanArmada = new javax.swing.JButton();
        browseKendaraan = new javax.swing.JDialog();
        jLabel26 = new javax.swing.JLabel();
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
        tblIjin = new javax.swing.JTable();
        btnCariNomor = new javax.swing.JButton();
        btnCariTanggal = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tglSuratPermohonan = new org.jdesktop.swingx.JXDatePicker();
        txtNoSuratPermohonan = new javax.swing.JTextField();
        cmbSpio = new javax.swing.JComboBox();
        tglBerlakuMulai = new org.jdesktop.swingx.JXDatePicker();
        jLabel9 = new javax.swing.JLabel();
        tglBerlakuAkhir = new org.jdesktop.swingx.JXDatePicker();
        cmbPerusahaan = new javax.swing.JComboBox();
        txtNomor = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnBuatBaru = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblArmada = new javax.swing.JTable();
        btnTambahArmada = new javax.swing.JButton();
        btnHapusArmada = new javax.swing.JButton();
        btnCetakLampiran = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuUbahWarna = new javax.swing.JMenu();
        mnuWarnaHeader = new javax.swing.JMenuItem();
        mnuWarnaBack = new javax.swing.JMenuItem();
        mnuWarnaDefault = new javax.swing.JMenuItem();

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Pilih Tanggal Masa Berakhir Ijin Operasi");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Mulai Tanggal :");

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Sampai Tanggal :");

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cariTanggalDialogLayout = new javax.swing.GroupLayout(cariTanggalDialog.getContentPane());
        cariTanggalDialog.getContentPane().setLayout(cariTanggalDialogLayout);
        cariTanggalDialogLayout.setHorizontalGroup(
            cariTanggalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cariTanggalDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cariTanggalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tglCariMulai, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(tglCariSelesai, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                    .addComponent(btnCari, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        cariTanggalDialogLayout.setVerticalGroup(
            cariTanggalDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cariTanggalDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(29, 29, 29)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tglCariMulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tglCariSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnCari)
                .addContainerGap())
        );

        createArmada.setTitle("Tambah Armada");

        jLabel23.setText("Kendaraan :");

        txtNoPol.setEditable(false);

        btnBrowseKendaraan.setText("....");
        btnBrowseKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseKendaraanActionPerformed(evt);
            }
        });

        jLabel24.setText("Trayek :");

        jLabel25.setText("Kelas Pelayanan :");

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
                    .addComponent(jLabel25)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23))
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
                    .addComponent(jLabel23)
                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowseKendaraan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(cmbTrayek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(createArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(cmbKelasPelayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btnSimpanArmada)
                .addContainerGap())
        );

        jLabel26.setText("Nomor Polisi :");

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
                        .addComponent(jLabel26)
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
                    .addComponent(jLabel26)
                    .addComponent(txtCariNomorPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariKendaraan1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ijin Operasi");

        headerPanel.setBackground(new java.awt.Color(0, 0, 204));
        headerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ijin Operasi");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pembuatan Ijin Opeasi Taksi ");

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
                .addContainerGap(675, Short.MAX_VALUE))
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

        tblIjin.setModel(new javax.swing.table.DefaultTableModel(
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
        tblIjin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblIjinMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblIjin);

        btnCariNomor.setText("Cari Berdarkan Nomor");
        btnCariNomor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariNomorActionPerformed(evt);
            }
        });

        btnCariTanggal.setText("Cari Bersarkan Masa Berlaku");
        btnCariTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariTanggalActionPerformed(evt);
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
                        .addComponent(btnCariTanggal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
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
                    .addComponent(btnCariTanggal)
                    .addComponent(btnRefresh))
                .addContainerGap())
        );

        tabpane.addTab("Daftar Ijin Operasi", jPanel2);

        jLabel3.setText("Nomor :");

        jLabel4.setText("Perusahaan :");

        jLabel5.setText("Masa Berlaku :");

        jLabel6.setText("Berdasar Pada SPIO :");

        jLabel7.setText("Nomor Surat Permohonan :");

        jLabel8.setText("Tanggal Surat Permohonan :");

        cmbSpio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("s/d");

        cmbPerusahaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        btnCetak.setText("Cetak Ijin Operasi");
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
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tglSuratPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNomor, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                                .addComponent(cmbPerusahaan, 0, 374, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(tglBerlakuMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(tglBerlakuAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cmbSpio, 0, 374, Short.MAX_VALUE)
                                .addComponent(txtNoSuratPermohonan, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)))
                        .addContainerGap(275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnCetak)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 446, Short.MAX_VALUE)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuatBaru)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan)
                        .addContainerGap())))
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
                    .addComponent(jLabel4)
                    .addComponent(cmbPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tglBerlakuMulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(tglBerlakuAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbSpio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNoSuratPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tglSuratPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
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

        jTabbedPane2.addTab("Lapiran Armada", jPanel5);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabpane.addTab("Detail Ijin Operasi / Buat Baru", jPanel3);

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

    private void btnCariTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariTanggalActionPerformed
        cariTanggalDialog.setLocationRelativeTo(this);
        cariTanggalDialog.getContentPane().setBackground(new java.awt.Color(51, 102, 255));
        cariTanggalDialog.setSize(350, 250);
        cariTanggalDialog.setModal(true);
        cariTanggalDialog.setVisible(true);
    }//GEN-LAST:event_btnCariTanggalActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        reloadAllIjin();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCariNomorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariNomorActionPerformed
        try {
            list = dao.getByNomor(JOptionPane.showInputDialog(this, "Nomor Surat :"));
            bindTabel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnCariNomorActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        try {
            list = dao.getByDate(tglCariMulai.getDate(), tglCariSelesai.getDate());
            bindTabel();
            cariTanggalDialog.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnCariActionPerformed

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

    private int getCmbPerusahaanIndex(Perusahaan p) {
        int index = 0;
        for (Perusahaan tmp : pDao.getAllPerusahaan()) {
            if (tmp.getId() == p.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }

    private void loadSpio() {
        try {
            cmbSpio.removeAllItems();
            for (Spio s : sDao.getAllSpio()) {
                cmbSpio.addItem(s);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private int getCmbSpioIndex(Spio s) {
        int index = 0;
        for (Spio tmp : sDao.getAllSpio()) {
            if (tmp.getId() == s.getId()) {
                break;
            }
            ++index;
        }
        return index;
    }

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

    private void tblIjinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIjinMouseClicked
        selected = list.get(tblIjin.getSelectedRow());
        txtNomor.setText(selected.getNomor());
        cmbPerusahaan.setSelectedIndex(getCmbPerusahaanIndex(selected.getPerusahaan()));
        tglBerlakuMulai.setDate(selected.getMasaBerlakuMulai());
        tglBerlakuAkhir.setDate(selected.getMasaBerlakuSelesai());
        cmbSpio.setSelectedIndex(getCmbSpioIndex(selected.getSpio()));
        tglSuratPermohonan.setDate(selected.getTanggalSuratPermohonan());
        txtNoSuratPermohonan.setText(selected.getNomorSuratPermohonan());
        loadArmada();
    }//GEN-LAST:event_tblIjinMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
            if (!txtNomor.getText().isEmpty() && !txtNoSuratPermohonan.getText().isEmpty()
                    && cmbPerusahaan.getSelectedItem() != null && cmbSpio.getSelectedItem() != null) {
                if (selected == null) {
                    IjinOperasi i = new IjinOperasi();
                    i.setNomor(txtNomor.getText());
                    i.setPerusahaan((Perusahaan) cmbPerusahaan.getSelectedItem());
                    i.setMasaBerlakuMulai(tglBerlakuMulai.getDate());
                    i.setMasaBerlakuSelesai(tglBerlakuAkhir.getDate());
                    i.setSpio((Spio) cmbSpio.getSelectedItem());
                    i.setNomorSuratPermohonan(txtNoSuratPermohonan.getText());
                    i.setTanggalSuratPermohonan(tglSuratPermohonan.getDate());
                    dao.insert(i);
                } else {
                    selected.setNomor(txtNomor.getText());
                    selected.setPerusahaan((Perusahaan) cmbPerusahaan.getSelectedItem());
                    selected.setMasaBerlakuMulai(tglBerlakuMulai.getDate());
                    selected.setMasaBerlakuSelesai(tglBerlakuAkhir.getDate());
                    selected.setSpio((Spio) cmbSpio.getSelectedItem());
                    selected.setNomorSuratPermohonan(txtNoSuratPermohonan.getText());
                    selected.setTanggalSuratPermohonan(tglSuratPermohonan.getDate());
                    dao.update(selected);
                    selected = null;
                }
                reloadAllIjin();
                txtNomor.setText("");
                cmbPerusahaan.setSelectedItem(null);
                tglBerlakuMulai.setDate(new Date());
                tglBerlakuAkhir.setDate(new Date());
                cmbSpio.setSelectedItem(null);
                txtNoSuratPermohonan.setText("");
                tglSuratPermohonan.setDate(new Date());
                tabpane.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Data tersimpan");
            } else {
                JOptionPane.showMessageDialog(this, "Masukan data dengan lengkap");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBuatBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuatBaruActionPerformed
        selected = null;
        txtNomor.setText("");
        cmbPerusahaan.setSelectedItem(null);
        tglBerlakuMulai.setDate(new Date());
        tglBerlakuAkhir.setDate(new Date());
        cmbSpio.setSelectedItem(null);
        txtNoSuratPermohonan.setText("");
        tglSuratPermohonan.setDate(new Date());
        txtNomor.requestFocus();
    }//GEN-LAST:event_btnBuatBaruActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        try {
            if (selected != null) {
                dao.delete(selected);
                selected = null;
                txtNomor.setText("");
                cmbPerusahaan.setSelectedItem(null);
                tglBerlakuMulai.setDate(new Date());
                tglBerlakuAkhir.setDate(new Date());
                cmbSpio.setSelectedItem(null);
                txtNoSuratPermohonan.setText("");
                tglSuratPermohonan.setDate(new Date());
                reloadAllIjin();
                JOptionPane.showMessageDialog(this, "Data terhapus");
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

    private void btnHapusArmadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusArmadaActionPerformed
        Armada a = selected.getArmadas().get(tblArmada.getSelectedRow());
        selected.getArmadas().remove(a);
        dao.update(selected);
        JOptionPane.showMessageDialog(this, "Armada terhapus");
        loadArmada();
    }//GEN-LAST:event_btnHapusArmadaActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed

        try {
            if (selected != null) {
                //ambil parameter
                Map mp = new HashMap();
                mp.put("nomor", selected.getNomor());

                DataSource ds = (DataSource) MainApps.appContext.getBean("dataSource");
                JDialog printView = new JDialog();
                JasperPrint print = JasperFillManager.fillReport("print/ijinOperasi"
                        + ".jasper",
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
                JasperPrint print = JasperFillManager.fillReport("print/lampiranIjinOperasi.jasper",
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

    private void mnuWarnaHeaderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuWarnaHeaderActionPerformed
        Color tmp = JColorChooser.showDialog(this, "Ganti Warna Header", headerPanel.getBackground());
        headerColor.setRgb(tmp.getRGB());
        wDao.update(headerColor);
        updateWarna();
    }//GEN-LAST:event_mnuWarnaHeaderActionPerformed

    private void mnuWarnaBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuWarnaBackActionPerformed
        Color tmp = JColorChooser.showDialog(this, "Ganti Warna Backgroud", this.getContentPane().getBackground());
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog browseKendaraan;
    private javax.swing.JButton btnBrowseKendaraan;
    private javax.swing.JButton btnBuatBaru;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCariKendaraan1;
    private javax.swing.JButton btnCariNomor;
    private javax.swing.JButton btnCariTanggal;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnCetakLampiran;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnHapusArmada;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnSimpanArmada;
    private javax.swing.JButton btnTambahArmada;
    private javax.swing.JDialog cariTanggalDialog;
    private javax.swing.JComboBox cmbKelasPelayanan;
    private javax.swing.JComboBox cmbPerusahaan;
    private javax.swing.JComboBox cmbSpio;
    private javax.swing.JComboBox cmbTrayek;
    private javax.swing.JDialog createArmada;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JMenu mnuUbahWarna;
    private javax.swing.JMenuItem mnuWarnaBack;
    private javax.swing.JMenuItem mnuWarnaDefault;
    private javax.swing.JMenuItem mnuWarnaHeader;
    private javax.swing.JTabbedPane tabpane;
    private javax.swing.JTable tblArmada;
    private javax.swing.JTable tblBrowseKendaraan;
    private javax.swing.JTable tblIjin;
    private org.jdesktop.swingx.JXDatePicker tglBerlakuAkhir;
    private org.jdesktop.swingx.JXDatePicker tglBerlakuMulai;
    private org.jdesktop.swingx.JXDatePicker tglCariMulai;
    private org.jdesktop.swingx.JXDatePicker tglCariSelesai;
    private org.jdesktop.swingx.JXDatePicker tglSuratPermohonan;
    private javax.swing.JTextField txtCariNomorPolisi;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtNoSuratPermohonan;
    private javax.swing.JTextField txtNomor;
    // End of variables declaration//GEN-END:variables
}
