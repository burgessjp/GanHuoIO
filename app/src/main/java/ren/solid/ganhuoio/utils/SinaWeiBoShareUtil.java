package ren.solid.ganhuoio.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.common.constant.Constants;

/**
 * Created by _SOLID
 * Date:2016/5/23
 * Time:15:34
 */
public class SinaWeiBoShareUtil {


    private final Context mContext;

    private WebpageObject mWebPageObject = null;
    private TextObject mTextObject = null;
    private ImageObject mImageObject = null;

    public SinaWeiBoShareUtil(Context context) {
        this.mContext = context;
        setImageObj();
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     */
    public void sendMultiMessage() {

        // 创建微博分享接口实例
        IWeiboShareAPI mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, Constants.SINA_APP_KEY);
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();


        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (mTextObject != null) {
            weiboMessage.textObject = mTextObject;
        }

        weiboMessage.imageObject = mImageObject;


        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        if (mWebPageObject != null) {
            weiboMessage.mediaObject = mWebPageObject;
        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
//        if (hasVoice) {
//            weiboMessage.mediaObject = getVoiceObj();
//        }

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        AuthInfo authInfo = new AuthInfo(mContext, Constants.SINA_APP_KEY, Constants.SINA_REDIRECT_URL, Constants.SINA_SCOPE);
        Oauth2AccessToken accessToken = AuthorityUtils.readOauth2AccessToken();
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        mWeiboShareAPI.sendRequest((Activity) mContext, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
            }

            @Override
            public void onComplete(Bundle bundle) {
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AuthorityUtils.writeAccessToken(newToken);
            }

            @Override
            public void onCancel() {
            }

        });
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    public void setWebpageObj(String title, String url, String desc) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = desc;
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "Webpage 默认文案";
        mWebPageObject = mediaObject;
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    public void setTextObj(String textContent) {
        TextObject textObject = new TextObject();
        textObject.text = textContent;
        mTextObject = textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private void setImageObj() {
        ImageObject imageObject = new ImageObject();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo);
        imageObject.setImageObject(bitmap);
        mImageObject = imageObject;
    }
}
