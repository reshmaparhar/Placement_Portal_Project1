

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;

public class addcomp extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,RemoteException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
         
         String name=request.getParameter("name");
         String dop=request.getParameter("dop");
         String branch=request.getParameter("branch");
         String offer=request.getParameter("offer");
         String cgpa=request.getParameter("cgpa");
         String ctc=request.getParameter("ctc");
         String stipend=request.getParameter("stipend");
        
         Connection conn ;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "portal";
        
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";              
	String password = "";
                
       
        try {
            
        
            Registry reg=LocateRegistry.getRegistry("127.0.0.1",9798);
            Interface i = (Interface)reg.lookup("Server");  
            String total=i.Validate(name);
            //out.println(total);
            if(total.matches("false")){
            out.println("Please Fill in the Company Details first");
            }
            else{
           
                try{
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url+dbName,userName,password);
	    Statement st = conn.createStatement();
            String sql="insert into company values('"+name+"','"+dop+"','"+branch+"','"+offer+"','"+cgpa+"','"+ctc+"','"+stipend+"')";
            st.executeUpdate(sql);
            out.println("Database Updated");
            out.println(name); 
            conn.close();
                }catch(Exception e){
                out.println("error in db "+e);
                System.out.println(e);
                }      
            }
          
          
            
            
            
        } catch(Exception e){            
            out.println("error in client"+e);
             System.out.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
