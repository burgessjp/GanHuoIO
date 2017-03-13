package ren.solid.ganhuoio.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ren.solid.ganhuoio.R;
import ren.solid.library.activity.ViewPicActivity;
import ren.solid.library.imageloader.ImageLoader;

/**
 * Created by _SOLID
 * Date:2016/6/2
 * Time:17:32
 */
public class PictureDialog extends Dialog {

    private ImageView mImageView;

    public PictureDialog(Context context) {
        super(context);
    }

    public PictureDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PictureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picture);
        setCanceledOnTouchOutside(true);
        mImageView = (ImageView) findViewById(R.id.iv_picture);
    }

    public void setPicture(final String url) {
        show();
        ImageLoader.displayImage(mImageView, url);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPicActivity.start(view.getContext(), view, url);
            }
        });

    }


}
