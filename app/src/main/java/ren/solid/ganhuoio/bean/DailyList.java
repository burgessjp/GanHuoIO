package ren.solid.ganhuoio.bean;

import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:27
 */
public class DailyList {


    private List<GanHuoData> Android;
    private List<GanHuoData> iOS;
    private List<GanHuoData> 休息视频;
    private List<GanHuoData> 拓展资源;
    private List<GanHuoData> 瞎推荐;
    private List<GanHuoData> 福利;
    private List<GanHuoData> App;
    private List<GanHuoData> 前端;

    public List<GanHuoData> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GanHuoData> Android) {
        this.Android = Android;
    }

    public List<GanHuoData> getIOS() {
        return iOS;
    }

    public void setIOS(List<GanHuoData> iOS) {
        this.iOS = iOS;
    }

    public List<GanHuoData> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<GanHuoData> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<GanHuoData> getGanHuoDataBean() {
        return 拓展资源;
    }

    public void set拓展资源(List<GanHuoData> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<GanHuoData> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<GanHuoData> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    public List<GanHuoData> get福利() {
        return 福利;
    }

    public void set福利(List<GanHuoData> 福利) {
        this.福利 = 福利;
    }

    public List<GanHuoData> get前端() {
        return 前端;
    }

    public void set前端(List<GanHuoData> 前端) {
        this.前端 = 前端;
    }

    public List<GanHuoData> getApp() {
        return App;
    }

    public void setApp(List<GanHuoData> app) {
        App = app;
    }
}
