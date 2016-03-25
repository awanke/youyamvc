package com.magicalcoder.youyamvc.app.model.defined;

import com.magicalcoder.youyamvc.app.model.TClass;
/**
* Created by www.magicalcoder.com
* 如果你改变了此类 read 请将此行删除
* 799374340@qq.com
*/
public class TTeacherClassRelMany extends TClass{

    private Integer classId;//班级id

    public Integer getClassId(){
        return classId;
    }

    public void setClassId(Integer classId){
        this.classId = classId;
    }

}
