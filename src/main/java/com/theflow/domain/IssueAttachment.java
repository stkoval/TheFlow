package com.theflow.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Stas
 */
@Entity
@Table(name = "issue_attachment")
public class IssueAttachment implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "attachment_id")
    private int id;
    
    @Column(name = "filename")
    private String fileName;
    
    @Column(name = "content_type")
    private String contentType;
    
    @JoinColumn(name = "issue_id")
    @ManyToOne(fetch=FetchType.EAGER)
    private Issue issue;
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IssueAttachment other = (IssueAttachment) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
