package ren.solid.ganhuoio.module.home.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.GanHuoData;
import ren.solid.ganhuoio.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.DialogUtils;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.utils.DateUtils;

/**
 * Created by _SOLID
 * Date:2016/11/30
 * Time:11:24
 * Desc:
 */
public class GanHuoTextViewProvider
        extends ItemViewProvider<GanHuoData, GanHuoTextViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_ganhuo_text, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final GanHuoData bean) {
        String date = bean.getPublishedAt().replace('T', ' ').replace('Z', ' ');
        holder.tv_title.setText(bean.getDesc());
        holder.tv_time.setText(DateUtils.friendlyTime(date));
        holder.tv_people.setText("via " + bean.getWho());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.start(holder.itemView.getContext(), bean.getDesc(), bean.getUrl());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogUtils.showActionDialog(view.getContext(), holder.itemView
                        , new CollectTable(bean.getDesc(), bean.getUrl(), bean.getType()));
                return false;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_people;
        TextView tv_time;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_people = (TextView) itemView.findViewById(R.id.tv_people);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}