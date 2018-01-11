<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>商品情報変更-確認画面 | クオンツ市場-商品マスタ管理</title>
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

<%
String updateParameter = (String)session.getAttribute("updatePara");
if (updateParameter!=null && updateParameter.equals("1")) {
%>
      <h2>商品情報変更確認</h2>
      <p class="lead">以下の商品情報を変更します。よろしいですか？</p>

      <form id="detail" action="Update" method="get">
        <dl>
          <dt>商品No</dt>
          <dd class="center">${updateItem.getFormattedItemNo()}</dd>
          <dt>商品分類</dt>
          <dd class="center">${updateItem.getItemCategoryName() }</dd>
          <dt>商品名</dt>
          <dd>${updateItem.getFormattedItemName()}</dd>
          <dt>説明</dt>
          <dd>${updateItem.getFormattedExplanation()} </dd>
          <dt>価格</dt>
          <dd class="right">${updateItem.getFormattedPrice()}</dd>
          <dt>おすすめ</dt>
          <dd class="center">${updateItem.getFormattedRecommendFLG(1)}</dd>
        </dl>
        <p id="btn_wrapper">
          <a class="btn_a" href="update.jsp">戻る</a>
          <input class="btn" type="submit" id="submit_button" value="変更">
        </p>
      </form>

<%
} else {
%>
      <h2>商品情報変更</h2>
      <p class="lead err">エラーが発生しました。</p>
      <div id="btn_wrapper">
        <a class="btn_a" href="Search?ITEM_CATEGORY_NAME=${categoryNameRecieved}&ITEM_NAME=${itemNameRecieved}">一覧へ</a>
      </div>
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
