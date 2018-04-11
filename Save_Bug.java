/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author admin
 */
public class Save_Bug extends HttpServlet {

  
       @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        System.out.print("welcome to save bug ---------------------");
        
         JSONObject json = new JSONObject();
         json.put("welcome", "savebug");
           Enumeration paramNames = request.getParameterNames();
        String params[] = new String[2];
        int i = 0;
       int found=0;
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement(); 
            //System.out.println(paramName);
            String[] paramValues = request.getParameterValues(paramName);
            params[i] = paramValues[0];
 
            //System.out.println(params[i]);
            i++;
 
        }
           
         Connection con=null;
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/securedprofile","root","root123");  
            Statement st= con.createStatement(); 
            ResultSet rs;
           
            System.out.println("username - "+params[0]);
            System.out.println("data - "+params[1]);
          
           String username = Z.Decrypt(params[0]);
            
//            
             int row=st.executeUpdate("insert into errors values("+params[0]+",'"+params[1]+"')");
                    if(row>0)
                            json.put("info", "success");
                    else
                            json.put("info", "fail");
                    
//                    ****

//
//                rs=st.executeQuery("select username from errors where username="+Z.getWhereWithDigest(username));
//               if(rs.next())
//               {
//                   found=1;
//                   //fire update query
//                  int row=st.executeUpdate("update errors set data='"+params[1]+"' where username="+Z.getWhereWithDigest(username));
//                   if(row>0)
//                   {
//                       json.put("info", "success");
//                   }
//                   else
//                       json.put("info", "fail");
//               }
//                if(found==0)
//                {
//                  int row1=st.executeUpdate("insert into errors values("+Z.setDataWithDigest(username)+",'"+params[1]+"')");
//                    json.put("username",""+username);
//                    if(row1>0)
//                    {
//                        json.put("info", "success");
//                    }
//                    else
//                            json.put("info", "fail");
//                }
        
//********
                    
                    
                    
                    
                    

              SendMail mail = new SendMail(params[1]);
            
        }
        catch(Exception e){
            e.printStackTrace();
            json.put("info", e.getMessage());
        }
        finally{if (con != null){try {con.close();} catch (SQLException ex) {ex.printStackTrace();}}}
        
         
//            System.out.println("username - "+params[0]);
//            System.out.println("data - "+params[1]);
         json.put("info", "success");         
         
         response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toString());
      
        }
       
  
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
