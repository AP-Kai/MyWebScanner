package test;

import com.dhee.entity.R;
import com.dhee.entity.UrlEntity;
import com.dhee.service.SpiderService;
import com.dhee.service.XssService;

import java.io.IOException;
import java.util.ArrayList;

public class XssServiceTest {
    public static void main(String[] args) throws IOException {

        XssService xssService = new XssService();

        UrlEntity urlEntity = new UrlEntity();
        String url = "http://192.168.52.143/pikachu/vul/xss/xss_reflected_get.php";
        R r = new R();
        String result;

        // 爬取全部链接
        SpiderService spiderService = new SpiderService();
        ArrayList<String> arrayLists = spiderService.getAllUrls(url);

        //测试Xss
        for (int i = 0; i < Math.min(arrayLists.size(), 100); i++) {
            String arrayList = arrayLists.get(i);
            r = xssService.checkxss(arrayList);

            if (r.getSuccess()) {
                result = String.format("链接%s存在漏洞，使用的payload：  %s\n", arrayList, r.getMessage());
                urlEntity.setUrl(arrayList);
                urlEntity.setXss(r.getMessage());
                xssService.stockUrl(urlEntity);
            } else {
                result = String.format("链接%s 不存在漏洞。\n", arrayList);
                urlEntity.setUrl(arrayList);
                urlEntity.setXss("0");
                xssService.stockUrl(urlEntity);
            }
            System.out.println(result);
        }
    }
}


