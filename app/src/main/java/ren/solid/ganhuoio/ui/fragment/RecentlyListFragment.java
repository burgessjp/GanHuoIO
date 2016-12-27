package ren.solid.ganhuoio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import ren.solid.ganhuoio.api.GankService;
import ren.solid.ganhuoio.model.bean.GanHuoDataBean;
import ren.solid.ganhuoio.model.bean.GanHuoRecentlyBean;
import ren.solid.ganhuoio.model.bean.Recently;
import ren.solid.ganhuoio.model.bean.RecentlyHeader;
import ren.solid.ganhuoio.model.bean.RecentlyTitle;
import ren.solid.library.fragment.base.AbsListFragment;
import ren.solid.library.rx.retrofit.HttpResult;
import ren.solid.library.rx.retrofit.RxUtils;
import ren.solid.library.rx.retrofit.factory.ServiceFactory;
import ren.solid.library.rx.retrofit.subscriber.HttpResultSubscriber;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:15:24
 */
public class RecentlyListFragment extends AbsListFragment {

    public static final String DATE_STRING = "dateString";
    public static final String TITLE = "fragment_index";

    private String date;

    private RecentlyHeader mRecentlyHeader;

    public static RecentlyListFragment newInstance(String date, String title) {
        Bundle args = new Bundle();
        args.putString(RecentlyListFragment.DATE_STRING, date);
        args.putString(RecentlyListFragment.TITLE, title);
        RecentlyListFragment fragment = new RecentlyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getArguments().getString(DATE_STRING);
        if (!TextUtils.isEmpty(date)) {
            date = date.replace('-', '/');
        }
        String title = getArguments().getString(TITLE);
        mRecentlyHeader = new RecentlyHeader();
        mRecentlyHeader.setTitle(title);
    }

    @Override
    protected void customConfig() {
        disAbleLoadMore();
    }

    @Override
    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(getItems()) {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object item) {
                if (item instanceof GanHuoDataBean) {
                    return Recently.class;
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
                .compose(RxUtils.<HttpResult<GanHuoRecentlyBean>>defaultSchedulers())
                .subscribe(new HttpResultSubscriber<GanHuoRecentlyBean>() {

                    @Override
                    public void _onError(Throwable e) {
                        showError(new Exception(e));
                    }

                    @Override
                    public void onSuccess(GanHuoRecentlyBean recentlyBean) {
                        List list = new ArrayList<>();

                        if (recentlyBean != null) {
                            if (recentlyBean.get福利() != null) {
                                mRecentlyHeader.setImgUrl(recentlyBean.get福利().get(0).getUrl());
                                list.add(mRecentlyHeader);
                            }
                            if (recentlyBean.get休息视频() != null) {
                                list.add(new RecentlyTitle("休息视频"));
                                list.addAll(recentlyBean.get休息视频());
                            }
                            if (recentlyBean.getAndroid() != null) {
                                list.add(new RecentlyTitle("Android"));
                                list.addAll(recentlyBean.getAndroid());
                            }
                            if (recentlyBean.getIOS() != null) {
                                list.add(new RecentlyTitle("iOS"));
                                list.addAll(recentlyBean.getIOS());
                            }
                            if (recentlyBean.get前端() != null) {
                                list.add(new RecentlyTitle("前端"));
                                list.addAll(recentlyBean.get前端());
                            }
                            if (recentlyBean.get瞎推荐() != null) {
                                list.add(new RecentlyTitle("瞎推荐"));
                                list.addAll(recentlyBean.get瞎推荐());
                            }


                        }
                        onDataSuccessReceived(pageIndex, list);
                    }
                });
    }
}
