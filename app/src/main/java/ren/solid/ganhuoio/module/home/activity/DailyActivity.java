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
        String date = getIntent().getExtras().getString("date");
        String title = getIntent().getExtras().getString("title");
        return title.replace("今日力推", date + " 力推");
    }

    @Override
    protected Fragment getFragment() {
        String date = getIntent().getExtras().getString("date");
        String[] dates = null;
        if (date.contains("-")) {
            dates = getIntent().getExtras().getString("date").split("-");
        } else if (date.contains("/")) {
            dates = getIntent().getExtras().getString("date").split("/");
        }

        RecentlyListFragment fragment = null;
        if (dates != null && dates.length == 3) {
            fragment = RecentlyListFragment.newInstance(
                    Integer.parseInt(dates[0]),
                    Integer.parseInt(dates[1]),
                    Integer.parseInt(dates[2]));
            fragment.setUserVisibleHint(true);
        }
        return fragment;
    }

    @Override
    protected boolean isHaveTitle() {
        return true;
    }
}
