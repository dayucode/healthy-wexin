package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.entity.Sugar;
import com.cdutcm.healthy.dataobject.mapper.SugarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 17:38 星期四
 * @Description :
 * 血糖表
 */
@Component
public class SugarDao {
    @Autowired
    private SugarMapper sugarMapper;

    public Boolean insertSugar(Sugar sugar) {
        return sugarMapper.insert(sugar) == 1;
    }

    public Boolean deleteSugarById(String sugarId) {
        return sugarMapper.deleteById(sugarId) == 1;
    }

    public Boolean deleteSugar(LambdaQueryWrapper<Sugar> queryWrapper) {
        return sugarMapper.delete(queryWrapper) == 1;
    }

    public Boolean updateSugar(Sugar sugar) {
        return sugarMapper.updateById(sugar) == 1;
    }

    public Sugar selectById(String sugarId) {
        return sugarMapper.selectById(sugarId);
    }

    public Sugar selectOne(LambdaQueryWrapper<Sugar> queryWrapper) {
        return sugarMapper.selectOne(queryWrapper);
    }

    public IPage<Sugar> selectPage(IPage<Sugar> page, LambdaQueryWrapper<Sugar> queryWrapper) {
        return sugarMapper.selectPage(page, queryWrapper);
    }

    public List<Sugar> selectList(LambdaQueryWrapper<Sugar> queryWrapper) {
        return sugarMapper.selectList(queryWrapper);
    }
}
