package com.example.demo.controller;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsrArticleController {

  @Autowired
  private ArticleService articleService;

  //액션 메서드 시작
  @RequestMapping("/usr/article/doAdd")
  @ResponseBody
  public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {//HttpSession을 HttpServletRequest로 바꾼다.
    Rq rq = new Rq(req);

    if (rq.isLogined() == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    if (Ut.empty(title)) {
      return ResultData.from("F-1", "title을 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return ResultData.from("F-2", "body(을)를 입력해주세요.");
    }

    ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body); // ResultData 뒤에 Rd 붙여준다!

    int id = (int) writeArticleRd.getData1(); //형변환 필요!

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    return ResultData.newData(writeArticleRd, "article", article);
  }

  @RequestMapping("/usr/article/list")//리스트로 바꿔줌
  public String showList(HttpServletRequest req, Model model) {// Model model 암기!

    Rq rq = new Rq(req);

    List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());

    model.addAttribute("articles", articles); //역시 암기! JSP로 보내준다

    return "usr/article/list";
  }

  @RequestMapping("/usr/article/detail")//상세보기로 바꿔줌
  public String showDetail(HttpServletRequest req, Model model, int id) {

    Rq rq = new Rq(req);

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    model.addAttribute("article", article);

    return "usr/article/detail";

  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public String doDelete(HttpServletRequest req, int id) {

    Rq rq = new Rq(req);

    if (rq.isLogined() == false) {
      return Ut.jsHistoryBack("로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if (article.getMemberId() != rq.getLoginedMemberId()) { //로그인아이디랑 멤버아이디랑 다르면 권한이 없다고 해야함
      return Ut.jsHistoryBack( "권한이 없습니다.");
    }

    if (article == null) {
      return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //실패
    }

    articleService.deleteArticle(id);

    return Ut.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");//성공
  }


  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {

    Rq rq = new Rq(req);

    if (rq.isLogined() == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId() , id);

    if (article.getMemberId() != rq.getLoginedMemberId()) { //로그인아이디랑 멤버아이디랑 다르면 권한이 없다고 해야함
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);//수정할 수 있다.

    if(actorCanModifyRd.isFail()){
      return actorCanModifyRd;
    }

    return articleService.modifyArticle(id, title, body);
    }

}

