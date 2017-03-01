/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

@Named("ttBasicView")
@ViewScoped
public class BasicView implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;

    private Document selectedDocument;

    @Inject
    private DocumentService service;

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
    
    public void setService(DocumentService service) {
        this.service = service;
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
    public void onNodeSelect(NodeSelectEvent event) {
       // TreeNode selectedNode = event.getTreeNode();
       
        System.out.println("path : "+((Document)event.getTreeNode().getData()).getPath());
     
    }
}
