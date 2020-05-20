package com.jjkj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>
* 
* </p>
*
* @author wangjun
* @since 2019-05-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_role_info")
public class TRoleInfo extends Model<TRoleInfo> {
    private static final long serialVersionUID=1L;

    @TableId(type= IdType.ID_WORKER_STR)
    @JsonSerialize(using= ToStringSerializer.class)
    private String id;

    /**
    * 名称
    */
    @TableField("name")
    private String name;

    /**
    * 代码
    */
    @TableField("code")
    private String code;

    /**
    * 创建人ID
    */
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField("create_user_id")
    private Long createUserId;
    /**
     * 创建人
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
    * 修改人Id
    */
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField("update_user_id")
    private String updateUserId;

    /**
     * 修改人
     */
    @TableField(exist = false)
    private String updateUserIdText;

    /**
    * 修改时间
    */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  updateTime;

    /**
    * 图标路径
    */
    @TableField("icon")
    private String icon;

    /**
     * 菜单id集合
     */
    @TableField(exist = false)
    private List<Long> menuIds;

    /**
     * 用户id集合
     */
    @TableField(exist = false)
    private List<Long> userIds;

    @Override
    protected Serializable pkVal(){
        return this.id;
    }

}
