package ren.solid.ganhuoio.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ren.solid.ganhuoio.ui.fragment.CategoryListFragment;
import ren.solid.ganhuoio.ui.fragment.MeizhiFragmant;

/**
 * Created by _SOLID
 * Date:2016/4/18
 * Time:17:04
 */
public class GanHuoPagerAdapter extends FragmentStatePagerAdapter {
    private  List<String> mTitles;
    private int mChildCount = 0;

    public GanHuoPagerAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    public void addAll(List<String> titles) {
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (!"福利".equals(mTitles.get(position))) {
            fragment = CategoryListFragment.newInstance(mTitles.get(position));
        } else {
            fragment = MeizhiFragmant.newInstance();
        }
        return fragment;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
