package com.cdutcm.healthy.utils.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.PageVO;
import org.springframework.beans.BeanUtils;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/24 18:09 星期日
 * @Description :
 */
public class IPage2IPageVO {
    public static IPageVO convert(IPage iPage) {
        IPageVO iPageVO = new PageVO();
        BeanUtils.copyProperties(iPage, iPageVO);
        return iPageVO;
    }
}
