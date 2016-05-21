package ren.solid.ganhuoio.ui.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ren.solid.ganhuoio.ui.fragment.GanHuoListFragment;
import ren.solid.ganhuoio.ui.fragment.ImagesFragmant;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/4/18
 * Time:17:04
 */
public class GanHuoPagerAdapter extends FragmentStatePagerAdapter {

    private static String TAG = "GanHuoPagerAdapter";
    private static List<String> mTitles;

    public GanHuoPagerAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    public void addAll(List<String> titles) {
        this.mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (!"福利".equals(mTitles.get(position))) {
            fragment = ViewUtils.createFragment(GanHuoListFragment.class, false);
            Bundle bundle = new Bundle();
            bundle.putString("type", mTitles.get(position));
            fragment.setArguments(bundle);
        } else {
            fragment = ViewUtils.createFragment(ImagesFragmant.class, false);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        // super.restoreState(state, loader);
        //重写这个方法是为了防止在restoreState的时候导致应用崩溃，这样做虽然不太好，但是目前我也只能想到这种方法了
        Log.i(TAG, "restoreState");
    }
}
