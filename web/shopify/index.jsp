<%@ page import="com.mashape.unirest.http.Unirest" %>
<%@ page import="com.mashape.unirest.http.exceptions.UnirestException" %>
<%@ page import="com.albertjvm.model.ShopifyAccessToken" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String code = request.getParameter("code");
%>
<html>
<head>
  <title>SPN</title>
</head>
<body>
<%
  if (code == null) {
%>
<a href="http://storestatus.myshopify.com/admin/oauth/authorize?client_id=aa22486dd71295321f9376463cf5da0f&scope=read_products,write_products&redirect_uri=http://localhost:18080/shopify&nonce=test">Link Shopify Account</a>
<%
} else {
%>
Thank you
<%
    try {
        ShopifyAccessToken accessToken = Unirest.post("http://localhost:18080/shopify/oauth/token")
            .queryString("authCode", code)
            .queryString("shop", "storestatus")
            .asObject(ShopifyAccessToken.class)
            .getBody();

        Unirest.post("http://localhost:18080/shopify/webhook/subscribe")
            .queryString("shop", accessToken.getShopName())
            .asString();
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }
%>
<br />
</body>
</html>
