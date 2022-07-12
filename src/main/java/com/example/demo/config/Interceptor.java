package com.example.demo.config;

import com.example.demo.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *
 * </p>
 *
 * @Author llzyy
 * @Date 2022/7/10 11:49
 **/

@Configuration
public class Interceptor implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(JwtInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/user/login","/user/register","/**/export","/**/import","/file/**");
	}

	@Bean
	public JwtInterceptor JwtInterceptor(){
		return new JwtInterceptor();
	}
}
