/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik.printer;

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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import wii.edu.core.dao.FakultasDAO;
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.MatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Semester;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class PrintJadwal extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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

            Semester semester = new SemesterDAO().getCurrentSemester();
            HashMap parameters = new HashMap();
            parameters.put("logo", getServletContext().getRealPath("page/images/logo_unpatti.jpg"));
            parameters.put("semester", "Semester " + Integer.toString(semester.getNama()) + " Tahun Ajaran " + semester.getTahunAjaran());
            List listJadwal = new ArrayList();
            Fakultas fakultas = null;
            if(request.getParameter("idFakultas") == null || request.getParameter("idFakultas").equals("")){
                listJadwal = new JadwalDAO().getJadwal(semester);
                parameters.put("fakultas", "");
            }
            else{
                long idFakultas = Long.parseLong(request.getParameter("idFakultas"));
                fakultas = new FakultasDAO().getFakultas(idFakultas);
                listJadwal = new JadwalDAO().getJadwal(semester, fakultas);
                parameters.put("fakultas", fakultas.getNama());
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listJadwal);
            System.out.println("1 " + getServletContext().getRealPath("report/akademik/jadwal.jrxml"));
            // Compile JRXML menjadi Jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(getServletContext().getRealPath("report/akademik/jadwal.jrxml"));
            System.out.println("2");
            // Fill report dengan datasource
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            System.out.println("3");
            // Export report
            byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);
            System.out.println("length : "+pdfByteArray.length);
            response.setContentType("application/pdf");
            response.setContentLength(pdfByteArray.length);
            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(pdfByteArray, 0, pdfByteArray.length);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception ex) {
            Logger.getLogger(PrintAbsensiAction.class.getName()).log(Level.SEVERE, null, ex);
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
