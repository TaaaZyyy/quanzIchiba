<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 新規登録エラー表示処理 --%>
<%
//==================<商品名入力値チェック>====================
String overrapErr = (String)request.getAttribute("overrapErr");
if (overrapErr!=null && overrapErr.equals("yes")) {
	out.println("<p class=\"lead err\">※すでに存在する商品名です。</p>");
}
request.removeAttribute("overrapErr");

String limitOverName = (String)request.getAttribute("limitOverName");
if (limitOverName!=null && limitOverName.equals("yes")) {
	out.println("<p class=\"lead err\">※商品名は１００文字以内で入力してください。</p>");
}
request.removeAttribute("limitOverName");

String onlySpaceName = (String)request.getAttribute("onlySpaceName");
if (onlySpaceName!=null && onlySpaceName.equals("yes")) {
	out.println("<p class=\"lead err\">※空白のみの商品名は使用できません。</p>");
}
request.removeAttribute("onlySpaceName");

//==================<説明入力値チェック>====================
String onlySpaceExp = (String)request.getAttribute("onlySpaceExp");
if (onlySpaceExp!=null && onlySpaceExp.equals("yes")) {
	out.println("<p class=\"lead err\">※空白のみの説明は使用できません。</p>");
}
request.removeAttribute("onlySpaceExp");

String limitOverExp = (String)request.getAttribute("limitOverExp");
if (limitOverExp!=null && limitOverExp.equals("yes")) {
	out.println("<p class=\"lead err\">※説明は５００文字以内で入力してください。</p>");
}
request.removeAttribute("limitOverExp");
%>
<%-- /新規登録エラー表示処理 --%>