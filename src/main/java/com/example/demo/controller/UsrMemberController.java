package com.example.demo.controller;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrMemberController {
  @Autowired
  private MemberService memberService;

  @RequestMapping("/usr/member/doJoin")
  @ResponseBody
  public Object doJoin(String loginId, String loginPw, String name, String nickname,
                       String cellphoneNo, String email) { //Object 나중에 코드 개선할꺼임!

    if( Ut.empty(loginId) ){
      return "loginId(을)를 입력 해주세요.";
    }

    if(Ut.empty(loginPw)  ){
      return "loginPw(을)를 입력 해주세요.";
    }

    if( Ut.empty(name)  ){
      return "name(을)를 입력 해주세요.";
    }

    if( Ut.empty(nickname)  ){
      return "nickname(을)를 입력 해주세요.";
    }

    if( Ut.empty(cellphoneNo) ){
      return "cellphoneNo(을)를 입력 해주세요.";
    }

    if( Ut.empty(email) ){
      return "email(을)를 입력 해주세요.";
    }

    int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    //resultCode 로그인했는지 안했는지 결과를 알려주는 코드

    //회원가입 중복 방지
    if ( id == -1) {
    }

    if ( id == -2){
      return Ut.f("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email);
    }

    Member member = memberService.getMemberById(id);

    return member;
  }
}
