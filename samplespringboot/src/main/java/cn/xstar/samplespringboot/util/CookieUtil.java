package cn.xstar.samplespringboot.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static Cookie getCookie(HttpServletRequest request, String key) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(key)) {
                    return c;
                }
            }
        }
        return null;
    }
}
