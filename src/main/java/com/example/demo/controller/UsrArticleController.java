package com.example.demo.controller;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UsrArticleController {

  @Autowired
  private ArticleService articleService;

  //액션 메서드 시작
  @RequestMapping("/usr/article/doAdd")
  @ResponseBody
  public ResultData<Article> doAdd(String title, String body) {

    if( Ut.empty(title) ){
      return ResultData.from("F-1", "title을 입력해주세요.");
    }

    if( Ut.empty(body) ){
      return ResultData.from("F-2", "body(을)를 입력해주세요.");
    }

    ResultData <Integer> writeArticleRd = articleService.writeArticle(title, body); // ResultData 뒤에 Rd 붙여준다!

    int id = (int)writeArticleRd.getData1(); //형변환 필요!

    Article article = articleService.getArticle(id);

    return ResultData.newData(writeArticleRd, article);
  }

  @RequestMapping("/usr/article/getArticles")
  @ResponseBody
  public ResultData<List<Article>> getArticles() {
    List<Article> articles = articleService.getArticles();

    return ResultData.from("S-1","게시물 리스트입니다.", articles);
  }

  @RequestMapping("/usr/article/getArticle")
  @ResponseBody
  public ResultData<Article> getArticle(int id) {
    Article article = articleService.getArticle(id);

    if( article == null ){
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //실패

    }

    return ResultData.from("S-1",Ut.f("%d번 게시물입니다.", id), article);//성공
  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public ResultData<Integer> doDelete(int id) {
    Article article = articleService.getArticle(id);

    if( article == null ) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //실패
    }

    articleService.deleteArticle(id);

    return ResultData.from("S-1",Ut.f("%d번 게시물을 삭제하였습니다.", id), id);//성공
  }


  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public ResultData doModify(int id,String title, String body) {
    Article article = articleService.getArticle(id);

    if( article == null ) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    articleService.modifyArticle(id, title, body);

    return ResultData.from("S-1",Ut.f("%d번 게시물을 수정하였습니다.", id), id);
  }


  //액션 메서드 끝

}