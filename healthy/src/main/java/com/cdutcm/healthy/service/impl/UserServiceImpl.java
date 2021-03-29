package com.cdutcm.healthy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dao.FamilyDao;
import com.cdutcm.healthy.dao.UserDao;
import com.cdutcm.healthy.dataobject.condition.UserCondition;
import com.cdutcm.healthy.dataobject.entity.User;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.UserBindForm;
import com.cdutcm.healthy.dataobject.form.user.UserForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.UserVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.UserService;
import com.cdutcm.healthy.utils.KeyUtil;
import com.cdutcm.healthy.utils.converter.Entity2VO;
import com.cdutcm.healthy.utils.converter.IPage2IPageVO;
import com.cdutcm.healthy.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/25 21:05 星期一
 * @Description :
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FamilyDao familyDao;
    @Autowired
    private WxMpService wxMpService;

    @Override
    public User isNewUser(String openid) {
        User user = userDao.selectByOpenid(openid);
        if (user == null) {
            user = new User();
            user.setUserId(KeyUtil.getUniqueKeyTime());
            user.setOpenid(openid);
            try {
                userDao.insertUser(user);
            } catch (Exception e) {
                log.error("【用户注册错误】，e = {}", e);
                throw new HealthyException(ResultEnum.USER_REGIST_ERROR);
            }
        }
        return user;
    }

    @Override
    public User addUser(String openid) {
        User user = new User();
        user.setUserId(KeyUtil.getUniqueKeyTime());// 用户ID
        user.setOpenid(openid);
        UserVO wxUserInfo = getWxUserInfo(openid);// 获取微信用户信息
        user.setNickname(wxUserInfo.getNickname());// 微信昵称
        user.setHeadImgUrl(wxUserInfo.getHeadImgUrl());// 微信头像
        try {
            userDao.insertUser(user);
        } catch (Exception e) {
            log.error("【用户注册错误】，e = {}", e);
            throw new HealthyException(ResultEnum.USER_REGIST_ERROR);
        }
        return user;
    }

    @Override
    public void perfectUser(UserForm userForm, String openid) {
        User user = userDao.selectByOpenid(openid);
        if (user == null) {
            log.error("【完善基本信息】用户不存在，openid = {}", openid);
            throw new HealthyException(ResultEnum.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(userForm, user);
        try {
            userDao.updateUser(user);
        } catch (Exception e) {
            log.error("【完善基本信息】用户信息更新失败，userForm = {}，openid = {}", userForm, openid);
            throw new HealthyException(ResultEnum.USERINFO_UPDATE_ERROR);
        }
    }

    @Override
    public void bindUser(UserBindForm userBindForm, String openid) {
        User user = userDao.selectByOpenid(openid);
        if (user == null) {
            log.error("【完善基本信息】用户不存在，openid = {}", openid);
            throw new HealthyException(ResultEnum.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(userBindForm, user);
        try {
            // 更新用户的基本信息
            userDao.updateUser(user);
            // 更新绑定家属的信息
            familyDao.updatePhone(userBindForm.getUserPhone(), openid);
        } catch (Exception e) {
            log.error("【信息绑定】用户信息更新失败，userBindForm = {}，openid = {}", userBindForm, openid);
            throw new HealthyException(ResultEnum.USERINFO_UPDATE_ERROR);
        }
    }

    @Override
    public IPageVO getUserList(UserCondition userCondition, PageForm page) {
        //构建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (userCondition != null) {
            if (userCondition.getNickname() != null) {
                queryWrapper.eq(User::getNickname, userCondition.getNickname());
            }
            if (userCondition.getUserPhone() != null) {
                queryWrapper.eq(User::getUserPhone, userCondition.getUserPhone());
            }
            if (userCondition.getMail() != null) {
                queryWrapper.eq(User::getMail, userCondition.getMail());
            }
        }
        IPage iPage = userDao.selectPage(PageForm2Page.convert(page), queryWrapper);//根据查询条件查询User
        return IPage2IPageVO.convert(Entity2VO.convert(iPage, UserVO.class));
    }

    //获取用户信息（微信昵称、微信头像）
    @Override
    public UserVO getWxUserInfo(String openid) {
        UserVO user = new UserVO();
        try {
            //通过微信接口获取用户的微信昵称和微信头像
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openid);
            user.setNickname(wxMpUser.getNickname());
            user.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        } catch (WxErrorException e) {
            log.error("【微信用户信息】获取微信用户信息失败，e = {}，openid = {}", e, openid);
            throw new HealthyException(ResultEnum.WECHAT_MP_ERROR);
        }
        return user;
    }

    //获取用户信息
    @Override
    public UserVO getUserInfo(String openid) {
        User user = userDao.selectByOpenid(openid);
        return Entity2VO.convert(user, UserVO.class);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }
}
