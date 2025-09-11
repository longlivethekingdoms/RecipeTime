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

<!-- 서버에서 전달된 메시지 -->
<c:if test="${not empty message}">
    <div class="error">${message}</div>
</c:if>

<form id="userForm" action="<c:url value='/join/insertUser' />" method="post">

    <!-- 아이디 -->
    <div class="form-group">
        <label for="userid">아이디</label>
        <input type="text" id="userid" name="userid" value="${users.userid}" required />
        <button type="button" id="checkID">중복확인</button>
        <span id="idMessage" class="error"></span>
    </div>

    <!-- 비밀번호 -->
    <div class="form-group">
        <label for="userpw">비밀번호</label>
        <input type="password" id="userpw" name="userpw" required />
    </div>

    <div class="form-group">
        <label for="confirmPw">비밀번호 확인</label>
        <input type="password" id="confirmPw" name="confirmPw" required />
        <span id="pwMessage" class="error"></span>
    </div>

    <!-- 닉네임 -->
    <div class="form-group">
        <label for="nickname">닉네임</label>
        <input type="text" id="nickname" name="nickname" value="${users.nickname}" required />
        <button type="button" id="checkNickname">중복확인</button>
        <span id="nicknameMessage" class="error"></span>
    </div>

    <!-- 이메일 -->
    <div class="form-group">
        <label for="useremail">이메일</label>
        <input type="email" id="useremail" name="useremail" value="${users.useremail}" required />
        <button type="button" id="checkEmail">중복확인</button>
        <span id="emailMessage" class="error"></span>
    </div>

    <!-- 전화번호 -->
    <div class="form-group">
        <label for="usertel">전화번호</label>
        <input type="tel" id="usertel" name="usertel" value="${users.usertel}" required />
    </div>

    <!-- 생년월일 -->
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
/*let idChecked = false;
let nicknameChecked = false;
let emailChecked = false;

// 아이디 중복체크
document.getElementById('checkID').addEventListener('click', () => {
    const userid = document.getElementById('userid').value.trim();
    if (!userid) { alert("아이디를 입력하세요."); return; }

    fetch(`/join/duplicateCheckID?userid=${userid}`)
        .then(res => res.json())
        .then(data => {
            document.getElementById('idMessage').textContent = data.message;
            idChecked = data.message.includes('사용 가능한');
        });
});

// 닉네임 중복체크
document.getElementById('checkNickname').addEventListener('click', () => {
    const nickname = document.getElementById('nickname').value.trim();
    if (!nickname) { alert("닉네임을 입력하세요."); return; }
	
    console.log(idChecked);
    
    fetch(`/join/duplicateCheckNickname?nickname=${nickname}`)
        .then(res => res.json())
        .then(data => {
            document.getElementById('nicknameMessage').textContent = data.message;
            nicknameChecked = data.message.includes('사용 가능한');
        });
});

// 이메일 중복체크
document.getElementById('checkEmail').addEventListener('click', () => {
    const email = document.getElementById('useremail').value.trim();
    if (!email) { alert("이메일을 입력하세요."); return; }

    fetch(`/join/duplicateCheckEmail?useremail=${email}`)
        .then(res => res.json())
        .then(data => {
            document.getElementById('emailMessage').textContent = data.message;
            emailChecked = data.message.includes('사용 가능한');
        });
});

// 비밀번호 확인
const pwField = document.getElementById('userpw');
const confirmField = document.getElementById('confirmPw');
confirmField.addEventListener('input', () => {
    const msg = pwField.value === confirmField.value ? '' : '비밀번호가 일치하지 않습니다.';
    document.getElementById('pwMessage').textContent = msg;
});

// 폼 제출 전 검증
document.getElementById('userForm').addEventListener('submit', (e) => {
    // 중복체크 안 했거나 바뀌었으면 막기
    if (!idChecked) { alert("아이디 중복체크를 해주세요."); e.preventDefault(); return; }
    if (!nicknameChecked) { alert("닉네임 중복체크를 해주세요."); e.preventDefault(); return; }
    if (!emailChecked) { alert("이메일 중복체크를 해주세요."); e.preventDefault(); return; }

    if (pwField.value !== confirmField.value) {
        alert("비밀번호와 확인이 일치하지 않습니다.");
        e.preventDefault();
        return;
    }
});*/
</script>

</body>
</html>