package com.dhee.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.dhee.dao.InjectDao;
import com.dhee.entity.InjectEntity;
import com.dhee.entity.R;
import com.dhee.entity.UrlEntity;
import com.dhee.entity.UserEntity;
import com.dhee.tools.PageComparison;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class InjectService {
    private InjectDao injectDao = new InjectDao();

    public R inject(UrlEntity urlEntity) throws IOException {
        String url = urlEntity.getUrl();
        System.out.printf("正在探测%s SQL注入漏洞\n", url);
        try {
            Document orignDoc = Jsoup.connect(url).get();
            String method = orignDoc.getElementsByTag("form").attr("method");
            // 读取注入的关键字
            ArrayList<InjectEntity> arrayList = injectDao.select();
            if (("get").equalsIgnoreCase(method)) {
                return new R(true, getSqlScan(url, arrayList));
            } else if (("post").equalsIgnoreCase(method)) {
                System.out.println("进post方法,但是没写");
//            postSqlScan(url, vo, indexVoList);
            }
        } catch (UnsupportedMimeTypeException e) {
            // 忽略不支持的内容类型异常，跳过当前链接的处理
            System.out.println("Skipping link: " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new R(false, "");
    }

    public String getSqlScan(String url, ArrayList<InjectEntity> arrayList) throws IOException {

        for (InjectEntity arr : arrayList) {
            String payload = arr.getSqlinject();
            URL newurl = new URL(url + payload + "&submit=查询");
//            System.out.printf("构造payload后的url: %s \n", newurl);
            URLConnection uCon = newurl.openConnection();//打开连接
            int statusCode = ((HttpURLConnection) uCon).getResponseCode();

            if (statusCode == 200) {
                PageComparison pageComparison = new PageComparison();
//        ArrayList<String> list = test.getNormalPageComparison(url1, url2);
                boolean issamePage = pageComparison.isSamePage(url, String.valueOf(newurl));
                if (!issamePage) {
//                    System.out.print("\tStatus Code 200: OK");
                    System.out.printf("\t疑似存在漏洞,payload: %s\n\n", payload);
                    return payload;
                } else {
//                    System.out.print("\tStatus Code: " + statusCode);
                    System.out.print("\t未探测到漏洞\n");
                    return "1";
                }
            }

        }
        return "1";
    }


    public void stockUrl(UrlEntity urlEntity) {
        List<UrlEntity> list = injectDao.selectByUrl(urlEntity);
        if (list.size() != 0) {
            // url存在于用户的数据库表中，update
            injectDao.updateUrl(urlEntity);
        } else {
            // url不存在于用户的数据库表中，insert
            injectDao.insertUrl(urlEntity);
        }

    }


}
