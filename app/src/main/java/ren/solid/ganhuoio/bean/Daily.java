package ren.solid.ganhuoio.bean;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:16:00
 * Desc:每日干货
 */
public class Daily {

    private String desc;
    private String _id;
    private String content;
    private String created_at;
    private String publishedAt;
    private String rand_id;
    private String title;
    private String updated_at;

    public String getImgUrl() {
        int start = content.indexOf("src=\"") + 5;
        int end = content.indexOf(".jpg") + 4;
        return content.substring(start, end);
    }


    public String getDate() {
        int end = publishedAt.indexOf("T");
        return publishedAt.substring(0, end);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getRand_id() {
        return rand_id;
    }

    public void setRand_id(String rand_id) {
        this.rand_id = rand_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}