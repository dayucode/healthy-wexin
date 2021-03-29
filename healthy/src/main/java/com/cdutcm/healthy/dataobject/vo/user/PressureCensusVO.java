package com.cdutcm.healthy.dataobject.vo.user;

import com.cdutcm.healthy.dataobject.vo.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Map;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 22:02 星期六
 * @Description :
 * 血压-历史记录-近三个月统计：统计数据
 */
@Data
@JsonView(BaseView.class)
public class PressureCensusVO {
    //总记录次数
    private Integer total;
    //正常血压次数
    private Integer normal = 0;
    //偏高血压次数
    private Integer high = 0;
    //舒张压最高值
    private Double lowValue;
    //收缩压最高值
    private Double highValue;
    //最大脉差值
    private Double venous;
    //血压变化趋势
    private Map<String, PressureTendencyVO> tendency;

    public void addNormal() {
        normal++;
    }

    public void addHigh() {
        high++;
    }
}
