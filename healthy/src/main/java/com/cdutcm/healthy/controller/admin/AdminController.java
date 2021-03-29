package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.dataobject.condition.AdminCondition;
import com.cdutcm.healthy.dataobject.form.admin.AdminAuthForm;
import com.cdutcm.healthy.dataobject.form.admin.AdminForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.AdminService;
import com.cdutcm.healthy.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 20:41 星期二
 * @Description :
 */
@Api(tags = "【管理员】")
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation("【新增管理员】添加新的管理员")
    @PostMapping("/add")
    public ResultVO add(@Valid @RequestBody AdminForm adminForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【新增管理员】参数错误，adminForm = {}", adminForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        adminService.addAdmin(adminForm);
        return ResultVOUtil.success();
    }

    @ApiOperation("【管理员列表】获取管理员信息列表")
    @GetMapping("/list")
    public ResultVO list(AdminCondition adminCondition, PageForm page) {
        return ResultVOUtil.success(adminService.getAdminList(adminCondition, page));
    }

    @ApiOperation("【删除管理员】根据管理员ID删除管理员")
    @DeleteMapping("/delete")
    public ResultVO delete(@ApiParam("管理员ID") @RequestParam("adminId") String adminId) {
        adminService.deleteAdmin(adminId);
        return ResultVOUtil.success();
    }

    @ApiOperation("【重置密码】重置管理员密码")
    @PutMapping("/reset")
    public ResultVO reset(@ApiParam("管理员ID") @RequestParam("adminId") String adminId) {
        adminService.resetAdmin(adminId);
        return ResultVOUtil.success();
    }

    @ApiOperation("【管理员登录】管理员利用账号密码登录系统")
    @PostMapping("/login")
    public ResultVO login(@RequestBody @Valid AdminAuthForm adminAuthForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【管理员登录】参数不正确，adminAuthForm = {}", adminAuthForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultVOUtil.success(adminService.checkLogin(adminAuthForm));
    }
}
