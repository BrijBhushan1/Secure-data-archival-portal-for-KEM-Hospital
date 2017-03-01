/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.bean;

import com.cdac.bioinfo.kem.entity.User;
import com.cdac.bioinfo.kem.session.UserFacade;
import com.cdac.bioinfo.kem.shiro.PasswordGenerator;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.shiro.util.ByteSource;

@Named
@SessionScoped
public class UserRegistrationBean implements Serializable {

    private User user = new User();
    private int flag=1;

    @Inject
    private UserFacade userFacade;
    @Inject
    private SessionInfo sessionInfo;
    
    @PostConstruct
    public void init(){
        String temp = null;
        temp=sessionInfo.getUsername();
        String role=userFacade.findByUsername(temp).getRole();
        if(role.equals("ADMIN")){
            flag=1;
        }
        else if(role.equals("NON-ADMIN")){
            flag=2;
        }
    }

    public int getFlag() {
        return flag;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String save() {
        ByteSource randomSalt = PasswordGenerator.getRandomSalt();

        user.setPassword(PasswordGenerator.getEncryptedPassword(user.getPassword(), randomSalt));
        user.setSalt(randomSalt.toHex());
        userFacade.create(user);
        user = new User();
        return "registration.xhtml?faces-redirect=true";
    }

    public void validate() {
        FacesMessage message = null;
        User valid = null;
        valid = userFacade.findByUsername(user.getUsername());
        if (valid == null) {

        } else {
            user.setUsername(null);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid Username");
            FacesContext.getCurrentInstance().addMessage(usernameUIComponent.getClientId(), message);
        }

    }
    private UIComponent usernameUIComponent;

    public void validatePassword(FacesContext context, UIComponent comp,
            Object value) {

        String mno = (String) value;

        if (mno.length() < 8) {
            ((UIInput) comp).setValid(false);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "Password must be of 8 characters");
            context.addMessage(comp.getClientId(context), message);
        } else {
            user.setPassword(mno);
        }

    }

    public UIComponent getUsernameUIComponent() {
        return usernameUIComponent;
    }

    public void setUsernameUIComponent(UIComponent usernameUIComponent) {
        this.usernameUIComponent = usernameUIComponent;
    }
}
