package com.example.demo.vo;

import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Rq {

  @Getter
  private boolean isLogined;
  @Getter
  private final int loginedMemberId;
  public Rq(HttpServletRequest req) {

    HttpSession httpSession = req.getSession(); //HttpSession 가져오기

    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) { //로그인되어있는것 != null이 아니란것  , 세션가져오기
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");//Object로 들어가기때문에 int로 형변환 해준다.
    }

    this.isLogined = isLogined;
    this.loginedMemberId = loginedMemberId;
  }

}
