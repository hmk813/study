package com.example.demo.vo;

import com.example.demo.service.MemberService;
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

  @Getter
  private Member loginedMember;

  private HttpServletRequest req;
  private HttpServletResponse resp;

  private HttpSession session;//HttpSession 쓰는 번거로움을 제거하기 위해 만들어주었다

  public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {

    this.req = req;
    this.resp = resp;
    this.session = req.getSession();//HttpSession 쓰는 번거로움을 제거하기 위해 만들어주었다

    HttpSession httpSession = req.getSession(); //HttpSession 가져오기
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) { //로그인되어있는것 != null이 아니란것  , 세션가져오기
      isLogined = true;
      loginedMemberId = (int) session.getAttribute("loginedMemberId");//Object로 들어가기때문에 int로 형변환 해준다.
      loginedMember = memberService.getMemberById(loginedMemberId);//로그인한 멤버Id를 가져와야지 작성자로 활용할 수 있다.
    }

    this.isLogined = isLogined;
    this.loginedMemberId = loginedMemberId;
    this.loginedMember = loginedMember;
  }

  public void printHistoryBackJs(String msg) {
    resp.setContentType("text/html; charset=UTF-8");//한글 깨지는거 방지
    print(Ut.jsHistoryBack(msg));
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

  public void login(Member member) {
    session.setAttribute("loginedMemberId", member.getId()); //세션 설정
  }

  public void logout() {
    session.removeAttribute("loginedMemberId"); //세션에서 삭제처리
  }

  public String historyBackJsOnView(String msg) {
    req.setAttribute("msg", msg);
    req.setAttribute("historyBack", true);
    return "common/js";
  }


  public String jsHistoryBack(String msg) {
    return Ut.jsHistoryBack(msg);
  }

  public String jsReplace(String msg, String uri) {
    return Ut.jsReplace(msg, uri);
  }
}
