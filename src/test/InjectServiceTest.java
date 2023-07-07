package test;

import com.dhee.entity.R;
import com.dhee.entity.UrlEntity;
import com.dhee.service.InjectService;
import com.dhee.service.SpiderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InjectServiceTest {
    public static void main(String[] args) throws IOException {
        InjectService injectService = new InjectService();
        UrlEntity urlEntity = new UrlEntity();

        String url = "http://192.168.52.143/pikachu/";

        R r = new R();

        // 爬取全部链接
        SpiderService spiderService = new SpiderService();
        Map<String, String> map = new HashMap<String, String>();
        ArrayList<String> arrayLists = spiderService.getAllUrls(url);

        // 测SQLi
        for (String arrayList : arrayLists) {
            urlEntity.setUrl(arrayList);
            r = injectService.inject(urlEntity);
            if (r.getSuccess()) {
                System.out.printf("链接%s存在漏洞，使用的payload：  %s\n\n", arrayList, r.getMessage());
                urlEntity.setSqli(r.getMessage());
                injectService.stockUrl(urlEntity);
            } else {
                System.out.printf("链接%s 不存在漏洞。\n\n", arrayList);
                urlEntity.setSqli("0");
                injectService.stockUrl(urlEntity);
            }
        }

    }
}
