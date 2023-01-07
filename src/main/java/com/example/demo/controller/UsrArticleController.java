package com.example.demo.controller;

import com.example.demo.service.ArticleService;
import com.example.demo.vo.Article;
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
  public Article doAdd(String title, String body) {
    int id = articleService.writeArticle(title, body);

    Article article = articleService.getArticle(id);

    return article;
  }

  @RequestMapping("/usr/article/getArticles")
  @ResponseBody
  public List<Article> getArticles() {

    return articleService.getArticles();
  }

  @RequestMapping("/usr/article/getArticle")
  @ResponseBody
  public Object getArticle(int id) {
    Article article = articleService.getArticle(id);

    if( article == null ){
      return id + "번 게시물이 존재하지 않습니다.";
    }

    return article;
  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public String doDelete(int id) {
    Article article = articleService.getArticle(id);

    if( article == null ) {
      return id + "번 게시물이 존재하지 않습니다용!";
    }

    articleService.deleteArticle(id);
    
    return id + "번 게시물을 삭제했다용!";
  }


  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public String doModify(int id,String title, String body) {
    Article article = articleService.getArticle(id);

    if( article == null ) {
      return id + "번 게시물이 존재하지 않습니다용!";
    }

    articleService.modifyArticle(id, title, body);

    return id + "번 게시물을 수정했다용!";
  }


  //액션 메서드 끝

}