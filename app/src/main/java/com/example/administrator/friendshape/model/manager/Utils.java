package com.example.administrator.friendshape.model.manager;


import okhttp3.HttpUrl;

/**
 * Created by Administrator on 2017/10/30.
 */

public class Utils {
    private Utils() {
        throw new IllegalStateException("do not instantiation me");
    }

    static HttpUrl checkUrl(String url) {
        HttpUrl parseUrl = HttpUrl.parse(url);
        if (null == parseUrl) {
            throw new InvalidUrlException(url);
        } else {
            return parseUrl;
        }
    }
}
