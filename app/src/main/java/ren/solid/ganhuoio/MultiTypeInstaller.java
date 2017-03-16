package ren.solid.ganhuoio;

import me.drakeet.multitype.GlobalMultiTypePool;
import ren.solid.ganhuoio.model.CategoryList;
import ren.solid.ganhuoio.model.Daily;
import ren.solid.ganhuoio.model.DailyHeader;
import ren.solid.ganhuoio.model.DailyTitle;
import ren.solid.ganhuoio.model.GanHuoData;
import ren.solid.ganhuoio.model.SearchResult;
import ren.solid.ganhuoio.model.bomb.CollectTable;
import ren.solid.ganhuoio.module.home.provider.CategoryViewProvider;
import ren.solid.ganhuoio.module.home.provider.DailyViewItemHeaderProvider;
import ren.solid.ganhuoio.module.home.provider.DailyViewItemProvider;
import ren.solid.ganhuoio.module.home.provider.DailyViewItemTitleProvider;
import ren.solid.ganhuoio.module.home.provider.GanHuoImageViewProvider;
import ren.solid.ganhuoio.module.home.provider.GanHuoTextViewProvider;
import ren.solid.ganhuoio.module.home.provider.MeizhiViewProvider;
import ren.solid.ganhuoio.module.mine.CollectViewProvider;
import ren.solid.ganhuoio.module.search.SearchResultViewProvider;

/**
 * Created by _SOLID
 * Date:2016/12/1
 * Time:13:06
 * Desc:
 */

public class MultiTypeInstaller {
    static void install() {
        GlobalMultiTypePool.register(GanHuoData.Text.class, new GanHuoTextViewProvider());
        GlobalMultiTypePool.register(GanHuoData.Image.class, new GanHuoImageViewProvider());
        GlobalMultiTypePool.register(GanHuoData.Meizhi.class, new MeizhiViewProvider());
        GlobalMultiTypePool.register(GanHuoData.DailyItem.class, new DailyViewItemProvider());
        GlobalMultiTypePool.register(DailyTitle.class, new DailyViewItemTitleProvider());
        GlobalMultiTypePool.register(DailyHeader.class, new DailyViewItemHeaderProvider());
        GlobalMultiTypePool.register(SearchResult.class, new SearchResultViewProvider());
        GlobalMultiTypePool.register(CollectTable.class, new CollectViewProvider());
        //新版
        GlobalMultiTypePool.register(CategoryList.class, new CategoryViewProvider());
        GlobalMultiTypePool.register(Daily.class, new ren.solid.ganhuoio.module.home.provider.DailyViewProvider());

    }
}
