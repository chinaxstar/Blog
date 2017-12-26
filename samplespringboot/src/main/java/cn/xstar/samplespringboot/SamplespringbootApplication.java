package cn.xstar.samplespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SamplespringbootApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(SamplespringbootApplication.class, args);
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
