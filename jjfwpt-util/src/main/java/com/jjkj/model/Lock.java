package com.jjkj.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sys_lock")
@SuppressWarnings("serial")
public class Lock implements Serializable {
    @TableId(value = "key_", type = IdType.INPUT)
    private String key;
    @TableField("name_")
    private String name;
    @TableField("expire_second")
    private Integer expireSecond;
    @TableField("create_time")
    private Date createTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(Integer expireSecond) {
        this.expireSecond = expireSecond;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
