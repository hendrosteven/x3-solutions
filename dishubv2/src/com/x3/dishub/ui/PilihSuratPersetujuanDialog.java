/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PilihIjinUsaha.java
 *
 * Created on Sep 26, 2011, 5:21:57 AM
 */
package com.x3.dishub.ui;

import javax.swing.JFrame;

/**
 *
 * @author Hendro Steven
 */
public class PilihSuratPersetujuanDialog extends javax.swing.JDialog {

    /** Creates new form PilihIjinUsaha */
    public PilihSuratPersetujuanDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(51, 102, 255));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSpit = new javax.swing.JButton();
        btnSpio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pilih Surat Persetujuan");

        btnSpit.setText("Suat Persetujuan Ijin Trayek (SPIT)");
        btnSpit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpitActionPerformed(evt);
            }
        });

        btnSpio.setText("Surat Persetujuan Ijin Operasi (SPIO)");
        btnSpio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSpit, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSpio, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSpit, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSpio, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSpitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpitActionPerformed
        SpitDialog spit = new SpitDialog((JFrame) this.getParent(), false);
        spit.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSpitActionPerformed

    private void btnSpioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpioActionPerformed
        SpioDialog spio = new SpioDialog((JFrame) this.getParent(), false);
        spio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSpioActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSpio;
    private javax.swing.JButton btnSpit;
    // End of variables declaration//GEN-END:variables
}
