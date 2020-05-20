package com.jjkj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wangjun
 * @since 2019-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TBserviceInfo extends Model<TBserviceInfo> {

    private static final long serialVersionUID=1L;

    @TableId(type= IdType.ID_WORKER_STR)
    private  String id;


    /**
     * 服务名
     */
    @TableField("service_name")
    private  String serviceName;


    /**
     * 所属区域
     */
    @TableField("area")
    private  String area;


    /**
     * 表单字段
     */
    @TableField("form_items")
    private  String formItems;


    /**
     * 状态
     */
    @TableField("state")
    private  String state;


    /**
     * 服务描述
     */
    @TableField("service_desc")
    private  String serviceDesc;


    /**
     * 创建人
     */
    @TableField("create_user_id")
    private  String createUserId;

    /**
     * 创建人Text
     */
    @TableField(exist = false)
    private String createUserIdText;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  createTime;


    /**
     * 修改人
     */
    @TableField("update_user_id")
    private  String updateUserId;

    /**
     * 修改人Text
     */
    @TableField(exist = false)
    private String updateUserIdText;

    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  updateTime;



    @Override
    protected Serializable pkVal(){
        return this.id;
    }

}
