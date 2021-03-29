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
public class PressureTendencyVO {

    public interface PressureCensusView extends BaseView {
    }

    public interface PressureHistoryView extends PressureCensusView {
    }

    //时间
    @JsonView(PressureHistoryView.class)
    private String time;
    //收缩压
    @JsonView(PressureCensusView.class)
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double highPressure = 0.0;
    //舒张压
    @JsonView(PressureCensusView.class)
    @JsonSerialize(using = NumeralAccuracy.class)
    private Double lowPressure = 0.0;
    @JsonIgnore
    int total = 1;

    //加pressureTendencyVO到lowPressure，highPressure；并将个数total加1
    public void add(PressureTendencyVO pressureTendencyVO) {
        lowPressure += pressureTendencyVO.getLowPressure();
        highPressure += pressureTendencyVO.getHighPressure();
        total++;
    }

    public Double getHighPressure() {
        return highPressure / total;
    }

    public Double getLowPressure() {
        return lowPressure / total;
    }
}
