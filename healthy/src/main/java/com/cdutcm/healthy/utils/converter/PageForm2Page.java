package com.cdutcm.healthy.utils.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdutcm.healthy.dataobject.form.user.PageForm;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 17:12 星期日
 * @Description :
 */
public class PageForm2Page {
    public static IPage convert(PageForm pageForm) {
        return new Page(pageForm.getPage(),pageForm.getSize());
    }
}
