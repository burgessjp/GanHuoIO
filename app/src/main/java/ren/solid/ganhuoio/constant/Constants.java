package ren.solid.ganhuoio.constant;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:17:40
 */
public class Constants {


    // 新浪微博信息
    //For Debug
    //public static String SINA_APP_KEY = "1446282162";
    //For Release
    public static String SINA_APP_KEY = "437628447";

    public static String SINA_REDIRECT_URL = "http://fir.im/ganhuoio";
    public static String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    // 获取新浪用户信息
    public static String SINA_USER_INFO_URL = "https://api.weibo.com/2/users/show.json";
}
