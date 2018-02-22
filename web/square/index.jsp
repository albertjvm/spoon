<%@ page import="com.mashape.unirest.http.Unirest" %>
<%@ page import="com.mashape.unirest.http.exceptions.UnirestException" %>
<%@ page import="com.albertjvm.model.SquareAccessToken" %>
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
<a href="https://connect.squareup.com/oauth2/authorize?client_id=sq0idp-aLcZa0mXJmRDknr_zuCXCg&scope=PAYMENTS_READ%20MERCHANT_PROFILE_READ">Link Square Account</a>
<%
} else {
%>
Thank you
<%
    try {
      SquareAccessToken accessToken =
          Unirest.post("http://localhost:18080/square/oauth/token")
              .queryString("authCode", code)
              .asObject(SquareAccessToken.class)
              .getBody();

      Unirest.post("http://localhost:18080/square/webhook/subscribe")
          .queryString("merchantId", accessToken.getMerchantId())
          .asString();
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }
%>
</body>
</html>
