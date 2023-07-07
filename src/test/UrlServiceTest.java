package test;

import com.dhee.entity.UrlEntity;
import com.dhee.service.UrlService;

import java.util.ArrayList;

public class UrlServiceTest {

    public static void main(String[] args){
        UrlService urlService = new UrlService();

        ArrayList<UrlEntity> urlEntities = new ArrayList<UrlEntity>();

        UrlEntity urlEntity1 = new UrlEntity();
        urlEntity1.setUrl("https://www.acwing.com/footer/faq/");
        urlEntities.add(urlEntity1);
        UrlEntity urlEntity2 = new UrlEntity();
        urlEntity2.setUrl("http://beian.miit.gov.cn/");
        urlEntities.add(urlEntity2);
        urlService.addUrls(urlEntities);

    }
}
