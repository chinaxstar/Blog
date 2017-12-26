package cn.xstar.samplespringboot;

import cn.xstar.samplespringboot.templates.SampleFMTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
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
    private SampleFMTemplate template;
    Logger logger = LoggerFactory.getLogger(BlogController.class);

    @RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
    ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletContext().getContextPath();
        System.out.println(path);
        logger.error(path);
        template.bindTemplateDic(request.getServletContext(), "/templates");
        Map<String, Object> map = new HashMap<>();
        ModelAndView modelAndView = new ModelAndView("test");
        map.put("name", name);
        map.put("age", age);
        map.put("level", lv);
        map.put("sign", sign);
        modelAndView.addObject("map",map);
        return modelAndView;
    }

    @RequestMapping(value = "/register")
    public void register(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write("Register is ok!");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
