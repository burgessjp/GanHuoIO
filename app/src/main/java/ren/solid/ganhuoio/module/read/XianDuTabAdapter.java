package ren.solid.ganhuoio.module.read;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ren.solid.ganhuoio.bean.XianDuCategory;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/18
 * Time:16:02
 * Desc:
 */

public class XianDuTabAdapter extends FragmentStatePagerAdapter {
    private List<XianDuCategory> list;

    public XianDuTabAdapter(FragmentManager fm, List<XianDuCategory> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ReadingListFragment.newInstance(list.get(position).getCategory());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
