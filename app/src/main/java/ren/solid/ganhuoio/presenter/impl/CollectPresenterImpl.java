package ren.solid.ganhuoio.presenter.impl;

import java.util.List;

import ren.solid.ganhuoio.model.ICollectModel;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.ganhuoio.model.impl.CollectModelImpl;
import ren.solid.ganhuoio.presenter.ICollectPreseter;
import ren.solid.ganhuoio.ui.view.ICollectView;
import ren.solid.library.http.callback.adapter.JsonHttpCallBack;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:14:13
 */
public class CollectPresenterImpl implements ICollectPreseter {


    private ICollectView view;
    private ICollectModel model;

    public CollectPresenterImpl(ICollectView view) {
        this.view = view;
        model = new CollectModelImpl();
    }

    @Override
    public void getCollect() {
        model.loadCollect(new JsonHttpCallBack<List<CollectTable>>() {
            @Override
            public void onStart() {
                view.showLoading();
            }

            @Override
            public void onSuccess(List<CollectTable> result) {
                view.hideLoading();
                view.getCollect(result);
            }

            @Override
            public void onError(Exception e) {
                view.hideLoading();
                view.showError(e.getMessage());
            }
        });
    }
}
