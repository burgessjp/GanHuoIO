package ren.solid.ganhuoio.ui.provider;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanMeizhi;
import ren.solid.library.activity.ViewPicActivity;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.imageloader.ImageLoader;
import ren.solid.library.utils.DateUtils;

import static ren.solid.ganhuoio.R.id.tv_people;
import static ren.solid.ganhuoio.R.id.tv_time;
import static ren.solid.ganhuoio.R.id.tv_title;

/**
 * Created by _SOLID
 * Date:2016/11/30
 * Time:11:24
 * Desc:
 */
public class MeizhiViewProvider
        extends ItemViewProvider<GanHuoDataBean, MeizhiViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final GanHuoDataBean bean) {
        ImageLoader.displayImage(holder.iv_img, bean.getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPicActivity.openActivity(holder.iv_img.getContext(), holder.iv_img, bean.getUrl());
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;

        ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}