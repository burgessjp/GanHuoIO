package ren.solid.ganhuoio.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.utils.DateUtils;

/**
 * Created by _SOLID
 * Date:2016/12/1
 * Time:13:38
 * Desc:
 */
public class CollectViewProvider
        extends ItemViewProvider<CollectTable, CollectViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_collect, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final CollectTable collect) {
        String date = collect.getCreatedAt();//bean.getPublishedAt().replace('T', ' ').replace('Z', ' ');

        holder.tv_people.setText("via " + collect.getWho());
        holder.tv_time.setText("收藏时间:" + DateUtils.friendlyTime(date));
        holder.tv_tag.setText(collect.getType());
        holder.tv_desc.setText(collect.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.openActivity(holder.itemView.getContext()
                        , collect.getDesc(), collect.getUrl());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_people;
        TextView tv_time;
        TextView tv_tag;
        TextView tv_desc;

        ViewHolder(View itemView) {
            super(itemView);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_people = (TextView) itemView.findViewById(R.id.tv_people);
        }
    }
}