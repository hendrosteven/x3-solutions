/*
 * Generated by JasperReports - 10/3/11 4:04 AM
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.*;

import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.net.*;

import net.sf.jasperreports.engine.*;
import java.util.*;
import net.sf.jasperreports.engine.data.*;


/**
 *
 */
public class ijinInsidentil_1317589498544_473602 extends JREvaluator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_TIME_ZONE = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_REPORT_FILE_RESOLVER = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_CLASS_LOADER = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_URL_HANDLER_FACTORY = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_REPORT_FORMAT_FACTORY = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;
    private JRFillParameter parameter_nomor = null;
    private JRFillParameter parameter_REPORT_TEMPLATES = null;
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillField field_perusahaan_NAMA = null;
    private JRFillField field_ijininsidentil_BERLAKUSAMPAI = null;
    private JRFillField field_kartupengawasanotobisumum_TANGGALBERLAKUMULAI = null;
    private JRFillField field_ijininsidentil_MAKSUDPERJALANAN = null;
    private JRFillField field_kendaraan_NOMORPOLISI = null;
    private JRFillField field_ijininsidentil_RUTEPERJALANAN = null;
    private JRFillField field_ijininsidentil_JUMLAHPENUMPANG = null;
    private JRFillField field_ijininsidentil_KAPASITASTEMPATDUDUK = null;
    private JRFillField field_ijininsidentil_NOMOR = null;
    private JRFillField field_kartupengawasanotobisumum_NOMOR = null;
    private JRFillField field_ijininsidentil_MASABERLAKUUJIBERKALA = null;
    private JRFillField field_kartupengawasanotobisumum_TANGGALBERLAKUSELESAI = null;
    private JRFillField field_ijininsidentil_NOMORUJIBERKALA = null;
    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;


    /**
     *
     */
    public void customizedInit(
        Map pm,
        Map fm,
        Map vm
        )
    {
        initParams(pm);
        initFields(fm);
        initVars(vm);
    }


    /**
     *
     */
    private void initParams(Map pm)
    {
        parameter_REPORT_LOCALE = (JRFillParameter)pm.get("REPORT_LOCALE");
        parameter_REPORT_TIME_ZONE = (JRFillParameter)pm.get("REPORT_TIME_ZONE");
        parameter_REPORT_VIRTUALIZER = (JRFillParameter)pm.get("REPORT_VIRTUALIZER");
        parameter_REPORT_FILE_RESOLVER = (JRFillParameter)pm.get("REPORT_FILE_RESOLVER");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)pm.get("REPORT_SCRIPTLET");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)pm.get("REPORT_PARAMETERS_MAP");
        parameter_REPORT_CONNECTION = (JRFillParameter)pm.get("REPORT_CONNECTION");
        parameter_REPORT_CLASS_LOADER = (JRFillParameter)pm.get("REPORT_CLASS_LOADER");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)pm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_URL_HANDLER_FACTORY = (JRFillParameter)pm.get("REPORT_URL_HANDLER_FACTORY");
        parameter_IS_IGNORE_PAGINATION = (JRFillParameter)pm.get("IS_IGNORE_PAGINATION");
        parameter_REPORT_FORMAT_FACTORY = (JRFillParameter)pm.get("REPORT_FORMAT_FACTORY");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)pm.get("REPORT_MAX_COUNT");
        parameter_nomor = (JRFillParameter)pm.get("nomor");
        parameter_REPORT_TEMPLATES = (JRFillParameter)pm.get("REPORT_TEMPLATES");
        parameter_REPORT_RESOURCE_BUNDLE = (JRFillParameter)pm.get("REPORT_RESOURCE_BUNDLE");
    }


    /**
     *
     */
    private void initFields(Map fm)
    {
        field_perusahaan_NAMA = (JRFillField)fm.get("perusahaan_NAMA");
        field_ijininsidentil_BERLAKUSAMPAI = (JRFillField)fm.get("ijininsidentil_BERLAKUSAMPAI");
        field_kartupengawasanotobisumum_TANGGALBERLAKUMULAI = (JRFillField)fm.get("kartupengawasanotobisumum_TANGGALBERLAKUMULAI");
        field_ijininsidentil_MAKSUDPERJALANAN = (JRFillField)fm.get("ijininsidentil_MAKSUDPERJALANAN");
        field_kendaraan_NOMORPOLISI = (JRFillField)fm.get("kendaraan_NOMORPOLISI");
        field_ijininsidentil_RUTEPERJALANAN = (JRFillField)fm.get("ijininsidentil_RUTEPERJALANAN");
        field_ijininsidentil_JUMLAHPENUMPANG = (JRFillField)fm.get("ijininsidentil_JUMLAHPENUMPANG");
        field_ijininsidentil_KAPASITASTEMPATDUDUK = (JRFillField)fm.get("ijininsidentil_KAPASITASTEMPATDUDUK");
        field_ijininsidentil_NOMOR = (JRFillField)fm.get("ijininsidentil_NOMOR");
        field_kartupengawasanotobisumum_NOMOR = (JRFillField)fm.get("kartupengawasanotobisumum_NOMOR");
        field_ijininsidentil_MASABERLAKUUJIBERKALA = (JRFillField)fm.get("ijininsidentil_MASABERLAKUUJIBERKALA");
        field_kartupengawasanotobisumum_TANGGALBERLAKUSELESAI = (JRFillField)fm.get("kartupengawasanotobisumum_TANGGALBERLAKUSELESAI");
        field_ijininsidentil_NOMORUJIBERKALA = (JRFillField)fm.get("ijininsidentil_NOMORUJIBERKALA");
    }


    /**
     *
     */
    private void initVars(Map vm)
    {
        variable_PAGE_NUMBER = (JRFillVariable)vm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)vm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)vm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)vm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)vm.get("COLUMN_COUNT");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.String)(((java.lang.String)field_perusahaan_NAMA.getValue()));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kendaraan_NOMORPOLISI.getValue()));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kartupengawasanotobisumum_NOMOR.getValue()));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_kartupengawasanotobisumum_TANGGALBERLAKUMULAI.getValue()));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_kartupengawasanotobisumum_TANGGALBERLAKUSELESAI.getValue()));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_MAKSUDPERJALANAN.getValue()));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_NOMORUJIBERKALA.getValue()));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_MASABERLAKUUJIBERKALA.getValue()));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_ijininsidentil_KAPASITASTEMPATDUDUK.getValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_ijininsidentil_BERLAKUSAMPAI.getValue()));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_ijininsidentil_JUMLAHPENUMPANG.getValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_RUTEPERJALANAN.getValue()));//$JR_EXPR_ID=19$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateOld(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.String)(((java.lang.String)field_perusahaan_NAMA.getOldValue()));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kendaraan_NOMORPOLISI.getOldValue()));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kartupengawasanotobisumum_NOMOR.getOldValue()));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_kartupengawasanotobisumum_TANGGALBERLAKUMULAI.getOldValue()));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_kartupengawasanotobisumum_TANGGALBERLAKUSELESAI.getOldValue()));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_MAKSUDPERJALANAN.getOldValue()));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_NOMORUJIBERKALA.getOldValue()));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_MASABERLAKUUJIBERKALA.getOldValue()));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_ijininsidentil_KAPASITASTEMPATDUDUK.getOldValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_ijininsidentil_BERLAKUSAMPAI.getOldValue()));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_ijininsidentil_JUMLAHPENUMPANG.getOldValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_RUTEPERJALANAN.getOldValue()));//$JR_EXPR_ID=19$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateEstimated(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.String)(((java.lang.String)field_perusahaan_NAMA.getValue()));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kendaraan_NOMORPOLISI.getValue()));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kartupengawasanotobisumum_NOMOR.getValue()));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_kartupengawasanotobisumum_TANGGALBERLAKUMULAI.getValue()));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_kartupengawasanotobisumum_TANGGALBERLAKUSELESAI.getValue()));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_MAKSUDPERJALANAN.getValue()));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_NOMORUJIBERKALA.getValue()));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_MASABERLAKUUJIBERKALA.getValue()));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_ijininsidentil_KAPASITASTEMPATDUDUK.getValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.util.Date)(((java.sql.Date)field_ijininsidentil_BERLAKUSAMPAI.getValue()));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_ijininsidentil_JUMLAHPENUMPANG.getValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ijininsidentil_RUTEPERJALANAN.getValue()));//$JR_EXPR_ID=19$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}
