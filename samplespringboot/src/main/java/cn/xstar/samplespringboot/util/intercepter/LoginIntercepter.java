package cn.xstar.samplespringboot.util.intercepter;

import cn.xstar.samplespringboot.LoginRestService;
import cn.xstar.samplespringboot.pojo.User;
import cn.xstar.samplespringboot.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.xstar.samplespringboot.util.Const.COOKIE_TICKET;
import static cn.xstar.samplespringboot.util.Const.LOGIN_SERVICE_NAME;
import static cn.xstar.samplespringboot.util.Const.SESSION_USER;

public class LoginIntercepter extends HandlerInterceptorAdapter {

    @Resource(name = LOGIN_SERVICE_NAME)
    private LoginRestService loginRestService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(SESSION_USER);
        if (user == null) {
            Cookie cookie = CookieUtil.getCookie(request, COOKIE_TICKET);
            if (cookie != null) {
                user = loginRestService.checLoginState(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute(SESSION_USER, user);
                    return true;
                }
            }

        } else return true;
        return false;
    }

}
