/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.Semester;
import wii.edu.core.model.TranskripAkhirMahasiswa;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class PrintKhsExcelAction extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {

            HttpSession sesi = request.getSession();
            if(sesi.getAttribute("role") == null){
                request.getRequestDispatcher("page/index.jsp").forward(request, response);
                return;
            }
            else{
                String role = (String)sesi.getAttribute("role");
                if(role.equals("user")){
                    User user = (User)sesi.getAttribute("currentUser");
                    if(user.getLevel() != 2){
                        response.sendRedirect("logout");
                        return;
                    }
                }
                else{
                        response.sendRedirect("logout");
                    return;
                }
            }

            long idMhs = Long.parseLong(request.getParameter("idMhs"));
            long idSemester = Long.parseLong(request.getParameter("idSemester"));
            Mahasiswa mhs = new MahasiswaDAO().getMahasiswa(idMhs);
            Semester semester = new SemesterDAO().getSemester(idSemester);
            RegistrasiMatakuliahDAO dao = new RegistrasiMatakuliahDAO();
            List listMatkulIpk = dao.getAllMatakuliahIPK(mhs, semester);
            List listMatkulIps = dao.getMatakuliahMahasiswa(mhs, semester);;
            List source = new ArrayList();

            HashMap parameters = new HashMap();
            parameters.put("logo", getServletContext().getRealPath("page/images/logo-iob.png"));
            System.out.println("id : " + semester.getId() + ", nama : " + semester.getTahunAjaran());
            parameters.put("thnAjaran", semester.getTahunAjaran());
            parameters.put("noInduk", mhs.getNomorInduk());
            parameters.put("nama", mhs.getNama());
            parameters.put("semester", Integer.toString(semester.getNama()));
            parameters.put("jurusan", mhs.getFakultas().getNama() + " / " + mhs.getProgdi().getNama());
            parameters.put("jenjang", "Diploma");
            parameters.put("angkatan", mhs.getAngkatan().getNama());

            //------------------------ ngitung ipk -----------------------------
            int total_bobot = 0;
            int total_sks = 0;
            double ipk = 0;

            for(int a=0;a<listMatkulIpk.size();a++){
                RegistrasiMatakuliah reg = (RegistrasiMatakuliah)listMatkulIpk.get(a);
                if(reg.getNilai() != null){
                    TranskripAkhirMahasiswa data = new TranskripAkhirMahasiswa();
                    int bobot = 0;
                    if(reg.getNilai().equals("A")){
                        bobot = 4;
                    }
                    else if(reg.getNilai().equals("B")){
                        bobot = 3;
                    }
                    else if(reg.getNilai().equals("C")){
                        bobot = 2;
                    }
                    else if(reg.getNilai().equals("D")){
                        bobot = 1;
                    }
                    else if(reg.getNilai().equals("E")){
                        bobot = 0;
                    }
                    total_bobot = total_bobot + bobot * reg.getMatakuliah().getSksAkademik();
                    total_sks = total_sks + reg.getMatakuliah().getSksAkademik();
                    System.out.println("total bobot : " + total_bobot);
                }
            }
            if(total_sks > 0)
                ipk = total_bobot / total_sks;
            else
                ipk = 0;

            System.out.println("ipk : " + ipk);

            parameters.put("ipk", Double.toString(ipk));
            parameters.put("totalSks", Integer.toString(total_sks));
            //------------------------------------------------------------------

            //------------------------ ngitung ips -----------------------------
            int total_bobot_ips = 0;
            int total_sks_ips = 0;
            double ips = 0;

            if(listMatkulIps.size() == 0){
                TranskripAkhirMahasiswa data = new TranskripAkhirMahasiswa();
                data.setKode("");
                data.setNama("");
                data.setSemester("");
                data.setSks("");
                data.setNilai("");
                data.setAngka("");
                data.setBobot("");
                source.add(data);
            }
            else
            for(int a=0;a<listMatkulIps.size();a++){
                RegistrasiMatakuliah reg = (RegistrasiMatakuliah)listMatkulIps.get(a);
                if(reg.getNilai() != null){
                    TranskripAkhirMahasiswa data = new TranskripAkhirMahasiswa();
                    data.setKode(reg.getMatakuliah().getKode());
                    data.setNama(reg.getMatakuliah().getNama());
                    data.setSemester(reg.getJadwal().getSemester().getNama() + " " + reg.getJadwal().getSemester().getTahunAjaran());
                    data.setSks(Integer.toString(reg.getMatakuliah().getSksAkademik()));
                    data.setNilai(reg.getNilai());
                    int bobot = 0;
                    if(reg.getNilai().equals("A")){
                        bobot = 4;
                    }
                    else if(reg.getNilai().equals("B")){
                        bobot = 3;
                    }
                    else if(reg.getNilai().equals("C")){
                        bobot = 2;
                    }
                    else if(reg.getNilai().equals("D")){
                        bobot = 1;
                    }
                    else if(reg.getNilai().equals("E")){
                        bobot = 0;
                    }
                    data.setAngka(Integer.toString(bobot));
                    total_bobot_ips = total_bobot_ips + bobot * reg.getMatakuliah().getSksAkademik();
                    total_sks_ips = total_sks_ips + reg.getMatakuliah().getSksAkademik();
                    System.out.println("total bobot : " + total_bobot_ips);
                    data.setBobot(Double.toString(bobot * reg.getMatakuliah().getSksAkademik()));
                    source.add(data);
                }
            }
            if(total_sks_ips > 0)
                ips = total_bobot_ips / total_sks_ips;
            else
                ips = 0;

            System.out.println("ips : " + ips);

            parameters.put("ips", Double.toString(ips));
            parameters.put("totalSksSemester", Integer.toString(total_sks_ips));
            //------------------------------------------------------------------

            SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");
            parameters.put("today", format.format(new Date()));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(source);
            System.out.println("1 " + getServletContext().getRealPath("report/akademik/khs.jrxml"));
            // Compile JRXML menjadi Jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(getServletContext().getRealPath("report/akademik/khs.jrxml"));
            System.out.println("2");
            // Fill report dengan datasource
            JasperPrint print = JasperManager.fillReport(jasperReport, parameters, dataSource);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ServletOutputStream outputfile = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");

            System.out.println("3");
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();
            System.out.println("length : " + output.size());
            response.setContentLength(output.size());
            outputfile.write(output.toByteArray(), 0, output.size());
            outputfile.flush();
            outputfile.close();
            System.out.println("4");
        } catch (Exception ex) {
            Logger.getLogger(PrintKhsExcelAction.class.getName()).log(Level.SEVERE, null, ex);
            // display stack trace in the browser
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            response.setContentType("text/plain");
            response.getOutputStream().print(stringWriter.toString());
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
