package com.cdutcm.healthy.service;

import com.cdutcm.healthy.dataobject.condition.AdminCondition;
import com.cdutcm.healthy.dataobject.form.admin.AdminAuthForm;
import com.cdutcm.healthy.dataobject.form.admin.AdminForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 20:52 星期二
 * @Description :
 */
public interface AdminService {
    void addAdmin(AdminForm adminForm);

    IPageVO getAdminList(AdminCondition adminCondition, PageForm page);

    void deleteAdmin(String adminId);

    void resetAdmin(String adminId);

    String checkLogin(AdminAuthForm adminAuthForm);
}