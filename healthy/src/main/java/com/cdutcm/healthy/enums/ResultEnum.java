package com.cdutcm.healthy.enums;

import lombok.Getter;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 20:48 星期四
 * @Description :
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数错误"),
    TIME_FORMAT_ERROR(1, "时间格式错误"),
    INTERNAL_SERVER_ERROR(1, "内部服务器错误"),

    OBJECT_CONVERT_ERROR(1, "对象型转换失败"),
    NUMERAL_ACCURACY_ERROR(1, "保留小数错误"),
    ENCRYPT_TYPE_ERROR(1, "加密方式错误"),

    SEX_TYPE_CONVERT_ERROR(1, "性别类型转换失败"),

    PRESSURE_SAVE_ERROR(1, "血压记录失败"),
    PRESSURE_TYPE_CONVERT_ERROR(1, "血压类型转换失败"),
    PRESSURE_PARAM_ERROR(1, "舒张压必须比收缩压低"),

    SUGAR_SAVE_ERROR(1, "血糖记录失败"),
    SUGAR_TYPE_CONVERT_ERROR(1, "血糖类型转换失败"),

    OBESITY_SAVE_ERROR(1, "体重记录失败"),
    OBESITY_TYPE_CONVERT_ERROR(1, "体重类型转换失败"),

    USER_NOT_EXIST(1, "用户不存在"),
    USER_REGIST_ERROR(1, "用户注册错误"),
    USER_INFO_IMPERFECT(1, "用户信息尚未完善"),
    USERINFO_UPDATE_ERROR(1, "用户信息更新失败"),

    CORONARY_SAVE_ERROR(1, "评估数据保存失败"),
    CORONARY_STATUS_CONVERT_ERROR(1, "冠心病风险评估结果转换错误"),
    CORONARY_VALUE_CONVERT_ERROR(1, "冠心病风险值转换错误"),

    TWEETS_TYPE_NOT_EXIST(1, "推文类型不存在"),
    TWEETS_TYPE_CONVERT_ERROR(1, "推文类型转换失败"),
    TWEETS_NOT_EXIST(1, "推文不存在"),
    TWEETS_SAVE_ERROR(1, "推文保存失败"),
    TWEETS_DELETE_ERROR(1, "推文删除失败"),
    TWEETS_UPDATE_ERROR(1, "推文更新失败"),
    TWEETS_ID_IS_EMPTY(1, "推文ID不能为空"),


    TWEETS_FILE_IS_EMPTY(1, "文件为空"),
    TWEETS_FILES_SIZE_TOO_LONG(1, "总文件大于50MB"),
    TWEETS_FILE_SIZE_TOO_LONG(1, "文件大于5MB"),
    TWEETS_FILE_NOT_SUFFIX(1, "文件没有后缀名"),
    TWEETS_FILE_UPLOAD_ERROR(1, "文件上传失败"),


    ADMIN_SAVE_ERROR(1, "管理员保存失败"),
    ADMIN_IS_EXIST(1, "用户名或手机号注册"),
    ADMIN_NOT_EXIST(1, "管理员不存在"),
    ADMIN_DELETE_ERROR(1, "管理员删除失败"),
    ADMIN_RESET_ERROR(1, "管理员密码重置失败"),
    ADMIN_NOT_BIND(1, "用户未关注公众号或未绑定手机"),

    WECHAT_MP_ERROR(1, "微信公众账号错误"),

    WECHAT_MP_GET_ACCESSTOKEN_ERROR(1, "获取AccessToken错误"),

    WECHAT_MP_CREATE_MENU_ERROR(1, "创建自定义菜单错误"),

    USERNAME_PASSWORD_ERROR(1, "用户名或密码错误"),

    AUTH_USER_ERROR(1, "用户未登录"),
    AUTH_ADMIN_ERROR(1, "管理员未登录"),
    OPEN_KEY_ERROR(1, "KEY错误");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
