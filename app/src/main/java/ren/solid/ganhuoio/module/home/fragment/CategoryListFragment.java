package ren.solid.ganhuoio.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.bean.GanHuoData;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.http.HttpResult;
import ren.solid.library.http.ServiceFactory;
import ren.solid.library.http.subscriber.HttpResultSubscriber;
import ren.solid.library.rx.RxUtils;

/**
 * Created by _SOLID
 * Date:2016/4/19
 * Time:10:57
 */
public class CategoryListFragment extends AbsListFragment {

    private String mType;

    public static CategoryListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString("type");
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
        if (TextUtils.isEmpty(mType)) {
            showError(new Exception("mType is null"));
            return;
        }
        ServiceFactory.getInstance().createService(GankService.class)
                .getGanHuo(mType, pageIndex)
                .compose(this.<HttpResult<List<GanHuoData>>>bindToLifecycle())
                .compose(RxUtils.<HttpResult<List<GanHuoData>>>defaultSchedulers_single())
                .subscribe(new HttpResultSubscriber<List<GanHuoData>>() {
                    @Override
                    public void _onSuccess(List<GanHuoData> list) {
                        onDataSuccessReceived(pageIndex, list);
                    }

                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
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
                if (item instanceof GanHuoData) {
                    GanHuoData bean = (GanHuoData) item;
                    if (bean.getImages() != null && bean.getImages().size() > 0) {
                        return GanHuoData.Image.class;
                    } else {
                        return GanHuoData.Text.class;
                    }
                }

                return super.onFlattenClass(item);
            }
        };
    }
}
