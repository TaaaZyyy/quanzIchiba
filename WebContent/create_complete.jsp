<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>商品情報新規登録-完了画面 | クオンツ市場-商品マスタ管理</title>
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>

    <!-- ヘッダー -->
    <header>
      <h1>クオンツ市場</h1>
      <div id="header_right">
        <a href="Logout">ログアウト</a>
        <p>ID: ${sessionScope.user}</p>
      </div>
    </header>
    <!-- / ヘッダー -->

    <!-- メイン -->
    <div id="main">

      <c:choose>
        <c:when test="${insertResult==1}">
          <h2>商品情報新規登録完了</h2>
          <p class="lead">商品情報の登録が完了しました。</p>
        </c:when>
        <c:when test="${insertResult==0}">
          <h2>商品情報新規登録失敗</h2>
          <p class="lead err">商品情報の登録が完了できませんでした。</p>
        </c:when>
        <c:otherwise>
          <h2>商品情報新規登録</h2>
          <p class="lead err">エラーが発生しました。</p>
        </c:otherwise>
      </c:choose>
      <c:remove var="insertResult" scope="session"/>

      <div id="btn_wrapper">
        <a class="btn_a" href="Search?ITEM_CATEGORY_NAME=${categoryNameRecieved}&ITEM_NAME=${itemNameRecieved}">一覧へ</a>
      </div>

    </div>
    <!-- / メイン -->
    <footer class="fixed">
      <small>&copy; 2016 quanz co., ltd.</small>
    </footer>

  </body>
</html>
