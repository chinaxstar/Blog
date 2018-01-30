package cn.xstar.samplespringboot.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    /**
     * 添加cookie
     *
     * @param response
     * @param name
     * @param value
     * @param age      保持时间/秒
     * @param path
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int age, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(age);
        if (!StringUtils.isEmpty(path))
            cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 添加cookie
     *
     * @param response
     * @param name
     * @param value
     */
    public static void addCookie(HttpServletResponse response, String name, String value) {
        addCookie(response, name, value, 0, "/");
    }

    /**
     * 修改cookie
     *
     * @param request
     * @param response
     * @param name
     * @param value
     */
    public static void editCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            addCookie(response, name, value);
        }
    }

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param name
     */
    public static void delCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = getCookie(request, name);
        if (cookie != null) {
            addCookie(response, name, null);
        }
    }
}
