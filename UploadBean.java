package com.cdac.bioinfo.kem.bean;

import com.cdac.bioinfo.kem.entity.FileDetails;
import com.cdac.bioinfo.kem.session.FileFacade;
import com.cdac.bioinfo.kem.view.BasicView;
import com.cdac.bioinfo.kem.view.Document;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.util.ByteSource;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class UploadBean implements Serializable {

    @Inject
    BasicView basicView;

    FileDetails fileEntries;
    @Inject
    FileFacade fileFacade;
    private UploadedFile file;
    private String uploadPath;
    private String name;
    private String folderName;
    private TreeNode root;

    private boolean fileUploadView = false;
    private boolean lab = false;
    private boolean uploadable = false;

    @PostConstruct
    public void init() {
        fileEntries = new FileDetails();

    }

    public boolean isFileUploadView() {
        return fileUploadView;
    }

    public void setFileUploadView(boolean fileUploadView) {
        this.fileUploadView = fileUploadView;
    }

    public UploadedFile getFile() {
        return file;
    }

    public String getFolderName() {
        return folderName;
    }

    public boolean isLab() {
        return lab;
    }

    public boolean isUploadable() {
        return uploadable;
    }

    public void setUploadable(boolean uploadable) {
        this.uploadable = uploadable;
    }

    public void setLab(boolean lab) {
        this.lab = lab;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void save(FileUploadEvent e) throws IOException {
        this.file = e.getFile();
        InputStream input = file.getInputstream();
        Files.copy(input, new File(uploadPath, file.getFileName()).toPath());
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        ByteSource salt = rng.nextBytes();
        String saltValue = salt.toHex();
        fileEntries.setFileName(file.getFileName());
        fileEntries.setFilePath(uploadPath.concat(File.separator + file.getFileName()));
        FileDetails flag = null;
        flag = fileFacade.find(saltValue);
        while (flag != null) {
            salt = rng.nextBytes();
            saltValue = salt.toHex();
            flag = fileFacade.find(saltValue);
        }
        fileEntries.setFileId(saltValue);
        fileFacade.create(fileEntries);
        init();

    }

    public void create(ActionEvent event) {
        FacesMessage message = null;
        String tryPath = uploadPath.concat(File.separator + folderName);
        File directory = new File(tryPath);
        boolean created = false;
        if (directory.exists()) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Folder alredy exixts", "Retry with another name");
            created = false;
        } else {
            directory.mkdir();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Folder Created", "Succesfully");
            created = true;
        }
        if (created) {
            basicView.init();
            fileUploadView = false;
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        //context.addCallbackParam("created", created);
    }

    public void onNodeSelect(NodeSelectEvent event) {
        uploadPath = ((Document) event.getTreeNode().getData()).getPath();
        root = event.getTreeNode();
        fileUploadView = true;
        uploadable = !((Document) event.getTreeNode().getData()).getRole().contains("root");
        if (((Document) event.getTreeNode().getData()).getRole().contains("MASTER") || ((Document) event.getTreeNode().getData()).getRole().contains("ADMIN")) {
            lab = true;
        } else if (((Document) event.getTreeNode().getData()).getRole().contains("LAB")) {
            lab = false;
        }

    }

    public void delete() throws IOException {
        FacesMessage message = null;
        File directory = new File(uploadPath);
        System.out.println("going in"+directory.getName());
        deleting(directory);
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Folder Deleted", "Succesfully");
        basicView.init();
        fileUploadView = false;
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void deleting(File file) throws IOException {
        System.out.println("here is"+file.getName());
        if (file.isFile()) {
            FileDetails query = fileFacade.findByPath(file.getPath());
            fileFacade.remove(query);
            file.delete();
        } else {
            if(file.list().length==0){			
    		file.delete();
            }
            else{
                String files[] = file.list();    
        	for (String temp : files) {                     
                    File fileDelete = new File(file, temp);
        	    deleting(fileDelete);
        	}
                if(file.list().length==0){
           	    file.delete();
                 }
            }           
        }
    }
//    public void collapseAll() {
//        setExpandedRecursively(root, false);
//    }
//
//    public void expandAll() {
//        setExpandedRecursively(root, true);
//    }
//
//    private void setExpandedRecursively(final TreeNode node, final boolean expanded) {
//        for (final TreeNode child : node.getChildren()) {
//            setExpandedRecursively(child, expanded);
//        }
//        node.setExpanded(expanded);
//    }
}
