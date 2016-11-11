package ren.solid.ganhuoio.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.Collections;
import java.util.List;

import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.event.SortChangeEvent;
import ren.solid.library.adapter.SolidRVBaseAdapter;
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

        mList = Apis.getGanHuoCateGory();
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


    public class SortAdapter extends SolidRVBaseAdapter<String> implements ItemTouchHelperAdapter {

        public SortAdapter(Context context, List<String> beans) {
            super(context, beans);
        }

        @Override
        protected void onBindDataToView(SolidCommonViewHolder holder, String bean, int position) {
            if (position != 0) {
                holder.setImage(R.id.iv_icon, R.drawable.icon_sort_item);
            } else {
                holder.setImage(R.id.iv_icon, R.drawable.icon_fix);
            }
            holder.setText(R.id.tv_name, bean);
        }

        @Override
        public int getItemLayoutID(int viewType) {
            return R.layout.item_sort;
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            if (fromPosition != 0 && toPosition != 0) {
                Collections.swap(mBeans, fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
                saveCategoryString();
                RxBus.getInstance().post(new SortChangeEvent());
            } else {
                SnackBarUtils.makeLong(mRecyclerView, "第一个条目不允许拖动").info();
            }
        }

        @Override
        protected void onItemLongClick(int position) {
            super.onItemLongClick(position);
            CommonUtils.vibrate(getMContext(), 100);
        }

        @Override
        public void onItemDismiss(int position) {
            mBeans.remove(position);
            notifyItemRemoved(position);
        }

    }


    public String saveCategoryString() {
        StringBuilder builder = new StringBuilder();
        List<String> list = mAdapter.getDatas();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                builder.append(list.get(i) + "|");
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

        public SortCallBack(ItemTouchHelperAdapter adapter) {
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
