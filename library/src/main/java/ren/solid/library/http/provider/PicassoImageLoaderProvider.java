package ren.solid.library.http.provider;

import android.content.Context;

import com.squareup.picasso.Picasso;

import ren.solid.library.R;
import ren.solid.library.SolidApplication;
import ren.solid.library.http.provider.base.IImageLoaderProvider;
import ren.solid.library.http.request.ImageRequest;
import ren.solid.library.utils.SettingUtils;

/**
 * Created by _SOLID
 * Date:2016/5/13
 * Time:10:27
 */
public class PicassoImageLoaderProvider implements IImageLoaderProvider {
    @Override
    public void loadImage(ImageRequest request) {
        if (!SettingUtils.onlyWifiLoadImage()) {
            Picasso.with(SolidApplication.getInstance()).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
        } else {
            request.getImageView().setImageResource(R.drawable.default_load_img);
        }
    }

    @Override
    public void loadImage(Context context, ImageRequest request) {
        if (!SettingUtils.onlyWifiLoadImage()) {
            Picasso.with(context).load(request.getUrl()).placeholder(request.getPlaceHolder()).into(request.getImageView());
        } else {
            request.getImageView().setImageResource(R.drawable.default_load_img);
        }
    }
}
