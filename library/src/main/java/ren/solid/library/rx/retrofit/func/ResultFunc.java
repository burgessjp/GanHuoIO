package ren.solid.library.rx.retrofit.func;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.utils.json.JsonConvert;

/**
 * Created by _SOLID
 * Date:2016/7/28
 * Time:11:04
 */
public class ResultFunc<T> implements Function<String, HttpResult<T>> {


    @Override
    public HttpResult<T> apply(@NonNull String result) throws Exception {
        JsonConvert<HttpResult<T>> convert = new JsonConvert<HttpResult<T>>() {
        };
        return convert.parseData(result);
    }
}
