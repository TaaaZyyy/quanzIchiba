<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <title>クオンツ市場-商品マスタ管理</title>
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>

    <!-- ヘッダー -->
    <header>
      <h1>クオンツ市場</h1>
      <div id="header_right">
        <p>ログイン画面</p>
      </div>
    </header>
    <!-- / ヘッダー -->

    <!-- メイン -->
    <div id="main">
      <h1>クオンツ市場<br>商品マスタ管理</h1>

      <form id="login_form" action="Login" method="post">
        <div id="form_box">
          <ul>
          <%
          String loginParam = (String)session.getAttribute("loginParam");
//          System.out.println("ログインパラメータ（JSP）：" + loginParam);
          if (loginParam == "ng") {
  			%>
            <li><p class="leader err">※ユーザID か PASSWORD が間違っています。</p></li>
            <%
            session.removeAttribute("loginParam");
//            以下のセッション要素が残っていると検索画面が正しく表示されないことがあるので取り除く。
            session.removeAttribute("categoryNameRecieved");
            session.removeAttribute("itemNameRecieved");
            session.removeAttribute("list");
            session.removeAttribute("listSize");
            }
          %>
            <li><input type="text" name="ADMIN_ID" id="admin_id" maxlength="20" placeholder="ユーザID" required></li>
            <li><input type="password" name="PASSWORD" id="password" maxlength="20" placeholder="PASSWORD" required></li>
            <li><input class="btn" id="login_btn" type="submit" id="submit_button" value="ログイン"></li>
          </ul>
        </div>
      </form>
    </div>
    <!-- / メイン -->
    <footer>
      <small>&copy; 2016 quanz co., ltd.</small>
    </footer>
  </body>
</html>
