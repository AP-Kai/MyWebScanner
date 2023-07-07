package com.dhee.service;

import com.dhee.dao.UrlDao;
import com.dhee.entity.UrlEntity;
import com.dhee.entity.UserEntity;

import java.util.ArrayList;

public class UrlService {
    private UrlDao urlDao = new UrlDao();

    public boolean addUrls(ArrayList<UrlEntity> urlEntities){
        urlDao.add(urlEntities);
        return true;
    }



}
