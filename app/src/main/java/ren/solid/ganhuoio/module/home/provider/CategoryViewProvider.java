package ren.solid.ganhuoio.module.home.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.common.activity.SubActivity;
import ren.solid.ganhuoio.bean.CategoryList;
import ren.solid.ganhuoio.module.home.activity.CategoryActivity;
import ren.solid.library.imageloader.ImageLoader;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/12
 * Time:15:11
 * Desc:
 */
public class CategoryViewProvider
        extends ItemViewProvider<CategoryList, CategoryViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_categorys, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CategoryList categoryList) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(new SubAdapter(categoryList));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }


    static class SubAdapter extends RecyclerView.Adapter<SubAdapter.SubViewHolder> {

        CategoryList categoryList;
        LayoutInflater inflater;

        SubAdapter(CategoryList categoryList) {
            this.categoryList = categoryList;
        }

        @Override
        public SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (inflater == null) {
                inflater = LayoutInflater.from(parent.getContext());
            }
            View itemView = inflater.inflate(R.layout.item_category, parent, false);
            return new SubViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SubViewHolder holder, int position) {
            final String type = categoryList.getData().get(position).getName();
            holder.tv_title.setText(type);
            ImageLoader.displayImage(holder.iv_img, categoryList.getData().get(position).getImgUrl());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("福利".equals(type)) {
                        SubActivity.start(v.getContext(), "看妹纸", SubActivity.TYPE_MEIZHI);
                    } else {
                        CategoryActivity.start(v.getContext(), type);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryList.getData().size();
        }

        static class SubViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            ImageView iv_img;

            SubViewHolder(View itemView) {
                super(itemView);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            }
        }
    }

}