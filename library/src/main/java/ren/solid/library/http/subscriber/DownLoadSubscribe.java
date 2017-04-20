package ren.solid.library.http.subscriber;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import ren.solid.library.SolidApplication;
import ren.solid.library.utils.FileUtils;

/**
 * Created by _SOLID
 * Date:2016/8/1
 * Time:16:40
 */
public abstract class DownLoadSubscribe implements SingleObserver<ResponseBody> {
    private String mSaveFilePath;
    private File mFile;
    private Handler handler = new Handler(Looper.getMainLooper());
    private long fileSizeDownloaded = 0;
    private long fileSize = 0;

    public DownLoadSubscribe(@NonNull String fileName) {
        mSaveFilePath = FileUtils.getCacheDir(SolidApplication.getInstance()).getAbsolutePath();
        mFile = new File(mSaveFilePath + File.separator + fileName);
    }

    public DownLoadSubscribe(@NonNull String filePath, @NonNull String fileName) {
        mSaveFilePath = filePath;
        mFile = new File(mSaveFilePath + File.separator + fileName);
    }

    @Override
    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

    }

    /**
     * 下载进度回调
     *
     * @param progress     下载进度 0.0~1.0
     * @param downloadByte 当前已经下载的字节
     * @param totalByte    文件的总字节数
     */
    public abstract void onProgress(double progress, long downloadByte, long totalByte);

    /**
     * 下载完成回调
     *
     * @param file 下载的文件
     */
    public abstract void onCompleted(File file);

    /**
     * 下载失败的回调
     *
     * @param e error
     */
    protected abstract void onFailed(Throwable e);

    @Override
    public final void onError(final Throwable e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(e);
            }
        });
    }

    @Override
    public final void onSuccess(@io.reactivex.annotations.NonNull ResponseBody responseBody) {
        writeResponseBodyToDisk(responseBody);
        handler.post(new Runnable() {
            @Override
            public void run() {
                onCompleted(mFile);
            }
        });

    }

    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                fileSize = body.contentLength();


                inputStream = body.byteStream();
                outputStream = new FileOutputStream(mFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(fileSizeDownloaded * 1.0f / fileSize, fileSizeDownloaded, fileSize);

                        }
                    });
                }

                outputStream.flush();

                return true;
            } catch (final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onError(e);
                    }
                });

                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (final IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onError(e);
                }
            });
            return false;
        }
    }


}
