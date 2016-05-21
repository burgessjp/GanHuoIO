package ren.solid.ganhuoio.model.impl;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import ren.solid.ganhuoio.GanHuoIOApplication;
import ren.solid.ganhuoio.R;
import ren.solid.ganhuoio.model.ICollectModel;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.ganhuoio.utils.AuthorityUtils;
import ren.solid.library.http.callback.adapter.JsonHttpCallBack;

/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:14:07
 */
public class CollectModelImpl implements ICollectModel {
    @Override
    public void loadCollect(final JsonHttpCallBack<List<CollectTable>> callBack) {
        callBack.onStart();
        BmobQuery<CollectTable> query = new BmobQuery<>();
        query.addWhereEqualTo("username", AuthorityUtils.getUserName());
        query.order("-createdAt");
        if (AuthorityUtils.isLogin()) {
            query.findObjects(GanHuoIOApplication.getInstance(), new FindListener<CollectTable>() {
                @Override
                public void onSuccess(List<CollectTable> list) {
                    callBack.onSuccess(list);
                }

                @Override
                public void onError(int i, String s) {
                    callBack.onError(new Exception(s));
                }
            });
        } else {
            callBack.onError(new Exception(GanHuoIOApplication.getInstance().getResources().getString(R.string.no_login)));
        }
    }
}
