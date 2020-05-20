package com.jjkj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
* 
* </p>
*
* @author wangjun
* @since 2019-05-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TCityInfo extends Model<TCityInfo> {

    private static final long serialVersionUID=1L;

    @TableId(type= IdType.ID_WORKER_STR)
    private String id;

    /**
    * 城市名称#
    */
    @TableField("city_name")
    private String cityName;

    /**
    * 省份名称#
    */
    @TableField("province_name")
    private String provinceName;


    @Override
    protected Serializable pkVal(){
        return this.id;
    }

}
