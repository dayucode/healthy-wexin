package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.condition.UserCondition;
import com.cdutcm.healthy.dataobject.entity.User;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.UserBindForm;
import com.cdutcm.healthy.dataobject.form.user.UserForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.UserVO;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 21:04 星期一
 * @Description :
 */
public interface UserService {
    //是否是新用户，若是则添加记录，
    User isNewUser(String openid);

    // 新增用户
    User addUser(String openid);

    void perfectUser(UserForm userForm, String openid);

    void bindUser(UserBindForm userBindForm, String openid);

    IPageVO getUserList(UserCondition userCondition, PageForm page);

    //获取用户信息（微信昵称、微信头像）
    UserVO getWxUserInfo(String openid);

    //获取用户信息（基本身体信息）
    UserVO getUserInfo(String openid);

    boolean updateUser(User user);
}
