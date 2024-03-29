/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserFrm.java
 *
 * Created on Feb 1, 2012, 11:14:19 PM
 */
package com.x3.musrenbang.ui;

import com.x3.musrenbang.dao.KecamatanDAO;
import com.x3.musrenbang.dao.RolesDAO;
import com.x3.musrenbang.dao.SkpdDAO;
import com.x3.musrenbang.dao.UsersDAO;
import com.x3.musrenbang.entity.Kecamatan;
import com.x3.musrenbang.entity.Roles;
import com.x3.musrenbang.entity.Skpd;
import com.x3.musrenbang.entity.Users;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hendro Steven
 */
public class UserFrm extends javax.swing.JDialog {

    private List<Users> list;
    private Users selected;
    private UsersDAO userDAO = (UsersDAO) MainApp.appContext.getBean("userDAO");
    private RolesDAO roleDAO = (RolesDAO) MainApp.appContext.getBean("rolesDAO");
    private SkpdDAO skpdDAO = (SkpdDAO) MainApp.appContext.getBean("skpdDAO");
    private KecamatanDAO kecamatanDAO = (KecamatanDAO) MainApp.appContext.getBean("kecamatanDAO");

    /** Creates new form UserFrm */
    public UserFrm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        loadRoles();
        loadSkpd();
        loadKecamatan();
        loadTable();
    }

    private void loadRoles() {
        try {
            DefaultComboBoxModel model = new DefaultComboBoxModel(roleDAO.gets().toArray());
            cmbRoles.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadSkpd() {
        try {
            DefaultComboBoxModel model = new DefaultComboBoxModel(skpdDAO.gets().toArray());
            cmbSkpd.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadKecamatan() {
        try {
            DefaultComboBoxModel model = new DefaultComboBoxModel(kecamatanDAO.gets().toArray());
            cmbKecamatan.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void loadTable() {
        try {
            list = userDAO.gets();
            String title[] = {"Nama", "Roles", "Skpd", "Kecamatan"};
            Object data[][] = new Object[list.size()][4];
            int row = 0;
            for (Users user : list) {
                data[row][0] = user.getNama();
                data[row][1] = user.getRoles().getKeterangan();
                if (user.getRoles().getId() == 2) {
                    data[row][2] = user.getSkpd().getNama();
                } else {
                    data[row][2] = "";
                }
                if (user.getRoles().getId() == 3) {
                    data[row][3] = user.getKecamatan().getNama();
                } else {
                    data[row][3] = "";
                }
                ++row;
            }
            DefaultTableModel model = new DefaultTableModel(data, title);
            tblUser.setModel(model);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void setEnableForm(boolean status) {
        txtUserName.setEnabled(status);
        txtPassword.setEnabled(status);
        txtNama.setEnabled(status);
        txtAlamat.setEnabled(status);
        txtEmail.setEnabled(status);
        txtTelp.setEnabled(status);
        cmbRoles.setEnabled(status);
    }

    private void clearForm() {
        txtUserName.setText("");
        txtPassword.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtEmail.setText("");
        txtTelp.setText("");
        cmbRoles.setSelectedIndex(0);
        cmbSkpd.setSelectedIndex(0);
        cmbKecamatan.setSelectedIndex(0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTelp = new javax.swing.JTextField();
        cmbRoles = new javax.swing.JComboBox();
        cmbSkpd = new javax.swing.JComboBox();
        cmbKecamatan = new javax.swing.JComboBox();
        txtPassword = new javax.swing.JPasswordField();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data User");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setText("User");

        jLabel2.setText("Pengaturan data User atau Pengguna Aplikasi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addComponent(jLabel1))
                .addContainerGap(588, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("Username :");

        jLabel4.setText("Password :");

        jLabel5.setText("Nama Lengkap :");

        jLabel6.setText("Alamat :");

        jLabel7.setText("Email :");

        jLabel8.setText("Telp :");

        jLabel9.setText("Role :");

        jLabel10.setText("Skpd :");

        jLabel11.setText("Kecamatan :");

        txtUserName.setEnabled(false);

        txtNama.setEnabled(false);

        txtAlamat.setEnabled(false);

        txtEmail.setEnabled(false);

        txtTelp.setEnabled(false);

        cmbRoles.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbRoles.setEnabled(false);
        cmbRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRolesActionPerformed(evt);
            }
        });

        cmbSkpd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbSkpd.setEnabled(false);

        cmbKecamatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKecamatan.setEnabled(false);

        txtPassword.setEnabled(false);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUbah.setText("Ubah");
        btnUbah.setEnabled(false);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.setEnabled(false);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.setEnabled(false);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbKecamatan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbSkpd, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbRoles, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTelp, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbRoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbSkpd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cmbKecamatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatal)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnUbah)
                    .addComponent(btnTambah))
                .addContainerGap())
        );

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        setEnableForm(true);
        clearForm();
        txtUserName.requestFocus();
        btnTambah.setEnabled(false);
        btnHapus.setEnabled(false);
        btnSimpan.setEnabled(true);
        btnBatal.setEnabled(true);
        btnUbah.setEnabled(false);
}//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        if (selected != null) {
            setEnableForm(true);
            txtUserName.requestFocus();
            btnUbah.setEnabled(false);
            btnTambah.setEnabled(false);
            btnHapus.setEnabled(false);
            btnSimpan.setEnabled(true);
            btnBatal.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih data pada tabel");
        }
}//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        try {
            if (selected != null) {
                //konfirmasi dan proses hapus
                if (JOptionPane.showConfirmDialog(this, "Hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    userDAO.delete(selected);
                    loadTable();
                    JOptionPane.showMessageDialog(this, "Data terhapus");
                    clearForm();
                }
                setEnableForm(false);
                clearForm();
                btnTambah.setEnabled(true);
                btnUbah.setEnabled(false);
                btnHapus.setEnabled(false);
                btnSimpan.setEnabled(false);
                btnBatal.setEnabled(false);
                selected = null;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnHapusActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        try {
            if (cmbRoles.getSelectedItem() != null && !txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
                Roles role = (Roles) cmbRoles.getSelectedItem();
                if (selected == null) { //insert
                    //insert data
                    Users user = new Users();
                    user.setUserName(txtUserName.getText());
                    user.setPassword(txtPassword.getText());
                    user.setAlamat(txtAlamat.getText());
                    user.setNama(txtNama.getText());
                    user.setEmail(txtEmail.getText());
                    user.setTlp(txtTelp.getText());
                    user.setRoles(role);

                    if (role.getId() == 2) {//skpd
                        user.setSkpd((Skpd) cmbSkpd.getSelectedItem());
                    } else if (role.getId() == 3) {//kecamatan
                        user.setKecamatan((Kecamatan) cmbKecamatan.getSelectedItem());
                    }
                    userDAO.insert(user);
                    loadTable();
                    JOptionPane.showMessageDialog(this, "Data tersimpan");
                    setEnableForm(false);
                    clearForm();
                    btnSimpan.setEnabled(false);
                    btnBatal.setEnabled(false);
                    btnUbah.setEnabled(false);
                    btnHapus.setEnabled(false);
                    btnTambah.setEnabled(true);
                } else {
                    //update data
                    selected.setUserName(txtUserName.getText());
                    selected.setPassword(txtPassword.getText());
                    selected.setAlamat(txtAlamat.getText());
                    selected.setNama(txtNama.getText());
                    selected.setEmail(txtEmail.getText());
                    selected.setTlp(txtTelp.getText());
                    selected.setRoles(role);
                    if (role.getId() == 2) {//skpd
                        selected.setSkpd((Skpd) cmbSkpd.getSelectedItem());
                    } else if (role.getId() == 3) {//kecamatan
                        selected.setKecamatan((Kecamatan) cmbKecamatan.getSelectedItem());
                    }
                    userDAO.update(selected);
                    loadTable();
                    setEnableForm(false);
                    btnTambah.setEnabled(false);
                    btnUbah.setEnabled(true);
                    btnHapus.setEnabled(true);
                    btnSimpan.setEnabled(false);
                    btnBatal.setEnabled(true);
                    selected = null;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Input data dengan lengkap");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        setEnableForm(false);
        clearForm();
        selected = null;
        btnTambah.setEnabled(true);
        btnHapus.setEnabled(false);
        btnUbah.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnBatal.setEnabled(false);
}//GEN-LAST:event_btnBatalActionPerformed

    private int getRolesIndex(Roles r) {
        int index = -1;
        for (Roles tmp : roleDAO.gets()) {
            ++index;
            if (tmp.getId() == r.getId()) {
                break;
            }
        }
        return index;
    }

    private int getSkpdIndex(Skpd s) {
        int index = -1;
        for (Skpd tmp : skpdDAO.gets()) {
            ++index;
            if (tmp.getId() == s.getId()) {
                break;
            }
        }
        return index;
    }

    private int getKecamatanIndex(Kecamatan k) {
        int index = -1;
        for (Kecamatan tmp : kecamatanDAO.gets()) {
            ++index;
            if (tmp.getId() == k.getId()) {
                break;
            }
        }
        return index;
    }

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        selected = list.get(tblUser.getSelectedRow());
        txtUserName.setText(selected.getUserName());
        txtPassword.setText(selected.getPassword());
        txtNama.setText(selected.getNama());
        txtAlamat.setText(selected.getAlamat());
        txtEmail.setText(selected.getEmail());
        txtTelp.setText(selected.getTlp());
        cmbRoles.setSelectedIndex(getRolesIndex(selected.getRoles()));
        if (selected.getRoles().getId() == 2) {
            cmbSkpd.setSelectedIndex(getSkpdIndex(selected.getSkpd()));
            //cmbSkpd.setEnabled(true);
        } else if (selected.getRoles().getId() == 3) {
            cmbKecamatan.setSelectedIndex(getKecamatanIndex(selected.getKecamatan()));
            //cmbKecamatan.setEnabled(true);
        }
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        btnBatal.setEnabled(true);
        btnTambah.setEnabled(false);
        btnSimpan.setEnabled(false);
    }//GEN-LAST:event_tblUserMouseClicked

    private void cmbRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRolesActionPerformed
        Roles r = (Roles) cmbRoles.getSelectedItem();
        if (r.getId() == 2) {
            cmbSkpd.setEnabled(true);
            cmbKecamatan.setEnabled(false);
        } else if (r.getId() == 3) {
            cmbSkpd.setEnabled(false);
            cmbKecamatan.setEnabled(true);
        } else {
            cmbSkpd.setEnabled(false);
            cmbKecamatan.setEnabled(false);
        }
    }//GEN-LAST:event_cmbRolesActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbKecamatan;
    private javax.swing.JComboBox cmbRoles;
    private javax.swing.JComboBox cmbSkpd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNama;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtTelp;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
