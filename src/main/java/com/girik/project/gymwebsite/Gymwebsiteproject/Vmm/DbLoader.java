 package com.girik.project.gymwebsite.Gymwebsiteproject.Vmm;


 import java.sql.*;
public class DbLoader {
    public static ResultSet executeQuery(String stm){
        
         try{
            // load jdbc driver
         Class.forName("com.mysql.cj.jdbc.Driver");
         System.out.println("Driver loaded successfully"); 
         
         //create connection to the mysql databases
         Connection conn = DriverManager.getConnection( "jdbc:mysql://127.0.01:3306/vmm1" , "root" , "girik@2003");
         
         Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
         System.out.println("Statement Created");
         
         ResultSet rs  = s.executeQuery(stm);
         return rs;
         }
        catch(Exception ex ){
            ex.printStackTrace();
        }
         return null;
    }
}