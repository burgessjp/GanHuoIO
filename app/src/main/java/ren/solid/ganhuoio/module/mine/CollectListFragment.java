package ren.solid.ganhuoio.module.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.AuthorityUtils;
import ren.solid.library.fragment.base.AbsListFragment;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:23
 */
public class CollectListFragment extends AbsListFragment {

    public static CollectListFragment newInstance() {
        Bundle args = new Bundle();
        CollectListFragment fragment = new CollectListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void registerItemProvider(MultiTypeAdapter adapter) {
        adapter.register(CollectTable.class, new CollectViewProvider(mRecyclerView));
    }

    @Override
    protected void customConfig() {
        addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .color(ContextCompat.getColor(getContext(), R.color.list_divider_color))
                .sizeResId(R.dimen.list_divider_height)
                .build());
    }

    @Override
    public void loadData(final int pageIndex) {
        mPageSize = 10;
        BmobQuery<CollectTable> query = new BmobQuery<>();
        query.addWhereEqualTo("username", AuthorityUtils.getUserName());
        query.setLimit(mPageSize);
        query.setSkip(mPageSize * (pageIndex - 1));
        query.order("-createdAt");
        if (AuthorityUtils.isLogin()) {
            query.findObjects(GanHuoIOApplication.getInstance(), new FindListener<CollectTable>() {
                @Override
                public void onSuccess(List<CollectTable> list) {
                    onDataSuccessReceived(pageIndex, list);
                }

                @Override
                public void onError(int i, String s) {
                    showError(new Exception(s));
                }
            });
        } else {
            showEmpty(getString(R.string.mine_no_login));
        }
    }

    @NonNull
    @Override
    protected String getEmptyMsg() {
        return getString(R.string.tips_no_collect);
    }
}
