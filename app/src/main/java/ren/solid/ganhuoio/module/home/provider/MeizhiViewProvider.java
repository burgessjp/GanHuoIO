package ren.solid.ganhuoio.module.home.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.GanHuoData;
import ren.solid.library.activity.ViewPicActivity;
import ren.solid.library.imageloader.ImageLoader;
import ren.solid.library.utils.ViewUtils;

/**
 * Created by _SOLID
 * Date:2016/11/30
 * Time:11:24
 * Desc:
 */
public class MeizhiViewProvider
        extends ItemViewProvider<GanHuoData, MeizhiViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_meizhi, parent, false);
        DisplayMetrics displayMetrics = ViewUtils.getDisplayMetrics(GanHuoIOApplication.getInstance());
        int width = displayMetrics.widthPixels / 2;
        int height = displayMetrics.heightPixels / 2;
        ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = width;
            layoutParams.height = height;
        }
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final GanHuoData bean) {
        ImageLoader.displayImage(holder.iv_img, bean.getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPicActivity.start(holder.iv_img.getContext(), holder.iv_img, bean.getUrl());
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