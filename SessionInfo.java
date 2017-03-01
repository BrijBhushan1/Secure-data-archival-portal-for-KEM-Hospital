/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;

/**
 *
 * @author Brij Bhushan Pandey
 */
@Named
@SessionScoped
public class SessionInfo implements Serializable{
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    @PostConstruct
    public void init(){
        
        username=(String)SecurityUtils.getSubject().getPrincipal();
        System.out.println("Username :"+username);
    }
    
}
