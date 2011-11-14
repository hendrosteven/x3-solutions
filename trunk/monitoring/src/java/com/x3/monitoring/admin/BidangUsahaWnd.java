/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.BidangUsahaDAO;
import com.x3.monitoring.dao.mysql.BidangUsahaDAOImpl;
import com.x3.monitoring.dao.mysql.TahunDAOImpl;
import com.x3.monitoring.entity.BidangUsaha;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Hendro Steven
 */
public class BidangUsahaWnd extends ApplicationContext {

    Textbox txtKbli;
    Textbox txtId;
    Textbox txtJenis;
    Textbox txtFilePath;
    Listbox lstBidangUsaha;

    public BidangUsahaWnd() {
    }

    public void onCreate() throws Exception {
        txtKbli = (Textbox) getFellow("txtKbli");
        txtId = (Textbox) getFellow("txtId");
        txtJenis = (Textbox) getFellow("txtJenis");
        lstBidangUsaha = (Listbox) getFellow("lstBidangUsaha");
        txtFilePath = (Textbox) getFellow("txtFilePath");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            final BidangUsahaDAO dao = new BidangUsahaDAOImpl(conn);
            List<BidangUsaha> list = dao.gets(1);
            lstBidangUsaha.getItems().clear();
            int no = 1;
            for (final BidangUsaha bu : list) {
                Listitem item = new Listitem();
                item.setValue(bu);
                item.appendChild(new Listcell(bu.getKbli()));
                item.appendChild(new Listcell(bu.getJenis()));

                Toolbarbutton btnEdit = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah Bidang Usaha");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtId.setValue(bu.getId() + "");
                        txtKbli.setValue(bu.getKbli());
                        txtJenis.setValue(bu.getJenis());
                        txtKbli.setFocus(true);
                    }
                });

                Toolbarbutton btnHapus = new Toolbarbutton();
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Bidang Usaha");
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                BidangUsahaDAO daoDel = new BidangUsahaDAOImpl(connDel);
                                daoDel.delete(bu.getId());
                                load();
                            } catch (Exception ex) {
                                Messagebox.show(ex.getMessage());
                            } finally {
                                connDel.close();
                            }
                        }
                    }
                });

                Listcell cellAksi = new Listcell();
                cellAksi.appendChild(btnEdit);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstBidangUsaha.appendChild(item);

            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void simpan() throws Exception {
        Connection conn = getConn();
        try {
            if (!txtKbli.getValue().isEmpty() && !txtJenis.getValue().isEmpty()) {
                BidangUsahaDAO dao = new BidangUsahaDAOImpl(conn);
                if (txtId.getValue().isEmpty()) {//insert new
                    BidangUsaha bu = new BidangUsaha();
                    bu.setKbli(txtKbli.getValue());
                    bu.setJenis(txtJenis.getValue());
                    bu.setTahun(new TahunDAOImpl(conn).getActif());
                    dao.insert(bu);
                } else {//update
                    BidangUsaha bu = dao.get(Integer.valueOf(txtId.getValue()));
                    bu.setKbli(txtKbli.getValue());
                    bu.setJenis(txtJenis.getValue());
                    dao.update(bu);
                }
                Messagebox.show("Data tersimpan");
                batal();
                load();
            } else {
                Messagebox.show("Input data dengan lengkap");
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void upload() throws Exception {
        if (!txtFilePath.getValue().isEmpty()) {
            List sheetData = new ArrayList();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(txtFilePath.getValue());
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheetAt(0);
                Iterator rows = sheet.rowIterator();
                while (rows.hasNext()) {
                    HSSFRow row = (HSSFRow) rows.next();
                    Iterator cells = row.cellIterator();
                    List data = new ArrayList();
                    while (cells.hasNext()) {
                        HSSFCell cell = (HSSFCell) cells.next();
                        data.add(cell);
                    }
                    sheetData.add(data);
                }
                insert(sheetData);
                load();
                batal();
                Messagebox.show("Upload data selesai..");
            } catch (IOException e) {
                e.printStackTrace();
                Messagebox.show(e.getMessage());
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        } else {
            Messagebox.show("Silahkan input lokasi/path file excel");
            txtFilePath.setFocus(true);
        }

    }

    private void insert(List sheetData) throws Exception {
        HSSFCell cell = null;
        Connection conn = getConn();
        try {
            BidangUsahaDAO dao = new BidangUsahaDAOImpl(conn);
            for (int i = 0; i < sheetData.size(); i++) {
                List list = (List) sheetData.get(i);
                BidangUsaha bu = new BidangUsaha();
                cell = (HSSFCell) list.get(0);
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    bu.setKbli(cell.getRichStringCellValue().getString());
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    double temp1 = cell.getNumericCellValue();
                    int temp2 = (int) temp1;
                    bu.setKbli(String.valueOf(temp2));
                }
                cell = (HSSFCell) list.get(1);
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    bu.setJenis(cell.getRichStringCellValue().getString());
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    double temp1 = cell.getNumericCellValue();
                    int temp2 = (int) temp1;
                    bu.setJenis(String.valueOf(temp2));
                }
                bu.setTahun(new TahunDAOImpl(conn).getActif());
                if (dao.get(bu.getKbli()) == null) {
                    dao.insert(bu);
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            conn.close();
        }
    }

    public void batal() {
        txtId.setValue("");
        txtKbli.setValue("");
        txtJenis.setValue("");
        txtFilePath.setValue("");
    }
}
