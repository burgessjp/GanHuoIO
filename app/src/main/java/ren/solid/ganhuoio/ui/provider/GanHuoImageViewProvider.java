package ren.solid.ganhuoio.ui.provider;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.DialogUtils;
import ren.solid.library.activity.ViewPicActivity;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.imageloader.ImageLoader;
import ren.solid.library.utils.DateUtils;

/**
 * Created by _SOLID
 * Date:2016/11/30
 * Time:11:22
 * Desc:
 */
public class GanHuoImageViewProvider
        extends ItemViewProvider<GanHuoDataBean, GanHuoImageViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_ganhuo_image, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final GanHuoDataBean bean) {
        final Context context = holder.itemView.getContext();
        String date = bean.getPublishedAt().replace('T', ' ').replace('Z', ' ');
        holder.tv_title.setText(bean.getDesc());
        holder.tv_time.setText(DateUtils.friendlyTime(date));
        holder.tv_people.setText("via " + bean.getWho());
        holder.viewpager.setAdapter(new VPAdapter(holder.viewpager.getContext(), bean.getImages()));

        holder.rl_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.openActivity(context, bean.getDesc(), bean.getUrl());
            }
        });
        holder.rl_bottom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogUtils.showActionPopWindow(view.getContext(), holder.itemView, new CollectTable(bean));
                return false;
            }
        });

    }

    class VPAdapter extends PagerAdapter {
        List<String> images;
        Context context;

        public VPAdapter(Context context, List<String> images) {
            this.images = images;
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImageLoader.displayImage(imageView, images.get(position));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewPicActivity.openActivity((Activity) context, imageView, (ArrayList<String>) images, 0);
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_people;
        TextView tv_time;
        ViewPager viewpager;
        View rl_bottom;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_people = (TextView) itemView.findViewById(R.id.tv_people);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            viewpager = (ViewPager) itemView.findViewById(R.id.viewpager);
            rl_bottom = itemView.findViewById(R.id.rl_bottom);
        }
    }
}