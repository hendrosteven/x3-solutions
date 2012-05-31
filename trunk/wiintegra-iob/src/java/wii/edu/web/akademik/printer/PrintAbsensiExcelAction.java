/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.model.Absensi;
import wii.edu.core.model.Jadwal;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class PrintAbsensiExcelAction extends HttpServlet {
   
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

            long idJadwal = Long.parseLong(request.getParameter("idJadwal"));
            Jadwal jadwal = new JadwalDAO().getJadwal(idJadwal);
            RegistrasiMatakuliahDAO dao = new RegistrasiMatakuliahDAO();
            List listMatkul = dao.getRegistrasiMatakuliah(jadwal);
            List source = new ArrayList();

            HashMap parameters = new HashMap();
            parameters.put("logo", getServletContext().getRealPath("page/images/logo_unpatti.jpg"));
            //System.out.println("id : " + semester.getId() + ", nama : " + semester.getTahunAjaran());
            parameters.put("matkul", jadwal.getMatakuliah().getKode() + " " + jadwal.getAksara() + " " + jadwal.getMatakuliah().getNama() + " " + jadwal.getAksara());
            String fakultas = "Fakultas " + jadwal.getMatakuliah().getFakultas().getNama();
            if(jadwal.getMatakuliah().getProgdi().getId() != 1)
                fakultas = fakultas + " Program Studi " + jadwal.getMatakuliah().getProgdi().getNama();
            parameters.put("fakultas", fakultas);
            parameters.put("semester", jadwal.getSemester().getNama() + " - " + jadwal.getSemester().getTahunAjaran());
            parameters.put("dosen", jadwal.getDosen().getNama());
            parameters.put("waktu", jadwal.getHari() + ", Pkl. " + jadwal.getJamMulai() + " - " + jadwal.getJamSelesai());
            parameters.put("ruang", jadwal.getRuang().getNama());

            if(listMatkul.size() == 0){
                Absensi data = new Absensi();
                data.setNoInduk("");
                data.setNama("");
                data.setStatus("");
                source.add(data);
            }
            else
            for(int a=0;a<listMatkul.size();a++){
                RegistrasiMatakuliah reg = (RegistrasiMatakuliah)listMatkul.get(a);
                Mahasiswa mhs = reg.getMahasiswa();

                Absensi data = new Absensi();
                data.setNoInduk(mhs.getNomorInduk());
                data.setNama(mhs.getNama());
                data.setStatus(reg.getStatus());
                source.add(data);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(source);
            System.out.println("1 " + getServletContext().getRealPath("report/akademik/daftar_kelas.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(getServletContext().getRealPath("report/akademik/daftar_kelas.jrxml"));
            System.out.println("2");
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
            Logger.getLogger(PrintAbsensiExcelAction.class.getName()).log(Level.SEVERE, null, ex);
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
