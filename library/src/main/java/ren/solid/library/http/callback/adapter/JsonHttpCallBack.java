package ren.solid.library.http.callback.adapter;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import ren.solid.library.http.callback.HttpCallBack;

/**
 * Created by _SOLID
 * Date:2016/5/13
 * Time:11:39
 */
public abstract class JsonHttpCallBack<T> extends HttpCallBack<T> {

   // public enum DataType {ARRAY, OBJECT}

    @Override
    public T parseData(String result) {
        T t = null;
        try {
            Type trueType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Gson gson = new Gson();
            if (!TextUtils.isEmpty(getDataName())) {
                JSONObject jsonObject = new JSONObject(result);
                t = gson.fromJson(jsonObject.getString(getDataName()), trueType);
            } else {
                t = gson.fromJson(result, trueType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 在数组中要获取的name（当数据类型为集合时）
     *
     * @return data_name
     */
    public String getDataName() {
        return null;
    }

//    /***
//     * 数据的类型（是集合型的还是只是一个对象）
//     *
//     * @return data_type
//     */
//    public abstract DataType getDataType();

//    /***
//     * 获取泛型的Type
//     *
//     * @return 泛型 Type
//     */
//    public abstract Type getType();
}
