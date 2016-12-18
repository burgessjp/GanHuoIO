package ren.solid.ganhuoio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.api.SinaApiService;
import ren.solid.ganhuoio.constant.Constants;
import ren.solid.ganhuoio.event.LoginEvent;
import ren.solid.ganhuoio.model.bean.WeiboBean;
import ren.solid.ganhuoio.utils.AuthorityUtils;
import ren.solid.library.rx.RxBus;
import ren.solid.library.rx.retrofit.RxUtils;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.utils.ToastUtils;
import rx.Subscriber;

public class LoginActivity extends AppCompatActivity {

    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("登录");
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        findViewById(R.id.ll_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        mAuthInfo = new AuthInfo(this, Constants.SINA_APP_KEY, Constants.SINA_REDIRECT_URL, Constants.SINA_SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        mSsoHandler.authorize(new AuthListener());
    }

    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                ToastUtils.getInstance().showToast("登录成功");
                AuthorityUtils.setUid(mAccessToken.getUid());
                AuthorityUtils.setAccessToken(mAccessToken.getToken());
                AuthorityUtils.setRefreshToken(mAccessToken.getRefreshToken());
                AuthorityUtils.setExpiresIn(mAccessToken.getExpiresTime());
                getUserInfo();
            } else {
                ToastUtils.getInstance().showToast(values.getString("code", ""));
            }
        }

        @Override
        public void onCancel() {
            ToastUtils.getInstance().showToast("onCancel");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtils.getInstance().showToast("微博授权异常");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }


    public void getUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", AuthorityUtils.getAccessToken());
        params.put("uid", AuthorityUtils.getUid());

        SinaApiService service = ServiceFactory.getInstance().createService(SinaApiService.class);
        service.getUserInfo(AuthorityUtils.getAccessToken(), AuthorityUtils.getUid()).compose(RxUtils.<WeiboBean>defaultSchedulers())
                .subscribe(new Subscriber<WeiboBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(WeiboBean result) {


                        if (result != null) {
                            AuthorityUtils.setUserName(result.getName());
                            AuthorityUtils.login(result);
                            RxBus.getInstance().post(new LoginEvent());
                            BmobUser user = new BmobUser();
                            user.setUsername(result.getName());
                            user.setPassword("123456");
                            try {
                                user.signUp(LoginActivity.this, null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            finish();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
