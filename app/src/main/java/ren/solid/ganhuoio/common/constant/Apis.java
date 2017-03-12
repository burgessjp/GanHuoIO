package ren.solid.ganhuoio.common.constant;

import android.text.TextUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.library.utils.PrefUtils;

/**
 * Created by _SOLID
 * Date:2016/5/16
 * Time:16:13
 */
public class Apis {

    public static String[] GanHuoCateGory = new String[]{"休息视频", "Android", "iOS", "前端", "拓展资源", "瞎推荐", "福利"};
//    private final static int CATEGORY_VERSION = 2;
//
//    static {
//        if (PrefUtils.getInt(GanHuoIOApplication.getInstance(), "category_version", 0) != CATEGORY_VERSION) {
//            PrefUtils.putString(GanHuoIOApplication.getInstance(), "HomeCategory", "");
//            PrefUtils.putInt(GanHuoIOApplication.getInstance(), "category_version", CATEGORY_VERSION);
//        }
//
//    }

    public static List<String> getGanHuoCateGory() {

//        List<String> list = new ArrayList<>();
//        String prefCategory = PrefUtils.getString(GanHuoIOApplication.getInstance(), "HomeCategory", "");
//        if (TextUtils.isEmpty(prefCategory)) {
//
//            for (int i = 0; i < GanHuoCateGory.length; i++) {
//                list.add(GanHuoCateGory[i]);
//            }
//        } else {
//            String[] str = prefCategory.split("\\|");
//            for (int i = 0; i < str.length; i++) {
//                list.add(str[i]);
//            }
//        }
        return Arrays.asList(GanHuoCateGory);
    }
}
