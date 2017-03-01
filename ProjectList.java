/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdac.bioinfo.kem.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Anupam Rai
 */
@Entity
@Table(name = "ProjectList")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectList.findAll", query = "SELECT p FROM ProjectList p"),
    @NamedQuery(name = "ProjectList.findByProjectid", query = "SELECT p FROM ProjectList p WHERE p.projectid = :projectid"),
    @NamedQuery(name = "ProjectList.findByCreationdate", query = "SELECT p FROM ProjectList p WHERE p.creationdate = :creationdate"),
    @NamedQuery(name = "ProjectList.findByProjectcreator", query = "SELECT p FROM ProjectList p WHERE p.projectcreator = :projectcreator"),
    @NamedQuery(name = "ProjectList.findByProjectname", query = "SELECT p FROM ProjectList p WHERE p.projectname = :projectname")})
public class ProjectList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROJECTID")
    private Long projectid;
    @Column(name = "CREATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date creationdate;
    @Size(max = 255)
    @Column(name = "PROJECTCREATOR")
    private String projectcreator;
    @Size(max = 255)
    @Column(name = "PROJECTNAME")
    private String projectname;
    @Size(max = 500)
    @Column(name = "REMARKS")
    private String remarks;
    @OneToMany(mappedBy = "projectidProjectid")
    private Collection<AccessMap> accessMapCollection;

    public ProjectList() {
    }

    public ProjectList(Long projectid) {
        this.projectid = projectid;
    }

    public Long getProjectid() {
        return projectid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setProjectid(Long projectid) {
        this.projectid = projectid;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getProjectcreator() {
        return projectcreator;
    }

    public void setProjectcreator(String projectcreator) {
        this.projectcreator = projectcreator;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    @XmlTransient
    public Collection<AccessMap> getAccessMapCollection() {
        return accessMapCollection;
    }

    public void setAccessMapCollection(Collection<AccessMap> accessMapCollection) {
        this.accessMapCollection = accessMapCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectList)) {
            return false;
        }
        ProjectList other = (ProjectList) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cdac.bioinfo.kem.entity.ProjectList[ projectid=" + projectid + " ]";
    }
    
}
