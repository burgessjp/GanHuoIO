package ren.solid.ganhuoio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanMeizhi;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.RxUtils;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.subscriber.HttpResultSubscriber;

/**
 * Created by _SOLID
 * Date:2016/5/21
 * Time:9:31
 */
public class MeizhiFragmant extends AbsListFragment {

    public static MeizhiFragmant newInstance() {
        Bundle args = new Bundle();
        MeizhiFragmant fragment = new MeizhiFragmant();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 1);
    }

    @Override
    public void loadData(final int pageIndex) {
        ServiceFactory.getInstance().createService(GankService.class)
                .getGanHuo("福利", pageIndex)
                .compose(RxUtils.<HttpResult<List<GanHuoDataBean>>>defaultSchedulers())
                .subscribe(new HttpResultSubscriber<List<GanHuoDataBean>>() {
                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void onSuccess(List<GanHuoDataBean> ganHuoDataBeen) {
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
                return GanHuoDataBeanMeizhi.class;
            }
        };
    }
}
