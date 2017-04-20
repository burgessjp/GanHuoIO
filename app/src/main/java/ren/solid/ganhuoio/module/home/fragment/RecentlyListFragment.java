package ren.solid.ganhuoio.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.bean.DailyList;
import ren.solid.ganhuoio.bean.DailyTitle;
import ren.solid.ganhuoio.bean.GanHuoData;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.http.HttpResult;
import ren.solid.library.http.ServiceFactory;
import ren.solid.library.http.subscriber.HttpResultSubscriber;
import ren.solid.library.rx.RxUtils;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:24
 */
public class RecentlyListFragment extends AbsListFragment {


    private String date;

    public static RecentlyListFragment newInstance(int year, int month, int day) {
        RecentlyListFragment fragment = new RecentlyListFragment();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getArguments().getInt("year") + "/"
                + getArguments().getInt("month") + "/"
                + getArguments().getInt("day");
    }

    @Override
    protected void customConfig() {
        disAbleLoadMore();
        disAbleRefresh();
    }

    @Override
    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(getItems()) {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object item) {
                if (item instanceof GanHuoData) {
                    return GanHuoData.DailyItem.class;
                }
                return super.onFlattenClass(item);
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadData(final int pageIndex) {
        if (TextUtils.isEmpty(date)) {
            showError(new Exception("date is null"));
            return;
        }
        ServiceFactory.getInstance().createService(GankService.class)
                .getRecentlyGanHuo(date)
                .compose(this.<HttpResult<DailyList>>bindToLifecycle())
                .compose(RxUtils.<HttpResult<DailyList>>defaultSchedulers_single())
                .subscribe(new HttpResultSubscriber<DailyList>() {

                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void _onSuccess(DailyList recentlyBean) {
                        List list = new ArrayList<>();

                        if (recentlyBean != null) {
                            if (recentlyBean.get休息视频() != null) {
                                list.add(new DailyTitle("休息视频"));
                                list.addAll(recentlyBean.get休息视频());
                            }
                            if (recentlyBean.getAndroid() != null) {
                                list.add(new DailyTitle("Android"));
                                list.addAll(recentlyBean.getAndroid());
                            }
                            if (recentlyBean.getIOS() != null) {
                                list.add(new DailyTitle("iOS"));
                                list.addAll(recentlyBean.getIOS());
                            }
                            if (recentlyBean.get前端() != null) {
                                list.add(new DailyTitle("前端"));
                                list.addAll(recentlyBean.get前端());
                            }
                            if (recentlyBean.getApp() != null) {
                                list.add(new DailyTitle("App"));
                                list.addAll(recentlyBean.getApp());
                            }
                            if (recentlyBean.get瞎推荐() != null) {
                                list.add(new DailyTitle("瞎推荐"));
                                list.addAll(recentlyBean.get瞎推荐());
                            }


                        }
                        onDataSuccessReceived(pageIndex, list);
                    }
                });
    }

    @NonNull
    @Override
    protected String getEmptyMsg() {
        return "今日暂无干货";
    }
}
