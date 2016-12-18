package ren.solid.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import ren.solid.library.R;

/**
 * Created by _SOLID
 * Date:2016/7/8
 * Time:10:36
 */
public class StatusViewLayout extends FrameLayout {

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private OnClickListener mOnRetryListener;
    private TextView status_view_tv_error;
    private TextView status_tv_empty_msg;

    public StatusViewLayout(Context context) {
        this(context, null);
    }

    public StatusViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();

    }

    private void setUpView() {
        FrameLayout.LayoutParams mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.gravity = Gravity.CENTER;

        mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.status_view_layout_loading, null);
        mErrorView = LayoutInflater.from(getContext()).inflate(R.layout.status_view_layout_error, null);
        mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.status_view_layout_empty, null);
        status_view_tv_error = (TextView) mErrorView.findViewById(R.id.status_view_tv_error);
        status_tv_empty_msg = (TextView) mEmptyView.findViewById(R.id.status_tv_empty_msg);
        addView(mLoadingView, mLayoutParams);
        addView(mErrorView, mLayoutParams);
        addView(mEmptyView, mLayoutParams);

        mErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRetryListener != null) {
                    showLoading();
                    mOnRetryListener.onClick(view);
                }
            }
        });
    }

    public void setOnRetryListener(OnClickListener listener) {
        mOnRetryListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showLoading();
    }

    public void showLoading() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void showError() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showError(String msg) {
        showError();
        status_view_tv_error.setText(msg);

    }

    public void showEmpty() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void showEmpty(String msg) {
        showEmpty();
        status_tv_empty_msg.setText(msg);

    }

    public void showContent() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        getChildAt(getChildCount() - 1).setVisibility(View.VISIBLE);
    }
}
