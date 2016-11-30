package ren.solid.library.fragment.base;

/**
 * Created by _SOLID
 * Date:2016/9/29
 * Time:9:28
 * Desc:列表接口
 */

public interface IList {

    void loadData(int pageIndex);

    void refreshData();

    void loadMore();

    void showError(Exception e);

    void showLoading();

    void showEmpty(String msg);

    void showContent();
}
