package ren.solid.ganhuoio.model.bean.bomb;

import cn.bmob.v3.BmobObject;

/**
 * Created by _SOLID
 * Date:2016/6/1
 * Time:16:57
 */
public class RxOperators extends BmobObject {

    private String name;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
