/*
 * SessionFilter.java
 *
 * Created on July 22, 2007, 8:56 AM
 */

package wii.edu.web;

import java.io.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import wii.edu.core.dao.HibernateUtil;

/**
 *
 * @author  Hendro
 * @version
 */

public class SessionFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{
        try{
            chain.doFilter(request,response);
            HibernateUtil.commitTransaction();
            //System.out.println("Session Commit...");
        }catch(Exception ex){
            HibernateUtil.rollbackTransaction();
            ex.printStackTrace();
            //request.getRequestDispatcher("admin?action=ruang&error=1").include(request, response);
            //r
            //throw ex;
            //request.
            /*System.out.println(ex.getMessage());
            System.out.println("Masuk filter");
            PrintWriter out = response.getWriter();
            out.println(ex.getMessage());*/
            //throw ex;
        }finally{
            HibernateUtil.closeSession();
            //System.out.println("Session diclose...");
        }
    }
    
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void destroy() {
    }
    
    
}
