
package com.cdac.bioinfo.kem.view;

import java.io.Serializable;
 
public class Document implements Serializable, Comparable<Document> {
 
    private String name;
     
    private String type;
   
    private String path;
    
    private String role;
     
    public Document(String name, String type,String path,String role) {
        this.name = name;
        this.type = type;
        this.path=path;
        this.role=role;
      
    }

   

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

 
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
 
    //Eclipse Generated hashCode and equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());

        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Document other = (Document) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
       
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return name;
    }
 
    public int compareTo(Document document) {
        return this.getName().compareTo(document.getName());
    }
}  