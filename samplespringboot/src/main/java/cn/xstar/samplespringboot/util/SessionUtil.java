package cn.xstar.samplespringboot.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class SessionUtil {
    public static void clearSession(HttpServletRequest request) {
        Enumeration<String> names = request.getSession().getAttributeNames();
        if (names != null) {
            while (names.hasMoreElements()) {
                request.getSession().removeAttribute(names.nextElement());
            }
        }
    }

    public static void delSession(HttpServletRequest request, String name) {
        Enumeration<String> names = request.getSession().getAttributeNames();
        if (names != null) {
            while (names.hasMoreElements()) {
                if (names.nextElement().equals(name))
                    request.getSession().removeAttribute(names.nextElement());
            }
        }
    }

    public static void addSession(HttpServletRequest request, String name, Object obj) {
        if (name != null && obj != null)
            request.getSession().setAttribute(name, obj);
    }
}
