<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="dao.Item" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>商品情報新規登録 | クオンツ市場-商品マスタ管理</title>
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
    	<c:set var="createPara" value="1" scope="session" />

      <h2>商品情報新規登録</h2>

<%-- 新規登録エラー表示処理 --%>
<jsp:include page="validateInput.jsp" />

<%-- 新規登録確認画面を経由していない場合の本文 --%>
      <form id="detail" action="Create" method="post">
<% if(session.getAttribute("createItem")==null) {%>
        <dl>
          <dt>商品分類 <span class="must">※</span></dt>
          <dd>
            <select name="ITEM_CATEGORY_CODE" id="item_category" required>
              <option selected disabled></option>
              <option value="001">書籍</option>
              <option value="002">DVD</option>
              <option value="003">家電</option>
              <option value="004">食品</option>
              <option value="005">その他</option>
            </select>
          </dd>
          <dt>商品名 <span class="must">※</span></dt>
          <dd><input type="text" name="ITEM_NAME" id="item_name" maxlength="100" required></dd>
          <dt>説明 <span class="must">※</span></dt>
          <dd><textarea name="EXPLANATION" id="explanation" maxlength="500" required></textarea></dd>
          <dt>価格 <span class="must">※</span></dt>
          <dd><input type="number" name="PRICE" id="price" min="0" max="99999" required>円</dd>
          <dt>おすすめ</dt>
            <dd><label><input type="checkbox" name="RECOMMEND_FLG" id="recommend_flg" value="1">おすすめ商品にする</label></dd>
        </dl>
<%-- /新規登録確認画面を経由していない場合の本文 --%>


<%-- 新規登録確認画面を経由した場合の本文 --%>
<%} else {%>
        <dl>
          <dt>商品分類 <span class="must">※</span></dt>
          <dd>
            <select name="ITEM_CATEGORY_CODE" id="item_category" required>
<%
Item createItem = (Item)session.getAttribute("createItem");
String itemCategoryCode = createItem.getItemCategoryCode();
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
          <dd><input type="text" name="ITEM_NAME" id="item_name" maxlength="100" required value="${createItem.getFormattedItemNameForTextBox()}"></dd>
          <dt>説明 <span class="must">※</span></dt>
          <dd><textarea name="EXPLANATION" id="explanation" maxlength="500" required>${createItem.getFormattedExplanationForTextArea()}</textarea></dd>
          <dt>価格 <span class="must">※</span></dt>
          <dd><input type="number" name="PRICE" id="price" min="0" max="99999" required value="${createItem.price}">円</dd>
          <dt>おすすめ</dt>
          <%
           if (createItem.getRecommendFLG()==1) { %>
          		<dd><label><input type="checkbox" name="RECOMMEND_FLG" id="recommend_flg" value="1" checked="checked">おすすめ商品にする</label></dd>
           <% } else { %>
            	<dd><label><input type="checkbox" name="RECOMMEND_FLG" id="recommend_flg" value="1">おすすめ商品にする</label></dd>
            <% } %>
        </dl>
<% } %>
<%-- /新規登録確認画面を経由した場合の本文 --%>



        <p id="btn_wrapper">
          <a class="btn_a" href="Search?ITEM_CATEGORY_NAME=${categoryNameRecieved}&ITEM_NAME=${itemNameRecieved}">一覧へ</a>
		  <input class="btn" type="submit" id="submit_button" value="確認">
        </p>
      </form>


    </div>
    <!-- / メイン -->
    <footer class="fixed">
      <small>&copy; 2016 quanz co., ltd.</small>
    </footer>

  </body>
</html>
