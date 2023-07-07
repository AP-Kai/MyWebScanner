package com.dhee.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhee.entity.UrlEntity;
import com.dhee.service.SpiderService;
import com.dhee.service.UrlService;

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

@WebServlet(name = "GetAllUrlsController", value = "/getallurls")
public class GetAllUrlsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpiderService spiderService = new SpiderService();
        Map<String, String> map = new HashMap<String, String>();
        ArrayList<String> results = spiderService.getAllUrls(req.getParameter("url"));
        UrlService urlService = new UrlService();
        ArrayList<UrlEntity> urlEntities = new ArrayList<UrlEntity>();
        for (String r : results) {
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setUrl(r);
            urlEntities.add(urlEntity);
        }
        urlService.addUrls(urlEntities);

        map.put("spiderContent", String.valueOf(results));
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(map));

    }
}
