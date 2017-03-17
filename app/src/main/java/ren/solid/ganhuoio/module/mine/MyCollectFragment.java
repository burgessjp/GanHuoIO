package ren.solid.ganhuoio.module.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.AuthorityUtils;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.widget.LinearDecoration;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:23
 */
public class MyCollectFragment extends AbsListFragment {

    public static MyCollectFragment newInstance() {
        Bundle args = new Bundle();
        MyCollectFragment fragment = new MyCollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void customConfig() {
        addItemDecoration(new LinearDecoration(getContext(), RecyclerView.VERTICAL));
    }

    @Override
    public void loadData(final int pageIndex) {
        BmobQuery<CollectTable> query = new BmobQuery<>();
        query.addWhereEqualTo("username", AuthorityUtils.getUserName());
        query.setLimit(10);
        query.setSkip(10 * (pageIndex - 1));
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


}
