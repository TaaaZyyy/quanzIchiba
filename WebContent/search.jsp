<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.*" import="dao.Item" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>商品情報検索 | クオンツ市場-商品マスタ管理</title>
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
		<c:remove var="createItem" scope="session" />
		<c:remove var="updateItem" scope="session" />
		<c:remove var="updatePara" scope="session" />
		<c:remove var="deletePara" scope="session" />

      <h2>商品情報検索</h2>
      <p><a class="btn_a" id="btn_create" href="create.jsp">新規商品追加</a></p>

      <form id="serch" action="Search" method="get">
        <dl>
          <dt>商品分類</dt>
          <dd>
            <select name="ITEM_CATEGORY_NAME" id="item_category">
<%
String itemCategoryNameSelected = (String)session.getAttribute("categoryNameRecieved");
if (itemCategoryNameSelected==null) {
%>
              <option selected></option>
              <option value="書籍">書籍</option>
              <option value="DVD">DVD</option>
              <option value="家電">家電</option>
              <option value="食品">食品</option>
              <option value="その他">その他</option>
<%
} else {
	out.println("<option selected></option>");
	String[] categoryNames = {"書籍","DVD","家電","食品","その他",};
	for (int i=0;i<categoryNames.length;i++) {
		if (categoryNames[i].equals(itemCategoryNameSelected)) {
			out.println("<option selected value=\"" + categoryNames[i] + "\">" + categoryNames[i] + "</option>");
		} else {
			out.println("<option value=\"" + categoryNames[i]  + "\">" + categoryNames[i] + "</option>");
		}
	}
}
%>
            </select>
          </dd>
          <dt>商品名</dt>
<%
String itemNameRecieved = (String)session.getAttribute("itemNameRecieved");
if (itemNameRecieved==null) {
%>
          <dd><input type="text" name="ITEM_NAME" id="item_name" maxlength="100"></dd>

<%
} else {
	out.println("<dd><input type=\"text\" name=\"ITEM_NAME\" id=\"item_name\" maxlength=\"100\" value=\""
		+  itemNameRecieved + "\"></dd>");
}
%>
        </dl>
        <input class="btn" id="serch_btn" type="submit" id="submit_button" value="検索">
      </form>
      <c:if test="${listSize != null}">
        <p class="lead">
        検索条件： 商品分類 = <c:if test="${categoryIsBlank==1}">ALL</c:if>${categoryNameRecieved} /
        商品名 = <c:if test="${nameIsBlank==1}">ALL</c:if>${itemNameRecieved} <br>
         該当件数 :  ${listSize} 件</p>
      </c:if>

      <c:if test="${listSize == 0}">
      	<p class="lead err">※検索条件に該当する商品はありません。</p>
      </c:if>

      <c:if test="${listSize != null && listSize != 0}">
	      <table>
	        <tr>
	          <th class="no">No</th>
	          <th class="category">商品分類</th>
	          <th class="name">商品名</th>
	          <th class="price">価格</th>
	          <th class="recomend">おすすめ</th>
	        </tr>
	        <c:forEach var="item" items="${list}" varStatus="status">
		          <tr>
		          <td class="center no">${status.count}</td>
		          <td class="center category">${item.itemCategoryName}</td>
		          <td class="left name"><a href="Refer?referTo=${item.itemNo}">${item.getFormattedItemName()}</a></td>
		          <td class="right price">${item.getFormattedPrice()}</td>
		          <td class="center recomend">${item.getFormattedRecommendFLG(0)}</td>
		        </tr>
	         </c:forEach>
	      </table>
      </c:if>
    </div>
    <!-- / メイン -->
    <footer>
      <small>&copy; 2016 quanz co., ltd.</small>
    </footer>

  </body>
</html>