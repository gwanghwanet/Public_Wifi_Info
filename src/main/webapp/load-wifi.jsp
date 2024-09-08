<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
    <%
    // 서블릿 호출
    String servletPath = "/loadWifi.do";
    RequestDispatcher dispatcher = request.getRequestDispatcher(servletPath);
    dispatcher.include(request, response);
    %>
</body>
</html>