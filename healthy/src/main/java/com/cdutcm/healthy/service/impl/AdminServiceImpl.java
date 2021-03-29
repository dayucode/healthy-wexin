package com.cdutcm.healthy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dao.AdminDao;
import com.cdutcm.healthy.dao.UserDao;
import com.cdutcm.healthy.dataobject.condition.AdminCondition;
import com.cdutcm.healthy.dataobject.entity.Admin;
import com.cdutcm.healthy.dataobject.form.admin.AdminAuthForm;
import com.cdutcm.healthy.dataobject.form.admin.AdminForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.admin.AdminVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.properties.HealthyProperties;
import com.cdutcm.healthy.service.AdminService;
import com.cdutcm.healthy.service.RedisOperator;
import com.cdutcm.healthy.utils.EncryptUtils;
import com.cdutcm.healthy.utils.KeyUtil;
import com.cdutcm.healthy.utils.converter.Entity2VO;
import com.cdutcm.healthy.utils.converter.IPage2IPageVO;
import com.cdutcm.healthy.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 20:53 星期二
 * @Description :
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisOperator redisOperator;

    private String password;

    public AdminServiceImpl(HealthyProperties healthyProperties) {
        password = healthyProperties.getAdmin().getPassword();
    }

    @Override
    public void addAdmin(AdminForm adminForm) {
        // 在user表中查询新增管理员是否存在。检查该用户是否关注公众号。是否绑定手机号。
        String openid = userDao.selectOpenidByPhone(adminForm.getAdminPhone());
        if (openid == null) {
            log.error("【新增管理员】用户未关注公众号或未绑定手机，adminForm = {}", adminForm);
            throw new HealthyException(ResultEnum.ADMIN_NOT_BIND);
        }

        //检查用户名或手机号是否重复
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, adminForm.getUsername())
                .or()
                .eq(Admin::getAdminPhone, adminForm.getAdminPhone());
        if (adminDao.selectOne(queryWrapper) != null) {
            log.error("【新增管理员】管理员已经存在（用户名或手机号注册），adminForm = {}", adminForm);
            throw new HealthyException(ResultEnum.ADMIN_IS_EXIST);
        }
        //新增管理员信息
        Admin admin = new Admin();
        admin.setOpenid(openid);// 管理员的openid
        BeanUtils.copyProperties(adminForm, admin);
        admin.setAdminId(KeyUtil.getUniqueKeyTime());// 管理员ID
        admin.setPassword(EncryptUtils.encrypt(password, "SHA1"));//默认密码为000000
        try {
            adminDao.insertAdmin(admin);
        } catch (Exception e) {
            log.error("【新增管理员】新增管理员失败，adminForm = {}", adminForm);
            throw new HealthyException(ResultEnum.ADMIN_SAVE_ERROR);
        }
    }

    @Override
    public IPageVO getAdminList(AdminCondition adminCondition, PageForm page) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (adminCondition != null) {
            if (adminCondition.getUsername() != null) {
                queryWrapper.eq(Admin::getUsername, adminCondition.getUsername());
            }
            if (adminCondition.getAdminName() != null) {
                queryWrapper.eq(Admin::getAdminName, adminCondition.getAdminName());
            }
            if (adminCondition.getAdminPhone() != null) {
                queryWrapper.eq(Admin::getAdminPhone, adminCondition.getAdminPhone());
            }
        }
        IPage iPage = adminDao.selectPage(PageForm2Page.convert(page), queryWrapper);
        return IPage2IPageVO.convert(Entity2VO.convert(iPage, AdminVO.class));
    }

    @Override
    public void deleteAdmin(String adminId) {
        if (adminDao.selectById(adminId) == null) {
            log.error("【删除管理员】管理员不存在，adminId = {}", adminId);
            throw new HealthyException(ResultEnum.ADMIN_NOT_EXIST);
        }
        try {
            adminDao.deleteAdminById(adminId);
        } catch (Exception e) {
            log.error("【删除管理员】管理员删除失败，adminId = {}", adminId);
            throw new HealthyException(ResultEnum.ADMIN_DELETE_ERROR);
        }
    }

    @Override
    public void resetAdmin(String adminId) {
        Admin admin = adminDao.selectById(adminId);
        if (admin == null) {
            log.error("【重置管理员密码】管理员不存在，adminId = {}", adminId);
            throw new HealthyException(ResultEnum.ADMIN_NOT_EXIST);
        }
        admin.setPassword(EncryptUtils.encrypt(password, "SHA1"));//重置默认密码为000000
        try {
            adminDao.updateAdmin(admin);
        } catch (Exception e) {
            log.error("【重置管理员密码】管理员密码重置失败，adminId = {}", adminId);
            throw new HealthyException(ResultEnum.ADMIN_RESET_ERROR);
        }
    }

    @Override
    public String checkLogin(AdminAuthForm adminAuthForm) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        //构建 用户名+密码 查询条件
        queryWrapper.eq(Admin::getUsername, adminAuthForm.getUsername())
                .eq(Admin::getPassword, EncryptUtils.encrypt(adminAuthForm.getPassword(), "SHA1"));
        Admin admin = adminDao.selectOne(queryWrapper);
        if (adminAuthForm == null) {
            log.error("【管理员登录】用户名或密码错误，adminAuthForm = {}", adminAuthForm);
            throw new HealthyException(ResultEnum.USERNAME_PASSWORD_ERROR);
        }

        //创建token
        String token = UUID.randomUUID().toString();
        redisOperator.set(String.format(RedisConstant.TOKEN_ADMIN, token), admin.getAdminId(), RedisConstant.EXPIRE);
        return token;
    }
}
