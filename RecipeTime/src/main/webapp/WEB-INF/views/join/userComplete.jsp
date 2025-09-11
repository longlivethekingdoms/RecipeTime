<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>회원가입 완료</title></head>
<body>
<h2>회원가입 완료!</h2>
<p>가입이 정상적으로 완료되었습니다.</p>
<button type="button"><a href="<c:url value='/' />">홈으로</a></button>
<li><a href="<c:url value='/login/login' />">로그인</a></li>
</body>
</html>