/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.girik.project.gymwebsite.Gymwebsiteproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/alogin")
    public String alogin()
    {
        return "AdminLogin";
    }
    
      @GetMapping("/ahome")
    public String ahome()
    {
        return "AdminHome";
    }
      @GetMapping("/AdminManageCities")
    public String aManageCities()
    {
        return "AdminManageCities";
    }
    @GetMapping("/osignup")
    public String osignup()
    {
        return "OwnerSignUp";
    }
    @GetMapping("/amanageowner")
    public String amanageowner()
    {
        return "AdminManageOwner";
    }
    @GetMapping("/ologin")
    public String ologin()
    {
        return "Ownerlogin";
    }
    @GetMapping("/ohome")
    public String ohome()
    {
        return "Ownerhome";
    }
    @GetMapping("/oManageGym")
    public String oManageGym()
    {
        return "OwnerManageGym";
    }
    
    
    
    
}
