package com.example.demo.member.controller;

import com.example.demo.member.service.MemberService;
import com.example.demo.member.vo.Member;
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

    int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

    if( loginId == null){
      return "loginId(을)를 입력 해주세요.";
    }

    if( loginPw == null){
      return "loginPw(을)를 입력 해주세요.";
    }

    if( name == null){
      return "name(을)를 입력 해주세요.";
    }

    if( nickname == null){
      return "nickname(을)를 입력 해주세요.";
    }

    if( cellphoneNo == null){
      return "cellphoneNo(을)를 입력 해주세요.";
    }

    if( email == null){
      return "email(을)를 입력 해주세요.";
    }

    //회원가입 중복 방지
    if ( id == -1) {
        return "해당 로그인 아이디는 이미 사용중입니다.";
    }

    Member member = memberService.getMemberById(id);

    return member;
  }
}
