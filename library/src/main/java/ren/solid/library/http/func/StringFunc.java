package ren.solid.library.http.func;

import java.io.IOException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:20:54
 */
public class StringFunc implements Function<ResponseBody, String> {
   
    @Override
    public String apply(@NonNull ResponseBody responseBody) throws Exception {
        String result = null;
        try {
            result = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
