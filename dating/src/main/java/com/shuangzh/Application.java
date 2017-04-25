package com.shuangzh;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/4/19.
 */
public class Application {


    public static String initCookie = "__guid=85OLHh58ca2e7d9ba8a5.33304212; LiveWSMBY90996241=1489645196571441042263; NMBY90996241fistvisitetime=1489645196579; vlstatId=vlstat-1489645196000-2613626222; __jsluid=85688deee6af1dce0181d49667e9dce5; NMBY90996241visitecounts=2; NMBY90996241lastinvite=1492586759168; _nyjy_newadv_=1; __jsl_clearance=1492617500.144|0|Zb%2FTDcjp8rA7cLHKQlxczElR310%3D; DKLFFDKD=%7B%22key%22%3A%22c41cfb2470b659006286d518dd7e9cef%22%2C%22val%22%3A%22e8dc72170f7444d2f0d260603b5fa2c3%22%2C%22tm%22%3A1492617501%7D; PHPSESSID=lp2c0ar7ci9q36c3f0spr4a0a6; Hm_lvt_c4e8e5b919a5c12647962ea08462e63b=1492617440; Hm_lpvt_c4e8e5b919a5c12647962ea08462e63b=1492617440; _ga=GA1.2.455600356.1492617440; _gat=1";

    public static String loginUrl = "https://user.91160.com/login.html";

//    public static String loginUrl = "http://localhost:8080/reqinfo";

    public static String tokens = null;

    public static Map<String, String> cookieMap = new HashMap<String, String>();

    public static String relocation = null;

//    public static DefaultHttpClient sslClient = new DefaultHttpClient();

    public static SSLClient sslClient;

    static {
        try {
            sslClient = new SSLClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void init() {
        HttpGet httpGet = new HttpGet(loginUrl);

        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml");
        httpGet.setHeader("accept-language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
        httpGet.setHeader("cache-control", "max-age=0");
        httpGet.setHeader("referer", "https://user.91160.com/login.html");

//        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        httpGet.setHeader("accept-Language", "zh-CN,zh;q=0.8");
//        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        sslClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

        httpGet.setHeader("cookie", initCookie);
        addCookies(initCookie);
        httpGet.setHeader("cookie", getCookie());

        try {
            HttpResponse httpResponse = sslClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            System.out.println("###### init page ");
            String content = EntityUtils.toString(httpEntity, "UTF-8");
            System.out.println(content);
            System.out.println("############### init page end");
            List<Cookie> cookieList = sslClient.getCookieStore().getCookies();
            for (Cookie cookie : cookieList) {
                System.out.println(cookie.getDomain() + ":" + cookie.getName() + "=" + cookie.getValue());
            }
            ByteArrayInputStream bi = new ByteArrayInputStream(content.getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(bi));
            String line = null;
            while ((line = br.readLine()) != null) {
                int i = line.indexOf("name=\"tokens\"");
                if (i > 0) {
                    int p1 = line.lastIndexOf("\"");
                    String l = line.substring(0, p1);
                    System.out.println("@@ l=" + l);

                    p1 = l.lastIndexOf("\"");
                    l = l.substring(p1 + 1, l.length());
                    System.out.println("@@ l=" + l);
                    tokens = l;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void postform() {
        // 登录
        HttpPost httpPost = new HttpPost(loginUrl);
        httpPost.setHeader("accept", "text/html,application/xhtml+xml,application/xml");
        httpPost.setHeader("accept-language", "zh-CN,zh;q=0.8");
        httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
        httpPost.setHeader("cache-control", "max-age=0");
        httpPost.setHeader("referer", "https://user.91160.com/login.html");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "13809884175"));
        params.add(new BasicNameValuePair("clientLoginUrl", ""));
        params.add(new BasicNameValuePair("password", "mfkcc1216"));
        params.add(new BasicNameValuePair("target", "https://user.91160.com/login.html"));
        params.add(new BasicNameValuePair("error_num", "0"));
        params.add(new BasicNameValuePair("checkcode", ""));
        params.add(new BasicNameValuePair("tokens", tokens));

        httpPost.setHeader("cookie", getCookie());
//        httpPost.setHeader("cookie", initCookie);
//        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
//        httpPost.setConfig(requestConfig);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = sslClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            System.out.println("###### post page ");
            String content = EntityUtils.toString(httpEntity, "UTF-8");
            System.out.println(content);
            System.out.println("############### post page end");
            System.out.println(response.getStatusLine().getStatusCode());
            Header[] heads = response.getAllHeaders();
            for (Header h : heads) {
                System.out.println(h.getName() + ":" + h.getValue());
                String name = h.getName();
                if ("location".equalsIgnoreCase(h.getName())) {
                    relocation = h.getValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 登录成功后，重定向
        if (relocation != null) {
            System.out.println("relocation ->"+ relocation);
            HttpGet httpGet = new HttpGet(relocation);
            httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml");
            httpGet.setHeader("accept-language", "zh-CN,zh;q=0.8");
            httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
 //           httpGet.setHeader("cache-control", "max-age=0");
            httpGet.setHeader("referer", relocation);
            httpGet.setHeader("connection","keep-alive");
            httpGet.setHeader("upgrade-insecure-requests","1");
  //          cookieMap.clear();
  //          httpGet.setHeader("cookie", getCookie());
            try {
                HttpResponse response1 = sslClient.execute(httpGet);
                HttpEntity httpEntity = response1.getEntity();
                System.out.println("###### 登录后页面 page ");
                String content = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println(content);
                System.out.println("############### 登录后页面 page end");
                System.out.println(response1.getStatusLine().getStatusCode());
                Header[] heads = response1.getAllHeaders();
                for (Header h : heads) {
                    System.out.println(h.getName() + ":" + h.getValue());
                }
                System.out.println(getCookie());
            } catch (IOException e) {
                e.printStackTrace();
            }

            httpGet.setHeader("cookie", getCookie());
            try {
                HttpResponse response1 = sslClient.execute(httpGet);
                HttpEntity httpEntity = response1.getEntity();
                System.out.println("###### 登录后页面11 page ");
                String content = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println(content);
                System.out.println("############### 登录后页面11 page end");
                System.out.println(response1.getStatusLine().getStatusCode());
                Header[] heads = response1.getAllHeaders();
                for (Header h : heads) {
                    System.out.println(h.getName() + ":" + h.getValue());
                }
                System.out.println(getCookie());
            } catch (IOException e) {
                e.printStackTrace();
            }






        }
    }

    public static void main(String[] args) {
        init();
        postform();
    }

    private static void addCookies(String cookie) {
        String[] cks = cookie.split(";");
        for (String s : cks) {
            s = s.trim();
            String[] ss = s.split("=");
            cookieMap.put(ss[0], ss[1]);
        }
    }

    private static String getCookie() {
        List<Cookie> cookieList = sslClient.getCookieStore().getCookies();
        for (Cookie c : cookieList) {
            cookieMap.put(c.getName(), c.getValue());
        }
        StringBuilder sb = new StringBuilder();
        for (String key : cookieMap.keySet()) {
            sb.append(key).append("=").append(cookieMap.get(key)).append(";");
        }
        return sb.toString();
    }
}
