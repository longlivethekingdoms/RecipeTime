<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header class="site-header">
		<div class="wrap">
			<div class="util">
				<ul>
					<li><a href="/">HOME</a></li>
					
					<c:choose>
		                <c:when test="${empty sessionScope.loginUser}">
		                    <li><a href="<c:url value='/login/login' />">로그인</a></li>
		                    <li><a href="<c:url value='/login/findId' />">아이디 찾기</a></li>
		                    <li><a href="<c:url value='/login/findPw' />">비밀번호 찾기</a></li>
		                    <li><a href="<c:url value='/join/siteUseAgree' />">회원가입</a></li>
		                </c:when>
		                <c:otherwise>
		                    <li><a href="<c:url value='/login/logout'/>">로그아웃</a></li>
		                    <li><a href="<c:url value='/post/insert'/>">글쓰기</a>	
		                    <li class="dropdown">
		                        <a href="#">마이홈페이지</a>
		                        <ul class="dropdown-menu">
		                            <!-- 나중에 기능별 메뉴 추가 -->
		                            <li><a href="<c:url value='/mypage/profile.do' />">프로필</a></li>
		                            <li><a href="<c:url value='/mypage/settings.do' />">설정</a></li>
		                            <li><a href="<c:url value='/mypage/activities.do' />">내 활동</a></li>
		                        </ul>
		                    </li>
		                </c:otherwise>
	                </c:choose>		            
				</ul>
			</div>
		</div>
	</header>
</body>
</html>