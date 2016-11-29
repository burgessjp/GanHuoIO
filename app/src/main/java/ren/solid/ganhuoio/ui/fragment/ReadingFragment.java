package ren.solid.ganhuoio.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import ren.solid.library.fragment.WebViewFragment;

/**
 * Created by _SOLID
 * Date:2016/11/29
 * Time:17:01
 * Desc:
 */

public class ReadingFragment extends WebViewFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = "http://gank.io/xiandu";

    }

    @Override
    protected void setUpData() {
        mWebView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPageLoadFinished(WebView view, String url) {

        String js ="function(){\n" +
                "  var f=document.getElementsByTagName(\"header\");\n" +
                "  var content=document.getElementsByClassName(\"content\");\n" +
                "  for(var i=0;i<f.length;i++)\n" +
                "  {\n" +
                "    f[i].remove();\n" +
                "  }\n" +
                "  content.style.marginTop=0;\n" +
                "}";
        mWebView.loadUrl("javascript:(" + js + ")()");
        mWebView.setVisibility(View.VISIBLE);

    }
}
