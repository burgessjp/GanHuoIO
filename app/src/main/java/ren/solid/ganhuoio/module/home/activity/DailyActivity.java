package ren.solid.ganhuoio.module.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Field;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.module.home.fragment.RecentlyListFragment;
import ren.solid.library.activity.ViewPicActivity;
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

    public static void start(Activity activity, View v, String title, String date, String imageUrl) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                v,
                activity.getResources().getString(R.string.image_transition));

        Intent intent = new Intent(activity, DailyActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        intent.putExtra("imageUrl", imageUrl);

        ActivityCompat.startActivity(activity, intent, compat.toBundle());
    }

    protected String getToolbarTitle() {
        String title = getIntent().getExtras().getString("title");
        return title.replace("今日力推：", "");
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
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPicActivity.start(DailyActivity.this, v, getImageUrl());
            }
        });

    }

    private void setUpToolbar() {
        CollapsingToolbarLayout collapsing_toolbar = $(R.id.collapsing_toolbar_layout);
        Toolbar toolbar = $(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsing_toolbar.setTitle(getToolbarTitle());

        boldTitleText(collapsing_toolbar);
    }

    /**
     * 加粗CollapsingToolbarLayout的标题文字
     *
     * @param collapsing_toolbar CollapsingToolbarLayout
     */
    private void boldTitleText(CollapsingToolbarLayout collapsing_toolbar) {
        try {
            Class<?> clazz = collapsing_toolbar.getClass();
            Field field = clazz.getDeclaredField("mCollapsingTextHelper");
            field.setAccessible(true);
            //二次反射
            Object textHelper = field.get(collapsing_toolbar);
            clazz = textHelper.getClass();
            field = clazz.getDeclaredField("mTextPaint");
            field.setAccessible(true);
            TextPaint textPaint = (TextPaint) field.get(textHelper);
            textPaint.setFakeBoldText(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
