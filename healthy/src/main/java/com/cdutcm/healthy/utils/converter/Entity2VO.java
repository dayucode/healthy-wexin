package com.cdutcm.healthy.utils.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 19:56 星期日
 * @Description :
 */
@Slf4j
public class Entity2VO {
    public static <T, Q> Q convert(T entity, Class<Q> vo) {
        Q voObj;
        try {
            voObj = vo.newInstance();
            BeanUtils.copyProperties(entity, voObj);
        } catch (Exception e) {
            log.error("【对象类型转换失败】entity = {}，vo = {}", entity, vo);
            throw new HealthyException(ResultEnum.OBJECT_CONVERT_ERROR);
        }
        return voObj;
    }

    public static <T, Q> List<Q> convert(List<T> infoList, Class<Q> vo) {
        List<Q> voList = new ArrayList<>();
        for (T info : infoList) {
            voList.add(convert(info, vo));
        }
        return voList;
    }

    public static <T, Q> IPage<Q> convert(IPage<T> infoPage, Class<Q> vo) {
        IPage<Q> iPage = new Page<>();
        BeanUtils.copyProperties(infoPage, iPage);
        iPage.setRecords(convert(infoPage.getRecords(), vo));
        return iPage;
    }
}
