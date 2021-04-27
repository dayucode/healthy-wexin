package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.form.user.CoronaryForm;
import com.cdutcm.healthy.dataobject.vo.user.CoronaryVO;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/26 0:01 星期二
 * @Description :
 */
public interface CoronaryService {
    CoronaryVO assessCoronary(CoronaryForm coronaryForm, String openid);
}
