package cn.xstar.samplespringboot.util.intercepter.config;

import cn.xstar.samplespringboot.util.intercepter.LoginIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// 关键，将拦截器作为bean写入配置中
	// 没有这个则loginIntercepter 中loginRestService注入失败
	@Bean
	public LoginIntercepter myInterceptor() {
		return new LoginIntercepter();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor()).addPathPatterns("/**");
	}
}
