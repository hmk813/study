package com.example.demo.controller;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    return ResultData.newData(joinRd, "member", member);//
  }

  @RequestMapping("/usr/member/login")
  public String showLogin(HttpSession httpSession){
    return "usr/member/login";
  }

  @RequestMapping("/usr/member/doLogin")
  @ResponseBody
  public String doLogin(HttpServletRequest req, String loginId, String loginPw) { //Object -> ResultData로 코드 개선

    Rq rq = (Rq) req.getAttribute("rq");// 형변환을 해줘야됨

    if( rq.isLogined() ){
      return Ut.jsHistoryBack("이미 로그인되었습니다.");
    }

    if( Ut.empty(loginId) ){
      return Ut.jsHistoryBack("loginId(을)를 입력 해주세요.");
    }

    if(Ut.empty(loginPw)  ){
      return Ut.jsHistoryBack("loginPw(을)를 입력 해주세요.");
    }

    Member member = memberService.getMemberByLoginId(loginId);

    if (member == null){
      return Ut.jsHistoryBack("존재하지 않는 로그인아이디 입니다.");
    }

    if(member.getLoginPw().equals(loginPw) == false){
      return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다.");
    }

    //httpSession.setAttribute("loginedMemberId", member.getId());//세션 설정->이거를 이제 rq로 위임한다!
    rq.login(member);

    return Ut.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), "/");//
  }

  @RequestMapping("/usr/member/doLogout")
  @ResponseBody
  public String doLogout(HttpServletRequest req) { //Object -> ResultData로 코드 개선

    Rq rq = (Rq) req.getAttribute("rq");// 형변환을 해줘야됨

    if( !rq.isLogined() ){//로그인된 상태가 아니라면 -> 즉, 이미 로그아웃 상태
      return rq.jsHistoryBack("이미 로그아웃 상태입니다.");
    }

    //httpSession.removeAttribute("loginedMemberId"); //세션 삭제
    rq.logout();//rq에다가 위임한다

    return rq.jsReplace(Ut.f("로그아웃 되었습니다."), "/");//
  }
}
