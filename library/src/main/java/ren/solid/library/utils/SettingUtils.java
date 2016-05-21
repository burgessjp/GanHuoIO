package ren.solid.library.utils;

import ren.solid.library.SolidApplication;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:21:54
 */
public class SettingUtils {

    public static boolean onlyWifiLoadImage() {
        return PrefUtils.getBoolean(SolidApplication.getInstance(), "onlyWifiLoadImage", false);
    }

    public static void setOnlyWifiLoadImage(boolean isEnable) {
        PrefUtils.putBoolean(SolidApplication.getInstance(), "onlyWifiLoadImage", isEnable);
    }
}
