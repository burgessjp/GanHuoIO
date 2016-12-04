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
 * Desc:闲读
 */

public class ReadingFragment extends WebViewFragment {
    private boolean jsIsLoaded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = "http://gank.io/xiandu";
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void loadStart() {
        jsIsLoaded = false;
    }

    @Override
    protected void loadJs() {

        if (!jsIsLoaded) {
            String js = "function(){\n" +
                    "  var f=document.getElementsByTagName(\"header\");\n" +
                    "  var h3=document.getElementsByTagName(\"h3\");\n" +
                    "  var content=document.getElementsByClassName(\"content\");\n" +
                    "  for(var i=0;i<f.length;i++)\n" +
                    "  {\n" +
                    "    f[i].remove();\n" +
                    "  }\n" +
                    "  h3[0].remove();\n" +
                    "  content[0].style.marginTop=0;\n" +
                    "}";
            mWebView.loadUrl("javascript:(" + js + ")()");
        }

    }
}
