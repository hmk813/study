<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="${board.name} 게시물 리스트" />
<%@ include file="../common/head.jspf" %>

<section class="mt-5 con-min-width">
  <div class="con mx-auto px-3">
    <div>
      게시물 개수 : ${articlesCount}건
    </div>
    <div class="table-box-type-1">
      <table border="1">
        <colgroup>
          <col width="80" />
          <col width="150" />
          <col width="150" />
          <col width="150" />
        </colgroup>
        <thead>
        <tr>
          <th>번호</th>
          <th>작성날짜</th>
          <th>수정날짜</th>
          <th>작성자</th>
          <th>제목</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" items="${articles}">
          <tr>
            <th>${article.id}</th>
            <th>${article.regDate.substring(2, 16)}</th>
            <th>${article.updateDate.substring(2, 16)}</th>
            <th>${article.extra__writerName}</th>
            <th>
              <a class="btn-text-link" href="../article/detail?id=${article.id}" >${article.title}
            </th>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="page-menu mt-3 flex justify-center">
        <div class="btn-group">
          <c:forEach begin="1" end="${pagesCount}" var="i">
            <a class="btn btn-sm ${page == i ? 'btn-active' : ' '}" href="?page=${i}">${i}</a>
          </c:forEach>
        </div>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf" %>