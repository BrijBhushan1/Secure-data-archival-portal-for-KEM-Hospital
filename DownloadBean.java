package com.cdac.bioinfo.kem.bean;

import com.cdac.bioinfo.kem.entity.FileDetails;
import com.cdac.bioinfo.kem.session.FileFacade;
import com.cdac.bioinfo.kem.view.Document;
import com.cdac.bioinfo.kem.view.DownloadView;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;

@Named
@ViewScoped
public class DownloadBean implements Serializable {

    @Inject
    DownloadView downloadView;
    @Inject
    FileFacade fileFacade;
    FileDetails details;
    private String uploadPath;
    private String id;

    private boolean fileDownloadView = false;
    private boolean fileDelete=false;

    @PostConstruct
    public void init() {
        details = new FileDetails();
    }

    public String getId() {
        return id;
    }

    public boolean isFileDownloadView() {
        return fileDownloadView;
    }

    public void setFileDownloadView(boolean fileDownloadView) {
        this.fileDownloadView = fileDownloadView;
    }

    public boolean isFileDelete() {
        return fileDelete;
    }

    public void setFileDelete(boolean fileDelete) {
        this.fileDelete = fileDelete;
    }
    

    public void onNodeSelect(NodeSelectEvent event) {
        uploadPath = ((Document) event.getTreeNode().getData()).getPath();
        fileDownloadView = !((Document) event.getTreeNode().getData()).getType().equals("Folder");
        fileDelete=((Document) event.getTreeNode().getData()).getRole().contains("MASTER");
        fileDelete = ((Document) event.getTreeNode().getData()).getRole().contains("MASTER") || ((Document) event.getTreeNode().getData()).getRole().contains("ADMIN");
    }

    public void delete() throws IOException {
        FacesMessage message = null;
        File directory = new File(uploadPath);
        FileDetails query = fileFacade.findByPath(uploadPath);
        fileFacade.remove(query);
        directory.delete();
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "File Deleted", "Succesfully");
        downloadView.init();
        fileDownloadView = false;
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void download() throws IOException {
        File directory = new File(uploadPath);
        FileDetails query = fileFacade.findByPath(uploadPath);
        id = "download?id=";
        id = id.concat(query.getFileId());
        System.out.println(id);
        RequestContext.getCurrentInstance().addCallbackParam("url", id);
    }

 
   
}
