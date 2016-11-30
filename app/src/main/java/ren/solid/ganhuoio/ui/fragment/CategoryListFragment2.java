package ren.solid.ganhuoio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanImage;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanText;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.TransformUtils;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.widget.LinearDecoration;
import rx.Subscriber;

/**
 * Created by _SOLID
 * Date:2016/4/19
 * Time:10:57
 */
public class CategoryListFragment2 extends AbsListFragment {

    private String mType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString("type");
    }


    @Override
    protected void customConfig() {
        addItemDecoration(new LinearDecoration(getContext(), RecyclerView.VERTICAL, 5));
    }

    @Override
    public void loadData(final int pageIndex) {
        ServiceFactory.getInstance().createService(GankService.class)
                .getGanHuo(mType, pageIndex)
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
    protected int getInitPageIndex() {
        return 1;
    }

    @Override
    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(getItems()) {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object item) {
                if (item instanceof GanHuoDataBean) {
                    GanHuoDataBean bean = (GanHuoDataBean) item;
                    if (bean.getImages() != null && bean.getImages().size() > 0) {
                        return GanHuoDataBeanImage.class;
                    } else {
                        return GanHuoDataBeanText.class;
                    }
                }

                return super.onFlattenClass(item);
            }
        };
    }
}
