/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girik.project.gymwebsite.Gymwebsiteproject.gymcontrollers;
//import Gymcontollers.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Gymcontrollers {

    @GetMapping("/osignup")
    public String osignup() {
        return "OwnerSignUp";
    }

    @GetMapping("/ownerlogin")
    public String ownerlogin() {
        return "OwnerLogin";
    }
    @GetMapping("/OwnerHome")
      public String OwnerHome(HttpSession session)
      {String oemail = (String) session.getAttribute("owneremail");
        if (oemail == null) {
            return "redirect:/ownerlogin";
        } else {
          return "OwnerHome";
        }
      }
       @GetMapping("/oManageGym")
    public String oManageGym(HttpSession session)
    {String oemail = (String) session.getAttribute("owneremail");
        if (oemail == null) {
            return "redirect:/ownerlogin";
        } else {
        return "OwnerManageGym";
        }
    }
     @GetMapping("/packageTable")
    public String packageTable(HttpSession session)
       
    {String oemail = (String) session.getAttribute("owneremail");
        if (oemail == null) {
            return "redirect:/ownerlogin";
        } else {
        return "PackageTable";
        }
    }
    
     @GetMapping("/editpackageTable")
    public String editpackageTable(HttpSession session)
       
    {String oemail = (String) session.getAttribute("owneremail");
        if (oemail == null) {
            return "redirect:/ownerlogin";
        } else {
        return "EditPackageTable";
        }
    }
    
      @GetMapping("/editownerDetail")
    public String editownerDetail(HttpSession session)
       
    {String oemail = (String) session.getAttribute("owneremail");
        if (oemail == null) {
            return "redirect:/ownerlogin";
        } else {
        return "/EditOwnerDetail";
        }
    }
    
     @GetMapping("/changeownerpassword")
      public String changeownerpassword(HttpSession session)
     {String oemail = (String) session.getAttribute("owneremail");
        if (oemail == null) {
            return "redirect:/ownerlogin";
        } else {
          return "ChangeOwnerPassword";
        }
      }
      @GetMapping("/logout2")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "redirect:/";
}
    @GetMapping("/addedgyms")
      public String addedgyms()
      {
          return "OwnerAddedGym";
      }
}
    
    
      

