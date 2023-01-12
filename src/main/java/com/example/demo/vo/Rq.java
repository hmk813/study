package com.example.demo.vo;

import com.example.demo.util.Ut;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Rq {

  @Getter
  private boolean isLogined;
  @Getter
  private final int loginedMemberId;

  private HttpServletRequest req;
  private HttpServletResponse resp;

  public Rq(HttpServletRequest req, HttpServletResponse resp) {

    this.req = req;
    this.resp = resp;

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

  public void printHistoryBackJs(String msg) {
    resp.setContentType("text/html; charset=UTF-8");

    println("<script>");

    if(!Ut.empty(msg)){
      println("alert(' " + msg + " ');");
    }

    println("history.back();");

    println("</script>");
  }

  public void print(String str){
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void println(String str){
    print(str + "\n");
  }
}
