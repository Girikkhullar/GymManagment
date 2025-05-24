/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girik.project.gymwebsite.Gymwebsiteproject.Controllers;

import com.girik.project.gymwebsite.Gymwebsiteproject.Vmm.DbLoader;
import com.girik.project.gymwebsite.Gymwebsiteproject.Vmm.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileOutputStream;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminRestController {
    @Autowired
    public EmailSenderService email;

    @PostMapping("/adminLogin")
    public String adminlogin(@RequestParam String name, @RequestParam String pass , HttpSession session) {
        try {
            System.out.println("Name " + name);
            System.out.println("Pass " + pass);
            ResultSet rs = DbLoader.executeQuery("Select * from  adminlogin where name = '" + name + "' and password = '" + pass + "' ");
            if (rs.next()) {
                session.setAttribute("adminname" , name);
                return "success";
            } else {
                return "failed";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    @PostMapping("/adminmanagecity")
    public String adminmanagecity(@RequestParam String cname, @RequestParam MultipartFile cphoto, @RequestParam String cdesc) {
        String projectPath = System.getProperty("user.dir");
        String internalPath = "/src/main/resources/static";
        String folderName = "/myUploads";
        String orgName = "/" + cphoto.getOriginalFilename();
        try {
            ResultSet rs = DbLoader.executeQuery("select * from adminmanagecities where cname='" + cname + "'");
            if (rs.next()) {
                return "Failed";
            } else {
                FileOutputStream fos = new FileOutputStream(projectPath + internalPath + folderName + orgName);

                byte[] b1 = cphoto.getBytes();

                fos.write(b1);
                fos.close();
                rs.moveToInsertRow();
                rs.updateString("cname", cname);
                rs.updateString("cdesc", cdesc);
                rs.updateString("cphoto", orgName);
                rs.insertRow();
                return "Added Successfully";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }

    }

    @GetMapping("/getcities")
    public String getcities() {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from adminmanagecities");
        return ans;
    }

    @PostMapping("/deletecity")
    public String deleteCity(@RequestParam String id) {
        try {
            int myid = Integer.parseInt(id);

            ResultSet rs = DbLoader.executeQuery("SELECT * FROM adminmanagecities WHERE id = " + myid);
            if (rs.next()) {
                rs.deleteRow();
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    @GetMapping("/getownerdetails")
    public String getownerdetails() {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from ownersignup");
        return ans;
    }

  @GetMapping("/ApprovedButton")
    public String approvedButton(@RequestParam String accept) {
        int id = Integer.parseInt(accept);
        try {
            ResultSet rs = DbLoader.executeQuery("UPDATE ownersignup SET ostatus = 'Approved' WHERE id = " + id);
            if (rs.next()) {
                return "Approved";
            } else {
                return "Owner not found or update failed";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    @GetMapping("/BlockedButton")
    public String blockedButton(@RequestParam String block) {
        int id = Integer.parseInt(block);
        try {
             ResultSet rs = DbLoader.executeQuery("UPDATE ownersignup SET ostatus = 'Blocked' WHERE id = " + id);
            if (rs.next()) {
                return "Blocked";
            } else {
                return "Owner not found or update failed";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }
    @PostMapping("/usersignup")
    public String usersignup(@RequestParam String name, @RequestParam String pass, @RequestParam String email, @RequestParam String phoneno, @RequestParam String address, @RequestParam MultipartFile photo) {

        try {

            ResultSet rs = DbLoader.executeQuery("SELECT * FROM usersignup WHERE useremail = '" + email + "'");
            if (rs.next()) {
                return "fail";
            } else {

                String projectPath = System.getProperty("user.dir");
                String internalPath = "/src/main/resources/static";
                String folderName = "/myUploads";
                String orgName = "/" + photo.getOriginalFilename();

                rs.moveToInsertRow();

                rs.updateString("username", name);
                rs.updateString("useremail", email);
                rs.updateString("userpassword", pass);
                rs.updateString("phoneno", phoneno);
                rs.updateString("address", address);
                rs.updateString("photo", orgName);
                
                rs.insertRow();

                return "success";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error: " + ex.getMessage();
        }
    }
    @PostMapping("/userlogin")
    public String ownerlogin(@RequestParam String email, @RequestParam String pass , HttpSession session) {
        try {
            System.out.println("useremail " + email);
            System.out.println("userpassword " + pass);
            ResultSet rs = DbLoader.executeQuery("Select * from  usersignup where useremail = '" + email + "' and userpassword = '" + pass + "' ");
            if (rs.next()) {
//                session.setAttribute("id", rs.getInt("id"));
session.setAttribute("useremail", email);
                return "success";
            } else {
                return "failed";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        

}
    @PostMapping("/choosecitiesgym")
    public String choosegymcities(@RequestParam String gid) {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from ownermanagegym where ogcity='"+gid+"'");
        return ans;
    }
    @PostMapping("/usergymdetails")
    public String usergymdetails(@RequestParam String uid) {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from ownermanagegym where id='"+uid+"'");
        return ans;
    }
    
    @PostMapping("/showgympack")
    public String showgympack(@RequestParam String sgpid) {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from packagetable where id='"+sgpid+"'");
        return ans;
    }
    
     @PostMapping("/bookingpayment")
    public String bookingpayment(HttpSession session , @RequestParam int pid , @RequestParam String sid, @RequestParam String eid , @RequestParam String ppid) {
        try{
            String useremail = (String) session.getAttribute("useremail");
            int gid = 0;
            int ownerid = 0;
            String ownerEmail = "";
            String pname = "";
            ResultSet rs = DbLoader.executeQuery("select * from packagetable where id = "+pid+" ");
            if(rs.next()){
               gid = rs.getInt("gid");
               pname = rs.getString("pname");
            }
            
            ResultSet rs2 = DbLoader.executeQuery("select * from ownermanagegym where id ="+gid+" ");
            if(rs2.next()){
               ownerid = rs2.getInt("ownerid");
            }
            
            ResultSet rs3 = DbLoader.executeQuery("select * from ownersignup where id ="+ownerid+" ");
            if(rs3.next()){
                ownerEmail = rs3.getString("oemail");
            }
            
            ResultSet rs4 = DbLoader.executeQuery("select * from paymenttable ");
            
                rs4.moveToInsertRow();
                rs4.updateInt("packageid", pid);
                rs4.updateString("startdate", sid);
                rs4.updateString("enddate", eid);
                rs4.updateString("price", ppid);
                rs4.updateString("useremail", useremail);
                rs4.updateString("owneremail", ownerEmail);
                rs4.updateString("packagename", pname);
                rs4.updateString("paymenttype", "online"); 
                rs4.updateString("address", "N/A");
                rs4.insertRow();
           return "success";
            
        }catch(Exception ex){
        ex.printStackTrace();
            return ex.toString();
        }

    }
    @PostMapping("/checkbookinghistory")
    public String checkbookinghistory(HttpSession session) {
        String useremail = (String) session.getAttribute("useremail");
        String ans = new RDBMS_TO_JSON().generateJSON("select * from paymenttable where useremail='"+useremail+"'");
        return ans;
    }
    @PostMapping("/changePassword")
    public String changePassword(HttpSession session , @RequestParam String oldPass, @RequestParam String newPass) {
        String admindetail = (String) session.getAttribute("adminname");
    try {
       
        ResultSet rs = DbLoader.executeQuery("SELECT * FROM adminlogin WHERE name='" + admindetail + "' AND password='" + oldPass + "'");
        if (rs.next()) {
           rs.updateString("password", newPass);  
            rs.updateRow();
             return "success";
    } else {
      return "fail";
        }
  }catch (Exception ex) {
        ex.printStackTrace();
            return ex.toString();     
    }
    }
    
     @PostMapping("/dochangeuserPassword")
    public String dochangeuserPassword(HttpSession session , @RequestParam String oldPass, @RequestParam String newPass) {
        String userdetail = (String) session.getAttribute("useremail");
         System.out.println(userdetail);
    try {
       
        ResultSet rs = DbLoader.executeQuery("SELECT * FROM usersignup WHERE useremail='" + userdetail + "' AND userpassword='" + oldPass + "'");
        if (rs.next()) {
           rs.updateString("userpassword", newPass);  
            rs.updateRow();
             return "success";
    } else {
      return "fail";
        }
  }catch (Exception ex) {
        ex.printStackTrace();
            return ex.toString();     
    }
    }
    
    @GetMapping("/userAddReview")
    public String userAddReview(@RequestParam String gid, @RequestParam int rating, @RequestParam String comment, HttpSession session) {
        String user_email = (String) session.getAttribute("useremail");
        System.out.println(user_email);
//        System.out.println(rating);
        String ans = "";
        try {
            ResultSet rs = DbLoader.executeQuery("Select * from review_table");

            rs.moveToInsertRow();
            rs.updateString("gym_id", gid);
            rs.updateString("user_email", user_email);
            rs.updateString("comment", comment);
            rs.updateInt("rating", rating);
            rs.insertRow();
            ans = "success";

        } catch (Exception e) {
            ans = e.toString();
        }

        return ans;
    }


    @GetMapping("/userShowAverageRatings")
    public String userShowAverageRatings(@RequestParam String gid) {

        // Assuming RDBMS_TO_JSON is available as a service or component
        String ans = new RDBMS_TO_JSON().generateJSON("select avg(rating) as r1 from review_table where gym_id='" + gid + "' ");
        System.out.println(ans);
        return ans;

    }

    @GetMapping("/userShowRatings")
    public String userShowRatings(@RequestParam String gid) {

        // Assuming RDBMS_TO_JSON is available as a service or component
        String ans = new RDBMS_TO_JSON().generateJSON("select * from review_table where gym_id='" + gid + "' ");
        System.out.println(ans);
        return ans;

    }
    
    
    @GetMapping("/ApprovedButton1")
    public String ApprovedButton(@RequestParam String accept)
    {
        ResultSet rs=DbLoader.executeQuery("select * from ownersignup where id='"+accept+"'");
        try {
            if(rs.next())
            {
                rs.moveToCurrentRow();
                rs.updateString("ostatus", "Approved");
                rs.updateRow();
                return "success";
            }
            else{
                return "fail";
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
           return ex.toString();
        }
    }
    
    
     @GetMapping("/BlockedButton1")
    public String BlockButton(@RequestParam String block)
    {
        ResultSet rs=DbLoader.executeQuery("select * from ownersignup where id='"+block+"'");
        try {
            if(rs.next())
            {
                rs.moveToCurrentRow();
                rs.updateString("ostatus", "Blocked");
                rs.updateRow();
                return "success";
            }
            else{
                return "fail";
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
           return ex.toString();
        }
    }
    @GetMapping("/sendemail")
    public String sendemail()
    {
        this.email.sendSimpleEmail("kumararyan4880@gmail.com", "Hello Everyone this is email testing mode", "Email Testing");
        return "success";
    }
    
    @GetMapping("/forgot")
    public String forgot(@RequestParam String email, @RequestParam String otp) {
        try {
            ResultSet rs = DbLoader.executeQuery("select * from usersignup where useremail='" + email + "'");
            if (rs.next()) {
                String body = "Your otp for login page is =" + otp;
                String subject = "Login Authntication";
                this.email.sendSimpleEmail(email, body, subject);
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    @GetMapping("/otpverify")
    public String otpverify(@RequestParam String email) {
        try {
            ResultSet rs = DbLoader.executeQuery("select * from usersignup where useremail='" + email + "'");
            if (rs.next()) {
                rs.moveToCurrentRow();
                String pass = rs.getString("userpassword");
                String subject = "Your Account Password - JC Pawfect";
                String body = "Dear User,\n\n"
                        + "As per your request, here is your account password:\n\n"
                        + "Password: " + pass + "\n\n"
                        + "Please do not share this password with anyone.\n"
                        + "We recommend changing your password after login for better security.\n\n"
                        + "Regards,\n"
                        + "JC Pawfect Team";
                this.email.sendSimpleEmail(email, body, subject);
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }
}

