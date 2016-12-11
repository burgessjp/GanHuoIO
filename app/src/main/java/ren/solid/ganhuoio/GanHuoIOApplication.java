package ren.solid.ganhuoio;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.leakcanary.LeakCanary;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.update.BmobUpdateAgent;
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

        //BmobUpdateAgent.initAppVersion(this);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.with(imageView.getContext()).onDestroy();
            }

        });
        LeakCanary.install(this);
        MultiTypeInstaller.install();
    }
}
