/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.TreeNode;

@Named("ttDownloadView")
@ViewScoped
public class DownloadView implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;

    private Document selectedDocument;

    @Inject
    private DownloadService service;

    @PostConstruct
    public void init() {
        root = service.createDocuments();
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    public void setService(DownloadService service) {
        this.service = service;
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}
