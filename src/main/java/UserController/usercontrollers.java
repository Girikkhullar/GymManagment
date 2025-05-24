/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserController;

import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author girik
 */
public class usercontrollers {
       @GetMapping("/usersignup")
    public String usersignup()
    {
        return "UserSignup";
    }
    @GetMapping("/userlogin")
    public String userlogin()
    {
        return "UserLogin";
    }
}
