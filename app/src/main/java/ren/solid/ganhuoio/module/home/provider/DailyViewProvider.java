package ren.solid.ganhuoio.module.home.provider;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.Daily;
import ren.solid.ganhuoio.module.home.activity.DailyActivity;
import ren.solid.library.imageloader.ImageLoader;
import ren.solid.library.utils.DateUtils;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:16:00
 * Desc:
 */
public class DailyViewProvider
        extends ItemViewProvider<Daily, DailyViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_daily, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final Daily daily) {
        boolean isToday = DateUtils.isToday(DateUtils.parseDate(daily.getDate()));
        String showDate = isToday ? "#今日推荐" : "#" + daily.getDate();
        ImageLoader.displayImage(holder.iv_img, daily.getImgUrl());
        holder.iv_img.setColorFilter(Color.parseColor("#5e000000"));
        holder.tv_date.setText(showDate);
        holder.tv_desc.setText(daily.getTitle().replace("今日力推：", ""));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailyActivity.start((Activity) v.getContext(), v, daily.getTitle(), daily.getDate(), daily.getImgUrl());
            }
        });

        holder.itemView.setOnTouchListener(new OnTapListener(holder));

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView tv_date;
        TextView tv_desc;

        ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);

        }
    }

    static class OnTapListener implements View.OnTouchListener {
        ViewHolder holder;

        public OnTapListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    holder.iv_img.setColorFilter(null);
                    hide(holder.tv_date);
                    hide(holder.tv_desc);
                    View parent = (View) holder.itemView.getParent();
                    if (parent != null)
                        parent.setOnTouchListener(this);
                    break;
                case MotionEvent.ACTION_UP:
                    holder.iv_img.setColorFilter(Color.parseColor("#5e000000"));
                    show(holder.tv_date);
                    show(holder.tv_desc);
                    break;

            }
            return false;
        }

        void show(View v) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
            alpha.setDuration(300);
            alpha.start();
        }

        void hide(View v) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1, 0);
            alpha.setDuration(300);
            alpha.start();
        }
    }
}