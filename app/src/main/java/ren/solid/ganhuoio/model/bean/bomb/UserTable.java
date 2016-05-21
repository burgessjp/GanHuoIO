package ren.solid.ganhuoio.model.bean.bomb;

import cn.bmob.v3.BmobObject;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:13:21
 */
public class UserTable extends BmobObject {

    private String name;
    private String des;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


}
