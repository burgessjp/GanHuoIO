package ren.solid.ganhuoio.module.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.module.home.fragment.RecentlyListFragment;
import ren.solid.library.activity.base.BaseActivity;
import ren.solid.library.imageloader.ImageLoader;


/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:17:30
 * Desc:
 */

public class DailyActivity extends BaseActivity {

    public static void start(Context context, String title, String date, String imageUrl) {
        Intent intent = new Intent(context, DailyActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        intent.putExtra("imageUrl", imageUrl);
        context.startActivity(intent);
    }

    protected String getToolbarTitle() {
        String date = getIntent().getExtras().getString("date");
        String title = getIntent().getExtras().getString("title");
        return title.replace("今日力推", date + " 力推");
    }

    protected String getImageUrl() {
        return getIntent().getExtras().getString("imageUrl");
    }

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
    protected int setLayoutResourceID() {
        return R.layout.activity_daily;
    }

    @Override
    protected void setUpView() {
        setUpToolbar();
        ImageView iv_image = $(R.id.iv_image);
        ImageLoader.displayImage(iv_image, getImageUrl());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, getFragment())
                .commit();


    }

    private void setUpToolbar() {
        CollapsingToolbarLayout collapsing_toolbar_layout = $(R.id.collapsing_toolbar_layout);
        Toolbar toolbar = $(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsing_toolbar_layout.setTitle(getToolbarTitle());
    }
}
