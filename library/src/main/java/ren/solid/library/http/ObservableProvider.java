
package ren.solid.library.http;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import ren.solid.library.http.func.ResultFunc;
import ren.solid.library.http.func.StringFunc;
import ren.solid.library.http.subscriber.DownLoadSubscribe;
import ren.solid.library.rx.RxUtils;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:9:22
 */
public class ObservableProvider {

    private CommonService mCommonService;

    private static class DefaultHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider();
    }

    private ObservableProvider() {
        mCommonService = ServiceFactory.getInstance().createService(CommonService.class);

    }

    public static ObservableProvider getDefault() {
        return DefaultHolder.INSTANCE;
    }

    public Single<String> loadString(String url) {
        return mCommonService
                .loadString(url)
                .map(new StringFunc());
    }

    public <T> Single<HttpResult<T>> loadResult(String url) {
        return loadString(url).map(new ResultFunc<T>());
    }

    public void download(String url, final DownLoadSubscribe subscribe) {
        mCommonService
                .download(url)
                .compose(RxUtils.<ResponseBody>all_io_single())
                .subscribe(subscribe);

    }


}
