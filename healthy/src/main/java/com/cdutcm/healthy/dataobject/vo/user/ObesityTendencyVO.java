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
public class ObesityTendencyVO {

    public interface ObesityCensusView extends BaseView {
    }

    public interface ObesityHistoryView extends ObesityCensusView {
    }

    //时间
    @JsonView(ObesityHistoryView.class)
    private String time;
    //体重
    @JsonView(ObesityCensusView.class)
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double obesityValue = 0.0;
    @JsonView(ObesityHistoryView.class)
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double bmi = 0.0;
    @JsonIgnore
    private int total = 1;

    public void add(ObesityTendencyVO obesityTendencyVO) {
        obesityValue += obesityTendencyVO.getObesityValue();
        total++;
    }

    public Double getObesityValue() {
        return obesityValue / total;
    }
}
