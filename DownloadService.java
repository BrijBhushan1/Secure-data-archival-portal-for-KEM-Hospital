/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.view;

import com.cdac.bioinfo.kem.bean.SessionInfo;
import com.cdac.bioinfo.kem.entity.AccessMap;
import com.cdac.bioinfo.kem.entity.ProjectList;
import com.cdac.bioinfo.kem.entity.User;
import com.cdac.bioinfo.kem.session.AccessFacade;
import com.cdac.bioinfo.kem.session.ProjectFacade;
import com.cdac.bioinfo.kem.session.UserFacade;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import java.io.File;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

@Named

@javax.enterprise.context.ApplicationScoped
public class DownloadService {

    @Inject
    private ServletContext context;
    @Inject
    private SessionInfo sessionInfo;
    @Inject
    private AccessFacade accessFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private ProjectFacade projectFacade;

    public TreeNode createDocuments() {
        String basePath = context.getInitParameter("basePath");
        TreeNode root = new DefaultTreeNode(new Document("Files", "Folder", basePath, "MASTER"), null);
        root.setExpanded(true);
        String user = sessionInfo.getUsername();
        User tempUser = userFacade.findByUsername(user);
        if (tempUser.getRole().equals("ADMIN")) {
            List<ProjectList> tempProject = projectFacade.findAll();
            for (ProjectList var : tempProject) {
                TreeNode baseNode = new DefaultTreeNode(new Document(var.getProjectname().concat("   ( " + tempUser.getRole() + " )"), "Folder", basePath.concat(File.separator + var.getProjectname()), tempUser.getRole().concat(".root")), root);
                File file = new File(basePath.concat(File.separator + var.getProjectname()));
                recursiveAdd(file, baseNode, basePath.concat(File.separator + var.getProjectname()), tempUser.getRole());
            }
        } else {
            List<AccessMap> tempAccess = accessFacade.findAll();
            for (AccessMap var : tempAccess) {
                if (Objects.equals(var.getUseridUserid(), tempUser)) {
                    TreeNode baseNode = new DefaultTreeNode(new Document(var.getProjectidProjectid().getProjectname().concat("   ( " + var.getProjectrole() + " )"), "Folder", basePath.concat(File.separator + var.getProjectidProjectid().getProjectname()), var.getProjectrole().concat(".root")), root);
                    File file = new File(basePath.concat(File.separator + var.getProjectidProjectid().getProjectname()));
                    recursiveAdd(file, baseNode, basePath.concat(File.separator + var.getProjectidProjectid().getProjectname()), var.getProjectrole());
                }
            }
        }
        return root;
    }

    private void recursiveAdd(File file, TreeNode root, String path, String role) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File localFile : listFiles) {
            if (localFile.isFile()) {
                String extension = "";

                int i = localFile.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = localFile.getName().substring(i + 1);
                }
                TreeNode fileNode = new DefaultTreeNode(new Document(localFile.getName(), extension, path.concat(File.separator + localFile.getName()), role), root);
            }
            if (localFile.isDirectory()) {
                TreeNode fileNode = new DefaultTreeNode(new Document(localFile.getName(), "Folder", path.concat(File.separator + localFile.getName()), role), root);
                fileNode.setExpanded(true);
                recursiveAdd(localFile, fileNode, path.concat(File.separator + localFile.getName()), role);
            }
        }

    }
}
