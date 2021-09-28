package com.neil.libnetwork;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class UrlCreator {
    public static String createUrlFromParams(String url, Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
            stringBuilder.append("&");
        } else {
            stringBuilder.append("?");
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                String value = URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            stringBuilder.append(entry.getKey()).append("=").append("value").append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return  stringBuilder.toString();
    }
}
