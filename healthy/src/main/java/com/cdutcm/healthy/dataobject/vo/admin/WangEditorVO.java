package com.cdutcm.healthy.dataobject.vo.admin;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author :  涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/3/5 23:46 星期二
 * @Description :
 */
@Data
public class WangEditorVO {
    Integer errno;
    List<String> data;

    public WangEditorVO(List<String> data) {
        this.errno = 0;
        this.data = data;
    }

    public WangEditorVO(String data) {
        this.errno = 0;
        this.data = new ArrayList<>();
        this.data.add(data);
    }
}
