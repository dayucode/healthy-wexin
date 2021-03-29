package com.cdutcm.healthy.controller.user;

import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.ResultVO;
import com.cdutcm.healthy.dataobject.vo.user.TweetsVO;
import com.cdutcm.healthy.service.TweetsService;
import com.cdutcm.healthy.utils.ResultVOUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 17:50 星期二
 * @Description :
 */
@Api(tags = "【用户-推文】")
@Slf4j
@RestController
@RequestMapping("/user/tweets")
public class TweetsController {
    @Autowired
    private TweetsService tweetsService;

    @ApiOperation("【分类新闻】根据新闻类型获取新闻")
    @GetMapping("/{tweetsType}")
    @JsonView(TweetsVO.TweetsListView.class)
    public ResultVO tweetsList(@ApiParam("新闻类型[HOTSPOT、PRESSURE、SUGAR、OBESITY、CORONARY]")
                               @PathVariable("tweetsType") String tweetsType, PageForm page) {
        return ResultVOUtil.success(tweetsService.getTweetsList(tweetsType, page));
    }

    @ApiOperation("【新闻详情】根据新闻ID获取新闻详情信息")
    @GetMapping("/detail/{tweetsId}")
    @JsonView(TweetsVO.TweetsDetailView.class)
    public ResultVO tweetsDetail(@ApiParam("新闻ID") @PathVariable("tweetsId") String tweetsId) {
        return ResultVOUtil.success(tweetsService.getTweets(tweetsId));
    }
}