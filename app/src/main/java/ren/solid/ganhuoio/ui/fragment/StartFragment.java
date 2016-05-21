package ren.solid.ganhuoio.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import ren.solid.ganhuoio.R;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/5/21
 * Time:10:16
 */
public class StartFragment extends BaseFragment {

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_start;
    }

    @Override
    protected void setUpView() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat($(R.id.iv_logo), "scaleX", 1.0f, 1.5f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat($(R.id.iv_logo), "scaleY", 1.0f, 1.5f);
        ObjectAnimator animatorTv = ObjectAnimator.ofFloat($(R.id.tv_appname), "translationY", 0, ViewUtils.dp2px(getMContext(), 30));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY,animatorTv);
        animatorSet.setDuration(2000).start();
    }

    @Override
    protected void setUpData() {

    }
}
