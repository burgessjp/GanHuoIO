package ren.solid.ganhuoio.model.bean;

/**
 * Created by _SOLID
 * Date:2016/8/3
 * Time:10:06
 */
public class GanHuoTitleBean {
    private String _id;
    private String content;
    private String publishedAt;
    private String title;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
