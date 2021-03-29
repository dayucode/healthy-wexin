package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.entity.Admin;
import com.cdutcm.healthy.dataobject.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 20:55 星期二
 * @Description :
 */
@Component
public class AdminDao {
    @Autowired
    private AdminMapper adminMapper;

    public Boolean insertAdmin(Admin admin) {
        return adminMapper.insert(admin) == 1;
    }

    public Boolean deleteAdminById(String adminId) {
        return adminMapper.deleteById(adminId) == 1;
    }

    public Boolean deleteAdmin(LambdaQueryWrapper<Admin> queryWrapper) {
        return adminMapper.delete(queryWrapper) == 1;
    }

    public Boolean updateAdmin(Admin admin) {
        return adminMapper.updateById(admin) == 1;
    }

    public Admin selectById(String adminId) {
        return adminMapper.selectById(adminId);
    }

    public Admin selectByOpenid(String openid) {
        return adminMapper.selectOne(new LambdaQueryWrapper<Admin>()
                .eq(Admin::getOpenid, openid));
    }

    public Admin selectOne(LambdaQueryWrapper<Admin> queryWrapper) {
        return adminMapper.selectOne(queryWrapper);
    }

    public IPage<Admin> selectPage(IPage<Admin> page, LambdaQueryWrapper<Admin> queryWrapper) {
        return adminMapper.selectPage(page, queryWrapper);
    }
}
