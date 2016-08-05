package ren.solid.library.fragment.base;

import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.List;

import ren.solid.library.R;
import ren.solid.library.adapter.SolidRVBaseAdapter;
import ren.solid.library.rx.retrofit.ObservableProvider;
import ren.solid.library.utils.FileUtils;
import ren.solid.library.utils.NetworkUtils;
import ren.solid.library.utils.StringUtils;
import ren.solid.library.utils.ToastUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by _SOLID
 * Date:2016/5/12
 * Time:15:54
 */
public abstract class BaseListFragment<T> extends BaseFragment {


    protected static final int ACTION_REFRESH = 1;//刷新
    protected static final int ACTION_LOAD_MORE = 2;//加载更多
    protected static final int ACTION_PRE_LOAD = 3;//预加载数据

    protected SolidRVBaseAdapter<T> mAdapter;

    protected int mCurrentAction = ACTION_REFRESH;
    protected int mCurrentPageIndex = 1;

    @Override
    protected void init() {
        super.init();
        mAdapter = setAdapter();
    }

    /**
     * the url of request
     *
     * @param mCurrentPageIndex
     * @return
     */
    protected abstract String getUrl(int mCurrentPageIndex);

    /**
     * Parse the response data
     *
     * @param result
     * @return
     */
    protected abstract List<T> parseData(String result);


    /**
     * load data(obtain data from local if no network)
     */
    protected void loadData() {
        final String reqUrl = getUrl(mCurrentPageIndex);
        if (!NetworkUtils.isNetworkConnected(getMContext()) && mCurrentAction == ACTION_REFRESH) {//no network
            loadDataFromLocal();
            ToastUtils.getInstance().showToast(getString(R.string.no_network));

        } else {
            loadDataFromNetWork(reqUrl);
        }
    }

    private void loadDataFromNetWork(String reqUrl) {
        ObservableProvider.getDefault().loadString(reqUrl)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataErrorReceived();
                    }

                    @Override
                    public void onNext(String result) {
                        if (mCurrentAction == ACTION_REFRESH) {//store the first page data
                            storeOfflineData(getUrl(1), result);
                        }
                        onDataSuccessReceived(result);
                    }
                });

    }

    private void loadDataFromLocal() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        String result = obtainOfflineData(getUrl(1));
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        onDataSuccessReceived(result);
                    }
                });
    }

    protected void switchActionAndLoadData(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_REFRESH:
                mCurrentPageIndex = 1;
                loadData();
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                loadData();
                break;
            case ACTION_PRE_LOAD:
                loadDataFromLocal();
                break;
        }

    }

    /**
     * obtain data occur error
     */
    protected abstract void onDataErrorReceived();

    /**
     * obtain data success
     *
     * @param result
     */
    protected abstract void onDataSuccessReceived(String result);

    /**
     * set RecyclerView's Adapter
     *
     * @return
     */
    protected abstract SolidRVBaseAdapter<T> setAdapter();

    /**
     * set RecyclerView's LayoutManager
     *
     * @return
     */
    protected abstract RecyclerView.LayoutManager setLayoutManager();

    protected abstract void loadComplete();

    /**
     * store offline data
     *
     * @param url
     * @param result
     */
    protected void storeOfflineData(String url, String result) {
        try {
            FileUtils.writeFile(getOfflineDir(url), result, "UTF-8", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * obtain offline data
     *
     * @param url
     * @return
     */
    protected String obtainOfflineData(String url) {
        String result = null;
        try {
            result = FileUtils.readFile(getOfflineDir(url), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get the dir of the offline data
     *
     * @param url
     * @return
     */
    protected String getOfflineDir(String url) {
        return FileUtils.getCacheDir(getMContext()) + File.separator + "offline_gan_huo_cache" + File.separator + StringUtils.md5(url);
    }

}
