package com.dhee.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.dhee.entity.R;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dhee.dao.XssDao;
import com.dhee.entity.UrlEntity;
import com.dhee.entity.XssEntity;
import com.dhee.tools.XSScon;

public class XssService {
    private XssDao xssDao = new XssDao();

    public boolean check(String url) {
        try {
            URL u = new URL(url);
            URLConnection uCon = u.openConnection();
            if (uCon.toString().contains(url)) {
                return true;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
        return false;
    }

    public R checkxss(String url) throws IOException {
        if (!check(url)) {
            return new R(false, "skip url: " + url);
        }

        System.out.printf("开始探测url:%s   XSS漏洞\n", url);
        ArrayList<XssEntity> arrayList = xssDao.select();
        for (XssEntity XssEntity : arrayList) {
            String payload = XssEntity.getXssPayload();
            String method = "";
            String actionName = "";
            String str;//用于存储每一个payload
            String oldStr;//原始字符串
            List<String> param = new ArrayList<String>();

            Document doc = null;  //该类表示通过Jsoup库加载HTML文档
            String a = payload;
            oldStr = a;
            str = a.replace(" ", "%20");//使用urlencoded方法将payload中的空格替换为%20
            str = str.replace("<", "%3C");
            str = str.replace(">", "%3E");
            try {
                doc = Jsoup.connect(url).get(); //使用url尝试链接并从Web获取HTML
            } catch (UnsupportedMimeTypeException e) {
                // 忽略不支持的内容类型异常，跳过当前链接的处理
                System.out.println("Skipping link: " + url);
                continue;
            }catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }

            //xss攻击常出现在form表单提交过程中所以如下
            Elements e1 = doc.getElementsByTag("form"); //获取所有form标签
            for (Element b : e1) {
                method = b.attr("method");  //获取form标签的method属性
            }

            Elements e2 = doc.select("input");//查找input标签
            for (Element b : e2) {
                if (!b.attr("name").equals("")) {  //获取所有input标签的name属性
                    param.add(b.attr("name"));
                }
            }
            for (Element b : e1) {
                actionName = b.attr("action");  //获取form标签action属性
            }
            if (!e1.isEmpty() && method.equalsIgnoreCase("get")) {  //如果form标签存在且method属性为get
                System.out.println("form标签存在且method属性为get");
                //如果返回true，则说明存在XSS漏洞
                boolean test = XSScon.xssGetConnect(url, str, actionName, param, oldStr);
//                System.out.println(XSScon.xssGetConnect(url, str, actionName, param, oldStr));
                if (test) {
                    return new R(true, payload);
                } else {
                    return new R(false, "");
                }
            }
        }
        return new R(false, "");
    }

    public void stockUrl(UrlEntity urlEntity) {//插入url
        ArrayList<UrlEntity> arrayList = xssDao.selectByUrl(urlEntity);
        // url存在于用户的数据库表中，update
        if (arrayList.size() != 0) {
            xssDao.updateUrl(urlEntity);

        } else {
            // url不存在于用户的数据库表中，insert
            xssDao.insertUrl(urlEntity);
        }

    }


}
