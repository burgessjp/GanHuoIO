package ren.solid.ganhuoio.ui.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ren.solid.ganhuoio.model.bean.GanHuoTitleBean;
import ren.solid.ganhuoio.ui.fragment.RecentlyListFragment;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:54
 */
public class GanHuoRecentlyPageAdapter extends FragmentStatePagerAdapter {

    private List<String> mDateString;
    private List<GanHuoTitleBean> mTitleList;

    public GanHuoRecentlyPageAdapter(FragmentManager fm, List<String> dateString, List<GanHuoTitleBean> titleList) {
        super(fm);
        mDateString = dateString;
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ViewUtils.createFragment(RecentlyListFragment.class, false);
        Bundle bundle = new Bundle();
        bundle.putString(RecentlyListFragment.DATE_STRING, mDateString.get(position));
        bundle.putString(RecentlyListFragment.TITLE, mTitleList.get(position).getTitle());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mDateString.size();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }
}
