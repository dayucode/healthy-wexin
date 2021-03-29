package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.form.user.ObesityForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.ObesityCensusVO;
import com.cdutcm.healthy.dataobject.vo.user.ObesityVO;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 18:59 星期日
 * @Description :
 */
public interface ObesityService {
    //记录测量体重
    boolean recordObesity(ObesityForm obesityForm, String openid);

    //记录测量体重
    boolean recordObesity(Long datetime, Double obesityValue, String openid) throws Exception;

    //测量体重
    String measureObesity(Double obesityValue, String openid);

    //最近一次体重记录数据
    ObesityVO newlyObesity(String openid);

    //体重变化趋势与近三个月统计
    ObesityCensusVO censusObesity(String openid);

    //体重历史记录
    IPageVO historyObesity(String openid, PageForm page);
}
