/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import wii.edu.core.dao.FakultasDAO;
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.MatakuliahDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.ProgramStudiDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Matakuliah;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class MatakuliahJSON extends HttpServlet {
   
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

            if(task.equals("FAKULTAS"))
                output = new FakultasDAO().getAllFakultasJSONObject().toString();
            else if(task.equals("PROGDI")){
                String strFak = request.getParameter("fakultas");
                System.out.println("strFak : " + strFak);
                if(strFak == null || strFak.equals("")){
                    output = new ProgramStudiDAO().getAllProgramStudiJSONObject().toString();
                }
                else{
                    long idFakultas = Long.parseLong(strFak);

                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new ProgramStudiDAO().getAllProgramStudiPlusJSONObject(fak).toString();
                }
            }
            else if(task.equals("LISTING_BY_FAKULTAS_LIMIT")){
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                   (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    output = new MatakuliahDAO().getAllMatakuliahMinusJSONObject(start, limit).toString();
                }
                else if(!(request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                         (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new MatakuliahDAO().getAllMatakuliahMinusJSONObject(fak, start, limit).toString();
                }
                else if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                        !(request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    Fakultas fak = prog.getFakultas();
                    output = new MatakuliahDAO().getAllMatakuliahMinusJSONObject(fak, prog, start, limit).toString();
                }
                else{
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    output = new MatakuliahDAO().getAllMatakuliahMinusJSONObject(fak, prog, start, limit).toString();
                }
            }
            else if(task.equals("PRASYARAT"))
                output = new MatakuliahDAO().getAllMatakuliahJSONObject().toString();
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                int ind = 0;
                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    Matakuliah mat = new MatakuliahDAO().getMatakuliah((Long)array.get(a));
                    List listJadwal = new JadwalDAO().getJadwal(mat);
                    List listReg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah(mat);
                    if((listJadwal == null || listJadwal.size()<=0) && (listReg == null || listReg.size()<=0)){
                        new ObjectDAO().delete(mat);
                        ind++;
                    }
                }
                if(ind == 0){
                    output = "0";
                }
                else if(ind == array.size()){
                    output = "1";
                }
                else if(ind < array.size()){
                    output = "-1";
                }
            }else if(task.equals("UPDATE")){
                Long id = Long.parseLong(request.getParameter("id"));
                String kode = request.getParameter("kode");
                MatakuliahDAO dao = new MatakuliahDAO();

                if(dao.kodeIsUsed(kode) && dao.getMatakuliah(kode).getId() != id){
                    output = "-1";
                }
                else{
                String nama = request.getParameter("nama");
                int sksAkademik = Integer.parseInt(request.getParameter("sksAkademik"));
                //int sksBayar = Integer.parseInt(request.getParameter("sksBayar"));
                int sksBayar = 0;
                long prasyarat;
                Matakuliah pra = null;
                if(request.getParameter("prasyarat") != null && !request.getParameter("prasyarat").equals("") && !request.getParameter("prasyarat").equals("1")){
                    prasyarat = Long.parseLong(request.getParameter("prasyarat"));
                    pra = new MatakuliahDAO().getMatakuliah(prasyarat);
                    System.out.println("prasyarat : " + prasyarat);
                }

                long fakultas = Long.parseLong(request.getParameter("fakultas"));
                long progdi = Long.parseLong(request.getParameter("progdi"));

                Matakuliah mat = new Matakuliah();
                mat.setId(id);
                mat.setKode(kode);
                mat.setNama(nama);
                mat.setSksAkademik(sksAkademik);
                mat.setSksBayar(sksBayar);

                Fakultas fak = new FakultasDAO().getFakultas(fakultas);
                ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(progdi);

                mat.setPrasyarat(pra);
                mat.setFakultas(fak);
                mat.setProgdi(prog);

                output = Integer.toString(new ObjectDAO().update(mat));
                }
            }else if(task.equals("CREATE")){
                String kode = request.getParameter("kode");

                if(new MatakuliahDAO().kodeIsUsed(kode)){
                    output = "-1";
                }
                else{
                    String nama = request.getParameter("nama");
                    int sksAkademik = Integer.parseInt(request.getParameter("sksAkademik"));
                    //int sksBayar = Integer.parseInt(request.getParameter("sksBayar"));
                    int sksBayar = 0;
                    long prasyarat;
                    Matakuliah pra = null;
                    if(request.getParameter("prasyarat") != null && !request.getParameter("prasyarat").equals("") && !request.getParameter("prasyarat").equals("1")){
                        prasyarat = Long.parseLong(request.getParameter("prasyarat"));
                        pra = new MatakuliahDAO().getMatakuliah(prasyarat);
                        System.out.println("prasyarat : " + prasyarat);
                    }

                    long fakultas = Long.parseLong(request.getParameter("fakultas"));
                    long progdi = Long.parseLong(request.getParameter("progdi"));

                    Matakuliah mat = new Matakuliah();
                    mat.setKode(kode);
                    mat.setNama(nama);
                    mat.setSksAkademik(sksAkademik);
                    mat.setSksBayar(sksBayar);

                    Fakultas fak = new FakultasDAO().getFakultas(fakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(progdi);

                    mat.setPrasyarat(pra);
                    mat.setFakultas(fak);
                    mat.setProgdi(prog);

                    output = Integer.toString(new ObjectDAO().insert(mat));
                }
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(MatakuliahJSON.class.getName()).log(Level.SEVERE, null, ex);
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
