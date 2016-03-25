package com.magicalcoder.youyamvc.app.model.defined;

import com.magicalcoder.youyamvc.app.model.TUsers;
/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TStudentParentRelMany extends TUsers{

    private Integer userId;//用户id

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

}
