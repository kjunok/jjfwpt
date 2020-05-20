package com.jjkj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_menu_info")
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TMenuInfo implements Serializable {


    private static final long serialVersionUID=1L;

    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type= IdType.ID_WORKER_STR)
    private  String id;


    /**
     * 父id
     */
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField("pid")
    private  String pid;


    /**
     * 名称
     */
    @TableField("name")
    private  String name;


    /**
     * 二级分组名称，只有模块菜单使用
     */
    @TableField("group_name")
    private  String groupName;


    /**
     * 描述
     */
    @TableField("description")
    private  String description;


    /**
     * 图标
     */
    @TableField("icon")
    private  String icon;


    @TableField("code")
    private  String code;


    /**
     * 视图路径
     */
    @TableField("view_path")
    private  String viewPath;


    /**
     * 状态 0启用，1禁用
     */
    @TableField("enabled")
    private  Boolean enabled;


    /**
     * 类型 1，功能模块，2，模块菜单，3页面权限
     */
    @TableField("type")
    private  String type;


    /**
     * 创建人
     */
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField("create_user_id")
    private  String createUserId;


    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  createTime;


    /**
     * 修改人
     */
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField("update_user_id")
    private  String updateUserId;


    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  updateTime;


    /**
     * 排序
     */
    @TableField("order_num")
    private  Integer orderNum;

    @TableField(exist = false)
    private List<TMenuInfo> children = new ArrayList<>();
}
