package com.example.demo.controller;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;

@Controller
public class UsrMemberController {
  @Autowired
  private MemberService memberService;

  @RequestMapping("/usr/member/doJoin")
  @ResponseBody
  public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname,
                           String cellphoneNo, String email) { //Object -> ResultData로 코드 개선

    if( Ut.empty(loginId) ){
      return ResultData.from("F-1","loginId(을)를 입력 해주세요.");
    }

    if(Ut.empty(loginPw)  ){
      return ResultData.from("F-2","loginPw(을)를 입력 해주세요.");
    }

    if( Ut.empty(name)  ){
      return ResultData.from("F-3","name(을)를 입력 해주세요.");
    }

    if( Ut.empty(nickname)  ){
      return ResultData.from("F-4","nickname(을)를 입력 해주세요.");
    }

    if( Ut.empty(cellphoneNo) ){
      return ResultData.from("F-5","cellphoneNo(을)를 입력 해주세요.");
    }

    if( Ut.empty(email) ){
      return ResultData.from("F-6","email(을)를 입력 해주세요.");
    }

    // S-1
    // 회원가입이 완료되었습니다.
    // 7
    ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
    //ResultData 뒤에 Rd를 약어로 해서 붙인다.


    if ( joinRd.isFail() ) {
      return (ResultData) joinRd;
    }

    Member member = memberService.getMemberById(joinRd.getData1()); //getData 들어오는 방식이 Object방식이라 int로 꼭 형변환을 해줘야됨

    return ResultData.newData(joinRd, member);//
  }

  @RequestMapping("/usr/member/doLogin")
  @ResponseBody
  public ResultData<Member> doLogin(HttpSession httpSession, String loginId, String loginPw) { //Object -> ResultData로 코드 개선

    boolean isLogined = false;

    if(httpSession.getAttribute("loginedMemberId") != null){ //로그인되어있는것 != null이 아니란것  ,
       isLogined = true;
    }

    if( isLogined ){
      return ResultData.from("F-5","이미 로그인되었습니다.");
    }

    if( Ut.empty(loginId) ){
      return ResultData.from("F-1","loginId(을)를 입력 해주세요.");
    }

    if(Ut.empty(loginPw)  ){
      return ResultData.from("F-2","loginPw(을)를 입력 해주세요.");
    }

    Member member = memberService.getMemberByLoginId(loginId);

    if (member == null){
      return ResultData.from("F-3", "존재하지 않는 로그인아이디 입니다.");
    }

    if(member.getLoginPw().equals(loginPw) == false){
      return ResultData.from("F-4","비밀번호가 일치하지 않습니다.");
    }

    httpSession.setAttribute("loginedMemberId", member.getId());//세션 설정

    return ResultData.from("S-1", Ut.f("%s님 환영합니다.", member.getNickname()));//
  }

  @RequestMapping("/usr/member/doLogout")
  @ResponseBody
  public ResultData<Member> doLogout(HttpSession httpSession) { //Object -> ResultData로 코드 개선

    boolean isLogined = false;

    if(httpSession.getAttribute("loginedMemberId") == null){ //이미 null이다(이미 로그아웃상태다)
      isLogined = true;
    }

    if( isLogined ){
      return ResultData.from("S-1","이미 로그아웃 상태입니다.");
    }

    httpSession.removeAttribute("loginedMemberId"); //세션 삭제

    return ResultData.from("S-2", "로그아웃 되었습니다.");//
  }
}
