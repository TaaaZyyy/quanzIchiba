<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="dao.Item" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>商品情報変更 | クオンツ市場-商品マスタ管理</title>
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
    <c:set var="updatePara" value="1" scope="session" />


    <%-- 該当する商品情報が存在しない場合/削除済みの場合など --%>
<%
Item item = (Item)session.getAttribute("itemReference");
if (item==null) {
%>
      <h2>商品情報変更</h2>
      <p class="lead err">エラーが発生しました。</p>
      <div id="btn_wrapper">
        <a class="btn_a" href="Search?ITEM_CATEGORY_NAME=${categoryNameRecieved}&ITEM_NAME=${itemNameRecieved}">一覧へ</a>
      </div>
<%-- /該当する商品情報が存在しない場合/削除済みの場合など --%>
<%
} else {
%>



      <h2>商品情報変更</h2>

<%-- 変更登録エラー表示処理 --%>
<jsp:include page="validateInput.jsp" />

<%-- 変更確認画面を経由していない場合の本文 --%>
      <form id="detail" action="Update" method="post">
<% if(session.getAttribute("updateItem")==null) {
System.out.println("updateItemがありません。");
%>
        <dl>
          <dt>商品No</dt>
          <dd class="center">${itemReference.getFormattedItemNo()}
          <input type="hidden" name="ITEM_NO" value="${itemReference.getItemNo()}"></dd>
          <dt>商品分類 <span class="must">※</span></dt>
          <dd>
            <select name="ITEM_CATEGORY_CODE" id="item_category" required>
<%
Item itemReference = (Item)session.getAttribute("itemReference");
String itemCategoryCode = itemReference.getItemCategoryCode();
String[] categoryIds = {"001","002","003","004","005"};
String[] categoryNames = {"書籍","DVD","家電","食品","その他",};
for (int i=0;i<categoryIds.length;i++) {
	if (itemCategoryCode.equals(categoryIds[i])) {
		out.println("<option selected value=\"" + categoryIds[i] + "\">" + categoryNames[i] + "</option>");
	} else {
		out.println("<option value=\"" + categoryIds[i] + "\">" + categoryNames[i] + "</option>");
	}
}
%>
            </select>
          </dd>
          <dt>商品名 <span class="must">※</span></dt>
          <dd><input type="text" name="ITEM_NAME" id="item_name" maxlength="100" required value="${itemReference.getFormattedItemNameForTextBox()}"></dd>
          <dt>説明 <span class="must">※</span></dt>
          <dd><textarea name="EXPLANATION" id="explanation" maxlength="500" required>${itemReference.getFormattedExplanationForTextArea()}</textarea></dd>
          <dt>価格 <span class="must">※</span></dt>
          <dd><input type="number" name="PRICE" id="price" min="0" max="99999" required value="${itemReference.price}">円</dd>
          <dt>おすすめ</dt>
          <%
           if (itemReference.getRecommendFLG()==1) { %>
          		<dd><label><input type="checkbox" name="RECOMMEND_FLG" id="recommend_flg" value="1" checked="checked">おすすめ商品にする</label></dd>
           <% } else { %>
            	<dd><label><input type="checkbox" name="RECOMMEND_FLG" id="recommend_flg" value="1">おすすめ商品にする</label></dd>
            <% } %>
        </dl>
<%-- /変更確認画面を経由していない場合の本文 --%>


<%-- 変更確認画面を経由した場合の本文 --%>
<%} else {%>
        <dl>
          <dt>商品No</dt>
          <dd class="center">${updateItem.getFormattedItemNo()}
          <input type="hidden" name="ITEM_NO" value="${itemReference.getItemNo()}"></dd>
          <dt>商品分類 <span class="must">※</span></dt>
          <dd>
            <select name="ITEM_CATEGORY_CODE" id="item_category" required>
<%
Item updateItem = (Item)session.getAttribute("updateItem");
String itemCategoryCode = updateItem.getItemCategoryCode();
String[] categoryIds = {"001","002","003","004","005"};
String[] categoryNames = {"書籍","DVD","家電","食品","その他",};
for (int i=0;i<categoryIds.length;i++) {
	if (itemCategoryCode.equals(categoryIds[i])) {
		out.println("<option selected value=\"" + categoryIds[i] + "\">" + categoryNames[i] + "</option>");
	} else {
		out.println("<option value=\"" + categoryIds[i] + "\">" + categoryNames[i] + "</option>");
	}
}
%>
            </select>
          </dd>
          <dt>商品名 <span class="must">※</span></dt>
          <dd><input type="text" name="ITEM_NAME" id="item_name" maxlength="100" required value="${updateItem.getFormattedItemNameForTextBox()}"></dd>
          <dt>説明 <span class="must">※</span></dt>
          <dd><textarea name="EXPLANATION" id="explanation" maxlength="500" required>${updateItem.getFormattedExplanationForTextArea()}</textarea></dd>
          <dt>価格 <span class="must">※</span></dt>
          <dd><input type="number" name="PRICE" id="price" min="0" max="99999" required value="${updateItem.price}">円</dd>
          <dt>おすすめ</dt>
          <%
           if (updateItem.getRecommendFLG()==1) { %>
          		<dd><label><input type="checkbox" name="RECOMMEND_FLG" id="recommend_flg" value="1" checked="checked">おすすめ商品にする</label></dd>
           <% } else { %>
            	<dd><label><input type="checkbox" name="RECOMMEND_FLG" id="recommend_flg" value="1">おすすめ商品にする</label></dd>
            <% } %>
        </dl>
<%
}
%>
<%-- /変更確認画面を経由した場合の本文 --%>

        <p id="btn_wrapper">
          <a class="btn_a" href="reference.jsp">戻る</a>
          <input class="btn" type="submit" id="submit_button" value="確認">
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
