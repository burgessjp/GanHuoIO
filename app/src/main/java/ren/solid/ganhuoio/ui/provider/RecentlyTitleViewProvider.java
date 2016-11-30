package ren.solid.ganhuoio.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.RecentlyTitle;

/**
 * Created by _SOLID
 * Date:2016/11/30
 * Time:22:20
 */
public class RecentlyTitleViewProvider
        extends ItemViewProvider<RecentlyTitle, RecentlyTitleViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_recently_title, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull ViewHolder holder, @NonNull RecentlyTitle recentlyTitle) {
        holder.tv_title.setText(recentlyTitle.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}