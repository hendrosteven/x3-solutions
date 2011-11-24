/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.entity.BadanHukum;
import com.x3.monitoring.entity.BentukModal;
import com.x3.monitoring.entity.BidangUsaha;
import com.x3.monitoring.entity.Kecamatan;
import com.x3.monitoring.entity.Perusahaan;
import com.x3.monitoring.entity.StatusPerusahaan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Hendro Steven
 */
public class PerusahaanDAOImpl implements PerusahaanDAO {

    private Connection conn;

    public PerusahaanDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(Perusahaan p) throws Exception {
        String sql = "INSERT INTO `perusahaan` "
                + "(`id`, `nama_perusahaan`, `nama_pimpinan`, `alamat_jalan`, `kelurahan_id`, `kecamatan_id`, `kota`, `telp`, `fax`, `luas_tanah`, `luas_gedung`, `badan_hukum_id`, `bentuk_modal_id`, `status_perusahaan_id`, `bidang_usaha_id`, `produk`, `modal_tetap`, `modal_kerja`, `jml_tki_l`, `jml_tki_p`, `jml_tka_l`, `jml_tka_p`, `tahun_kegiatan`, `jenis_produksi`, `kapasitas_terpasang`, `realisasi_produksi`, `eksport`, `nilai_eksport`, `tahun_omset1`, `nilai_omset1`, `tahun_omset2`, `nilai_omset2`, `tahun_omset3`, `nilai_omset3`, `dok_amdal`, `lokasi_nomor`, `lokasi_tanggal`, `imb_nomor`, `imb_tanggal`, `gudang_nomor`, `gudang_tanggal`, `ho_nomor`, `ho_tanggal`, `siup_nomor`, `siup_tanggal`, `tdp_nomor`, `tdp_tanggal`, `npwp`,`tgl_input`,"
                + "`tdi_nomor`,`tdi_tanggal`) "
                + "VALUES ( ?,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, p.getId());
            ps.setString(2, p.getNamaPerusahaan());
            ps.setString(3, p.getNamaPimpinan());
            ps.setString(4, p.getAlamatJalan());
            ps.setInt(5, p.getKelurahan().getId());
            ps.setInt(6, p.getKecamatan().getId());
            ps.setString(7, p.getKota());
            ps.setString(8, p.getTelp());
            ps.setString(9, p.getFax());
            ps.setDouble(10, p.getLuasTanah());
            ps.setDouble(11, p.getLuasGedung());
            ps.setInt(12, p.getBadanHukum().getId());
            ps.setInt(13, p.getBentukModal().getId());
            ps.setInt(14, p.getStatusPerusahaan().getId());
            ps.setInt(15, p.getBidangUsaha().getId());
            ps.setString(16, p.getProduk());
            ps.setDouble(17, p.getModalTetap());
            ps.setDouble(18, p.getModalKerja());
            ps.setInt(19, p.getJmlTKILaki());
            ps.setInt(20, p.getJmlTKIPerem());
            ps.setInt(21, p.getJmlTKALaki());
            ps.setInt(22, p.getJmlTKAPerem());
            ps.setString(23, p.getTahunKegiatan());
            ps.setString(24, p.getJenisProduksi());
            ps.setString(25, p.getKapasitasTerpasang());
            ps.setString(26, p.getRealisasiProduksi());
            ps.setString(27, p.getExport());
            ps.setDouble(28, p.getNilaiExport());
            ps.setString(29, p.getTahunOmset1());
            ps.setDouble(30, p.getNilaiOmset1());
            ps.setString(31, p.getTahunOmset2());
            ps.setDouble(32, p.getNilaiOmset2());
            ps.setString(33, p.getTahunOmset3());
            ps.setDouble(34, p.getNilaiOmset3());
            ps.setString(35, p.getDokumenAmdal());
            ps.setString(36, p.getLokasiNomor());
            ps.setString(37, new DateTime(p.getLokasiTanggal()).toString("yyyy-MM-dd"));
            ps.setString(38, p.getImbNomor());
            ps.setString(39, new DateTime(p.getImbTanggal()).toString("yyyy-MM-dd"));
            ps.setString(40, p.getGudangNomor());
            ps.setString(41, new DateTime(p.getGudangTanggal()).toString("yyyy-MM-dd"));
            ps.setString(42, p.getHoNomor());
            ps.setString(43, new DateTime(p.getHoTanggal()).toString("yyyy-MM-dd"));
            ps.setString(44, p.getSiupNomor());
            ps.setString(45, new DateTime(p.getSiupTanggal()).toString("yyyy-MM-dd"));
            ps.setString(46, p.getTdpNomor());
            ps.setString(47, new DateTime(p.getTdpTanggal()).toString("yyyy-MM-dd"));
            ps.setString(48, p.getNpwp());
            ps.setString(49, new DateTime(p.getTanggalInput()).toString("yyyy-MM-dd"));
            ps.setString(50, p.getTdiNomor());
            ps.setString(51, new DateTime(p.getTdiTanggal()).toString("yyyy-MM-dd"));
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Insert Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(Perusahaan p) throws Exception {
        String sql = "UPDATE `perusahaan` SET "
                + "`nama_perusahaan`=?, `nama_pimpinan`=?, `alamat_jalan`=?, `kelurahan_id`=?, `kecamatan_id`=?, `kota`=?, `telp`=?, `fax`=?, "
                + "`luas_tanah`=?, `luas_gedung`=?, `badan_hukum_id`=?, `bentuk_modal_id`=?, `status_perusahaan_id`=?, `bidang_usaha_id`=?, `produk`=?, "
                + "`modal_tetap`=?, `modal_kerja`=?, `jml_tki_l`=?, `jml_tki_p`=?, `jml_tka_l`=?, `jml_tka_p`=?, `tahun_kegiatan`=?, `jenis_produksi`=?,"
                + "`kapasitas_terpasang`=?, `realisasi_produksi`=?, `eksport`=?, `nilai_eksport`=?, `tahun_omset1`=?, `nilai_omset1`=?, `tahun_omset2`=?, "
                + "`nilai_omset2`=?, `tahun_omset3`=?, `nilai_omset3`=?, `dok_amdal`=?, `lokasi_nomor`=?, `lokasi_tanggal`=?, `imb_nomor`=?, `imb_tanggal`=?,"
                + "`gudang_nomor`=?, `gudang_tanggal`=?, `ho_nomor`=?, `ho_tanggal`=?, `siup_nomor`=?, `siup_tanggal`=?, `tdp_nomor`=?, `tdp_tanggal`=?, `npwp`=?, `tgl_input`=?,"
                + "`tdi_nomor`=?,`tdi_tanggal`=? "
                + "WHERE `id`=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, p.getNamaPerusahaan());
            ps.setString(2, p.getNamaPimpinan());
            ps.setString(3, p.getAlamatJalan());
            ps.setInt(4, p.getKelurahan().getId());
            ps.setInt(5, p.getKecamatan().getId());
            ps.setString(6, p.getKota());
            ps.setString(7, p.getTelp());
            ps.setString(8, p.getFax());
            ps.setDouble(9, p.getLuasTanah());
            ps.setDouble(10, p.getLuasGedung());
            ps.setInt(11, p.getBadanHukum().getId());
            ps.setInt(12, p.getBentukModal().getId());
            ps.setInt(13, p.getStatusPerusahaan().getId());
            ps.setInt(14, p.getBidangUsaha().getId());
            ps.setString(15, p.getProduk());
            ps.setDouble(16, p.getModalTetap());
            ps.setDouble(17, p.getModalKerja());
            ps.setInt(18, p.getJmlTKILaki());
            ps.setInt(19, p.getJmlTKIPerem());
            ps.setInt(20, p.getJmlTKALaki());
            ps.setInt(21, p.getJmlTKAPerem());
            ps.setString(22, p.getTahunKegiatan());
            ps.setString(23, p.getJenisProduksi());
            ps.setString(24, p.getKapasitasTerpasang());
            ps.setString(25, p.getRealisasiProduksi());
            ps.setString(26, p.getExport());
            ps.setDouble(27, p.getNilaiExport());
            ps.setString(28, p.getTahunOmset1());
            ps.setDouble(29, p.getNilaiOmset1());
            ps.setString(30, p.getTahunOmset2());
            ps.setDouble(31, p.getNilaiOmset2());
            ps.setString(32, p.getTahunOmset3());
            ps.setDouble(33, p.getNilaiOmset3());
            ps.setString(34, p.getDokumenAmdal());
            ps.setString(35, p.getLokasiNomor());
            ps.setString(36, new DateTime(p.getLokasiTanggal()).toString("yyyy-MM-dd"));
            ps.setString(37, p.getImbNomor());
            ps.setString(38, new DateTime(p.getImbTanggal()).toString("yyyy-MM-dd"));
            ps.setString(39, p.getGudangNomor());
            ps.setString(40, new DateTime(p.getGudangTanggal()).toString("yyyy-MM-dd"));
            ps.setString(41, p.getHoNomor());
            ps.setString(42, new DateTime(p.getHoTanggal()).toString("yyyy-MM-dd"));
            ps.setString(43, p.getSiupNomor());
            ps.setString(44, new DateTime(p.getSiupTanggal()).toString("yyyy-MM-dd"));
            ps.setString(45, p.getTdpNomor());
            ps.setString(46, new DateTime(p.getTdpTanggal()).toString("yyyy-MM-dd"));
            ps.setString(47, p.getNpwp());
            ps.setString(48, new DateTime(p.getTanggalInput()).toString("yyyy-MM-dd"));
            ps.setString(49, p.getTdiNomor());
            ps.setString(50, new DateTime(p.getTdiTanggal()).toString("yyyy-MM-dd"));
            ps.setString(51, p.getId());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Update Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(String id) throws Exception {
        String sql = "DELETE FROM perusahaan WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Delete Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public Perusahaan get(String id) throws Exception {
        String sql = "SELECT * FROM perusahaan WHERE id=?";
        PreparedStatement ps = null;
        Perusahaan p = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return p;
    }

    public List<Perusahaan> gets() throws Exception {
        String sql = "SELECT * FROM perusahaan";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(Kecamatan kecamatan) throws Exception {
        String sql = "SELECT * FROM perusahaan WHERE kecamatan_id=?";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, kecamatan.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(BadanHukum bh) throws Exception {
        String sql = "SELECT * FROM perusahaan WHERE badan_hukum_id=?";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, bh.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(BentukModal bh) throws Exception {
        String sql = "SELECT * FROM perusahaan WHERE bentuk_modal_id=?";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, bh.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(BidangUsaha bu) throws Exception {
        String sql = "SELECT * FROM perusahaan WHERE bidang_usaha_id=?";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, bu.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(String key) throws Exception {
        String sql = "SELECT * FROM perusahaan WHERE nama_perusahaan LIKE ?";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(StatusPerusahaan sp) throws Exception {
        String sql = "SELECT * FROM perusahaan WHERE status_perusahaan_id=?";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, sp.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> getsPerusahaan(String sql) throws Exception {
        //String sql = "SELECT * FROM perusahaan WHERE status_perusahaan_id=?";
        PreparedStatement ps = null;
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        try {
            ps = this.conn.prepareStatement(sql);
            //ps.setInt(1, sp.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(double nilaiInvestasi, boolean isLebihBesar) throws Exception {
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        String sql = "";
        if (isLebihBesar) {
            sql = "SELECT * FROM perusahaan WHERE modal_tetap+modal_kerja>=?";
        } else {
            sql = "SELECT * FROM perusahaan WHERE modal_tetap+modal_kerja<=?";
        }
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setDouble(1, nilaiInvestasi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<Perusahaan> gets(double nilaiInvestasiA, double nilaiInvestasiB) throws Exception {
        List<Perusahaan> list = new ArrayList<Perusahaan>();
        String sql = "SELECT * FROM perusahaan WHERE modal_tetap+modal_kerja BETWEEN ? AND ?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setDouble(1, nilaiInvestasiA);
            ps.setDouble(2, nilaiInvestasiB);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Perusahaan p = new Perusahaan();
                p.setId(rs.getString(1));
                p.setNamaPerusahaan(rs.getString(2));
                p.setNamaPimpinan(rs.getString(3));
                p.setAlamatJalan(rs.getString(4));
                p.setKelurahan(new KelurahanDAOImpl(this.conn).get(rs.getInt(5)));
                p.setKecamatan(new KecamatanDAOImpl(this.conn).get(rs.getInt(6)));
                p.setKota(rs.getString(7));
                p.setTelp(rs.getString(8));
                p.setFax(rs.getString(9));
                p.setLuasTanah(rs.getDouble(10));
                p.setLuasGedung(rs.getDouble(11));
                p.setBadanHukum(new BadanHukumDAOImpl(this.conn).get(rs.getInt(12)));
                p.setBentukModal(new BentukModalDAOImpl(this.conn).get(rs.getInt(13)));
                p.setStatusPerusahaan(new StatusPerusahaanDAOImpl(this.conn).get(rs.getInt(14)));
                p.setBidangUsaha(new BidangUsahaDAOImpl(this.conn).get(rs.getInt(15)));
                p.setProduk(rs.getString(16));
                p.setModalTetap(rs.getDouble(17));
                p.setModalKerja(rs.getDouble(18));
                p.setJmlTKILaki(rs.getInt(19));
                p.setJmlTKIPerem(rs.getInt(20));
                p.setJmlTKALaki(rs.getInt(21));
                p.setJmlTKAPerem(rs.getInt(22));
                p.setTahunKegiatan(rs.getString(23));
                p.setJenisProduksi(rs.getString(24));
                p.setKapasitasTerpasang(rs.getString(25));
                p.setRealisasiProduksi(rs.getString(26));
                p.setExport(rs.getString(27));
                p.setNilaiExport(rs.getDouble(28));
                p.setTahunOmset1(rs.getString(29));
                p.setNilaiOmset1(rs.getDouble(30));
                p.setTahunOmset2(rs.getString(31));
                p.setNilaiOmset2(rs.getDouble(32));
                p.setTahunOmset3(rs.getString(33));
                p.setNilaiOmset3(rs.getDouble(34));
                p.setDokumenAmdal(rs.getString(35));
                p.setLokasiNomor(rs.getString(36));
                p.setLokasiTanggal(new DateTime(rs.getString(37)).toDate());
                p.setImbNomor(rs.getString(38));
                p.setImbTanggal(new DateTime(rs.getString(39)).toDate());
                p.setGudangNomor(rs.getString(40));
                p.setGudangTanggal(new DateTime(rs.getString(41)).toDate());
                p.setHoNomor(rs.getString(42));
                p.setHoTanggal(new DateTime(rs.getString(43)).toDate());
                p.setSiupNomor(rs.getString(44));
                p.setSiupTanggal(new DateTime(rs.getString(45)).toDate());
                p.setTdpNomor(rs.getString(46));
                p.setTdpTanggal(new DateTime(rs.getString(47)).toDate());
                p.setNpwp(rs.getString(48));
                p.setTanggalInput(new DateTime(rs.getString(49)).toDate());
                p.setTdiNomor(rs.getString(50));
                p.setTdiTanggal(new DateTime(rs.getString(51)).toDate());
                list.add(p);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Perusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public boolean cekNamaPerusahaan(String nama) throws Exception {
        String sql = "SELECT id from perusahaan where nama_perusahaan=?";
        PreparedStatement ps = null;
        boolean isValid = false;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1,nama);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                isValid=false;
            }else{
                isValid=true;
            }
        }catch(Exception ex){
            throw new Exception("Cek Nama Perusahaan gagal");
        }
        return isValid;
    }
}
