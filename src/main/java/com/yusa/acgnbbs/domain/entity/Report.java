package com.yusa.acgnbbs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.Date;
import java.io.Serializable;

/**
 * (Report)实体类
 *
 * @author makejava
 * @since 2023-04-17 20:34:37
 */
public class Report implements Serializable {
    private static final long serialVersionUID = 593784190507786245L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;

    private Integer postId;
    private Integer isHandled;

    private String reportContent;
    
    private Date createAt;
    @TableLogic
    private Integer delFlag;

    public Report() {
    }

    public Report(Integer id, Integer userId, Integer postId, Integer isHandled, String reportContent, Date createAt, Integer delFlag) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.isHandled = isHandled;
        this.reportContent = reportContent;
        this.createAt = createAt;
        this.delFlag = delFlag;
    }
    public Report(Report report) {
        this.id = report.id;
        userId = report.userId;
        this.postId = report.postId;
        this.isHandled = report.isHandled;
        this.reportContent = report.reportContent;
        this.createAt = report.createAt;
        this.delFlag = report.delFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(Integer isHandled) {
        this.isHandled = isHandled;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                ", isHandled=" + isHandled +
                ", reportContent='" + reportContent + '\'' +
                ", createAt=" + createAt +
                ", delFlag=" + delFlag +
                '}';
    }
}

