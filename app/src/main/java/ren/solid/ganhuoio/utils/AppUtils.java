package ren.solid.ganhuoio.utils;

import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.library.utils.PrefUtils;

/**
 * Created by _SOLID
 * Date:2016/5/20
 * Time:8:42
 */
public class AppUtils {

    public static boolean isFirstRun() {
        return PrefUtils.getBoolean(GanHuoIOApplication.getInstance(), "isFirstRun", true);
    }

    public static void setFirstRun(boolean isFirstRun) {
        PrefUtils.putBoolean(GanHuoIOApplication.getInstance(), "isFirstRun", isFirstRun);
    }
}
