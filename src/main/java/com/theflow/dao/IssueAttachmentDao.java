package com.theflow.dao;

import com.theflow.domain.IssueAttachment;

/**
 *
 * @author Stas
 */
public interface IssueAttachmentDao {
    
    public IssueAttachment getIssueAttachmentById(int attachId);
    public void removeIssueAttachment(int attachId);
}
