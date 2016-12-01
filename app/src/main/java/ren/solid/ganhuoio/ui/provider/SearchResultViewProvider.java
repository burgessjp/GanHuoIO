package ren.solid.ganhuoio.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewProvider;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.bean.SearchResult;
import ren.solid.library.activity.WebViewActivity;
import ren.solid.library.utils.DateUtils;
import ren.solid.library.utils.StringStyleUtils;

/**
 * Created by _SOLID
 * Date:2016/12/1
 * Time:13:13
 * Desc:
 */
public class SearchResultViewProvider
        extends ItemViewProvider<SearchResult, SearchResultViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull final ViewHolder holder, @NonNull final SearchResult searchResult) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(
                StringStyleUtils.format(holder.tv_title.getContext(), "[" + searchResult.getType() +
                        "]", R.style.ByTextAppearance));
        builder.append(searchResult.getDesc());
        holder.tv_title.setText(builder.subSequence(0, builder.length()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.openActivity(holder.itemView.getContext()
                        , searchResult.getDesc(), searchResult.getUrl());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}