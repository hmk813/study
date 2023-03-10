package com.example.demo.config;

import com.example.demo.interceptor.BeforeActionInterceptor;
import com.example.demo.interceptor.NeedLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
  // beforeActionInterceptor 인터셉터 불러오기
  @Autowired
  BeforeActionInterceptor beforeActionInterceptor;

  // needLoginInterceptor 인터셉터 불러오기
  @Autowired
  NeedLoginInterceptor needLoginInterceptor;

  // 이 함수는 인터셉터를 적용하는 역할을 합니다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(beforeActionInterceptor)// 앞에 추가한것부터 실행된다 !(순서)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**")
        .excludePathPatterns("/js/**")
        .excludePathPatterns("/error");

    registry.addInterceptor(needLoginInterceptor)
        .addPathPatterns("/usr/article/write")
        .addPathPatterns("/usr/article/doWrite")
        .addPathPatterns("/usr/article/modify")
        .addPathPatterns("/usr/article/doModify")
        .addPathPatterns("/usr/article/doDelete");
  }
}
