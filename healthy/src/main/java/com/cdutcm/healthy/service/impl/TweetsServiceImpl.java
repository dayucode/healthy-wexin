package com.cdutcm.healthy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdutcm.healthy.dao.TweetsDao;
import com.cdutcm.healthy.dataobject.entity.Tweets;
import com.cdutcm.healthy.dataobject.form.admin.TweetsForm;
import com.cdutcm.healthy.dataobject.form.user.PageForm;
import com.cdutcm.healthy.dataobject.vo.IPageVO;
import com.cdutcm.healthy.dataobject.vo.user.TweetsVO;
import com.cdutcm.healthy.enums.ResultEnum;
import com.cdutcm.healthy.enums.TweetsTypeEnum;
import com.cdutcm.healthy.exception.HealthyException;
import com.cdutcm.healthy.properties.HealthyProperties;
import com.cdutcm.healthy.service.TweetsService;
import com.cdutcm.healthy.utils.EnumUtil;
import com.cdutcm.healthy.utils.KeyUtil;
import com.cdutcm.healthy.utils.converter.Entity2VO;
import com.cdutcm.healthy.utils.converter.IPage2IPageVO;
import com.cdutcm.healthy.utils.converter.PageForm2Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 17:53 星期二
 * @Description :
 */
@Slf4j
@Service
public class TweetsServiceImpl implements TweetsService {
    @Autowired
    private TweetsDao tweetsDao;

    private String healthyUrl;

    public TweetsServiceImpl(HealthyProperties healthyProperties) {
        healthyUrl = healthyProperties.getUrl().getHealthy();
    }

    @Override
    public IPageVO hotspotTweets(PageForm page) {
        LambdaQueryWrapper<Tweets> queryWrapper = new LambdaQueryWrapper<Tweets>()
                .eq(Tweets::getTweetsStatus, false)//未删除的推文
                .orderByDesc(Tweets::getTweetsAmount);
        IPage iPage = tweetsDao.selectPage(PageForm2Page.convert(page), queryWrapper);
        return IPage2IPageVO.convert(Entity2VO.convert(iPage, TweetsVO.class));
    }

    @Override
    public IPageVO getTweetsList(String tweetsType, PageForm page) {
        LambdaQueryWrapper<Tweets> queryWrapper = new LambdaQueryWrapper<Tweets>()
                .eq(Tweets::getTweetsStatus, false);//未删除的推文

        if (tweetsType != null) {
            try {
                // 通过推文类型英文名称获取该推文的编号
                Integer tweetsTypeCode = EnumUtil.getByName(tweetsType, TweetsTypeEnum.class).getCode();
                // 如果是热点推文，则通过推文浏览量逆序排列
                if (TweetsTypeEnum.HOTSPOT.getCode().equals(tweetsTypeCode)) {
                    queryWrapper.orderByDesc(Tweets::getTweetsAmount);
                } else {
                    queryWrapper.eq(Tweets::getTweetsType, tweetsTypeCode);
                    queryWrapper.orderByDesc(Tweets::getCreateTime);
                }
            } catch (Exception e) {
                log.error("【健康推文】该推文类型不存在，tweetsType = {}", tweetsType);
                throw new HealthyException(ResultEnum.TWEETS_TYPE_NOT_EXIST);
            }
        }
        IPage iPage = tweetsDao.selectPage(PageForm2Page.convert(page), queryWrapper);
        return IPage2IPageVO.convert(Entity2VO.convert(iPage, TweetsVO.class));
    }

    @Override
    public TweetsVO getTweets(String tweetsId) {
        Tweets tweets = tweetsDao.selectById(tweetsId);
        if (tweets == null || tweets.getTweetsStatus()) {//推文被删除，或者推文不存在
            log.error("【健康推文】该推文不存在，tweetsId = {}", tweetsId);
            throw new HealthyException(ResultEnum.TWEETS_NOT_EXIST);
        }
        tweets.addTweetsAmount();//阅读量+1
        tweetsDao.updateTweets(tweets);
        return Entity2VO.convert(tweets, TweetsVO.class);
    }

    @Override
    public void deleteTweets(String tweetsId) {
        Tweets tweets = tweetsDao.selectById(tweetsId);
        if (tweets == null || tweets.getTweetsStatus()) {
            log.error("【删除推文】该推文不存在，tweetsId = {}", tweetsId);
            throw new HealthyException(ResultEnum.TWEETS_NOT_EXIST);
        }
        tweets.setTweetsStatus(true);
        try {
            tweetsDao.updateTweets(tweets);
        } catch (Exception e) {
            log.error("【删除推文】推文删除失败，tweetsId = {}", tweetsId);
            throw new HealthyException(ResultEnum.TWEETS_DELETE_ERROR);
        }
    }

    @Override
    public void modifyTweets(TweetsForm tweetsForm) {
        if (tweetsForm.getTweetsId() == null) {
            log.error("【修改推文】推文ID不能为空，tweetsId = {}", tweetsForm);
            throw new HealthyException(ResultEnum.TWEETS_ID_IS_EMPTY);
        }
        Tweets tweets = tweetsDao.selectById(tweetsForm.getTweetsId());
        BeanUtils.copyProperties(tweetsForm, tweets);
        log.info("【修改推文】\ntweetsForm = {}\ntweets = {}", tweetsForm, tweets);
        try {
            tweetsDao.updateTweets(tweets);
        } catch (Exception e) {
            log.error("【修改推文】推文修改失败，tweetsForm = {}", tweetsForm);
            throw new HealthyException(ResultEnum.TWEETS_UPDATE_ERROR);
        }
    }


    @Override
    public void saveTweets(TweetsForm tweetsForm) {
        Tweets tweets = Entity2VO.convert(tweetsForm, Tweets.class);
        try {
            tweets.setTweetsId(KeyUtil.getUniqueKeyTime());
            tweetsDao.insertTweets(tweets);
        } catch (Exception e) {
            log.error("【新增推文】推文保存失败，tweets = {}", tweets);
            throw new HealthyException(ResultEnum.TWEETS_SAVE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> uploadFile(List<MultipartFile> files) {
        List<String> imgUrls = new ArrayList<>();
        String fileName;
        for (MultipartFile file : files) {
            //返回的是字节长度,1M=1024k=1048576字节 也就是if(fileSize<5*1048576)
            if (file.getSize() > (1048576 * 5)) {
                log.error("【编辑推文】上传文件大于5MB，fileSize = {}", file.getSize());
                throw new HealthyException(ResultEnum.TWEETS_FILE_SIZE_TOO_LONG);
            }
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (StringUtils.isBlank(suffix)) {
                log.error("【编辑推文】文件没有后缀名，filename = {}", file.getOriginalFilename());
                throw new HealthyException(ResultEnum.TWEETS_FILE_NOT_SUFFIX);
            }
            try {
                fileName = "/tweets/" + KeyUtil.getUniqueKeyTime() + suffix;
                File dest = new File(System.getProperty("user.dir") + "/img" + fileName);
                if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                    dest.getParentFile().mkdirs();
                }
                file.transferTo(dest); //保存文件
            } catch (Exception e) {
                log.error("【编辑推文】文件上传失败，filename = {}", file.getOriginalFilename());
                throw new HealthyException(ResultEnum.TWEETS_FILE_UPLOAD_ERROR);
            }
            imgUrls.add(healthyUrl.concat(fileName));
        }
        return imgUrls;
    }
}
