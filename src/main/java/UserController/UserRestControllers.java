/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserController;

import com.girik.project.gymwebsite.Gymwebsiteproject.Vmm.DbLoader;
import jakarta.servlet.http.HttpSession;
import java.sql.ResultSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author girik
 */
public class UserRestControllers {
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
    public String ownerlogin(@RequestParam String email, @RequestParam String pass) {
        try {
            System.out.println("useremail " + email);
            System.out.println("userpassword " + pass);
            ResultSet rs = DbLoader.executeQuery("Select * from  usersignup where useremail = '" + email + "' and userpassword = '" + pass + "' ");
            if (rs.next()) {
//                session.setAttribute("id", rs.getInt("id"));
                return "success";
            } else {
                return "failed";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }

    
    
}
