package ren.solid.ganhuoio.module.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ren.solid.ganhuoio.module.home.fragment.CategoryListFragment;
import ren.solid.library.activity.ToolbarActivity;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:16:51
 * Desc:
 */

public class CategoryActivity extends ToolbarActivity {

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected String getToolbarTitle() {
        return getIntent().getExtras().getString("type");
    }

    @Override
    protected Fragment getFragment() {
        CategoryListFragment fragment =
                CategoryListFragment.newInstance(getIntent().getExtras().getString("type"));
        fragment.setUserVisibleHint(true);
        return fragment;
    }
}
