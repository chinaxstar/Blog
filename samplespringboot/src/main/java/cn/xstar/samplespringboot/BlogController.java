package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.pojo.Article;
import cn.xstar.samplespringboot.pojo.Data;
import cn.xstar.samplespringboot.pojo.User;
import cn.xstar.samplespringboot.util.Const;
import cn.xstar.samplespringboot.util.CookieUtil;
import cn.xstar.samplespringboot.util.JacksonUtil;
import cn.xstar.samplespringboot.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.xstar.samplespringboot.util.Const.*;

@SessionAttributes(SESSION_USER)
@Controller
public class BlogController {

    @Resource(name = LOGIN_SERVICE_NAME)
    private LoginRestService loginRestService;
    Logger logger = LoggerFactory.getLogger(BlogController.class);
    @Autowired
    private ArticleRestService articleRestService;
    @Value(value = "${work.config.logincookieage}")
    public int logincookieage;

    @RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
    public String index(Model model, @SessionAttribute(value = SESSION_USER, required = false) User user) {
        List<Article> articleList = articleRestService.getIndexArticle();
        model.addAttribute("map", articleList);
        if (user != null) model.addAttribute(SESSION_USER, user);
        return "index";
    }


    @RequestMapping(value = "/register/verify")
    public String register(Model model, @RequestParam String username, @RequestParam String passwd, @RequestParam String passwd2) {
        if (passwd.equalsIgnoreCase(passwd2)) {
            Map<String, String> map = loginRestService.register(username, passwd);
            model.addAttribute(Const.MSG, map);
            return "login";
        } else {
            return "密码不一致！";
        }
    }


    /**
     * 检测登录
     *
     * @param ticket cookie中存储的登录状态票据
     * @return
     */
    @RequestMapping("/login/checkstate")
    public @ResponseBody
    String checkLoginSession(@RequestBody String ticket) {
        Data<User> data = new Data<>();
        if (StringUtils.isEmpty(ticket)) {
            data.setCode(Const.FAILURE);
            data.setMsg("票据不存在！");
        } else {
            User user = loginRestService.checLoginState(ticket);
            if (user != null) {
                user.setPassword(ticket);
                data.setData(Collections.singletonList(user));
                data.setCode(Const.SUCCESS);
                data.setMsg("票据可用！");
            } else {
                data.setCode(Const.FAILURE);
                data.setMsg("票据不存在！");
            }
        }
        return JacksonUtil.toJson(data);
    }

    @RequestMapping(value = "/login/verify", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
        Data<User> data = new Data<>();
        String username = request.getParameter("username");
        String passwd = request.getParameter("passwd");
        if (StringUtils.isEmpty(username)) {
            data.setCode(Const.LOGIN_NAME_EMPTY);
            data.setMsg("用户名不能为空！");
        } else if (StringUtils.isEmpty(username)) {
            data.setCode(Const.LOGIN_PASSWD_EMPTY);
            data.setMsg("密码不能为空！");
        } else {
            User user = loginRestService.login(username, passwd);
            if (user == null) {
                data.setCode(Const.LOGIN_NO_USER);
                data.setMsg("用户名或密码不正确！");
            } else {
                data.setData(Collections.singletonList(user));
                data.setMsg("登陆成功");
                data.setCode(Const.SUCCESS);
                model.addAttribute(SESSION_USER, user);
                request.getSession().setAttribute(SESSION_USER, user);
                CookieUtil.addCookie(response, COOKIE_TICKET, user.getPassword(), logincookieage, "/");
                return "redirect:/";
            }
        }
        model.addAttribute(MODEL_DATA, data);
        model.addAttribute(SESSION_USER, new User(username));
        return "login";
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public String articleDetails(@PathVariable int articleId, Model model) {
        Article article = articleRestService.getArticleById(articleId);
        model.addAttribute(article);
        return "articleDetails";
    }


    @RequestMapping(value = "/register")
    public String registerPage() {
        return "register";
    }

    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/search", produces = "text/plain;charset=UTF-8")
    public String search() {
        return "search";
    }

    @RequestMapping(value = "/user/{id}")
    public String userDetail(Model model, @PathVariable int id, @SessionAttribute(name = SESSION_USER, required = false) User user) {
        if (user == null) return "login";
        else {
            model.addAttribute(SESSION_USER, user);
            int count = articleRestService.getUserArticleCount(id);
            model.addAttribute(SESSION_COUNT, count);
            return "user";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.asMap().clear();
        CookieUtil.delCookie(request, response, COOKIE_TICKET);
        SessionUtil.clearSession(request);
        return "redirect:/";
    }
}
