/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girik.project.gymwebsite.Gymwebsiteproject.Controllers;

import com.girik.project.gymwebsite.Gymwebsiteproject.Vmm.DbLoader;
import com.girik.project.gymwebsite.Gymwebsiteproject.Vmm.RDBMS_TO_JSON;
import java.sql.ResultSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileOutputStream;
import java.sql.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminRestController {

    @PostMapping("/adminLogin")
    public String adminlogin(@RequestParam String name, @RequestParam String pass) {
        try {
            System.out.println("Name " + name);
            System.out.println("Pass " + pass);
            ResultSet rs = DbLoader.executeQuery("Select * from  admintable where name = '" + name + "' and password = '" + pass + "' ");
            if (rs.next()) {
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
    
    
   
}

