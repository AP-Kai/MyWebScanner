package com.dhee.tools;


import com.dhee.service.SpiderService;

import java.util.ArrayList;
import java.util.HashMap;

public class PageComparison {
    private SpiderService spiderService = new SpiderService();
    StringSubSame stringSubSame = new StringSubSame();

    public PageComparison() {
    }

    //返回两个正常网页的公共字符串，相似比
    public ArrayList<String> getNormalPageComparison(String url1, String url2) {
        ArrayList<String> rst = new ArrayList<>();
        String htmlOne = "";
        String htmlTwo = "";

        htmlOne = spiderService.spider(url1);
        htmlTwo = spiderService.spider(url2);

//        System.out.println("页面1的长度：" + htmlOne.length());
//        System.out.println("页面2的长度：" + htmlTwo.length());
        String s = stringSubSame.intersection(htmlOne, htmlTwo);
        System.out.println("页面1和页面2的交集长度：" + s.length());

        String rate = "";
        if (s != null && htmlOne != null && htmlTwo != null)
            rate = String.valueOf((double) s.length() * 2 / (htmlOne.length() + htmlTwo.length()));
        else
            rate = "0";
        System.out.println("相似比：" + rate);
        rst.add(s);//交集
        rst.add(rate);//相似比
        return rst;
    }


    //传入两个参数，是否改变
    public boolean isSamePage(String url1, String url2) {
        String html1 = spiderService.spider(url1);
        String html2 = spiderService.spider(url2);
//        System.out.println("页面1的长度：" + html1.length());
//        System.out.println("页面2的长度：" + html2.length());
        return html1.length() == html2.length();

    }

    public static void main(String[] args) throws InterruptedException {
        PageComparison test = new PageComparison();
        String url1 = new String("http://192.168.52.143/pikachu/vul/sqli/sqli_str.php");
        String url2 = new String("http://192.168.52.143/pikachu/vul/sqli/sqli_str.php");
//        ArrayList<String> list = test.getNormalPageComparison(url1, url2);
        boolean b = test.isSamePage(url1, url2);
        System.out.println(b);
    }
}
