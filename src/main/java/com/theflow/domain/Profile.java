package com.theflow.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Stas
 */

@Entity
@Table(name = "profiles")
public class Profile implements Serializable{
    
    @Id
    private int userId;
            
    @OneToOne
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    
    @Lob
    @Column(name = "image", unique = false, length = 3100)
    private byte[] image;
    
    @Column(name = "fullname")
    private String fullName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
