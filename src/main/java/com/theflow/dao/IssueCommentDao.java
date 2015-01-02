/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.theflow.dao;

import com.theflow.domain.IssueComment;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stas
 */
public class IssueCommentDao {
    public static Map<Integer, IssueComment> commentMap = new HashMap<>();
    
    public void saveUpdateIssueComment(IssueComment comment) {
        commentMap.put(comment.getCommentId(), comment);
    }
        
    public void removeIssueComment(int id) {
        commentMap.remove(id);
    }
    
    public IssueComment getIssueComment(int id) {
        return commentMap.get(id);
    }
}
