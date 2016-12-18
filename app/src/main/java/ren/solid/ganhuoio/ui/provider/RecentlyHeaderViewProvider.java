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
import ren.solid.ganhuoio.model.bean.RecentlyHeader;
import ren.solid.library.activity.ViewPicActivity;
import ren.solid.library.imageloader.ImageLoader;

/**
 * Created by _SOLID
 * Date:2016/11/30
 * Time:22:23
 */
public class RecentlyHeaderViewProvider
        extends ItemViewProvider<RecentlyHeader, RecentlyHeaderViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_recently_header, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final RecentlyHeader recentlyHeader) {
        holder.tv_title.setText(recentlyHeader.getTitle());
        ImageLoader.displayImage(holder.iv_img, recentlyHeader.getImgUrl());
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPicActivity.openActivity((Activity) holder.iv_img.getContext(), holder.iv_img, recentlyHeader.getImgUrl());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_img;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}