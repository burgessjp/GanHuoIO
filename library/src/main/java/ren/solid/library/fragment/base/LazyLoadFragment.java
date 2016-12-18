package ren.solid.library.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by _SOLID
 * Date:2016/12/18
 * Time:1:07
 * Desc:懒加载Fragment
 */

public abstract class LazyLoadFragment extends BaseFragment {

    protected boolean isViewCreated = false;
    protected boolean isFirstLoad = true;
    protected boolean isNeedInitView = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        if (isNeedInitView) {
            lazyLoad();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoad) {
            if (isViewCreated) {
                isFirstLoad = false;
                lazyLoad();
            } else {
                isNeedInitView = true;
            }
        }
    }

    protected abstract void lazyLoad();
}
