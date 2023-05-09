package com.yusa.acgnbbs.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;

/**
 * (Admin)实体类
 *
 * @author makejava
 * @since 2023-05-09 20:22:22
 */
@TableName("admin")

public class Admin implements Serializable {
    private static final long serialVersionUID = -98323580962245348L;
    @TableId(type = IdType.AUTO)

    private Integer id;
    
    private String adminName;
    
    private String password;
    
    private Date createAt;
    @TableLogic

    private Integer delFlag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}

