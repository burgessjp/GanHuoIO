package ren.solid.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

/**
 * Created by _SOLID
 * Date:2016/12/12
 * Time:17:25
 * Desc:
 */

public class WebViewUtil {
    public static void setWebViewOptions(WebView webView) {
        Context context = webView.getContext().getApplicationContext();
        //设置编码
        webView.getSettings().setDefaultTextEncodingName("UTF-8");

        //设置缓存
        webView.getSettings().setDomStorageEnabled(true); //开启DOM storage API 功能
        webView.getSettings().setDatabaseEnabled(true); //开启database storage API 功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webView.getSettings().setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //设置WebView视图大小与HTML中viewport Tag的关系
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置支持缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        File cacheFile = context.getCacheDir();
        if (cacheFile != null) {
            webView.getSettings().setAppCachePath(cacheFile.getAbsolutePath());
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        settWebViewDownloadListener(webView);
    }

    private static void settWebViewDownloadListener(final WebView webView) {
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                webView.getContext().startActivity(intent);
            }
        });
    }
}
