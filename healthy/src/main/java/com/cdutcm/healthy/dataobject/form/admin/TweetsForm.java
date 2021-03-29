package com.cdutcm.healthy.dataobject.form.admin;

import com.cdutcm.healthy.enums.TweetsTypeEnum;
import com.cdutcm.healthy.utils.EnumUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/6 18:05 星期三
 * @Description :
 */
@Data
public class TweetsForm {
    @ApiModelProperty("推文ID")
    private String tweetsId;
    @ApiModelProperty("推文标题")
    @NotBlank(message = "推文标题不能为空")
    private String tweetsTitle;
    @ApiModelProperty("推文类型")
    @NotBlank(message = "推文类型不能为空")
    private String tweetsType;
    @ApiModelProperty("推文简介，正文的前30个字符")
    @NotBlank(message = "推文简介不能为空")
    private String tweetsSynopsis;
    @ApiModelProperty("推文正文")
    @NotBlank(message = "推文正文不能为空")
    private String tweetsText;
    @ApiModelProperty("推文图片")
    @NotBlank(message = "推文图片不能为空")
    private String tweetsImg;

    public Integer getTweetsType() {
        return EnumUtil.getByMsg(tweetsType, TweetsTypeEnum.class).getCode();
    }
}
