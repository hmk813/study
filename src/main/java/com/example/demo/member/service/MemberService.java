package com.example.demo.member.service;

import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.vo.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

    Member oldMember = getMemberByLoginId(loginId);

    if( oldMember != null ){
      return -1; //로그인id가 있다면
    }

    memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    return memberRepository.getLastInsertId();
  }

  private Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }

  public Member getMemberById(int id) {
    return memberRepository.getMemberById(id);
  }
}
