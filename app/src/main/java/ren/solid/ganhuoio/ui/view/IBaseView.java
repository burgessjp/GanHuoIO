package ren.solid.ganhuoio.ui.view;

/**
 * Created by _SOLID
 * Date:2016/5/18
 * Time:14:55
 */
public interface IBaseView {

    void showLoading();

    void hideLoading();

    void showError(String errMsg);
}
