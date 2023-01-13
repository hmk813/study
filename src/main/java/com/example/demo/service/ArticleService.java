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

  public ResultData writeArticle(int memberId, String title, String body) {
    articleRepository.writeArticle(memberId, title, body);
    int id = articleRepository.getLastInsertId();

    return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }

  public List<Article> getForPrintArticles(int actorId) {
    List<Article> articles = articleRepository.getForPrintArticles();

    for(Article article : articles){ //어려움 쉬운거 아님@@@!
      updateForPrintData(actorId, article);
    }

    return articles;
  }

  private void updateForPrintData(int actorId, Article article) {

    if(  article == null  ){//게시물이 없다면
      return;
    }

    ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
    article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

    ResultData actorCanModifyRd = actorCanModify(actorId, article);
    article.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
  }

  public Article getForPrintArticle(int actorId, int id) {
   Article article = articleRepository.getForPrintArticle(id);

   updateForPrintData(actorId, article);

   return article;
  }

  public void deleteArticle(int id) {
    //void는 return 타입이 없다!
    articleRepository.deleteArticle(id);
  }


  public ResultData<Article> modifyArticle(int id, String title, String body) {
    //void는 return 타입이 없다!
    articleRepository.modifyArticle(id, title, body);

    Article article = getForPrintArticle(0, id);
    return ResultData.from("S-1", Ut.f("%d번 게시물을 수정하였습니다.", id), "article", article);
  }

  public ResultData actorCanModify(int actorId, Article article) {
    if ( article == null ){ //권한이 없다!
      return ResultData.from("F-1", "권한이 없습니다.");
    }

    if( article.getMemberId() !=  actorId ){
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    return ResultData.from("S-1", "게시물 수정이 가능합니다.");
  }

  public ResultData actorCanDelete(int actorId, Article article) {
    if ( article == null ){ //권한이 없다!
      return ResultData.from("F-1", "권한이 없습니다.");
    }

    if( article.getMemberId() !=  actorId ){
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    return ResultData.from("S-1", "게시물 삭제가 가능합니다.");
  }
}
