package com.cdutcm.healthy.dataobject.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @Author :  daYu
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/24 18:04 星期日
 * @Description :
 */
@JsonView(BaseView.class)
public interface IPageVO<T> extends IPage<T> {
}
