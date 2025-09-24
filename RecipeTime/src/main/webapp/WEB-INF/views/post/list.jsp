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

    <!-- ✅ 카테고리 필터 박스 -->
    <form method="get" action="/recipe/list">
        <div class="filter-box">
            <c:forEach var="item" items="${categoryItems}">
                <div class="mb-3">
                    <strong>${item.itemname}</strong><br/>
                    <c:forEach var="option" items="${item.options}">
                        <input type="checkbox" name="categoryOptions" value="${option.optionid}"
                            <c:if test="${selectedOptions != null and fn:contains(selectedOptions, option.optionid)}">
                                checked
                            </c:if>
                        > ${option.optionname}
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
        <button type="submit" class="btn btn-primary">필터 적용</button>
    </form>

    <hr/>

    <!-- ✅ 레시피 앨범 그리드 -->
    <div class="row row-cols-1 row-cols-md-4 g-5">
        <c:forEach var="post" items="${recipeList}">
            <div class="col">
                <div class="card">
                    <img src="${post.recipeMainVidLink}" class="card-img-top" alt="레시피 이미지">
                    <div class="card-body">
                        <h5 class="card-title">${post.recipetitle}</h5>
                        <p class="card-text">
                            작성일: <fmt:formatDate value="${post.recipewritedate}" pattern="yyyy-MM-dd"/>
                        </p>
                        <p class="card-text">
                            태그:
                            <c:forEach var="tag" items="${post.tags}">
                                <span class="badge bg-secondary">${tag.tagname}</span>
                            </c:forEach>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <!-- ✅ 페이지네이션 -->
	<nav aria-label="Page navigation">
	  <ul class="pagination justify-content-center">
	
	    <!-- 처음으로 -->
	    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
	      <a class="page-link" href="?page=1">처음</a>
	    </li>
	
	    <!-- 이전 -->
	    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
	      <a class="page-link" href="?page=${currentPage - 1}">이전</a>
	    </li>
	
	    <!-- 페이지 번호 -->
	    <c:forEach begin="1" end="${totalPages}" var="i">
	      <li class="page-item ${i == currentPage ? 'active' : ''}">
	        <a class="page-link" href="?page=${i}">${i}</a>
	      </li>
	    </c:forEach>
	
	    <!-- 다음 -->
	    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
	      <a class="page-link" href="?page=${currentPage + 1}">다음</a>
	    </li>
	
	    <!-- 마지막으로 -->
	    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
	      <a class="page-link" href="?page=${totalPages}">마지막</a>
	    </li>
	  </ul>
	</nav>

<jsp:include page="/WEB-INF/views/template/footer.jsp"/>
</body>
</html>