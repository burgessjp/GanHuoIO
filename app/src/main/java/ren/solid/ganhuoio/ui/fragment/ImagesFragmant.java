package ren.solid.ganhuoio.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.library.activity.ViewPicActivity;
import ren.solid.library.adapter.SolidRVBaseAdapter;
import ren.solid.library.fragment.XRecyclerViewFragment;
import ren.solid.library.http.HttpClientManager;

/**
 * Created by _SOLID
 * Date:2016/5/21
 * Time:9:31
 */
public class ImagesFragmant extends XRecyclerViewFragment {
    @Override
    protected String getUrl(int mCurrentPageIndex) {
        String url = Apis.Urls.GanHuoData + "福利/10/" + mCurrentPageIndex;
        return url;
    }

    @Override
    protected List parseData(String result) {
        List<GanHuoDataBean> list = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            Gson gson = new Gson();
            list = gson.fromJson(
                    jsonObject.getString("results"),
                    new TypeToken<List<GanHuoDataBean>>() {
                    }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    protected SolidRVBaseAdapter setAdapter() {
        return new SolidRVBaseAdapter<GanHuoDataBean>(getMContext(), new ArrayList<GanHuoDataBean>()) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, GanHuoDataBean bean, int position) {
                ImageView imageView = holder.getView(R.id.iv_img);
                HttpClientManager.displayImage(imageView, bean.getUrl());
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_image;
            }

            @Override
            protected void onItemClick(int position) {
                String url = mBeans.get(position - 1).getUrl();
                ArrayList<String> images = new ArrayList<String>();
                images.add(url);
                Intent intent = new Intent(getMContext(), ViewPicActivity.class);
                intent.putStringArrayListExtra(ViewPicActivity.IMG_URLS, images);
                getMContext().startActivity(intent);
            }
        };
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
}
