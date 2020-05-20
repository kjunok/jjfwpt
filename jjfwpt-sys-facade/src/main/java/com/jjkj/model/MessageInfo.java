package com.jjkj.model;

import lombok.Data;

@Data
public class MessageInfo {

    private String msgContext;

    private String msgType;

    private TUserInfo toUser;
}
