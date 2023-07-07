package com.dhee.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhee.entity.R;
import com.dhee.entity.UrlEntity;
import com.dhee.entity.UserEntity;
import com.dhee.service.InjectService;
import com.dhee.service.SpiderService;
import com.dhee.service.UrlService;
import com.dhee.service.UserService;
import com.dhee.tools.CreateTxt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "InjectController", value = "/sqli")
public class InjectController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InjectService injectService = new InjectService();
        UrlEntity urlEntity = new UrlEntity();
        String url = req.getParameter("url");
        Map<String, String> map = new HashMap<String, String>();
        ArrayList<String> results = new ArrayList<>();
        String result = "";
        // 爬取全部链接
        SpiderService spiderService = new SpiderService();
        ArrayList<String> arrayLists = spiderService.getAllUrls(url);

        // 测SQLi
        for (String arrayList : arrayLists) {
            urlEntity.setUrl(arrayList);
            R r = new R();
            r = injectService.inject(urlEntity);
            if (r.getSuccess()) {
                result = String.format("链接%s存在漏洞，使用的payload：  %s\n", arrayList, r.getMessage());

                urlEntity.setSqli(r.getMessage());
                injectService.stockUrl(urlEntity);
            } else {
                result = String.format("链接%s 不存在漏洞。\n", arrayList);

                urlEntity.setSqli("0");
                injectService.stockUrl(urlEntity);
            }
            results.add(result);
        }
        CreateTxt.createtxt(results);
        resp.setContentType("text/html;charset=utf-8");

        map.put("injectContent", String.valueOf(results));
        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(map));

    }
}
