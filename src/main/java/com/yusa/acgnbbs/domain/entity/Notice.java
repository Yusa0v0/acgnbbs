package com.yusa.acgnbbs.domain.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Notice)实体类
 *
 * @author makejava
 * @since 2023-03-04 15:41:39
 */
public class Notice implements Serializable {
    private static final long serialVersionUID = 910217272536448827L;
    
    private Integer id;
    
    private String createdAdminName;
    
    private String title;
    
    private String content;
    
    private Date createdAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAdminName() {
        return createdAdminName;
    }

    public void setCreatedAdminName(String createdAdminName) {
        this.createdAdminName = createdAdminName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Notice() {
    }

    public Notice(Integer id, String createdAdminName, String title, String content, Date createdAt) {
        this.id = id;
        this.createdAdminName = createdAdminName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", createdAdminName='" + createdAdminName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

