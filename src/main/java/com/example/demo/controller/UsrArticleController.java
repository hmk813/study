package com.example.demo.controller;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsrArticleController {

  @Autowired
  private ArticleService articleService;

  //액션 메서드 시작
  @RequestMapping("/usr/article/doAdd")
  @ResponseBody
  public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {

    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) { //로그인되어있는것 != null이 아니란것  , 세션가져오기
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");//Object로 들어가기때문에 int로 형변환 해준다.
    }

    if (isLogined == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    if (Ut.empty(title)) {
      return ResultData.from("F-1", "title을 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return ResultData.from("F-2", "body(을)를 입력해주세요.");
    }

    ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body); // ResultData 뒤에 Rd 붙여준다!

    int id = (int) writeArticleRd.getData1(); //형변환 필요!

    Article article = articleService.getArticle(id);

    return ResultData.newData(writeArticleRd, "article", article);
  }

  @RequestMapping("/usr/article/list")
  public String showList(Model model) {// Model model 암기!
    List<Article> articles = articleService.getArticles();

    model.addAttribute("articles", articles); //역시 암기! JSP로 보내준다

    return "usr/article/list";
  }

  @RequestMapping("/usr/article/getArticle")
  @ResponseBody
  public ResultData<Article> getArticle(int id) {
    Article article = articleService.getArticle(id);

    if (article == null) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //실패

    }

    return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id),  "article", article);//성공
  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public ResultData<Integer> doDelete(HttpSession httpSession, int id) {

    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) { //로그인되어있는것 != null이 아니란것  , 세션가져오기
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");//Object로 들어가기때문에 int로 형변환 해준다.
    }

    if (isLogined == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    Article article = articleService.getArticle(id);

    if (article.getMemberId() != loginedMemberId) { //로그인아이디랑 멤버아이디랑 다르면 권한이 없다고 해야함
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    if (article == null) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //실패
    }

    articleService.deleteArticle(id);

    return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제하였습니다.", id), "id", id);//성공
  }


  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {


    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) { //로그인되어있는것 != null이 아니란것  , 세션가져오기
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");//Object로 들어가기때문에 int로 형변환 해준다.
    }

    if (isLogined == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    Article article = articleService.getArticle(id);

    if (article.getMemberId() != loginedMemberId) { //로그인아이디랑 멤버아이디랑 다르면 권한이 없다고 해야함
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);//수정할 수 있다.

    if(actorCanModifyRd.isFail()){
      return actorCanModifyRd;
    }

    return articleService.modifyArticle(id, title, body);
    }

}

