package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cdutcm.healthy.dataobject.entity.Family;
import com.cdutcm.healthy.dataobject.mapper.FamilyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/9 10:12 星期二
 * @Description :
 */
@Component
public class FamilyDao {

    @Autowired
    public FamilyMapper familyMapper;

    public boolean insert(String uOpenid, String phone, String fOpenid) {
        return familyMapper.insert(new Family(uOpenid, phone, fOpenid)) == 1;
    }

    public boolean delete(String uOpenid, String phone) {
        LambdaQueryWrapper<Family> queryWrapper = new LambdaQueryWrapper<Family>()
                .eq(Family::getUOpenid, uOpenid)
                .eq(Family::getPhone, phone);
        return familyMapper.delete(queryWrapper) == 1;
    }

    public List<Family> select(String uOpenid) {
        LambdaQueryWrapper<Family> queryWrapper = new LambdaQueryWrapper<Family>()
                .eq(Family::getUOpenid, uOpenid);
        return familyMapper.selectList(queryWrapper);
    }

    public Family select(String uOpenid, String phone, String fOpenid) {
        LambdaQueryWrapper<Family> queryWrapper = new LambdaQueryWrapper<Family>()
                .eq(Family::getUOpenid, uOpenid)
                .eq(Family::getPhone, phone)
                .eq(Family::getFOpenid, fOpenid);
        return familyMapper.selectOne(queryWrapper);
    }

    public void updatePhone(String phone, String fOpenid) {
        familyMapper.updatePhoneByFOpenid(phone, fOpenid);
    }
}
