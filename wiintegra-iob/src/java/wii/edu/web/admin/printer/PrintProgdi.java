/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.admin.printer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import wii.edu.core.dao.ProgramStudiDAO;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.User;
import wii.edu.web.akademik.printer.PrintAbsensiAction;

/**
 *
 * @author Retha@wii
 */
public class PrintProgdi extends HttpServlet {
   
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
                    if(user.getLevel() != 1){
                        response.sendRedirect("logout");
                        return;
                    }
                }
                else{
                        response.sendRedirect("logout");
                    return;
                }
            }

            List listProgdi = new ArrayList();
            Iterator iter = new ProgramStudiDAO().getAllProgramStudiDanJumlahMahasiswa();
            long total = 0;
            String fak = "";
            long inc = 0;

            for (Iterator it = iter; it.hasNext();) {
                Object[] row = (Object[]) it.next();
                ProgramStudi prog = new ProgramStudi();
                if(fak.equals((String)row[0])){
                    inc = inc + (Long)row[1];
                    prog.setJumlahFak(inc);
                }else{
                    fak = (String)row[0];
                    inc = 0;
                }
                prog.setNamaFakultas((String)row[0]);
                prog.setJumlahFak((Long)row[1]);
                prog.setKode((String)row[2]);
                prog.setNama((String)row[3]);
                prog.setJumlah((Long)row[4]);
                total = total + (Long)row[1];
                listProgdi.add(prog);
                System.out.println((String)row[0] + " " + (Long)row[1] + " " + (String)row[2] + " " + (String)row[3] + " " + (Long)row[4]);
            }

            //List listFakultas = new FakultasDAO().getAllFakultasDanJumlahMahasiswa();
            HashMap parameters = new HashMap();
            parameters.put("logo", getServletContext().getRealPath("page/images/logo_unpatti.jpg"));
            parameters.put("total", total);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listProgdi);
            System.out.println("1 " + getServletContext().getRealPath("report/admin/progdi.jrxml"));
            // Compile JRXML menjadi Jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(getServletContext().getRealPath("report/admin/progdi.jrxml"));
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
