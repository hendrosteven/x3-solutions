/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.UserDAO;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class UserJSON extends HttpServlet {
   
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
            String output = "";
            String task = request.getParameter("task");

            //HttpSession sesi = request.getSession();
            User currentUser = (User)sesi.getAttribute("currentUser");

            if(task.equals("LISTING"))
                output = new UserDAO().getAllUserJSONObject().toString();
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                output = "1";
                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    User user = new UserDAO().getUser((Long)array.get(a));
                    if(user.getId() == currentUser.getId() || user.getIsLogin() == 1)
                        output = "0";
                    else
                        output = "" + new ObjectDAO().delete(user);
                }
            }else if(task.equals("LOGOUT")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    User user = new UserDAO().getUser((Long)array.get(a));
                    if(user.getId() == currentUser.getId()){
                        output = "-1";
                    }
                    else
                        new UserDAO().logout(user);
                }
                output = "1";
            }else if(task.equals("UPDATE")){
                Long id = Long.parseLong(request.getParameter("id"));
                String userName = request.getParameter("userName");
                UserDAO dao = new UserDAO();

                if(dao.idIsUsed(userName) && dao.getUser(userName).getId() != id){
                    output = "-1";
                }
                else{
                String password = request.getParameter("password");
                String realName = request.getParameter("realName");
                int level = Integer.parseInt(request.getParameter("level"));

                User user = new UserDAO().getUser(id);
                user.setUsername(userName);
                user.setPassword(password);
                user.setRealName(realName);
                user.setLevel(level);
                output = Integer.toString(new ObjectDAO().update(user));
                }
            }else if(task.equals("CREATE")){
                String userName = request.getParameter("userName");

                if(new UserDAO().idIsUsed(userName)){
                    output = "-1";
                }
                else{
                    String password = request.getParameter("password");
                    String realName = request.getParameter("realName");
                    int level = Integer.parseInt(request.getParameter("level"));

                    User user = new User();
                    user.setUsername(userName);
                    user.setPassword(password);
                    user.setRealName(realName);
                    user.setLevel(level);
                    user.setIsLogin(0);
                    output = Integer.toString(new ObjectDAO().insert(user));
                }
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(UserJSON.class.getName()).log(Level.SEVERE, null, ex);
            out.println(ex.getMessage());
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
