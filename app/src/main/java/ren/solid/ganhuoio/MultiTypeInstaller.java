package ren.solid.ganhuoio;

import me.drakeet.multitype.GlobalMultiTypePool;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanImage;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanMeizhi;
import ren.solid.ganhuoio.model.bean.GanHuoDataBeanText;
import ren.solid.ganhuoio.model.bean.Recently;
import ren.solid.ganhuoio.model.bean.RecentlyHeader;
import ren.solid.ganhuoio.model.bean.RecentlyTitle;
import ren.solid.ganhuoio.model.bean.SearchResult;
import ren.solid.ganhuoio.model.bean.bomb.CollectTable;
import ren.solid.ganhuoio.ui.provider.CollectViewProvider;
import ren.solid.ganhuoio.ui.provider.GanHuoImageViewProvider;
import ren.solid.ganhuoio.ui.provider.GanHuoTextViewProvider;
import ren.solid.ganhuoio.ui.provider.MeizhiViewProvider;
import ren.solid.ganhuoio.ui.provider.RecentlyHeaderViewProvider;
import ren.solid.ganhuoio.ui.provider.RecentlyTitleViewProvider;
import ren.solid.ganhuoio.ui.provider.RecentlyViewProvider;
import ren.solid.ganhuoio.ui.provider.SearchResultViewProvider;

/**
 * Created by _SOLID
 * Date:2016/12/1
 * Time:13:06
 * Desc:
 */

public class MultiTypeInstaller {
    static void install() {
        GlobalMultiTypePool.register(GanHuoDataBeanText.class, new GanHuoTextViewProvider());
        GlobalMultiTypePool.register(GanHuoDataBeanImage.class, new GanHuoImageViewProvider());
        GlobalMultiTypePool.register(GanHuoDataBeanMeizhi.class, new MeizhiViewProvider());
        GlobalMultiTypePool.register(Recently.class, new RecentlyViewProvider());
        GlobalMultiTypePool.register(RecentlyTitle.class, new RecentlyTitleViewProvider());
        GlobalMultiTypePool.register(RecentlyHeader.class, new RecentlyHeaderViewProvider());
        GlobalMultiTypePool.register(SearchResult.class, new SearchResultViewProvider());
        GlobalMultiTypePool.register(CollectTable.class, new CollectViewProvider());

    }
}
