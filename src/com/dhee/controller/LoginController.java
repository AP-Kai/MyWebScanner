package com.dhee.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhee.entity.UserEntity;
import com.dhee.service.UserService;

import javax.jws.WebService;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginController extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);

        UserService userService = new UserService();
        UserEntity loginUser = userService.login(userEntity);

        if(loginUser!=null){
            HttpSession session = request.getSession();
            session.setAttribute("loginUser",loginUser);
        }

        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSON(loginUser));


    }
}
