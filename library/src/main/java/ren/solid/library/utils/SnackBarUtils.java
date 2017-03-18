package ren.solid.library.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by _SOLID
 * Date:2016/5/9
 * Time:11:30
 */
public class SnackBarUtils {
    private static final int color_danger = 0XFFA94442;
    private static final int color_success = 0XFF00CD00;
    private static final int color_info = 0XFF31708F;
    private static final int color_warning = 0XFFFF8247;

    private static final int color_action = 0XFFCDC5BF;

    private Snackbar mSnackBar;

    private SnackBarUtils(Snackbar snackbar) {
        mSnackBar = snackbar;
    }

    public static SnackBarUtils makeShort(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        return new SnackBarUtils(snackbar);
    }

    public static SnackBarUtils makeLong(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        return new SnackBarUtils(snackbar);
    }

    private View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;

    }


    private Snackbar setSnackBarBackColor(int colorId) {
        View snackBarView = getSnackBarLayout(mSnackBar);
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
        }
        return mSnackBar;
    }

    public void info() {
        setSnackBarBackColor(color_info);
        show();
    }

    public void info(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_info);
        show(actionText, listener);
    }

    public void warning() {
        setSnackBarBackColor(color_warning);
        show();
    }

    public void warning(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_warning);
        show(actionText, listener);
    }

    public void danger() {
        setSnackBarBackColor(color_danger);
        show();
    }

    public void danger(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_danger);
        show(actionText, listener);
    }

    public void success() {
        setSnackBarBackColor(color_success);
        show();
    }

    public void success(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_success);
        show(actionText, listener);
    }

    public void show() {
        mSnackBar.show();
    }

    public void show(String actionText, View.OnClickListener listener) {
        mSnackBar.setActionTextColor(color_action);
        mSnackBar.setAction(actionText, listener).show();
    }
}
