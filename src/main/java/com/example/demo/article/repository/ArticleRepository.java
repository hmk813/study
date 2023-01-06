package com.example.demo.article.repository;

import com.example.demo.article.vo.Article;
import lombok.experimental.Delegate;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ArticleRepository {

  @Insert("INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = #{title}, BODY = #{body}")
  public void writeArticle(String title, String body);

  @Select("SELECT * FROM article WHERE id = #{id}")
  public Article getArticle(int id);

  @Delete("DELETE FROM article WHERE id = #{id}")
  public void deleteArticle(int id);

  @Select("SELECT * FROM article ORDER BY id DESC")
  public List<Article> getArticles();

  @Update("UPDATE article SET title = #{title}, BODY = #{body}, updateDate = NOW() WHERE id = #{id}")
  public void modifyArticle(int id, String title, String body);

  @Select("SELECT LAST_INSERT_ID()")
  public int getLastInsertId();
}
