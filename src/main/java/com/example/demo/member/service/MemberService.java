package com.example.demo.member.service;

import com.example.demo.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
  //private MemberRepository memberRepository;

  public void join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
   // memberRepository .join(loginId, loginPw, name, nickname, cellphoneNo, email);
  }
}