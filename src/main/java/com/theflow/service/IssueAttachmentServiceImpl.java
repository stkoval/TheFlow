package com.theflow.service;

import com.theflow.dao.IssueAttachmentDao;
import com.theflow.domain.IssueAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Stas
 */
@Service
public class IssueAttachmentServiceImpl implements IssueAttachmentService{
    
    @Autowired
    IssueAttachmentDao issusAttachmentDao;

    @Override
    @Transactional
    public IssueAttachment getIssueAttachmentById(int attachId) {
        return issusAttachmentDao.getIssueAttachmentById(attachId);
    }

    @Override
    @Transactional
    public void removeIssueAttachment(int attachId) {
        issusAttachmentDao.removeIssueAttachment(attachId);
    }
    
}
