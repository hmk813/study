package com.example.demo.interceptor;

import com.example.demo.service.MemberService;
import com.example.demo.vo.Rq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {

  //Autowired를 해주어도 되고 생성자를 만들어도된다.
  private Rq rq;

  //생성자 선언
  public BeforeActionInterceptor(Rq rq) {
    this.rq = rq;
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    // 이제는 Rq 객체가 자동으로 만들어지기 때문에 필요 없음

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}