package test;

import com.dhee.entity.UrlEntity;
import com.dhee.service.SpiderService;
import com.dhee.service.UrlService;

import java.util.ArrayList;
import java.util.Vector;

public class SpiderServiceTest {
    public static void main(String[] args) {
        SpiderService spiderService = new SpiderService();
        ArrayList<String> results = spiderService.getAllUrls("http://192.168.52.143/sqli/index-1.html");
        UrlService urlService = new UrlService();
        ArrayList<UrlEntity> urlEntities = new ArrayList<UrlEntity>();
        for(String result:results){
            System.out.println(result);
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setUrl(result);
            urlEntities.add(urlEntity);
        }
        urlService.addUrls(urlEntities);
    }
}
