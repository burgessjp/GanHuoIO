package ren.solid.ganhuoio.common;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.common.event.SortChangeEvent;
import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.rx.RxBus;
import ren.solid.library.utils.CommonUtils;
import ren.solid.library.utils.PrefUtils;
import ren.solid.library.utils.SnackBarUtils;

/**
 * Created by _SOLID
 * Date:2016/5/17
 * Time:15:52
 */
public class SortFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SortAdapter mAdapter;
    private List<String> mList;


    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_sort;
    }


    @Override
    protected void setUpView() {
        mRecyclerView = $(R.id.recyclerview);

      //  mList = Category.getGanHuoCateGory();
        mAdapter = new SortAdapter(getMContext(), mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SortCallBack(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void showTips() {
        boolean isHaveTips = PrefUtils.getBoolean(GanHuoIOApplication.getInstance(), "isHaveTips", true);
        if (isHaveTips) {
            SnackBarUtils.makeLong(mRecyclerView, "长按条目进行拖拽排序").info("我知道了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrefUtils.putBoolean(GanHuoIOApplication.getInstance(), "isHaveTips", false);
                }
            });
        }
    }

    @Override
    protected void setUpData() {

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showTips();
            }
        }, 100);
    }


    public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> implements ItemTouchHelperAdapter {

        Context context;
        List<String> beans = new ArrayList<>();

        SortAdapter(Context context, List<String> beans) {
            this.context = context;
            this.beans = beans;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_sort, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position != 0) {
                holder.iv_icon.setImageResource(R.drawable.icon_sort_item);
            } else {
                holder.iv_icon.setImageResource(R.drawable.icon_fix);
            }
            holder.tv_name.setText(beans.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.vibrate(getMContext(), 100);
                }
            });
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }


        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            if (fromPosition != 0 && toPosition != 0) {
                Collections.swap(beans, fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
                saveCategoryString();
                RxBus.getInstance().send(new SortChangeEvent());
            } else {
                SnackBarUtils.makeLong(mRecyclerView, "第一个条目不允许拖动").info();
            }
        }

        @Override
        public void onItemDismiss(int position) {
            beans.remove(position);
            notifyItemRemoved(position);
        }

        List<String> getDatas() {
            return beans;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_icon;
            TextView tv_name;

            ViewHolder(View itemView) {
                super(itemView);
                iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }

    }


    public String saveCategoryString() {
        StringBuilder builder = new StringBuilder();
        List<String> list = mAdapter.getDatas();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                builder.append(list.get(i));
                builder.append("|");
            } else {
                builder.append(list.get(i));
            }
        }
        String str = builder.toString();
        PrefUtils.putString(getMContext(), "HomeCategory", str);
        return str;
    }

    public class SortCallBack extends ItemTouchHelper.Callback {
        private ItemTouchHelperAdapter adapter = null;

        SortCallBack(ItemTouchHelperAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.onItemDismiss(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }
    }

    public interface ItemTouchHelperAdapter {
        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }

}
