package ren.solid.ganhuoio.common.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ren.solid.ganhuoio.module.home.fragment.MeiZhiFragment;
import ren.solid.ganhuoio.module.mine.CollectListFragment;
import ren.solid.library.activity.ToolbarActivity;

/**
 * Created by _SOLID
 * Date:2017/3/14
 * Time:13:24
 * Desc:包含有一个共同的Title的页面
 */

public class SubActivity extends ToolbarActivity {
    public final static int TYPE_MEIZHI = 0X000001;
    public final static int TYPE_COLLECT = 0X000002;

    public static void start(Context context, String title, int type) {
        Intent intent = new Intent(context, SubActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected String getToolbarTitle() {
        return getIntent().getExtras().getString("title");
    }

    @Override
    protected Fragment getFragment() {
        int type = getIntent().getExtras().getInt("type");
        Fragment fragment = null;
        if (type == TYPE_MEIZHI) {
            fragment = MeiZhiFragment.newInstance();
        } else if (type == TYPE_COLLECT) {
            fragment = CollectListFragment.newInstance();
        }
        //由于在之前使用了懒加载，所以加上这个才会显示
        if (fragment != null) fragment.setUserVisibleHint(true);
        return fragment;
    }
}
