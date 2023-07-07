package com.dhee.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhee.entity.R;
import com.dhee.entity.UrlEntity;
import com.dhee.service.SpiderService;
import com.dhee.service.XssService;
import com.dhee.tools.CreateTxt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "XssController", value = "/xss")
public class XssController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        XssService xssService = new XssService();
        UrlEntity urlEntity = new UrlEntity();
        String url = req.getParameter("url");
        String result = "";
        ArrayList<String> results = new ArrayList<>();
        R r = new R();
        // 爬取全部链接
        SpiderService spiderService = new SpiderService();
        ArrayList<String> arrayLists = spiderService.getAllUrls(url);

        // 测试Xss
        for (int i = 0; i < Math.min(arrayLists.size(), 100); i++) {
            String arrayList = arrayLists.get(i);
            r = xssService.checkxss(arrayList);

            if (r.getSuccess()) {
                result = String.format("链接%s存在漏洞，使用的payload：  %s\n", arrayList, r.getMessage());
                urlEntity.setXss(r.getMessage());
                xssService.stockUrl(urlEntity);
            } else {
                result = String.format("链接%s 不存在漏洞。\n", arrayList);
                urlEntity.setXss("0");
                xssService.stockUrl(urlEntity);
            }
            results.add(result);
        }
        CreateTxt.createtxt(results);
        resp.setContentType("text/html;charset=utf-8");

        Map<String, String> map = new HashMap<String, String>();
        map.put("xssContent", String.valueOf(results));
        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(map));

    }

}
