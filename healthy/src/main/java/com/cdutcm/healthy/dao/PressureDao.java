package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.entity.Pressure;
import com.cdutcm.healthy.dataobject.mapper.PressureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 17:38 星期四
 * @Description :
 * 血压表
 */
@Component
public class PressureDao {
    @Autowired
    private PressureMapper pressureMapper;

    public Boolean insertPressure(Pressure pressure) {
        return pressureMapper.insert(pressure) == 1;
    }

    public Boolean deletePressureById(String pressureId) {
        return pressureMapper.deleteById(pressureId) == 1;
    }

    public Boolean deletePressure(LambdaQueryWrapper<Pressure> queryWrapper) {
        return pressureMapper.delete(queryWrapper) == 1;
    }

    public Boolean updatePressure(Pressure pressure) {
        return pressureMapper.updateById(pressure) == 1;
    }

    public Pressure selectById(String pressureId) {
        return pressureMapper.selectById(pressureId);
    }

    public Pressure selectOne(LambdaQueryWrapper<Pressure> queryWrapper) {
        return pressureMapper.selectOne(queryWrapper);
    }

    public IPage<Pressure> selectPage(IPage<Pressure> page, LambdaQueryWrapper<Pressure> queryWrapper) {
        return pressureMapper.selectPage(page, queryWrapper);
    }

    public List<Pressure> selectList(LambdaQueryWrapper<Pressure> queryWrapper) {
        return pressureMapper.selectList(queryWrapper);
    }
}
