package com.theflow.service;

import com.theflow.domain.IssueAttachment;

/**
 *
 * @author Stas
 */
public interface IssueAttachmentService {

    public IssueAttachment getIssueAttachmentById(int attachId);

    public void removeIssueAttachment(int attachId);
}
