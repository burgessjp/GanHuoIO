package ren.solid.ganhuoio.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.bean.RandomPictureBean;
import ren.solid.ganhuoio.ui.dialog.PictureDialog;
import ren.solid.library.http.HttpClientManager;
import ren.solid.library.http.callback.adapter.JsonHttpCallBack;
import ren.solid.library.utils.Logger;
import ren.solid.library.utils.ToastUtils;

/**
 * Created by _SOLID
 * Date:2016/6/3
 * Time:9:55
 */
public class ShakePictureUtils implements SensorEventListener {

    // 两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 100;
    // 加速度变化阈值，当摇晃速度达到这值后产生作用
    private static final int SPEED_THRESHOLD = 2000;

    private Context mContext;
    private SensorManager mSensorManager = null;
    private Vibrator mVibrator = null;
    private PictureDialog mPictureDialog = null;


    public ShakePictureUtils(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mPictureDialog = new PictureDialog(mContext, R.style.PictureDialog);
        mPictureDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                registerSensor();
            }
        });
    }


    private long lastUpdateTime;
    private float lastX;
    private float lastY;
    private float lastZ;

    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();

        long timeInterval = currentUpdateTime - lastUpdateTime;

        if (timeInterval < UPTATE_INTERVAL_TIME) {
            return;
        }

        lastUpdateTime = currentUpdateTime;
        float[] values = event.values;

        // 获得x,y,z加速度
        float x = values[0];
        float y = values[1];
        float z = values[2];

        // 获得x,y,z加速度的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        // 将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;


        Logger.i("values[0] = " + values[0]);
        Logger.i("values[1] = " + values[1]);
        Logger.i("values[2] = " + values[2]);

        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)
                / timeInterval * 10000;
        Logger.i("speed:" + speed);
        if (speed > SPEED_THRESHOLD && AppUtils.shakePicture()) {
            //在这里可以提供一个回调
            mVibrator.vibrate(300);
            requestPicture();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void registerSensor() {
        Sensor sensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (null != sensor)
            mSensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegisterSensor() {

        lastX = 0;
        lastY = 0;
        lastZ = 0;
        mSensorManager.unregisterListener(this);
    }


    private void requestPicture() {
        HttpClientManager.getData(Apis.Urls.RandomPicture, new JsonHttpCallBack<RandomPictureBean>() {
            @Override
            public void onStart() {
                unRegisterSensor();
                ToastUtils.getInstance().showToast("图片获取中...");
            }

            @Override
            public void onSuccess(RandomPictureBean result) {
                mPictureDialog.setPicture(result.getP_ori());
            }

            @Override
            public void onError(Exception e) {
                registerSensor();
            }
        });
    }
}
