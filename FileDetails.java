/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anupam Rai
 */
@Entity
@Table(name = "FileDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FileDetails.findAll", query = "SELECT f FROM FileDetails f"),
    @NamedQuery(name = "FileDetails.findByFileId", query = "SELECT f FROM FileDetails f WHERE f.fileId = :fileId"),
    @NamedQuery(name = "FileDetails.findByFileName", query = "SELECT f FROM FileDetails f WHERE f.fileName = :fileName"),
    @NamedQuery(name = "FileDetails.findByFilePath", query = "SELECT f FROM FileDetails f WHERE f.filePath = :filePath")})
public class FileDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FILE_ID")
    private String fileId;
    @Size(max = 255)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Size(max = 255)
    @Column(name = "FILE_PATH")
    private String filePath;

    public FileDetails() {
    }

    public FileDetails(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileId != null ? fileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FileDetails)) {
            return false;
        }
        FileDetails other = (FileDetails) object;
        if ((this.fileId == null && other.fileId != null) || (this.fileId != null && !this.fileId.equals(other.fileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cdac.bioinfo.kem.entity.FileDetails[ fileId=" + fileId + " ]";
    }
    
}
