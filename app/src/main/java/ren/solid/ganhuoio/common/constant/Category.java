package ren.solid.ganhuoio.common.constant;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ren.solid.ganhuoio.bean.CategoryList;

/**
 * Created by _SOLID
 * Date:2016/5/16
 * Time:16:13
 */
public class Category {

    static Map<String, String> cateMaps = new ArrayMap<>();
    static String[] ganHuoCateGory = new String[]{
            "Android",
            "iOS",
            "前端",
            "拓展资源",
            "瞎推荐",
            "福利",
            "休息视频"};

    static {
        cateMaps.put("休息视频", "http://omqomam0q.bkt.clouddn.com/video.png");
        cateMaps.put("Android", "http://omqomam0q.bkt.clouddn.com/android.png");
        cateMaps.put("iOS", "http://omqomam0q.bkt.clouddn.com/ios.png");
        cateMaps.put("前端", "http://omqomam0q.bkt.clouddn.com/qianduan.png");
        cateMaps.put("拓展资源", "http://omqomam0q.bkt.clouddn.com/tuozhan.png");
        cateMaps.put("瞎推荐", "http://omqomam0q.bkt.clouddn.com/xiatuijian.png");
        cateMaps.put("福利", "http://omqomam0q.bkt.clouddn.com/meizhi.png");
    }


    public static List<CategoryList.Category> getGanHuoCateGory() {
        List<CategoryList.Category> list = new ArrayList<>();

        for (String name : ganHuoCateGory) {
            CategoryList.Category category = new CategoryList.Category();
            category.setName(name);
            category.setImgUrl(cateMaps.get(name));
            list.add(category);
        }
        return list;
    }
}
