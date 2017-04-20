package ren.solid.ganhuoio.api;

import android.support.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import ren.solid.ganhuoio.bean.XianDuCategory;
import ren.solid.ganhuoio.bean.XianDuItem;
import ren.solid.library.http.CommonService;
import ren.solid.library.http.ServiceFactory;

/**
 * Created by _SOLID
 * GitHub:https://github.com/burgessjp
 * Date:2017/3/18
 * Time:15:22
 * Desc:使用Jsoup解析闲读数据
 */

public class XianDuService {

    private final static String BASE_URL = "http://gank.io/xiandu";

    public static Single<List<XianDuCategory>> getCategorys() {

        return getDocumentObservable(BASE_URL)
                .map(new Function<Document, List<XianDuCategory>>() {
                    @Override
                    public List<XianDuCategory> apply(@io.reactivex.annotations.NonNull Document document) throws Exception {
                        List<XianDuCategory> list = new ArrayList<>();
                        Elements elements = document.body().getElementById("xiandu_cat").getElementsByTag("a");
                        for (Element element : elements) {
                            XianDuCategory item = new XianDuCategory();
                            item.setTitle(element.text());
                            item.setCategory(element.attr("href").substring(element.attr("href").lastIndexOf("/") + 1));
                            list.add(item);

                        }
                        return list;
                    }
                });
    }

    public static Single<List<XianDuItem>> getData(String category, int pageIndex) {
        if ("xiandu".equals(category)) {//处理默认页
            category = "wow";
        }
        final String requestUrl = BASE_URL + "/" + category + "/" + "page" + "/" + pageIndex;
        return getDocumentObservable(requestUrl)
                .map(new Function<Document, List<XianDuItem>>() {
                    @Override
                    public List<XianDuItem> apply(@io.reactivex.annotations.NonNull Document document) throws Exception {
                        List<XianDuItem> list = new ArrayList<>();
                        Elements items = document.body().getElementsByClass("xiandu_item");
                        for (Element element : items) {
                            String title = element.getElementsByClass("site-title").text();
                            String url = element.getElementsByClass("site-title").attr("href");
                            String time = element.getElementsByTag("small").text();
                            String source = element.getElementsByClass("site-name").get(0).attr("title");
                            String sourceAvatar = element.getElementsByClass("site-name").get(0)
                                    .getElementsByTag("img").get(0).attr("src");
                            XianDuItem item = new XianDuItem();
                            item.setTitle(title);
                            item.setUrl(url);
                            item.setSource(source);
                            item.setTime(time);
                            item.setSourceAvatar(sourceAvatar);
                            list.add(item);
                        }
                        return list;
                    }
                });
    }

    @NonNull
    private static Single<Document> getDocumentObservable(String requestUrl) {
        return ServiceFactory.getInstance()
                .createService(CommonService.class)
                .loadString(requestUrl)
                .map(new Function<ResponseBody, Document>() {
                    @Override
                    public Document apply(@io.reactivex.annotations.NonNull ResponseBody responseBody) throws Exception {
                        return Jsoup.parse(responseBody.string());
                    }
                });
    }

}
