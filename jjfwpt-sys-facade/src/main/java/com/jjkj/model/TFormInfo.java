package com.jjkj.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
* <p>
* 
* </p>
*
* @author WangJun
* @since 2020-05-13
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_form_info")
public class TFormInfo extends Model<TFormInfo> {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private  String id;

    @TableField("name")
    private  String name;

    @TableField("code")
    private  String code;

    @TableField("description")
    private  String description;

    @TableField("form_options")
    private  String formOptions;

    @TableField("design_options")
    private  String designOptions;

    @TableField("version")
    private  String version;


    @TableField("enable")
    private  Boolean enable;


    /**
    * 创建用户
    */
    @TableField("create_user_id")
    private  String createUserId;


    /**
    * 创建时间
    */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  createTime;


    /**
    * 更改人id
    */
    @TableField("update_user_id")
    private  String updateUserId;


    /**
    * 更新时间
    */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  updateTime;
}
