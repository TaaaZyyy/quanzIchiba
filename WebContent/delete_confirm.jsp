<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.Item" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>商品情報削除確認 | クオンツ市場-商品マスタ管理</title>
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
    <c:set var="deletePara" value="1" scope="session" />

<%
Item item = (Item)session.getAttribute("itemReference");
if (item==null) {
%>

      <h2>商品情報削除</h2>
      <p class="lead err">エラーが発生しました。</p>
      <div id="btn_wrapper">
        <a class="btn_a" href="Search?ITEM_CATEGORY_NAME=${categoryNameRecieved}&ITEM_NAME=${itemNameRecieved}">一覧へ</a>
      </div>

<%
} else {
%>

      <h2>商品情報<em>削除</em>確認</h2>
      <p class="lead">以下の商品情報を削除します。よろしいですか？</p>

      <form id="detail" action="Delete" method="get">
        <dl>
          <dt>商品No</dt>
          <dd class="center">${itemReference.getFormattedItemNo()}
          <input type="hidden" name="ITEM_NO" value="${itemReference.getItemNo()}"></dd>
          <dt>商品分類</dt>
          <dd class="center">${itemReference.getItemCategoryName()}</dd>
          <dt>商品名</dt>
          <dd>${itemReference.getFormattedItemName()}</dd>
          <dt>説明</dt>
          <dd>${itemReference.getFormattedExplanation()}</dd>
          <dt>価格</dt>
          <dd class="right">${itemReference.getFormattedPrice()}</dd>
          <dt>おすすめ</dt>
          <dd class="center">${itemReference.getFormattedRecommendFLG(1)}</dd>
        </dl>
        <p id="btn_wrapper">
          <a class="btn_a" href="reference.jsp">戻る</a>
          <input class="btn" type="submit" id="submit_button" value="削除">
        </p>
      </form>

<%
}
%>

    </div>
    <!-- / メイン -->
    <footer class="fixed">
      <small>&copy; 2016 quanz co., ltd.</small>
    </footer>

  </body>
</html>
