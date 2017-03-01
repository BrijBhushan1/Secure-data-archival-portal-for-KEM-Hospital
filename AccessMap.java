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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anupam Rai
 */
@Entity
@Table(name = "AccessMap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessMap.findAll", query = "SELECT a FROM AccessMap a"),
    @NamedQuery(name = "AccessMap.findByAccessId", query = "SELECT a FROM AccessMap a WHERE a.accessId = :accessId"),
    @NamedQuery(name = "AccessMap.findByProjectrole", query = "SELECT a FROM AccessMap a WHERE a.projectrole = :projectrole")})
public class AccessMap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "accessId")
    private Long accessId;
    @Size(max = 255)
    @Column(name = "PROJECTROLE")
    private String projectrole;
    @JoinColumn(name = "USERID_USERID", referencedColumnName = "userId")
    @ManyToOne
    private User useridUserid;
    @JoinColumn(name = "PROJECTID_PROJECTID", referencedColumnName = "PROJECTID")
    @ManyToOne
    private ProjectList projectidProjectid;

    public AccessMap() {
    }

    public AccessMap(Long accessId) {
        this.accessId = accessId;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public String getProjectrole() {
        return projectrole;
    }

    public void setProjectrole(String projectrole) {
        this.projectrole = projectrole;
    }

    public User getUseridUserid() {
        return useridUserid;
    }

    public void setUseridUserid(User useridUserid) {
        this.useridUserid = useridUserid;
    }

    public ProjectList getProjectidProjectid() {
        return projectidProjectid;
    }

    public void setProjectidProjectid(ProjectList projectidProjectid) {
        this.projectidProjectid = projectidProjectid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessId != null ? accessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessMap)) {
            return false;
        }
        AccessMap other = (AccessMap) object;
        if ((this.accessId == null && other.accessId != null) || (this.accessId != null && !this.accessId.equals(other.accessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cdac.bioinfo.kem.entity.AccessMap[ accessId=" + accessId + " ]";
    }
    
}
