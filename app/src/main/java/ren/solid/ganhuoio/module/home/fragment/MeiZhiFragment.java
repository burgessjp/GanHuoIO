package ren.solid.ganhuoio.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.bean.GanHuoData;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.http.HttpResult;
import ren.solid.library.http.ServiceFactory;
import ren.solid.library.http.subscriber.HttpResultSubscriber;
import ren.solid.library.rx.RxUtils;

/**
 * Created by _SOLID
 * Date:2016/5/21
 * Time:9:31
 */
public class MeiZhiFragment extends AbsListFragment {

    public static MeiZhiFragment newInstance() {
        Bundle args = new Bundle();
        MeiZhiFragment fragment = new MeiZhiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void customConfig() {
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public void loadData(final int pageIndex) {
        ServiceFactory.getInstance()
                .createService(GankService.class)
                .getGanHuo("福利", pageIndex)
                .compose(this.<HttpResult<List<GanHuoData>>>bindToLifecycle())
                .compose(RxUtils.<HttpResult<List<GanHuoData>>>defaultSchedulers_single())
                .subscribe(new HttpResultSubscriber<List<GanHuoData>>() {
                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void _onSuccess(List<GanHuoData> ganHuoDataBeen) {
                        onDataSuccessReceived(pageIndex, ganHuoDataBeen);
                    }
                });
    }

    @Override
    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(getItems()) {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object item) {
                return GanHuoData.Meizhi.class;
            }
        };
    }
}
