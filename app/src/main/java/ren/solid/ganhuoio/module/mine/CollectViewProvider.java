package ren.solid.ganhuoio.module.mine;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bmob.v3.listener.DeleteListener;
import me.drakeet.multitype.ItemViewProvider;
import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.DialogUtils;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.utils.DateUtils;
import ren.solid.library.utils.SnackBarUtils;

/**
 * Created by _SOLID
 * Date:2016/12/1
 * Time:13:38
 * Desc:
 */
public class CollectViewProvider
        extends ItemViewProvider<CollectTable, CollectViewProvider.ViewHolder> {

    private RecyclerView recyclerView;

    public CollectViewProvider(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

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
        holder.tv_time.setText(DateUtils.friendlyTime(collect.getCreatedAt()));
        holder.tv_tag.setText(collect.getType());
        holder.tv_desc.setText(collect.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.start(holder.itemView.getContext()
                        , collect.getDesc(), collect.getUrl());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CollectTable deleteBean = new CollectTable();
                deleteBean.setObjectId(collect.getObjectId());
                DialogUtils.showUnDoCollectDialog(v, deleteBean, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        int position = getPosition(holder);
                        MultiTypeAdapter adapter = (MultiTypeAdapter) getAdapter();
                        adapter.getItems().remove(position);
                        recyclerView.getAdapter().notifyItemRemoved(position);
                        recyclerView.getAdapter().notifyItemChanged(position, adapter.getItemCount());
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        SnackBarUtils.makeShort(holder.itemView, "删除失败").danger();
                    }
                });
                return true;
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