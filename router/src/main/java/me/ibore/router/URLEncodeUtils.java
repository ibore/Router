package me.ibore.router;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

class URLEncodeUtils {
    static String decode(String origin) {
        String out;
        try {
            out = URLDecoder.decode(origin, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            out = origin;
        }
        return out;
    }

    static String encode(String origin) {
        String out;
        try {
            out = URLEncoder.encode(origin, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            out = origin;
        }
        return out;
    }
}
