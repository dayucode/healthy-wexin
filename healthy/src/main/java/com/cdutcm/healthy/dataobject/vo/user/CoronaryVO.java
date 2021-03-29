package com.cdutcm.healthy.dataobject.vo.user;

import com.cdutcm.healthy.utils.serializer.CoronaryStatus2String;
import com.cdutcm.healthy.utils.serializer.CoronaryValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/26 16:02 星期二
 * @Description :
 */
@Data
public class CoronaryVO {
    //风险值
    @JsonSerialize(using = CoronaryValue.class)
    private Double coronaryValue;
    //风险等级
    @JsonSerialize(using = CoronaryStatus2String.class)
    private Integer coronaryStatus;
}
