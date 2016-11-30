package ren.solid.ganhuoio.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanMeizhi;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.TransformUtils;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import rx.Subscriber;

/**
 * Created by _SOLID
 * Date:2016/5/21
 * Time:9:31
 */
public class MeizhiFragmant extends AbsListFragment {


    @NonNull
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 1);
    }

    @Override
    public void loadData(final int pageIndex) {
        ServiceFactory.getInstance().createService(GankService.class)
                .getGanHuo("福利", pageIndex)
                .compose(TransformUtils.<HttpResult<List<GanHuoDataBean>>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<List<GanHuoDataBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void onNext(HttpResult<List<GanHuoDataBean>> listHttpResult) {
                        onDataSuccessReceived(pageIndex, listHttpResult.results);
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
