package ren.solid.ganhuoio.model.bean;

import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:27
 */
public class GanHuoRecentlyBean {


    private List<GanHuoDataBean> Android;

    private List<GanHuoDataBean> iOS;

    private List<GanHuoDataBean> 休息视频;

    private List<GanHuoDataBean> 拓展资源;

    private List<GanHuoDataBean> 瞎推荐;

    private List<GanHuoDataBean> 福利;

    public List<GanHuoDataBean> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GanHuoDataBean> Android) {
        this.Android = Android;
    }

    public List<GanHuoDataBean> getIOS() {
        return iOS;
    }

    public void setIOS(List<GanHuoDataBean> iOS) {
        this.iOS = iOS;
    }

    public List<GanHuoDataBean> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<GanHuoDataBean> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<GanHuoDataBean> getGanHuoDataBean() {
        return 拓展资源;
    }

    public void set拓展资源(List<GanHuoDataBean> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<GanHuoDataBean> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<GanHuoDataBean> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    public List<GanHuoDataBean> get福利() {
        return 福利;
    }

    public void set福利(List<GanHuoDataBean> 福利) {
        this.福利 = 福利;
    }
}
