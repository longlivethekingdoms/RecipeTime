<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title><c:out value="${title}"/></title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/template/header.jsp"/>
	
	<div class="main-content">
		<ul>
			<li><b><a href="<c:url value='/' />">홈</a></b></li>
			<li><a href="<c:url value='/post/list' />">분류</a></li>
			<li><a href="<c:url value='/announcement/list' />">공지사항</a></li>
		</ul>
	</div>
	
	<jsp:include page="/WEB-INF/views/template/footer.jsp"/>
</body>
</html>