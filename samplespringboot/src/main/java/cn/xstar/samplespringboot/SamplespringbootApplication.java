package cn.xstar.samplespringboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SamplespringbootApplication {

    @Value(value = "${author.name}")
    private String name;
    @Value(value = "${author.age}")
    private int age;
    @Value(value = "${author.lv}")
    private int lv;
    @Value(value = "${author.guotations}")
    private String sign;

    public static void main(String[] args) {
        SpringApplication.run(SamplespringbootApplication.class, args);
    }

    @RequestMapping(value = "/", produces = "text/plain;charset=UTF-8")
    String index() {
        return "Hello String Boot! by author:" + name + " age:" + age + " level:" + lv + "sign:" + sign;
    }
}
