package com.example.demo.member.controller;

import com.example.demo.article.vo.Article;
import com.example.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrMemberController {
  @Autowired
  private MemberService memberService;

  @RequestMapping("/usr/member/doJoin")
  @ResponseBody
  public String doJoin(String loginId, String loginPw, String name, String nickname,
                                    String cellphoneNo, String email) {

    memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    return "성공!!!";
  }
}