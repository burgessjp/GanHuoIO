package ren.solid.ganhuoio.module.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.api.XianDuService;
import ren.solid.ganhuoio.bean.XianDuItem;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.rx.retrofit.RxUtils;
import rx.Subscriber;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/18
 * Time:16:16
 * Desc:
 */

public class ReadingListFragment extends AbsListFragment {

    private String category;

    public static ReadingListFragment newInstance(String category) {

        Bundle args = new Bundle();
        args.putString("category", category);
        ReadingListFragment fragment = new ReadingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");
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
        XianDuService.getData(category, pageIndex)
                .compose(RxUtils.<List<XianDuItem>>defaultSchedulers())
                .subscribe(new Subscriber<List<XianDuItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void onNext(List<XianDuItem> list) {
                        onDataSuccessReceived(pageIndex, list);
                    }
                });
    }

    @Override
    protected int getInitPageIndex() {
        return 1;
    }
}
