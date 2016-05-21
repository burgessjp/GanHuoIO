package ren.solid.ganhuoio.ui.activity;

import android.support.v4.app.Fragment;

import ren.solid.ganhuoio.ui.fragment.SortFragment;
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
    protected Fragment setFragment() {
        return new SortFragment();
    }
}
