package ren.solid.ganhuoio.common.recevier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Calendar;

import cn.bmob.push.PushConstants;
import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.library.activity.WebViewActivity;

/**
 * Created by _SOLID
 * Date:2016/6/2
 * Time:15:41
 */
public class BombPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            String msg = intent.getStringExtra("msg");
            Gson gson = new Gson();
            Message message = gson.fromJson(msg, Message.class);
            if (!TextUtils.isEmpty(message.getTitle()) &&
                    !TextUtils.isEmpty(message.getContent())) {
                showNotification(context, message);
            }
        }
    }

    private void showNotification(Context context, Message message) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.logo)
                        .setContentTitle(message.getTitle())
                        .setContentText(message.getContent())
                        .setTicker(message.getContent());
        if (!TextUtils.isEmpty(message.getUrl())) {
            Intent resultIntent = new Intent(context, WebViewActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            resultIntent.putExtra(WebViewActivity.WEB_URL, message.getUrl());
            resultIntent.putExtra(WebViewActivity.TITLE, message.getTitle());
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(GanHuoIOApplication.getInstance(), 0, resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }

        builder.setAutoCancel(true);

        int mNotificationId = Calendar.getInstance().get(Calendar.SECOND)
                + Calendar.getInstance().get(Calendar.MILLISECOND);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        NotificationManager notifyManager =
                (NotificationManager) GanHuoIOApplication.getInstance()
                        .getSystemService(
                                Context.NOTIFICATION_SERVICE);
        notifyManager.notify(mNotificationId, notification);
    }

    class Message {
        private String title;
        private String content;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
