package com.example.demo.service;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

  private ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository){
    this.articleRepository = articleRepository;
  }

  public ResultData writeArticle(String title, String body) {
    articleRepository.writeArticle(title, body);
    int id = articleRepository.getLastInsertId();

    return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), id);
  }

  public List<Article> getArticles() {
    return articleRepository.getArticles();
  }

  public Article getArticle(int id) {
    return articleRepository.getArticle(id);
  }

  public void deleteArticle(int id) {
    //void는 return 타입이 없다!
    articleRepository.deleteArticle(id);
  }


  public void modifyArticle(int id, String title, String body) {
    //void는 return 타입이 없다!
    articleRepository.modifyArticle(id, title, body);
  }

}
