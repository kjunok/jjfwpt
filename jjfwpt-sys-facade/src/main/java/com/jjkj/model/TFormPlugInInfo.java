package com.jjkj.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.elasticsearch.annotations.Document;

/**
* <p>
* 
* </p>
*
* @author WangJun
* @since 2020-04-18
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(indexName = "t_form_plugin_info")
public class TFormPlugInInfo extends Model<TFormPlugInInfo> {

    private static final long serialVersionUID=1L;

    @TableField("id")
    private  String id;


    /**
    * 表单元素名称
    */
    @TableField("type_name")
    private  String typeName;


    /**
    * 表单元素代码
    */
    @TableField("type_code")
    private  String typeCode;


    /**
    * 插件代码
    */
    @TableField("plug_in")
    private  String plugIn;


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
