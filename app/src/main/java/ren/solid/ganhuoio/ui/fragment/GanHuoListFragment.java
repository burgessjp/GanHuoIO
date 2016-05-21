package ren.solid.ganhuoio.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.AuthorityUtils;
import ren.solid.ganhuoio.utils.DialogUtils;
import ren.solid.library.activity.ViewPicActivity;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.adapter.SolidRVBaseAdapter;
import ren.solid.library.fragment.XRecyclerViewFragment;
import ren.solid.library.http.HttpClientManager;
import ren.solid.library.utils.DateUtils;
import ren.solid.library.utils.Logger;

/**
 * Created by _SOLID
 * Date:2016/4/19
 * Time:10:57
 */
public class GanHuoListFragment extends XRecyclerViewFragment {

    private static String TAG = "GanHuoListFragment";
    private String mType;

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
    protected String getUrl(int mCurrentPageIndex) {
        mType = getArguments().getString("type");
        String url = Apis.Urls.GanHuoData + mType + "/10/" + mCurrentPageIndex;
        Log.i(TAG, url);
        return url;
    }

    @Override
    protected SolidRVBaseAdapter setAdapter() {
        return new SolidRVBaseAdapter<GanHuoDataBean>(getMContext(), new ArrayList<GanHuoDataBean>()) {
            @Override
            protected void onBindDataToView(final SolidCommonViewHolder holder, final GanHuoDataBean bean, int position) {
                holder.getView(R.id.tv_desc).setVisibility(View.GONE);
                holder.getView(R.id.iv_img).setVisibility(View.GONE);
                holder.getView(R.id.tv_tag).setVisibility(View.GONE);
                holder.getView(R.id.iv_action).setVisibility(View.VISIBLE);
                holder.getView(R.id.iv_action).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showActionPopWindow(mContext, v, new CollectTable(bean));
                    }
                });

                if (isImage(bean.getUrl())) {
                    holder.getView(R.id.iv_action).setVisibility(View.GONE);//如果是图片则隐藏Action
                    holder.getView(R.id.iv_img).setVisibility(View.VISIBLE);
                    ImageView imageView = holder.getView(R.id.iv_img);
                    HttpClientManager.displayImage(imageView, bean.getUrl());
                } else {
                    holder.getView(R.id.tv_desc).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_desc, bean.getDesc());
                }
                if (mType.equals("all")) {
                    holder.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
                }

                String date = bean.getPublishedAt().replace('T', ' ').replace('Z', ' ');
                holder.setText(R.id.tv_source, bean.getSource());
                holder.setText(R.id.tv_people, "by " + bean.getWho());
                holder.setText(R.id.tv_time, DateUtils.friendlyTime(date));
                holder.setText(R.id.tv_tag, bean.getType());


                holder.getView(R.id.iv_collected).setVisibility(View.GONE);

                BmobQuery<CollectTable> query = new BmobQuery<>();
                query.addWhereEqualTo("username", AuthorityUtils.getUserName());
                query.addWhereEqualTo("url", bean.getUrl());
                query.findObjects(GanHuoIOApplication.getInstance(), new FindListener<CollectTable>() {
                    @Override
                    public void onSuccess(List<CollectTable> list) {
                        if (list.size() > 0)
                            holder.getView(R.id.iv_collected).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Logger.i("onError:" + s + " code:" + i);
                    }
                });

            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_ganhuo;
            }

            @Override
            protected void onItemClick(int position) {
                String url = mBeans.get(position - 1).getUrl();
                ArrayList<String> images = new ArrayList<String>();
                images.add(url);
                if (!isImage(url)) {
                    Intent intent = new Intent(getMContext(), WebViewActivity.class);
                    intent.putExtra(WebViewActivity.WEB_URL, url);
                    intent.putExtra(WebViewActivity.TITLE, mBeans.get(position - 1).getDesc());
                    getMContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(getMContext(), ViewPicActivity.class);
                    intent.putStringArrayListExtra(ViewPicActivity.IMG_URLS, images);
                    getMContext().startActivity(intent);
                }
            }
        };
    }

    public boolean isImage(String url) {
        return url.endsWith(".jpg") || url.endsWith(".png");
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {
        return new LinearLayoutManager(getMContext());
    }
}
