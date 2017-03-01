/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.bean;

import com.cdac.bioinfo.kem.entity.AccessMap;
import com.cdac.bioinfo.kem.entity.ProjectList;
import com.cdac.bioinfo.kem.session.AccessFacade;
import com.cdac.bioinfo.kem.session.ProjectFacade;
import com.cdac.bioinfo.kem.session.UserFacade;
import com.cdac.bioinfo.kem.view.BasicView;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

/**
 *
 * @author Anupam Rai
 */
@Named
@SessionScoped
public class ProjectBean implements Serializable {

    private ProjectList projectList = new ProjectList();
    private AccessMap accessMap = new AccessMap();
    @Inject
    private ProjectFacade projectFacade;
    @Inject
    private BasicView basicView;
    @Inject
    private UploadBean uploadBean;
    @Inject
    private ServletContext context;
    @Inject
    private SessionInfo sessionInfo;
    @Inject
    private AccessFacade accessFacade;
    @Inject
    private UserFacade userFacade;

    private List<String> master;
    private List<String> lab;
    private List<String> guest;

    public List<String> getLab() {
        return lab;
    }

    public void setLab(List<String> lab) {
        this.lab = lab;
    }

    public List<String> getGuest() {
        return guest;
    }

    public void setGuest(List<String> guest) {
        this.guest = guest;
    }

    public List<String> getMaster() {
        return master;
    }

    public void setMaster(List<String> master) {
        this.master = master;
    }

    public ProjectList getProjectList() {
        return projectList;
    }

    public void setProjectList(ProjectList projectList) {
        this.projectList = projectList;
    }

    public String create() {
        String basePath=context.getInitParameter("basePath");
        String tryPath = basePath.concat(File.separator + projectList.getProjectname());
        File directory = new File(tryPath);
        directory.mkdir();
        basicView.init();
        uploadBean.setFileUploadView(false);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        projectList.setProjectcreator(sessionInfo.getUsername());
        projectList.setCreationdate(date);
        projectFacade.create(projectList);
        for(String value:master){
           accessMap.setUseridUserid(userFacade.findByUsername(value));
           accessMap.setProjectidProjectid(projectList);
           accessMap.setProjectrole("MASTER");
           accessFacade.create(accessMap);
           accessMap=new AccessMap();
        }
        for(String value:lab){
           accessMap.setUseridUserid(userFacade.findByUsername(value));
           accessMap.setProjectidProjectid(projectList);
           accessMap.setProjectrole("LAB");
           accessFacade.create(accessMap);
           accessMap=new AccessMap();
        }
        for(String value:guest){
           accessMap.setUseridUserid(userFacade.findByUsername(value));
           accessMap.setProjectidProjectid(projectList);
           accessMap.setProjectrole("GUEST");
           accessFacade.create(accessMap);
           accessMap=new AccessMap();
        }       
        projectList = new ProjectList();
        master=new ArrayList<>();
        lab=new ArrayList<>();
        guest=new ArrayList<>();
        
        return "upload.xhtml?faces-redirect=true";

    }

    public void validate() {
        FacesMessage message = null;
        List<ProjectList> valid = new ArrayList<>();

        valid = projectFacade.findByProjectname(projectList.getProjectname());

        if (valid.isEmpty()) {

        } else {
            projectList.setProjectname(null);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Project Already Exists");
            FacesContext.getCurrentInstance().addMessage(projectnameUIComponent.getClientId(), message);
        }

    }
    private UIComponent projectnameUIComponent;

    public UIComponent getProjectnameUIComponent() {
        return projectnameUIComponent;
    }

    public void setProjectnameUIComponent(UIComponent projectnameUIComponent) {
        this.projectnameUIComponent = projectnameUIComponent;
    }

}
