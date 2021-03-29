package com.cdutcm.healthy.dataobject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdutcm.healthy.dataobject.entity.Family;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/4/9 10:11 星期二
 * @Description :
 */
@Component
public interface FamilyMapper extends BaseMapper<Family> {
    /**
     * 根据家属openid修改phone
     * @param phone 家属电话号码
     * @param fOpenid 家属openid
     */
    @Update("UPDATE h_family SET phone = #{phone} WHERE f_openid = #{fOpenid}")
    void updatePhoneByFOpenid(@Param("phone") String phone, @Param("fOpenid") String fOpenid);
}
