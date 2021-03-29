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
 * 血糖-历史记录-变化趋势/近三个月统计
 */
@Data
@JsonView(BaseView.class)
public class SugarCensusVO {
    //总记录次数
    private Integer total;
    //正常血糖次数
    private Integer normal = 0;
    //偏高血糖次数
    private Integer high = 0;
    //最低血糖
    private Double lowValue;
    //最高血糖
    private Double highValue;
    //平均血糖
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double average;
    //血糖变化趋势
    private Map<String, SugarTendencyVO> tendency;

    public void addNormal() {
        normal++;
    }

    public void addHigh() {
        high++;
    }
}
