package ren.solid.ganhuoio.module.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ren.solid.ganhuoio.module.home.fragment.RecentlyListFragment;
import ren.solid.library.activity.ToolbarActivity;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:17:30
 * Desc:
 */

public class DailyActivity extends ToolbarActivity {
    public static void start(Context context, String title, String date) {
        Intent intent = new Intent(context, DailyActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        context.startActivity(intent);
    }

    @Override
    protected String getToolbarTitle() {
        return getIntent().getExtras().getString("date").replace("-", "/");
    }

    @Override
    protected String getToolbarSubTitle() {
        return getIntent().getExtras().getString("title").replace("今日力推：", "");
    }

    @Override
    protected Fragment setFragment() {
        RecentlyListFragment fragment = RecentlyListFragment.newInstance(
                getIntent().getExtras().getString("date").replace("-", "/"),
                getIntent().getExtras().getString("title"));
        fragment.setUserVisibleHint(true);
        return fragment;
    }

    @Override
    protected boolean isHaveTitle() {
        return true;
    }
}
