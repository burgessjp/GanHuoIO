
package ren.solid.ganhuoio.common.event;

/**
 * Created by _SOLID
 * Date:2016/11/11
 * Time:17:29
 * Desc:
 */

public class LoginEvent {

    int type = 1;//1:登录成功 0:登出
    public LoginEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
