package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.SugarForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.SugarCensusVO;
import com.cdutcm.healthy.dataobject.vo.user.SugarVO;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 18:59 星期日
 * @Description :
 */
public interface SugarService {
    //记录测量血糖
    boolean recordSugar(SugarForm sugarForm, String openid);

    //记录测量血糖
    boolean recordSugar(Long datetime, Double sugarValue, String openid) throws Exception;

    //测量血糖
    String measureSugar(Double sugarValue);

    //最近一次血糖记录数据
    SugarVO newlySugar(String openid);

    //血糖变化趋势与近三个月统计
    SugarCensusVO censusSugar(String openid);

    //血糖历史记录
    IPageVO historySugar(String openid, PageForm page);
}
