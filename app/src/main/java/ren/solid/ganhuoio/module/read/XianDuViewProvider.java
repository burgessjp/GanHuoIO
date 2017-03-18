package ren.solid.ganhuoio.module.read;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.XianDuItem;
import ren.solid.ganhuoio.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.DialogUtils;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.imageloader.ImageLoader;
import ren.solid.library.utils.ToastUtils;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/18
 * Time:15:02
 * Desc:
 */
public class XianDuViewProvider
        extends ItemViewProvider<XianDuItem, XianDuViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_xian_du, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final XianDuItem item) {
        holder.tv_title.setText(item.getTitle());
        holder.tv_source.setText(item.getSource());
        holder.tv_time.setText(item.getTime());
        ImageLoader.displayImage(holder.iv_avatar, item.getSourceAvatar());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.start(v.getContext(), item.getTitle(), item.getUrl());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtils.getInstance().showToast("LongClick");
                return true;
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtils.showActionDialog(v.getContext(), v
                        , new CollectTable(item.getTitle(), item.getUrl(), item.getSource()));
                return true;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_source;
        TextView tv_time;
        ImageView iv_avatar;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}