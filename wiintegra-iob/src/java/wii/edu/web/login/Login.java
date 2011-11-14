/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import wii.edu.core.dao.DosenDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.UserDAO;
import wii.edu.core.model.Dosen;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class Login extends HttpServlet {
   
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
        PrintWriter out = response.getWriter();
        try {
            String userid = request.getParameter("userid");
            String password = request.getParameter("password");
            int role = Integer.parseInt(request.getParameter("sebagai"));

            if(userid.equals("") || password.equals("") || request.getParameter("sebagai").equals("")){
                request.setAttribute("error", "Isi User ID, Password, dan Level User terlebih dulu!");
                request.getRequestDispatcher("/page/index.jsp").include(request, response);
                return;
            }
            else if(role == 1){ //mahasiswa
                MahasiswaDAO mhsDAO = new MahasiswaDAO();
                Mahasiswa mhs = mhsDAO.login(userid,password);
                if(mhs==null){
                    request.setAttribute("error", "User ID atau password Anda salah!");
                    request.getRequestDispatcher("/page/index.jsp").include(request, response);
                    return;
                }
                else if(mhs.getDataKelulusan() != null){
                    request.setAttribute("error", "Anda sudah dinyatakan lulus dari Institute of Business Dili, Timor Leste!");
                    request.getRequestDispatcher("/page/index.jsp").include(request, response);
                    return;
                }
                else if(mhs.getIsLogin() == 1){
                    request.setAttribute("error", "Anda masih login di komputer lain.<br>Logout terlebih dahulu atau minta Administrator untuk melogout account Anda.");
                    request.getRequestDispatcher("/page/index.jsp").include(request, response);
                    return;
                }
                else{
                    mhs.setIsLogin(1);
                    new ObjectDAO().update(mhs);
                    HttpSession session = request.getSession();
                    session.setAttribute("currentMhs",mhs);
                    session.setAttribute("role", "mahasiswa");
                    request.getRequestDispatcher("/page/main.jsp").include(request, response);
                    return;
                }
            }else if(role==2){  //dosen
                DosenDAO dsnDAO = new DosenDAO();
                Dosen dosen = dsnDAO.login(userid,password);
                if(dosen==null){
                    request.setAttribute("error", "User ID atau password Anda salah!!");
                    request.getRequestDispatcher("/page/index.jsp").include(request, response);
                    return;
                }
                else if(dosen.getIsLogin() == 1){
                    request.setAttribute("error", "Anda masih login di komputer lain.<br>Logout terlebih dahulu atau minta Administrator untuk melogout account Anda.");
                    request.getRequestDispatcher("/page/index.jsp").include(request, response);
                    return;
                }
                else{
                    dosen.setIsLogin(1);
                    new ObjectDAO().update(dosen);
                    HttpSession session = request.getSession();
                    session.setAttribute("currentDosen",dosen);
                    session.setAttribute("role", "dosen");
                    request.getRequestDispatcher("/page/main.jsp").include(request, response);
                    return;
                }
            }else if(role == 3){//keuangan, akademik, administrator
                UserDAO userDAO = new UserDAO();
                User user = userDAO.login(userid,password);

                if(user==null){
                    request.setAttribute("error", "User ID atau password Anda salah!!");
                    request.getRequestDispatcher("/page/index.jsp").include(request, response);
                    return;
                }
                else if(user.getIsLogin() == 1){
                    request.setAttribute("error", "Anda masih login di komputer lain.<br>Logout terlebih dahulu atau minta Administrator untuk melogout account Anda.");
                    request.getRequestDispatcher("/page/index.jsp").include(request, response);
                    return;
                }
                else{
                    user.setIsLogin(1);
                    new ObjectDAO().update(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("currentUser",user);
                    session.setAttribute("role", "user");
                    request.getRequestDispatcher("/page/main.jsp").include(request, response);
                    return;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } finally { 
            out.close();
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
