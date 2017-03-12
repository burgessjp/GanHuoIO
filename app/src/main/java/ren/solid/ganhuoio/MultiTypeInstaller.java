package ren.solid.ganhuoio;

import me.drakeet.multitype.GlobalMultiTypePool;
import ren.solid.ganhuoio.model.GanHuoDataBeanImage;
import ren.solid.ganhuoio.model.GanHuoDataBeanMeizhi;
import ren.solid.ganhuoio.model.GanHuoDataBeanText;
import ren.solid.ganhuoio.model.Recently;
import ren.solid.ganhuoio.model.RecentlyHeader;
import ren.solid.ganhuoio.model.RecentlyTitle;
import ren.solid.ganhuoio.model.SearchResult;
import ren.solid.ganhuoio.model.bomb.CollectTable;
import ren.solid.ganhuoio.model.CategoryList;
import ren.solid.ganhuoio.module.home.provider.CategoryViewProvider;
import ren.solid.ganhuoio.model.Daily;
import ren.solid.ganhuoio.module.mine.CollectViewProvider;
import ren.solid.ganhuoio.module.home.provider.GanHuoImageViewProvider;
import ren.solid.ganhuoio.module.home.provider.GanHuoTextViewProvider;
import ren.solid.ganhuoio.module.home.provider.MeizhiViewProvider;
import ren.solid.ganhuoio.module.home.provider.DailyViewItemHeaderProvider;
import ren.solid.ganhuoio.module.home.provider.DailyViewItemTitleProvider;
import ren.solid.ganhuoio.module.home.provider.DailyViewItemProvider;
import ren.solid.ganhuoio.module.search.SearchResultViewProvider;

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
        GlobalMultiTypePool.register(Recently.class, new DailyViewItemProvider());
        GlobalMultiTypePool.register(RecentlyTitle.class, new DailyViewItemTitleProvider());
        GlobalMultiTypePool.register(RecentlyHeader.class, new DailyViewItemHeaderProvider());
        GlobalMultiTypePool.register(SearchResult.class, new SearchResultViewProvider());
        GlobalMultiTypePool.register(CollectTable.class, new CollectViewProvider());
        //新版
        GlobalMultiTypePool.register(CategoryList.class, new CategoryViewProvider());
        GlobalMultiTypePool.register(Daily.class, new ren.solid.ganhuoio.module.home.provider.DailyViewProvider());

    }
}
