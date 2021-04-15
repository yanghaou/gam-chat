package com.chat.common;

/**
*
* @author chenlong
* @date 2020/12/8
*/
public enum ResultCodeEnum {
    SERVER_ERROR(1000, "服务器异常"),
    USERNAME_NOT_FOUND(1001,"用户名不存在"),
    USERNAME_EXIST(1002,"用户名已存在"),
    NICKNAME_EXIST(1003,"昵称已存在"),
    APP_PARAM_ERROR(1004,"app参数不能为空"),
    PASSWORD_ERROR(1005,"密码错误"),
    TOKEN_GENERATE_ERROR(1006,"token生成错误"),
//    USER_ID_NOT_FOUND(1007,"用户id不存在"),
    OLD_PASSWORD_ERROR(1008,"旧密码不正确"),
    VERIFY_CODE_EXPIRED(1009,"验证码已过期，请点击重新请求"),
    VERIFY_CODE_ERROR(1010,"验证码不正确"),
    PARAM_ERROR(1011,"参数错误"),
    TOKEN_ERROR(1012,"token错误"),
    TOKEN_EXPIRED(1013,"token expired"),
    PASSWORD_INVALID(1014,"密码必须包含大写字母、小写字母、和特殊字符，且长度在8-16之间"),
    USERNAME_INVALID(1015,"用户名必须是一个合法的邮箱地址"),
    NICKNAME_INVALID(1016,"昵称长度必须在1-30"),

    //未知异常。1开发
    //用户相关异常2开头
    UID_FROM_TOKEN_EX(2001,"empty uid from token"),
    UID_NOT_EQ_EX(2002,"uid invalid"),
    TOKEN_EXPIRED_EX(2003,"token expired"),
    USER_EXIST_EX(2004,"username or email already exist"),
    USER_EMAIL_NOT_EXIST_EX(2005,"email not exist"),
    APP_TYPE_CLIENT_TYPE_BLANK_EX(2006,"appType, clientType can't be empty"),
    PASSWORD_INCORRECT_EX(2007,"incorrect password"),
    TOKEN_GENERATE_EX(2008,"token generate error"),
    OLD_PASSWORD_ERROR_EX(2009,"old password is incorrect"),
    FOUND_PASSWORD_VERIFY_CODE_NOT_EXIST_EX(2010,"verify code not exist or expired, please click send again"),
    FOUND_PASSWORD_VERIFY_CODE_ERROR_EX(2011,"invalid verify code"),
    USER_HAVE_FAVORED_ERROR_EX(2012,"you have favor the user, can't favor again"),
    USER_HAVE_NOT_FAVORED_ERROR_EX(2013,"you have not favor the user, cant' cancel"),
    TOKEN_ERROR_EX(2015,"token error"),
    PASSWORD_LEN_ERROR_EX(2016,"password length must between 8,16"),
    EMAIL_FORMAT_ERROR_EX(2017,"invalid email address"),
    USERNAME_FORMAT_ERROR_EX(2018,"username length must between 5,50"),
    USER_BASE_AND_LOCATION_EX(2019,"user base and location can't be empty"),
    USER_VERIFY_TYPE_EX(2020,"invalid verify type"),
    USER_PIC_EX(2021,"pic not exist"),
    USER_NOT_ADMIN_EX(2022,"you have not verify permission"),
    USER_VERIFY_CONTENT_EMPTY_EX(2023,"verify content can't be empty"),
    USER_ID_NOT_FOUND(1007,"用户id不存在"),

    //关注相关异常3开头
    FAVOR_USER_NOT_EXIST_EX(3001,"favor user not exist"),
    //文章相关异常4开头
    MOMENT_NOT_EXIST_EX(4001,"moment not exist"),
    MOMENT_CONTENT_EMPTY_EX(4002,"content can't be empty"),
    MOMENT_REPLY_CONTENT_EMPTY_EX(4003,"reply content can't be empty"),
    MOMENT_COMMENT_NOT_EXIST_EX(4004,"comment not exist"),
    MOMENT_COMMENT_TYPE_INVALID_EX(4005,"comment type invalid"),
    MOMENT_FAVOR_EX(4006,"you have done this"),
    MOMENT_FAVOR_NOT_EX(4006,"you have not favor"),
    //文件相关异常5开头

    //建议相关的异常6开头
    SUGGEST_NOT_EXIST_EX(6001,"suggest not exist"),

    //审核相关异常
    VERIFY_CONTENT_EMPTY_EX(7001,"verify content can't be empty"),



    SUCCESS(200, "success"),
    FAILED(500, "failed"),
    VALIDATE_FAILED(404, "param error"),
    UNAUTHORIZED(401, "no login or token is expired"),

    FORBIDDEN(403, "no permission");

    // 状态码
    private int code;
    // 结果信息
    private String message;
    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
