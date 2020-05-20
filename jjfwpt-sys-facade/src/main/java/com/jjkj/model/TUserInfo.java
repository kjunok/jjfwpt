
package com.jjkj.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Collection;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author WangJun
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("t_user_info")
@Document(indexName = "t_user_info")
public class TUserInfo implements UserDetails, Serializable{

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @JsonSerialize(using= ToStringSerializer.class)
    @Id
    private  Long id;

    /**
     * 登陆帐户
     */
    @TableField("account")
    @Field(type= FieldType.Keyword)
    private  String account;


    /**
     * 密码
     */
    @TableField("password")
    private  String password;


    /**
     * 用户类型(1普通用户2管理员3系统用户)
     */
    @TableField("user_type")
    private  String userType;


    /**
     * 姓名
     */
    @TableField("user_name")
    @MultiField(
            mainField = @Field(type=FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = @InnerField(suffix = "keyword", type=FieldType.Keyword))
    private  String userName;


    /**
     * 姓名拼音
     */
    @TableField("name_pinyin")
    @MultiField(
            mainField = @Field(type=FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = @InnerField(suffix = "keyword", type=FieldType.Keyword))
    private  String namePinyin;


    /**
     * 性别(0:未知;1:男;2:女)
     */
    @TableField("sex")
    private  Integer sex;


    /**
     * 头像
     */
    @TableField("avatar")
    private  String avatar;


    /**
     * 电话
     */
    @MultiField(
            mainField = @Field(type=FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = @InnerField(suffix = "keyword", type=FieldType.Keyword))
    private  String phone;


    /**
     * 邮箱
     */
    @TableField("email")
    @MultiField(
            mainField = @Field(type=FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart"),
            otherFields = @InnerField(suffix = "keyword", type=FieldType.Keyword))
    private  String email;


    /**
     * 身份证号码
     */
    @TableField("id_card")
    private  String idCard;


    /**
     * 微信
     */
    @TableField("wei_xin")
    private  String weiXin;


    /**
     * 微博
     */
    @TableField("wei_bo")
    private  String weiBo;


    /**
     * QQ
     */
    @TableField("qq")
    private  String qq;


    /**
     * 出生日期
     */
    @TableField("birth_day")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private  Date birthDay;


    /**
     * 详细地址
     */
    @TableField("address")
    private  String address;

    private  Boolean enable;


    @TableField("remark")
    private  String remark;


    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  createTime;


    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private  Date  updateTime;

    @TableField(exist = false)
    private List<Long> roleIds;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUserName() {
        return this.userName;
    }
    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }
}
