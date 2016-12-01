package ren.solid.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import ren.solid.library.R;


/**
 * Created by _SOLID
 * Date: 2016/9/25
 * Time: 17:51
 * Desc:线性布局的分割线
 */
public class LinearDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerColor;
    private int mOrientation;//列表的方向
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};


    /**
     * @param context     context
     * @param orientation 列表方向
     */
    public LinearDecoration(Context context, int orientation) {
        this(context, orientation, 1);
    }
//
//    /**
//     * 自定义分割线
//     *
//     * @param context
//     * @param orientation 列表方向
//     * @param drawableId  分割线图片
//     */
//    public LinearDecoration(Context context, int orientation, @DrawableRes int drawableId) {
//        this(context, orientation);
//        mDivider = ContextCompat.getDrawable(context, drawableId);
//        mDividerHeight = mDivider.getIntrinsicHeight();
//    }

    /**
     * 自定义分割线
     *
     * @param context       context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度(dp)
     */
    public LinearDecoration(Context context, int orientation, int dividerHeight) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mDividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerHeight, context.getResources().getDisplayMetrics());
        mDividerColor = ContextCompat.getColor(context, R.color.md_grey_200);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mDividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setDividerColor(int color) {
        mDividerColor = color;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent);
        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawVertical(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {

        int childSize = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        for (int i = 0; i < childSize; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
