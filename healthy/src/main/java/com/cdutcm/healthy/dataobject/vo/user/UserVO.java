package com.cdutcm.healthy.dataobject.vo.user;

import com.cdutcm.healthy.dataobject.vo.BaseView;
import com.cdutcm.healthy.utils.serializer.Birthday2String;
import com.cdutcm.healthy.utils.serializer.Sex2String;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 21:00 星期一
 * @Description :
 */
@Data
public class UserVO {

    public interface UserInfoView extends BaseView {
    }

    public interface BodyInfoView extends BaseView {
    }

    public interface BindInfoView extends BaseView {
    }

    public interface UserListView extends BaseView {
    }

    @JsonView(UserListView.class)
    private String openid;

    @JsonView(BodyInfoView.class)
    @JsonSerialize(using = Sex2String.class)
    private Integer userSex;
    @JsonView(BodyInfoView.class)
    @JsonSerialize(using = Birthday2String.class)
    private Date birthday;
    @JsonView(BodyInfoView.class)
    private Double height;
    @JsonView(BodyInfoView.class)
    private Double weight;
    @JsonView(BindInfoView.class)
    private String userPhone;
    @JsonView(BindInfoView.class)
    private String mail;
    @JsonView({UserInfoView.class, UserListView.class})
    private String nickname;
    @JsonView(UserInfoView.class)
    private String headImgUrl;
}
