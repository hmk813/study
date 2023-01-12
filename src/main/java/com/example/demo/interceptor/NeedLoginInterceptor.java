package com.example.demo.interceptor;

import com.example.demo.vo.Rq;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {

    Rq rq = (Rq) req.getAttribute("rq");// 형변환을 해줘야됨

   if( !rq.isLogined() ){ //로그인이 안되어있다면 로그인후 이용해주세요라고 해라!
     rq.printHistoryBackJs("로그인 후 이용해주세요.");
     return false;
   }

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}
