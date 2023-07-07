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

@WebServlet(name = "spiderController", value = "/spider")
public class SpiderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SpiderService spiderService = new SpiderService();
        String result = spiderService.spider(req.getParameter("url"));

        Map<String, String> map = new HashMap<String, String>();
        map.put("spiderContent", result);

//        System.out.println(result);
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter writer = resp.getWriter();
        writer.print(JSONObject.toJSON(map));

    }
}
