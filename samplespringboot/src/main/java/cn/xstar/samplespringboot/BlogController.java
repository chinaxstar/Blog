package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.dao.ArticleDao;
import cn.xstar.samplespringboot.pojo.Article;
import cn.xstar.samplespringboot.pojo.Data;
import cn.xstar.samplespringboot.pojo.User;
import cn.xstar.samplespringboot.util.Const;
import cn.xstar.samplespringboot.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
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
    @Autowired
    private IndexService indexService;
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


    /**
     * 检测登录
     *
     * @param ticket cookie中存储的登录状态票据
     * @return
     */
    @RequestMapping("/login/checkstate")
    public @ResponseBody String checkLoginSession(@RequestBody String ticket) {
        Data<User> data = new Data<>();
        if (StringUtils.isEmpty(ticket)) {
            data.setCode(Const.FAILURE);
            data.setMsg("票据不存在！");
        } else {
            User user = indexService.checLoginState(ticket);
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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody String login(@RequestBody String json) throws IOException {
        Data<User> data = new Data<>();
        System.out.println(json);
        JsonNode node=JacksonUtil.getMapper().readTree(json);
        String username=node.get("username").asText("");
        String passwd=node.get("passwd").asText("");
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
                data.setMsg("用户不存在！");
            } else {
                data.setData(Collections.singletonList(user));
                data.setMsg("登陆成功");
                data.setCode(Const.SUCCESS);
            }
        }
        return JacksonUtil.toJson(data);
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public String articleDetails(@PathVariable int articleId, Model model) {
        Article article = articleDao.selectById(articleId);
        model.addAttribute(article);
        return "articleDetails";
    }


    @RequestMapping(value = "/register.action")
    public String registerPage() {
        return "register";
    }

    @RequestMapping(value = "/login.action")
    public String loginPage() {
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
