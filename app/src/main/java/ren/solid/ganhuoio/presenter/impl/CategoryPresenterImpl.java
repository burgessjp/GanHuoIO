package ren.solid.ganhuoio.presenter.impl;

import ren.solid.ganhuoio.model.ICategoryModel;
import ren.solid.ganhuoio.model.impl.CategoryModelImpl;
import ren.solid.ganhuoio.presenter.ICategoryPresenter;
import ren.solid.ganhuoio.ui.view.ICategoryView;

/**
 * Created by _SOLID
 * Date:2016/5/17
 * Time:10:33
 */
public class CategoryPresenterImpl implements ICategoryPresenter {

    ICategoryView mIHomeView;
    ICategoryModel mIHomeModel;

    public CategoryPresenterImpl(ICategoryView homeView) {
        mIHomeView = homeView;
        mIHomeModel = new CategoryModelImpl();
    }

    @Override
    public void getAdapterData() {
        mIHomeView.showLoading();
        mIHomeView.setPagerAdapterData(mIHomeModel.loadCateGory());
        mIHomeView.hideLoading();
    }
}
