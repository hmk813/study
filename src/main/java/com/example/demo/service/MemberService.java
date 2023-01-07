package com.example.demo.service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public ResultData join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

    // 로그인 아이디 중복 체크
    Member oldMember = getMemberByLoginId(loginId);

    if( oldMember != null ){
      return ResultData.from("F-7", Ut.f("해당 로그인아이디는(%s)는 이미 사용중입니다.", loginId)); //로그인id가 있다면

    }

    // 이름 + 이메일 중복 체크
    oldMember = getMemberByNameAndEmail(name, email);

    if( oldMember != null ){
      return ResultData.from("F-8", Ut.f("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email));//name과 email이 있다면
    }

    memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    int id = memberRepository.getLastInsertId();

    return ResultData.from("S-1", "회원가입이 완료되었습니다.", id);
  }

  private Member getMemberByNameAndEmail(String name, String email) {
    return memberRepository.getMemberByNameAndEmail(name, email);
  }

  private Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }

  public Member getMemberById(int id) {
    return memberRepository.getMemberById(id);
  }
}
