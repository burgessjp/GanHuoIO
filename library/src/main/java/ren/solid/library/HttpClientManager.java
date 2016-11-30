package ren.solid.library;

import android.widget.ImageView;

import ren.solid.library.imageloader.ImageLoader;


/**
 * Created by _SOLID
 * Date:2016/5/14
 * Time:11:03
 */
public class HttpClientManager {

    public static void displayImage(ImageView iv, String url) {
        ImageLoader.displayImage(iv, url);
    }
}
