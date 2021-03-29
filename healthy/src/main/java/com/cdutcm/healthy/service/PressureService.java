package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.form.user.PressureForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.PressureCensusVO;
import com.cdutcm.healthy.dataobject.vo.user.PressureVO;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 21:17 星期四
 * @Description :
 */
public interface PressureService {
    //记录测量血压
    boolean recordPressure(PressureForm pressureForm, String openid);

    //记录测量血压
    boolean recordPressure(Long datetime, Double highPressure, Double lowPressure, String openid) throws Exception;

    //测量血压
    String measurePressure(Double highPressure, Double lowPressure);

    //最近一次血压记录数据
    PressureVO newlyPressure(String openid);

    //血压变化趋势与近三个月统计
    PressureCensusVO censusPressure(String openid);

    //血压历史记录
    IPageVO historyPressure(String openid, PageForm page);
}
