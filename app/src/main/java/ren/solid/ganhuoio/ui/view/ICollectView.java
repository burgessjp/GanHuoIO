package ren.solid.ganhuoio.ui.view;

import java.util.List;

import ren.solid.ganhuoio.model.bean.bomb.CollectTable;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:14:04
 */
public interface ICollectView extends IBaseView {
    void getCollect(List<CollectTable> list);
}
