<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 정보입력</title>
<style>
    .form-group { margin-bottom: 10px; }
    label { display: block; margin-bottom: 5px; }
    input[type="text"], input[type="password"], input[type="email"], input[type="tel"], input[type="date"] {
        width: 300px;
        padding: 5px;
    }
    .error { color: red; margin-top: 5px; }
</style>
</head>
<body>

<h2>회원가입 정보 입력</h2>

<c:if test="${not empty message}">
    <div class="error">${message}</div>
</c:if>

<form action="<c:url value='/join/insertUser' />" method="post">
    <div class="form-group">
        <label for="userid">아이디</label>
        <input type="text" id="userid" name="userid" value="${users.userid}" required />
        <button type="button" onclick="checkDuplicateID()">중복확인</button>
    </div>

    <div class="form-group">
        <label for="userpw">비밀번호</label>
        <input type="password" id="userpw" name="userpw" required />
    </div>

    <div class="form-group">
        <label for="confirmPw">비밀번호 확인</label>
        <input type="password" id="confirmPw" name="confirmPw" required />
    </div>

    <div class="form-group">
        <label for="nickname">닉네임</label>
        <input type="text" id="nickname" name="nickname" value="${users.nickname}" required />
        <button type="button" onclick="checkDuplicateNickname()">중복확인</button>
    </div>

    <div class="form-group">
        <label for="useremail">이메일</label>
        <input type="email" id="useremail" name="useremail" value="${users.useremail}" required />
        <button type="button" onclick="checkDuplicateEmail()">중복확인</button>
    </div>

    <div class="form-group">
        <label for="usertel">전화번호</label>
        <input type="tel" id="usertel" name="usertel" value="${users.usertel}" required />
    </div>

    <div class="form-group">
        <label for="userbirth">생년월일</label>
        <input type="date" id="userbirth" name="userbirth" value="${users.userbirth}" required />
    </div>

    <!-- 약관동의 유지 -->
    <input type="hidden" name="agree01" value="${users.agree01}" />
    <input type="hidden" name="agree02" value="${users.agree02}" />

    <button type="submit">회원가입 완료</button>
</form>

<script>
// AJAX 중복검사 예시 (실제 구현 시 컨트롤러에 맞춰 URL 변경)
function checkDuplicateID() {
    const userid = document.getElementById('userid').value;
    if (!userid) { alert("아이디를 입력하세요."); return; }
    fetch(`/join/duplicateCheckID?userid=${userid}`)
        .then(res => res.json())
        .then(data => alert(data.message));
}

function checkDuplicateNickname() {
    const nickname = document.getElementById('nickname').value;
    if (!nickname) { alert("닉네임을 입력하세요."); return; }
    fetch(`/join/duplicateCheckNickname?nickname=${nickname}`)
        .then(res => res.json())
        .then(data => alert(data.message));
}

function checkDuplicateEmail() {
    const email = document.getElementById('useremail').value;
    if (!email) { alert("이메일을 입력하세요."); return; }
    fetch(`/join/duplicateCheckEmail?useremail=${email}`)
        .then(res => res.json())
        .then(data => alert(data.message));
}
</script>

</body>
</html>