<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원가입 약관동의</title>
		<style>
		    .check { margin: 10px 0; }
		</style>
	</head>
	<body>
	<div>
	    <ul>
	        <li><b>약관동의</b></li>
	        <li>정보입력</li>
	        <li>가입완료</li>
	    </ul>
	</div>
	
	<h2>약관동의</h2>
	<p>약관 및 개인정보 수집·이용에 대한 내용을 자세히 읽고 동의 여부를 결정해 주세요.</p>
	
	<form action="<c:url value='/join/userRegist' />" method="post">
	
	    <!-- 개별 동의 1 -->
	    <div class="check">
	        <label>
	            <input type="checkbox" id="agree01" name="agree01" value="Y" />
	            이용약관 동의(필수)
	        </label>
	    </div>
	
	    <!-- 개별 동의 2 -->
	    <div class="check">
	        <label>
	            <input type="checkbox" id="agree02" name="agree02" value="Y" />
	            개인정보 수집·이용 동의(필수)
	        </label>
	    </div>
	    
	   	<!-- 전체 동의 -->
	    <div class="check">
	        <label>
	            <input type="checkbox" id="agreeAll" />
	            전체 동의
	        </label>
	    </div>
	
	    <button type="submit">다음</button>
	</form>
	
	<script>
	const agreeAll = document.getElementById("agreeAll");
	const agree01 = document.getElementById("agree01");
	const agree02 = document.getElementById("agree02");
	
	// 전체 동의 체크 시 두 개도 체크
	agreeAll.addEventListener("change", () => {
	    agree01.checked = agreeAll.checked;
	    agree02.checked = agreeAll.checked;
	});
	
	// 개별 체크 변경 시 전체 동의 자동 반영
	[agree01, agree02].forEach(el => {
	    el.addEventListener("change", () => {
	        agreeAll.checked = agree01.checked && agree02.checked;
	    });
	});
	</script>
	</body>
</html>