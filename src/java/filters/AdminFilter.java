/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.UserService;

/**
 *
 * @author aymen
 */
public class AdminFilter implements Filter {
    
    public AdminFilter() {
    }    

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
                                
        // this code will execute before HomeServlet and UsersServlet
        HttpServletRequest r = (HttpServletRequest)request;
        HttpSession session = r.getSession();
            
        String username = (String) (session.getAttribute("username"));
        UserService us = new UserService();
            
        try {
            int role = us.get(username).getRole().getRoleid();
            
            if (role == 1) {
                // if they are an admin, then allow them to continue on to the servlet
                chain.doFilter(request, response);
            }
            else {
                // they are not an admin so, send them to home page
                HttpServletResponse resp = (HttpServletResponse)response;
                resp.sendRedirect("home");
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        

    }  
}
