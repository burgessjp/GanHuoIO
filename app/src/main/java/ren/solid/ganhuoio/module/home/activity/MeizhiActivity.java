package ren.solid.ganhuoio.module.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ren.solid.ganhuoio.module.home.fragment.CategoryListFragment;
import ren.solid.ganhuoio.module.home.fragment.MeiZhiFragment;
import ren.solid.library.activity.ToolbarActivity;

import static android.R.attr.type;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:16:51
 * Desc:
 */

public class MeizhiActivity extends ToolbarActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MeizhiActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected String getToolbarTitle() {
        return "看妹纸";
    }

    @Override
    protected Fragment setFragment() {
        MeiZhiFragment fragment =
                MeiZhiFragment.newInstance();
        //由于在之前使用了懒加载，所以加上这个才会显示
        fragment.setUserVisibleHint(true);
        return fragment;
    }
}
