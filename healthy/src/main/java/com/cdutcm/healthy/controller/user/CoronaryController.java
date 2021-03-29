package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.constant.RedisConstant;
import com.cdutcm.healthy.dataobject.form.user.CoronaryForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.CoronaryService;
import com.cdutcm.healthy.service.RedisOperator;
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
 * @Create : 2019/2/25 23:57 星期一
 * @Description :
 */
@Api(tags = "【用户-冠心病】")
@Slf4j
@RestController
@RequestMapping("/user/coronary")
public class CoronaryController {
    @Autowired
    private CoronaryService coronaryService;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation("【冠心病风险评估】提交测评数据，返回测评结果")
    @PostMapping("/assess")
    public ResultVO assess(@Valid @RequestBody CoronaryForm coronaryForm, BindingResult bindingResult,
                           @ApiParam("登录凭证token") @CookieValue("token") String token) {
        if (bindingResult.hasErrors()) {
            log.error("【冠心病风险评估】参数错误，coronaryForm = {}", coronaryForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        String openid = redisOperator.get(String.format(RedisConstant.TOKEN_USER, token));
        return ResultVOUtil.success(coronaryService.assessCoronary(coronaryForm, openid));
    }
}
