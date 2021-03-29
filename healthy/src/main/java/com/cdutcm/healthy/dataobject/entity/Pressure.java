package com.cdutcm.healthy.dataobject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/21 16:41 星期四
 * @Description :
 * 血压表
 */
@Data
@TableName("h_pressure")
public class Pressure {
    //血压测量ID
    @TableId
    private String pressureId;
    //用户微信openid
    private String openid;
    //血压测量时间
    private Date pressureDatetime;
    //血压收缩压
    private Double highPressure;
    //血压舒张压
    private Double lowPressure;
    //血压类型
    private Integer pressureType;
    // 用户地理位置
    private String location;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    //获取静脉差
    public Double getVenous() {
        return highPressure - lowPressure;
    }
}
