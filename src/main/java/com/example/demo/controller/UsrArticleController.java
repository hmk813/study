package com.example.demo.controller;

import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Board;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UsrArticleController {

  //요샌 Autowired 안하고 생성자를 만드는 추세이다.
  private ArticleService articleService;
  private BoardService boardService;
  private Rq rq;

  //생성자 선언
  public UsrArticleController(ArticleService articleService, BoardService boardService, Rq rq) {
    this.articleService = articleService;
    this.boardService = boardService;
    this.rq = rq;
  }

  @RequestMapping("/usr/article/write")
  public String showWrite(HttpServletRequest req) {
    return "usr/article/write";
  }

  //액션 메서드 시작
  @RequestMapping("/usr/article/doWrite")
  @ResponseBody
  public String doWrite(String title, String body, String replaceUri) {//HttpSession을 HttpServletRequest로 바꾼다.

    if (  rq.isLogined() == false ) {
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }

    if (Ut.empty(title)) {
      return rq.jsHistoryBack("title(을)를 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return rq.jsHistoryBack("body(을)를 입력해주세요.");
    }

    ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body); // ResultData 뒤에 Rd 붙여준다!

    int id = (int) writeArticleRd.getData1(); //형변환 필요!

    if(Ut.empty(replaceUri)){
      replaceUri = Ut.f("../article/detail?id=%d", id);
    }

    return rq.jsReplace(Ut.f("%d번 게시물이 생성되었습니다.", id), replaceUri);
  }

  @RequestMapping("/usr/article/list")//리스트로 바꿔줌
  public String showList(Model model, int boardId) {// Model model 암기!

    Board board = boardService.getBoardById(boardId);

    if( board == null ){//존재하지 않는 게시판 체크 추가!
      return rq.historyBackJsOnView(Ut.f("%d번 게시판은 존재하지 않습니다.",  boardId));
    }

    int articlesCount = articleService.getArticlesCount(boardId); //게시물리스트에 게시물 개수 표시 추가!
    List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId);

    model.addAttribute("board",board); //게시판을 만들었으니 추가해준다.
    model.addAttribute("articlesCount", articlesCount);
    model.addAttribute("articles", articles); //역시 암기! JSP로 보내준다

    return "usr/article/list";
  }

  @RequestMapping("/usr/article/detail")//상세보기로 바꿔줌
  public String showDetail(Model model, int id) {
    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    model.addAttribute("article", article);

    return "usr/article/detail";

  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public String doDelete(int id) {

    if (rq.isLogined() == false) {
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if (article.getMemberId() != rq.getLoginedMemberId()) { //로그인아이디랑 멤버아이디랑 다르면 권한이 없다고 해야함
      return rq.jsHistoryBack( "권한이 없습니다.");
    }

    if (article == null) {
      return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //실패
    }

    articleService.deleteArticle(id);

    return rq.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");//성공
  }

  @RequestMapping("/usr/article/modify")
  public String showModify(Model model, int id) {

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId() , id);

    if( article == null ){
      return rq.historyBackJsOnView(Ut.f("%번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);//수정할 수 있다.

    if(actorCanModifyRd.isFail()){
      return rq.historyBackJsOnView(actorCanModifyRd.getMsg());//일치하지 않으면 권한이 없다!
    }

    model.addAttribute("article", article);

    return "usr/article/modify";
  }

  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public String doModify(int id, String title, String body) {

    if (  rq.isLogined() == false ) {
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId() , id);

    if (article.getMemberId() != rq.getLoginedMemberId()) { //로그인아이디랑 멤버아이디랑 다르면 권한이 없다고 해야함
      return rq.jsHistoryBack("권한이 없습니다.");
    }

    if( article == null ){
        return rq.jsHistoryBack(Ut.f("%번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);//수정할 수 있다.

    if(actorCanModifyRd.isFail()){
      return rq.jsHistoryBack(actorCanModifyRd.getMsg());
    }

    articleService.modifyArticle(id, title, body);

    return rq.jsReplace(Ut.f("%d번 게시물이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
    }

}

