package com.chat.common;

public class UserConstant {
    public static final String EMAIL_HOST = "smtp.exmail.qq.com";
    public static final String EMAIL_USER = "1273543070@qq.com";
    public static final String EMAIL_PASS = "as159753df";

    /**
     * 数据库删除标识。0=未删除，1=已删除
     */
    public static final int NOT_DEL = 0;
    public static final int IS_DEL = 1;

    /**
     * 用户互动类型。1=关注,2=winked
     */
    public static final int INTERACTION_TYPE_FAVOR = 1;
    public static final int INTERACTION_TYPE_WINKED = 2;

    //我关注别人
    public static final int ME_FAVOR_OTHERS = 1;
    //别人关注我的
    public static final int OTHERS_FAVOR_ME = 2;
    //查看过我的
    public static final int VIEWED_ME = 2;

    //关注和取消
    public static final int FAVOR = 1;
    public static final int FAVOR_CANCEL = 2;




    /**
     * 评论相关静态变量 1=查看,2=点赞,3=不喜欢,4=评论,5=收藏,6=转发,7=举报, 8=撤销点赞
     */
    public static final int MOMENT_VIEW = 1;
    public static final int MOMENT_LOVED = 2;
    public static final int MOMENT_NOT_LOVED = 3;
    public static final int MOMENT_COMMENT = 4;
    public static final int MOMENT_KEEP = 5;
    public static final int MOMENT_SHARE = 6;
    public static final int MOMENT_REPORT = 7;
    public static final int MOMENT_LOVED_REVOKE = 8;

    //评论还是回复
    public static final int COMMENT = 1;
    public static final int REPLY = 2;

    /**
     * 账号状态（int,默认为0为新创建，1为审核中，2为审核通过，3为审核不通过，4为用户账号删除，5为账号暂停使用）
     */
    public static final int ACCOUNT_STATUS_CREATE = 0;
    public static final int ACCOUNT_STATUS_VERIFY = 1;
    public static final int ACCOUNT_STATUS_PASS = 2;
    public static final int ACCOUNT_STATUS_NOT_PASS = 3;
    public static final int ACCOUNT_STATUS_DELETE = 4;
    public static final int ACCOUNT_STATUS_PAUSE = 5;

    /**
     * 审核信息
     */
    public static final int VERIFY_PASS = 1;
    public static final int VERIFY_NOT_PASS = 2;

    //审核中
    public static final int VERIFY_PASS_IN = 1;
    //已通过
    public static final int VERIFY_PASS_STATUS = 2;
    //不通过
    public static final int VERIFY_NOT_PASS_STATUS = 3;

    public static final int VERIFY_TYPE_AVATAR = 1;
    public static final int VERIFY_TYPE_ABOUT_ME = 2;
    public static final int VERIFY_TYPE_ABOUT_MATCH = 3;
    public static final int VERIFY_TYPE_ACCOUNT = 4;
    public static final int VERIFY_TYPE_ALBUM = 5;

    public static final int VERIFY_TYPE_MOMENT = 6;
    public static final int VERIFY_TYPE_COMMENT = 7;

    /**
     * 图片常量 图片类型。1=头像,2=公有相册,3=私有相册
     */
    public static final int USER_PIC_AVATAR = 1;
    public static final int USER_PIC_PUBLIC = 2;
    public static final int USER_PIC_PRIVATE = 3;

    /**
     * 用户男女
     */
    public static final int GENDER_BOY = 1;
    public static final int GENDER_GIRL = 2;



}
