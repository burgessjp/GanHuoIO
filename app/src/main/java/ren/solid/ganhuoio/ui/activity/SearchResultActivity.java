package ren.solid.ganhuoio.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.ui.fragment.SearchResultListFragment;
import ren.solid.library.activity.base.BaseActivity;

/**
 * Created by _SOLID
 * Date:2016/11/22
 * Time:10:08
 * Desc:
 */

public class SearchResultActivity extends BaseActivity {

    private Toolbar mToolbar;
    private String mKeyWord;

    public static void openActivity(Context context, String word) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("keyWord", word);
        context.startActivity(intent);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mKeyWord = getIntent().getExtras().getString("keyWord");
    }

    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar);
        mToolbar.setTitle(mKeyWord + "的搜索结果");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_result, SearchResultListFragment.newInstance(mKeyWord)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
