package com.cdutcm.healthy.dataobject.vo.user;

import com.cdutcm.healthy.dataobject.vo.BaseView;
import com.cdutcm.healthy.utils.serializer.NumeralAccuracy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/24 16:22 星期日
 * @Description :
 */
@Data
public class SugarTendencyVO {

    public interface SugarCensusView extends BaseView {
    }

    public interface SugarHistoryView extends SugarCensusView {
    }

    //时间
    @JsonView(SugarHistoryView.class)
    private String time;
    //血糖指数
    @JsonView(SugarCensusView.class)
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double sugarValue = 0.0;
    @JsonIgnore
    private Integer total = 1;

    public void add(SugarTendencyVO sugarTendencyVO) {
        sugarValue += sugarTendencyVO.getSugarValue();
        total++;
    }

    public Double getSugarValue() {
        return sugarValue / total;
    }
}
