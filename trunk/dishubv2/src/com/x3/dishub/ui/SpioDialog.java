/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SpioDialog.java
 *
 * Created on Sep 26, 2011, 5:48:02 AM
 */
package com.x3.dishub.ui;

import com.x3.dishub.dao.MerkDAO;
import com.x3.dishub.dao.PerusahaanDAO;
import com.x3.dishub.dao.SpioDAO;
import com.x3.dishub.dao.TrayekDAO;
import com.x3.dishub.dao.WarnaDAO;
import com.x3.dishub.entity.Merk;
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
public class SpioDialog extends javax.swing.JDialog {

    private WarnaDAO wDao = (WarnaDAO) MainApps.appContext.getBean("warnaDAO");
    private Warna headerColor;
    private Warna backgroudColor;
    private SpioDAO dao = (SpioDAO) MainApps.appContext.getBean("spioDAO");
    private List<Spio> list;
    private Spio selected;
    private SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
    private PerusahaanDAO pDao = (PerusahaanDAO) MainApps.appContext.getBean("perusahaanDAO");
    private MerkDAO mDao = (MerkDAO) MainApps.appContext.getBean("merkDAO");
    private TrayekDAO tDao = (TrayekDAO) MainApps.appContext.getBean("trayekDAO");

    /** Creates new form SpioDialog */
    public SpioDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        //this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));

        loadWarna();
        updateWarna();
        loadSpio();
        loadPerusahaan();
        loadMerk();
        loadTrayek();
    }

    private void loadWarna() {
        headerColor = wDao.getByNama("SpioDialogHd");
        if (headerColor == null) {
            headerColor = new Warna();
            headerColor.setName("SpioDialogHd");
            headerColor.setRgb(-16777012);
            wDao.insert(headerColor);
            loadWarna();
        }
        backgroudColor = wDao.getByNama("SpioDialogBg");
        if (backgroudColor == null) {
            backgroudColor = new Warna();
            backgroudColor.setName("SpioDialogBg");
            backgroudColor.setRgb(-13408513);
            wDao.insert(backgroudColor);
            loadWarna();
        }
    }

    private void updateWarna() {
        headerPanel.setBackground(new Color(headerColor.getRgb()));
        this.getContentPane().setBackground(new Color(backgroudColor.getRgb()));
    }

    private void loadSpio() {
        try {
            list = dao.getAllSpio();
            String title[] = {"Nomor", "Perihal", "Berakhir", "Perusahaan"};
            Object data[][] = new Object[list.size()][4];
            int row = 0;
            for (Spio s : list) {
                data[row][0] = s.getNomor();
                data[row][1] = s.getPerihal();
                data[row][2] = formater.format(s.getTanggalBerakhir());
                data[row][3] = s.getPerusahaan().getNama();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblSpio.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

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

    private void loadMerk() {
        try {
            cmbMerk.removeAllItems();
            for (Merk m : mDao.getAllMerk()) {
                cmbMerk.addItem(m);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private int getCmbMerkIndex(Merk m) {
        int index = 0;
        for (Merk tmp : mDao.getAllMerk()) {
            if (tmp.getId() == m.getId()) {
                break;
            }
            ++index;
        }
        return index;
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

    private int getCmbTrayekIndex(Trayek t) {
        int index = 0;
        for (Trayek tmp : tDao.getAllTrayek()) {
            if (tmp.getId() == t.getId()) {
                break;
            }
            ++index;
        }
        return index;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSpio = new javax.swing.JTable();
        btnCari = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
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
        txtNoPermohonan = new javax.swing.JTextField();
        txtNomor = new javax.swing.JTextField();
        txtSifat = new javax.swing.JTextField();
        txtPerihal = new javax.swing.JTextField();
        tglPenetapan = new org.jdesktop.swingx.JXDatePicker();
        tglBerakhir = new org.jdesktop.swingx.JXDatePicker();
        cmbPerusahaan = new javax.swing.JComboBox();
        tglPermohonan = new org.jdesktop.swingx.JXDatePicker();
        cmbMerk = new javax.swing.JComboBox();
        txtTypeJenis = new javax.swing.JTextField();
        txtTahunKendaraan = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        cmbTrayek = new javax.swing.JComboBox();
        txtLainLain = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuWarna = new javax.swing.JMenu();
        mnuHeaer = new javax.swing.JMenuItem();
        mnuBack = new javax.swing.JMenuItem();
        mnuDefault = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Surat Persetujuan Ijin Operasi");

        headerPanel.setBackground(new java.awt.Color(0, 0, 204));
        headerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Surat Persetujuan Ijin Opearasi (SPIO)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pembuatan SPIO");

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
                .addContainerGap(432, Short.MAX_VALUE))
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
        tblSpio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSpioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSpio);

        btnCari.setText("Pencarian Bersasarkan Nomor");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCari)
                    .addComponent(btnRefresh))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Daftar Surat", jPanel2);

        jLabel3.setText("Nomor :");

        jLabel4.setText("Sifat :");

        jLabel5.setText("Perihal :");

        jLabel6.setText("Tanggal Penetapan :");

        jLabel7.setText("Tanggal Berakhir :");

        jLabel8.setText("Perusahaan :");

        jLabel9.setText("Tanggal Permohonan :");

        jLabel10.setText("Nomor Surat Permohonan :");

        jLabel11.setText("Merk :");

        jLabel12.setText("Type / Jenis :");

        jLabel13.setText("Tahun Kendaraan :");

        jLabel14.setText("Jumlah :");

        jLabel15.setText("Trayek :");

        jLabel16.setText("Lain-lain :");

        cmbPerusahaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbMerk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbTrayek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Buat Baru");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Hapus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnCetak.setText("Cetak SPIO");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtJumlah, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTahunKendaraan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                            .addComponent(txtLainLain, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                            .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSifat, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tglBerakhir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tglPenetapan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbMerk, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNoPermohonan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE))
                            .addComponent(txtTypeJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbTrayek, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbPerusahaan, javax.swing.GroupLayout.Alignment.LEADING, 0, 357, Short.MAX_VALUE))
                            .addComponent(txtPerihal, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tglPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCetak)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 488, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSifat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPerihal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tglPenetapan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tglBerakhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(tglPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNoPermohonan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cmbMerk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTypeJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTahunKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cmbTrayek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtLainLain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(btnCetak))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Detail,Penambahan dan Perubahan Surat", jPanel3);

        mnuWarna.setText("Seting");

        mnuHeaer.setText("Warna Header");
        mnuHeaer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHeaerActionPerformed(evt);
            }
        });
        mnuWarna.add(mnuHeaer);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadSpio();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        try {
            String nomor = JOptionPane.showInputDialog(this, "Nomor :");
            list = dao.getByNomor(nomor);
            String title[] = {"Nomor", "Perihal", "Berakhir", "Perusahaan"};
            Object data[][] = new Object[list.size()][4];
            int row = 0;
            for (Spio s : list) {
                data[row][0] = s.getNomor();
                data[row][1] = s.getPerihal();
                data[row][2] = formater.format(s.getTanggalBerakhir());
                data[row][3] = s.getPerusahaan().getNama();
                ++row;
            }
            TableModel model = new DefaultTableModel(data, title);
            tblSpio.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void tblSpioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSpioMouseClicked
        selected = list.get(tblSpio.getSelectedRow());
        txtNomor.setText(selected.getNomor());
        txtSifat.setText(selected.getSifat());
        txtPerihal.setText(selected.getPerihal());
        tglPenetapan.setDate(selected.getTanggalPenetapan());
        tglBerakhir.setDate(selected.getTanggalBerakhir());
        cmbPerusahaan.setSelectedIndex(getCmbPerusahaanIndex(selected.getPerusahaan()));
        tglPermohonan.setDate(selected.getTanggalPermohonan());
        txtNoPermohonan.setText(selected.getNomorSuratPermohonan());
        cmbMerk.setSelectedIndex(getCmbMerkIndex(selected.getMerk()));
        txtTypeJenis.setText(selected.getTypeJenis());
        txtTahunKendaraan.setText(selected.getTahunKendaraan());
        txtJumlah.setText(selected.getJumlah() + "");
        cmbTrayek.setSelectedIndex(getCmbTrayekIndex(selected.getTrayek()));
        txtLainLain.setText(selected.getLainLain());
    }//GEN-LAST:event_tblSpioMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            if (selected != null) {
                dao.delete(selected);
                selected = null;
                txtNomor.setText("");
                txtSifat.setText("");
                txtPerihal.setText("");
                tglPenetapan.setDate(new Date());
                tglBerakhir.setDate(new Date());
                cmbPerusahaan.setSelectedItem(null);
                tglPermohonan.setDate(new Date());
                txtNoPermohonan.setText("");
                cmbMerk.setSelectedItem(null);
                txtTypeJenis.setText("");
                txtTahunKendaraan.setText("");
                txtJumlah.setText("");
                cmbTrayek.setSelectedItem(null);
                txtLainLain.setText("");
                loadSpio();
                JOptionPane.showMessageDialog(this, "Data terhapus");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        selected = null;
        txtNomor.setText("");
        txtSifat.setText("");
        txtPerihal.setText("");
        tglPenetapan.setDate(new Date());
        tglBerakhir.setDate(new Date());
        cmbPerusahaan.setSelectedItem(null);
        tglPermohonan.setDate(new Date());
        txtNoPermohonan.setText("");
        cmbMerk.setSelectedItem(null);
        txtTypeJenis.setText("");
        txtTahunKendaraan.setText("");
        txtJumlah.setText("");
        cmbTrayek.setSelectedItem(null);
        txtLainLain.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (selected == null) {
                Spio s = new Spio();
                s.setNomor(txtNomor.getText());
                s.setSifat(txtSifat.getText());
                s.setPerihal(txtPerihal.getText());
                s.setTanggalPenetapan(tglPenetapan.getDate());
                s.setTanggalBerakhir(tglBerakhir.getDate());
                s.setPerusahaan((Perusahaan) cmbPerusahaan.getSelectedItem());
                s.setTanggalPermohonan(tglPermohonan.getDate());
                s.setNomorSuratPermohonan(txtNoPermohonan.getText());
                s.setMerk((Merk) cmbMerk.getSelectedItem());
                s.setTypeJenis(txtTypeJenis.getText());
                s.setTahunKendaraan(txtTahunKendaraan.getText());
                s.setJumlah(Integer.valueOf(txtJumlah.getText()));
                s.setTrayek((Trayek) cmbTrayek.getSelectedItem());
                s.setLainLain(txtLainLain.getText());
                dao.insert(s);
            } else {
                selected.setNomor(txtNomor.getText());
                selected.setSifat(txtSifat.getText());
                selected.setPerihal(txtPerihal.getText());
                selected.setTanggalPenetapan(tglPenetapan.getDate());
                selected.setTanggalBerakhir(tglBerakhir.getDate());
                selected.setPerusahaan((Perusahaan) cmbPerusahaan.getSelectedItem());
                selected.setTanggalPermohonan(tglPermohonan.getDate());
                selected.setNomorSuratPermohonan(txtNoPermohonan.getText());
                selected.setMerk((Merk) cmbMerk.getSelectedItem());
                selected.setTypeJenis(txtTypeJenis.getText());
                selected.setTahunKendaraan(txtTahunKendaraan.getText());
                selected.setJumlah(Integer.valueOf(txtJumlah.getText()));
                selected.setTrayek((Trayek) cmbTrayek.getSelectedItem());
                selected.setLainLain(txtLainLain.getText());
                dao.update(selected);
                selected = null;
            }
            loadSpio();
            txtNomor.setText("");
            txtSifat.setText("");
            txtPerihal.setText("");
            tglPenetapan.setDate(new Date());
            tglBerakhir.setDate(new Date());
            cmbPerusahaan.setSelectedItem(null);
            tglPermohonan.setDate(new Date());
            txtNoPermohonan.setText("");
            cmbMerk.setSelectedItem(null);
            txtTypeJenis.setText("");
            txtTahunKendaraan.setText("");
            txtJumlah.setText("");
            cmbTrayek.setSelectedItem(null);
            txtLainLain.setText("");
            JOptionPane.showMessageDialog(this, "Data tersimpan");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed

        try {
            if (selected != null) {
                //ambil parameter
                Map mp = new HashMap();
                mp.put("nomor", selected.getNomor());

                DataSource ds = (DataSource) MainApps.appContext.getBean("dataSource");
                JDialog printView = new JDialog();
                JasperPrint print = JasperFillManager.fillReport("print/spio.jasper",
                        mp, ds.getConnection());
                JRViewer viewer = new JRViewer(print);
                printView.getContentPane().add(viewer);
                printView.setModal(false);
                printView.setTitle("Print SPIO");
                printView.setSize(800, 500);
                printView.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnCetakActionPerformed

    private void mnuHeaerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHeaerActionPerformed
        Color tmp = JColorChooser.showDialog(this, "Ganti Warna Header", headerPanel.getBackground());
        headerColor.setRgb(tmp.getRGB());
        wDao.update(headerColor);
        updateWarna();
    }//GEN-LAST:event_mnuHeaerActionPerformed

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
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox cmbMerk;
    private javax.swing.JComboBox cmbPerusahaan;
    private javax.swing.JComboBox cmbTrayek;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem mnuBack;
    private javax.swing.JMenuItem mnuDefault;
    private javax.swing.JMenuItem mnuHeaer;
    private javax.swing.JMenu mnuWarna;
    private javax.swing.JTable tblSpio;
    private org.jdesktop.swingx.JXDatePicker tglBerakhir;
    private org.jdesktop.swingx.JXDatePicker tglPenetapan;
    private org.jdesktop.swingx.JXDatePicker tglPermohonan;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtLainLain;
    private javax.swing.JTextField txtNoPermohonan;
    private javax.swing.JTextField txtNomor;
    private javax.swing.JTextField txtPerihal;
    private javax.swing.JTextField txtSifat;
    private javax.swing.JTextField txtTahunKendaraan;
    private javax.swing.JTextField txtTypeJenis;
    // End of variables declaration//GEN-END:variables
}
