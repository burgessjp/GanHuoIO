package ren.solid.ganhuoio.model.bean.bomb;

import cn.bmob.v3.BmobObject;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:13:32
 */
public class CollectTable extends BmobObject {

    private String desc;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private String publishedAt;
    private String username;

    public CollectTable(GanHuoDataBean bean) {
        desc = bean.getDesc();
        source = bean.getSource();
        type = bean.getType();
        url = bean.getUrl();
        used = bean.isUsed();
        who = bean.getWho();
        publishedAt = bean.getPublishedAt();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
