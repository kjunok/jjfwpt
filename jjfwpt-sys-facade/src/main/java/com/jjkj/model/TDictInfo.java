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
 * @since 2019-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TDictInfo extends Model<TDictInfo> {

    private static final long serialVersionUID=1L;

    @TableId(type= IdType.ID_WORKER_STR)
    private String id;

    /**
     * 父id
     */
    @TableField("pid")
    private String pid;

    /**
     * 分组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 字典代码
     */
    @TableField("dict_code")
    private String dictCode;
    /**
     * 键
     */
    @TableField("text")
    private String text;

    /**
     * 值
     */
    @TableField("val")
    private String val;

    /**
     * 数据字典类型
     */
    @TableField("type")
    private String type;

    /**
     * 创建人
     */
    @TableField("create_user_id")
    private String createUserId;

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
    private String updateUserId;

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
