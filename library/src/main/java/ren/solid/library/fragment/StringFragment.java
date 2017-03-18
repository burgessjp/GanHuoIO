package ren.solid.library.fragment;

import android.os.Bundle;
import android.widget.TextView;

import ren.solid.library.R;
import ren.solid.library.fragment.base.BaseFragment;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:21:43
 */
public class StringFragment extends BaseFragment {
    private String mText;
    private TextView mTvText;

    public static StringFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("text", text);
        StringFragment fragment = new StringFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_string;
    }

    @Override
    protected void setUpView() {
        mText = getArguments().getString("text");
        mTvText = $(R.id.tv_text);

    }

    @Override
    protected void setUpData() {
        if (!mText.equals(""))
            mTvText.setText(mText);
        else
            mTvText.setText("暂无信息");
    }
}
