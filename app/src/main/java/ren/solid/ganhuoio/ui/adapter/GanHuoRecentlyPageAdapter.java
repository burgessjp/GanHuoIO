package ren.solid.ganhuoio.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ren.solid.ganhuoio.ui.fragment.GanHuoListFragment;
import ren.solid.ganhuoio.ui.fragment.RecentlyListFragment;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:54
 */
public class GanHuoRecentlyPageAdapter extends FragmentStatePagerAdapter {

    private List<String> mDateString;

    public GanHuoRecentlyPageAdapter(FragmentManager fm, List<String> dateString) {
        super(fm);
        mDateString = dateString;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(RecentlyListFragment.class, false);
        Bundle bundle = new Bundle();
        bundle.putString(RecentlyListFragment.DATE_STRING, mDateString.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDateString.size();
    }
}
