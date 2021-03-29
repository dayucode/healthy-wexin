package com.cdutcm.healthy.controller.admin;

import com.cdutcm.healthy.dataobject.form.admin.TweetsForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.admin.WangEditorVO;
import com.cdutcm.healthy.dataobject.vo.user.TweetsVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.service.TweetsService;
import com.cdutcm.healthy.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 17:50 星期二
 * @Description :
 */
@Api(tags = "【管理员-推文】")
@Slf4j
@RestController
@RequestMapping("/admin/tweets")
public class AdminTweetsController {
    @Autowired
    private TweetsService tweetsService;

    @ApiOperation("【推文列表】获取推文列表")
    @GetMapping("/list")
    @JsonView(TweetsVO.TweetsPublicView.class)
    public ResultVO list(PageForm pageForm) {
        return ResultVOUtil.success(tweetsService.getTweetsList(null, pageForm));
    }

    @ApiOperation("【删除推文】根据推文ID删除推文")
    @DeleteMapping("/delete")
    public ResultVO delete(@ApiParam("推文ID") @RequestParam("tweetsId") String tweetsId) {
        tweetsService.deleteTweets(tweetsId);
        return ResultVOUtil.success();
    }

    @ApiOperation("【推文详情】根据推文ID获取推文详细内容")
    @GetMapping("/detail/{tweetsId}")
    @JsonView(TweetsVO.TweetsDetailView.class)
    public ResultVO detail(@ApiParam("推文ID") @PathVariable("tweetsId") String tweetsId) {
        return ResultVOUtil.success(tweetsService.getTweets(tweetsId));
    }

    @ApiOperation("【更新推文】更新推文详细内容")
    @PutMapping("/modify")
    public ResultVO modify(@RequestBody @Valid TweetsForm tweetsForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【修改推文】参数错误，tweetsForm = {}", tweetsForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        tweetsService.modifyTweets(tweetsForm);
        return ResultVOUtil.success();
    }

    @ApiOperation("【新增推文】新增推文信息")
    @PostMapping("/save")
    public ResultVO save(@RequestBody @Valid TweetsForm tweetsForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【新增推文】参数错误，tweetsForm = {}", tweetsForm);
            throw new HealthyException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        tweetsService.saveTweets(tweetsForm);
        return ResultVOUtil.success();
    }

    @ApiOperation("【推文图片】上传推文新闻图片")
    @PostMapping("/upload")
    public WangEditorVO editor(@RequestParam("file") List<MultipartFile> files) {
        if (files.size() == 0) {//文件个数为0
            log.error("【编辑推文】上传文件个数为0");
            throw new HealthyException(ResultEnum.TWEETS_FILE_IS_EMPTY);
        }
        long filesSize = files.stream().mapToLong(MultipartFile::getSize).sum();
        if (filesSize > (1048576 * 50)) {//总文件大于50MB
            log.error("【编辑推文】上传总文件大于50MB，filesSize = {}", filesSize);
            throw new HealthyException(ResultEnum.TWEETS_FILES_SIZE_TOO_LONG);
        }
        return new WangEditorVO(tweetsService.uploadFile(files));
    }
}