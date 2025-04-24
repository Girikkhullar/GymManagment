/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girik.project.gymwebsite.Gymwebsiteproject.gymcontrollers;

//import com.example.Gym.GymManagement.Vmm.DbLoader;
import com.girik.project.gymwebsite.Gymwebsiteproject.Vmm.DbLoader;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GymRestcontrollers {

    @PostMapping("/ownersignup")
    public String ownersignup(@RequestParam String name, @RequestParam String pass, @RequestParam String email, @RequestParam String city, @RequestParam String franchise, @RequestParam MultipartFile photo) {

        try {

            ResultSet rs = DbLoader.executeQuery("SELECT * FROM ownersignup WHERE oemail = '" + email + "'");
            if (rs.next()) {
                return "fail";
            } else {

                String projectPath = System.getProperty("user.dir");
                String internalPath = "/src/main/resources/static";
                String folderName = "/myuploads";
                String orgName = "/" + photo.getOriginalFilename();

                rs.moveToInsertRow();

                rs.updateString("oname", name);
                rs.updateString("oemail", email);
                rs.updateString("opassword", pass);
                rs.updateString("ocity", city);
                rs.updateString("franchise", franchise);
                rs.updateString("ophoto", orgName);
                rs.updateString("ostatus", "Blocked");
                rs.insertRow();

                return "success";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error: " + ex.getMessage();
        }
    }

}