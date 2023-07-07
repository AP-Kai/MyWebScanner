package com.dhee.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderService {
    public String spider(String url) {
        String result = "";
        try {
            URL myUrl = new URL(url);
            URLConnection urlConnection = myUrl.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String content = bufferedReader.readLine();

            while (content != null) {
                result += content + "\n";
                content = bufferedReader.readLine();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    ArrayList<String> baseUrl = new ArrayList<>();
    ArrayList<String> addedUrl = new ArrayList<>();

    public ArrayList<String> getAllUrls(String urls) {

        ArrayList<String> allUrls = new ArrayList<>();
        ArrayList<String> baseUrls = getBaseUrls(urls);

        for (String baseUrl : baseUrls) {
            allUrls.addAll(getImportsUrl(baseUrl));
            allUrls.addAll(getMediaUrl(baseUrl));
            allUrls.addAll(getLinkUrl(baseUrl));
            allUrls.addAll(getAreaUrl(baseUrl));
        }
        Iterator<String> iterator = allUrls.iterator();
        while (iterator.hasNext()) {
            String allUrl = iterator.next();
            if (!allUrl.endsWith(".php") && !allUrl.endsWith(".html")) {
                iterator.remove();
            }
        }
        return allUrls;
    }

    private ArrayList<String> getBaseUrls(String urls) {

        addedUrl.add(urls);
        ArrayList<String> allUrls = new ArrayList<>();
        ArrayList<String> baseUrl = new ArrayList<>();
        allUrls = getLinkUrl(urls);
        for (String url : allUrls) {
            if (url.contains(".html") || url.contains(".php"))
                baseUrl.add(url);
        }
        for (int i = 0; i < baseUrl.size() - 1; i++) {
            for (int j = i + 1; j < baseUrl.size() - 1; j++) {
                if (baseUrl.get(j).equals(baseUrl.get(i)))
                    baseUrl.remove(j);
            }
            if (!baseUrl.get(i).contains("xxx.com"))
                baseUrl.remove(i);
        }
        this.baseUrl.addAll(baseUrl);
        for (int i = 0; i < baseUrl.size() - 1; i++) {
            baseUrl.remove(0);
            String url = baseUrl.get(i);
            if (!addedUrl.contains(url) && url.contains("xxx.com")) {
                // System.out.println("begin debug");
                // System.out.println(url);
                // System.out.println("end debug");
                getBaseUrls(url);
                // addedUrl.add(url);
            }
        }
        for (int i = 0; i < this.baseUrl.size() - 1; i++) {
            for (int j = i + 1; j < this.baseUrl.size() - 1; j++) {
                if (this.baseUrl.get(j).equals(this.baseUrl.get(i))) {
                    this.baseUrl.remove(j);
                }
            }
            if (!this.baseUrl.get(i).contains("xxx.com"))
                this.baseUrl.remove(i);
        }
//        System.out.println("baseurl:\t" + baseUrl);
        return this.baseUrl;
    }

    private ArrayList<String> getMediaUrl(String urls) {

        ArrayList<String> allUrls = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(urls).get();

            Elements media = doc.select("[src]");

            for (Element src : media) {
                String url = src.attr("abs:src");

                if (!url.isEmpty()) {
                    allUrls.add(url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allUrls;
    }

    private ArrayList<String> getImportsUrl(String urls) {

        ArrayList<String> allUrls = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(urls).get();

            Elements imports = doc.select("link[href]");

            for (Element src : imports) {
                String url = src.attr("abs:href");

                if (!url.isEmpty()) {
                    allUrls.add(url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allUrls;
    }

    private ArrayList<String> getLinkUrl(String urls) {

        ArrayList<String> allUrls = new ArrayList<>();
        Document doc;
        try {

            doc = Jsoup.connect(urls).get();

            Elements links = doc.select("a[href]");
//            System.out.println("links:\t" + links);
            for (Element src : links) {
                String url = src.attr("abs:href");

                if (!url.isEmpty()) {
                    allUrls.add(url);
                }
            }
            for (int i = 0; i < allUrls.size() - 1; i++) {
                for (int j = i + 1; j < allUrls.size() - 1; j++) {
                    if (allUrls.get(j).equals(allUrls.get(i))) {
                        allUrls.remove(j);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(allUrls);
        return allUrls;
    }

    private ArrayList<String> getAreaUrl(String urls) {

        ArrayList<String> allUrls = new ArrayList<>();
        Document doc;
        try {

            doc = Jsoup.connect(urls).get();

            Elements links = doc.select("area[href]");

            for (Element src : links) {
                String url = src.attr("abs:href");

                if (!url.isEmpty()) {
                    allUrls.add(url);
                }
            }
            for (int i = 0; i < allUrls.size() - 1; i++) {
                for (int j = i + 1; j < allUrls.size() - 1; j++) {
                    if (allUrls.get(j).equals(allUrls.get(i))) {
                        allUrls.remove(j);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allUrls;
    }
}
