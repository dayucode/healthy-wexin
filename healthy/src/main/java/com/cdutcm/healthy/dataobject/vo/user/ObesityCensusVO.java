package com.cdutcm.healthy.dataobject.vo.user;

import com.cdutcm.healthy.dataobject.vo.BaseView;
import com.cdutcm.healthy.utils.serializer.NumeralAccuracy;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Map;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 22:02 星期六
 * @Description :
 * 体重-历史记录-变化趋势/近三个月统计
 */
@Data
@JsonView(BaseView.class)
public class ObesityCensusVO {
    //总记录次数
    private Integer total;
    //体重最高值
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double highValue;
    //体重最低值
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double lowValue;
    //体重变化趋势
    private Map<String, ObesityTendencyVO> tendency;
}
