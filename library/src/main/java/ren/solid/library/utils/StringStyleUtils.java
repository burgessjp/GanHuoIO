package ren.solid.library.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:17:10
 */
public class StringStyleUtils {
    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(),
                0);
        return spannableString;
    }
}
