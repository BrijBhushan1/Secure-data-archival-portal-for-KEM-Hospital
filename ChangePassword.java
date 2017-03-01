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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

@Named
@SessionScoped
public class ChangePassword implements Serializable {

    @Inject
    SessionInfo sessionInfo;
    @Inject
    UserFacade userFacade;
    PasswordGenerator passwordGenerator = new PasswordGenerator();
    private String oldPassword;
    private String newPassword;
    private UIComponent passwordUIComponent;
    private String username;
    private List<User> users;
    private List<String> usernames;

    @PostConstruct
    public void init() {

        users = userFacade.findAll();
        usernames = new ArrayList<>();
        for (User value : users) {
            if(value.getRole().equals("NON-ADMIN")){
               usernames.add(value.getUsername()); 
            }
        }
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void validatePassword(FacesContext context, UIComponent comp,
            Object value) {
        String mno = (String) value;

        if (mno.length() < 8) {
            ((UIInput) comp).setValid(false);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "Password must be of 8 characters");
            context.addMessage(comp.getClientId(context), message);
        } else {
            setNewPassword(mno);
        }
    }

    public String set() {
        FacesMessage message = null;
        String userName = sessionInfo.getUsername();
        User data = null;
        data = userFacade.findByUsername(userName);
        if (data != null) {
            boolean flag = passwordGenerator.isPasswordEqual(getOldPassword(), data.getPassword(), data.getSalt());
            if (flag) {
                //setNewPassword(passwordGenerator.getEncryptedPassword(getNewPassword(), ByteSource.Util.bytes(Hex.decode(data.getSalt()))));

                data.setSalt(passwordGenerator.getRandomSalt().toHex());
                setNewPassword(passwordGenerator.getEncryptedPassword(getNewPassword(), ByteSource.Util.bytes(Hex.decode(data.getSalt()))));
                data.setPassword(getNewPassword());
                userFacade.edit(data);
                return "index.xhtml?faces-redirect=true";
            } else {

                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Incorrect Password");
                FacesContext.getCurrentInstance().addMessage(passwordUIComponent.getClientId(), message);
            }

        }
        return "";
    }

    public String adminSet() {
        FacesMessage message = null;
        User data = null;
        data = userFacade.findByUsername(username);

        if (data != null) {
            boolean flag = passwordGenerator.isPasswordEqual(getNewPassword(), data.getPassword(), data.getSalt());
            if (flag) {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed", "Enter different password");
                FacesContext.getCurrentInstance().addMessage(passwordUIComponent.getClientId(), message);
            } else {
                data.setSalt(passwordGenerator.getRandomSalt().toHex());
                setNewPassword(passwordGenerator.getEncryptedPassword(getNewPassword(), ByteSource.Util.bytes(Hex.decode(data.getSalt()))));
                data.setPassword(getNewPassword());
                userFacade.edit(data);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Changed Successfully");
                FacesContext.getCurrentInstance().addMessage("", message);

                return "adminpasswordchange.xhtml";
            }

        }
        return "";

    }

    public UIComponent getPasswordUIComponent() {
        return passwordUIComponent;
    }

    public void setPasswordUIComponent(UIComponent passwordUIComponent) {
        this.passwordUIComponent = passwordUIComponent;
    }

}