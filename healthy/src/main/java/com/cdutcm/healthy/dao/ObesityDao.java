package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.entity.Obesity;
import com.cdutcm.healthy.dataobject.mapper.ObesityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 17:38 星期四
 * @Description :
 * 体重表
 */
@Component
public class ObesityDao {
    @Autowired
    private ObesityMapper obesityMapper;

    public Boolean insertObesity(Obesity obesity) {
        return obesityMapper.insert(obesity) == 1;
    }

    public Boolean deleteObesityById(String obesityId) {
        return obesityMapper.deleteById(obesityId) == 1;
    }

    public Boolean deleteObesity(LambdaQueryWrapper<Obesity> queryWrapper) {
        return obesityMapper.delete(queryWrapper) == 1;
    }

    public Boolean updateObesity(Obesity obesity) {
        return obesityMapper.updateById(obesity) == 1;
    }

    public Obesity selectById(String obesityId) {
        return obesityMapper.selectById(obesityId);
    }

    public Obesity selectOne(LambdaQueryWrapper<Obesity> queryWrapper) {
        return obesityMapper.selectOne(queryWrapper);
    }

    public IPage<Obesity> selectPage(IPage<Obesity> page, LambdaQueryWrapper<Obesity> queryWrapper) {
        return obesityMapper.selectPage(page, queryWrapper);
    }

    public List<Obesity> selectList(LambdaQueryWrapper<Obesity> queryWrapper) {
        return obesityMapper.selectList(queryWrapper);
    }
}
