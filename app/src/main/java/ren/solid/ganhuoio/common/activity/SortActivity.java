package ren.solid.ganhuoio.common.activity;

import android.support.v4.app.Fragment;

import ren.solid.ganhuoio.common.SortFragment;
import ren.solid.library.activity.ToolbarActivity;

/**
 * Created by _SOLID
 * Date:2016/5/17
 * Time:15:49
 */
public class SortActivity extends ToolbarActivity {
    @Override
    protected String getToolbarTitle() {
        return "排序";
    }

    @Override
    protected Fragment getFragment() {
        return new SortFragment();
    }
}
