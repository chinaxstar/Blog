package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.dao.ArticleDao;
import cn.xstar.samplespringboot.pojo.Article;
import cn.xstar.samplespringboot.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    @Value(value = "${author.name}")
    private String name;
    @Value(value = "${author.age}")
    private int age;
    @Value(value = "${author.lv}")
    private int lv;
    @Value(value = "${author.guotations}")
    private String sign;

    @Autowired
    private LoginRestService loginRestService;
    Logger logger = LoggerFactory.getLogger(BlogController.class);
    @Autowired
    private ArticleDao articleDao;

    @RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
    public String index(Model model) {

        List<Article> articleList = articleDao.selectByLimit(0, 100);
        model.addAttribute("map", articleList);

        return "index";
    }


    @RequestMapping(value = "/register")
    public String register(Model model, @RequestParam String username, @RequestParam String passwd, @RequestParam String passwd2) {
        if (passwd.equalsIgnoreCase(passwd2)) {
            Map<String, String> map = loginRestService.register(username, passwd);
            model.addAttribute(Const.MSG, map);
            return "login";
        } else {
            return "密码不一致！";
        }
    }



    @RequestMapping(value = "/login", produces = "text/plain;charset=UTF-8")
    public String login(Model model, @RequestParam String username, @RequestParam String passwd) {
        int code = loginRestService.login(username, passwd);
        String str = "登陆成功";
        switch (code) {
            case Const.LOGIN_NAME_EMPTY:
            case Const.LOGIN_PASSWD_EMPTY:
            case Const.LOGIN_NO_USER:
            case Const.LOGIN_WRONG_PASSSWD:
                str = "登录失败！";
                break;

        }
        model.addAttribute(Const.MSG, str);
        return "login";
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public String articleDetails(@PathVariable int articleId, Model model) {
        Article article = articleDao.selectById(articleId);
        model.addAttribute(article);
        return "articleDetails";
    }


    @RequestMapping(value = "/register.action")
    public String registerPage(){
        return "register";
    }
    @RequestMapping(value = "/login.action")
    public String loginPage(){
        return "login";
    }
    @RequestMapping(value = "/search.action", produces = "text/plain;charset=UTF-8")
    public String search(Model model) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        map.put("level", lv);
        map.put("sign", sign);
        model.addAttribute("map", map);
        return "search";
    }
}
