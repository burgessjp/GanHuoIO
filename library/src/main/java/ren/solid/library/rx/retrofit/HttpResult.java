package ren.solid.library.rx.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by _SOLID
 * Date:2016/7/27
 * Time:15:57
 */
public class HttpResult<T> {

    public boolean error;
    @SerializedName(value = "results", alternate = {"result"})
    public T results;

}
