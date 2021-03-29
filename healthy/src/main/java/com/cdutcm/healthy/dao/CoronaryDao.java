package com.cdutcm.healthy.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.entity.Coronary;
import com.cdutcm.healthy.dataobject.mapper.CoronaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 17:25 星期四
 * @Description :
 * 冠心病评估表
 */
@Component
public class CoronaryDao {
    @Autowired
    private CoronaryMapper coronaryMapper;

    public Boolean insertCoronary(Coronary coronary) {
        return coronaryMapper.insert(coronary) == 1;
    }

    public Boolean deleteCoronaryById(String coronaryId) {
        return coronaryMapper.deleteById(coronaryId) == 1;
    }

    public Boolean deleteCoronary(LambdaQueryWrapper<Coronary> queryWrapper) {
        return coronaryMapper.delete(queryWrapper) == 1;
    }

    public Boolean updateCoronary(Coronary coronary) {
        return coronaryMapper.updateById(coronary) == 1;
    }

    public Coronary selectById(String coronaryId) {
        return coronaryMapper.selectById(coronaryId);
    }

    public Coronary selectOne(LambdaQueryWrapper<Coronary> queryWrapper) {
        return coronaryMapper.selectOne(queryWrapper);
    }

    public IPage<Coronary> selectPage(IPage<Coronary> page, LambdaQueryWrapper<Coronary> queryWrapper) {
        return coronaryMapper.selectPage(page, queryWrapper);
    }
}
