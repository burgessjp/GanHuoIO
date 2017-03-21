package ren.solid.ganhuoio;

import com.squareup.leakcanary.LeakCanary;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import ren.solid.library.SolidApplication;

/**
 * Created by _SOLID
 * Date:2016/5/16
 * Time:11:46
 */
public class GanHuoIOApplication extends SolidApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "caed915330178bff62bfd281b627c77f");

        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);

        LeakCanary.install(this);
        MultiTypeInstaller.install();
    }
}
