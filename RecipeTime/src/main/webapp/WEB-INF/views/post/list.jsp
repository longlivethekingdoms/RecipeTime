<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>레시피 목록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <style>
        .card-img-top {
            height: 200px;
            object-fit: cover;
        }
        .filter-box {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
        }
    </style>
</head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="container mt-4">
<jsp:include page="/WEB-INF/views/template/header.jsp"/>

<h2>레시피 목록</h2>
	
	<!-- 카테고리 필터 -->
	<form>
	<div>
		검색어<input type="text" name="keyword">
	</div>
	<c:forEach var="item" items="${categoryList}">
    <div>
        ${item.itemname}
        <!-- 전체 옵션, name을 다른 옵션들과 동일하게 설정 -->
        <input type="radio" name="${item.itemparam}" value="0" checked> 전체
        <c:forEach var="option" items="${item.optionList}">
            <label>
                <input type="radio" name="${item.itemparam}" value="${option.optionid}">
                ${option.optionname}
            </label>			
        </c:forEach>
    </div>
	</c:forEach>
	<button type="submit" class="btn btn-primary">필터 적용</button>
</form>
    <hr/>

    <!-- ✅ 레시피 앨범 그리드 -->
    <div class="row row-cols-1 row-cols-md-4 g-5">
        <c:forEach var="post" items="${recipeList}">
        	<a href="detail/${post.recipeid}">
	            <div class="col">
	                <div class="card">
	                    <img src="${post.attachments.get(1)}" class="card-img-top" alt="레시피 이미지">
	                    <div class="card-body">
	                        <h5 class="card-title">${post.recipetitle}</h5>
	                        <p class="card-text">
	                            작성일: ${post.recipewritedate}
	                        </p>
	                    </div>
	                </div>
	            </div>
            </a>
        </c:forEach>
    </div>
    
    <!-- ✅ 페이지네이션 -->
	<div>
	</div>
	
	<div>
		<div><a href="dummy">대량 등록</a></div>
	</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"/>
</body>
</html>