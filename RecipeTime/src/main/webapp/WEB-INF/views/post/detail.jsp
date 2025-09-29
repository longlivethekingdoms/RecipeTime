<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${post.recipetitle}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">

    <!-- 제목 -->
    <h2>${post.recipetitle}</h2>
    <p>작성자: ${post.userid} | 작성일: ${post.recipewritedate} | 조회수: ${post.recipeviews}</p>
	
	<!-- 카테고리 / 장르 -->
	<h2>${post.recipetitle}</h2>

	<!-- 카테고리 영역 -->
	<div>
	    <span>종류: ${post.typename}</span> |
	    <span>상황: ${post.situationname}</span> |
	    <span>조리법: ${post.methodname}</span> |
	    <span>인원: ${post.peoplename}</span> |
	    <span>시간: ${post.timename}</span> | 
	    <span>난이도: ${post.difficultyname}</span>
	</div>

    <!-- 대표 이미지 -->
    <c:forEach var="img" items="${post.attachments}">
    <c:if test="${img.ismain == 1}">
        <div class="mb-3">
            <img src="/upload/${img.fileuuid}.${img.fileext}" class="img-fluid rounded">
        </div>
    </c:if>
	</c:forEach>

    <!-- 첨부 이미지 -->
    <c:if test="${not empty post.attachments}">
        <div class="row mb-4">
            <c:forEach var="att" items="${post.attachments}">
                <div class="col-md-3 mb-3">
                    <img src="/upload/${att.fileuuid}.${att.fileext}" class="img-fluid rounded">
                </div>
            </c:forEach>
        </div>
    </c:if>

    <!-- 동영상 -->
    <c:if test="${not empty post.recipeMainVidLink}">
        <div class="ratio ratio-16x9 mb-3">
            <iframe src="https://www.youtube.com/embed/${post.recipeMainVidLink}" allowfullscreen></iframe>
        </div>
    </c:if>

    <!-- 본문 -->
    <div class="mb-4">
        <p>${post.recipecontent}</p>
    </div>

    <!-- 태그 -->
    <h5>태그</h5>
    <p>
        <c:forEach var="tag" items="${post.tags}">
            #${tag.tagname}
        </c:forEach>
    </p>

    <!-- 재료 -->
    <h4>재료</h4>
    <ul>
        <c:forEach var="ing" items="${post.ingredients}">
            <li>${ing.ingname} - ${ing.ingquantity} ${ing.unit} (${ing.exp})</li>
        </c:forEach>
    </ul>

    <!-- 요리 순서 -->
    <h4>요리 순서</h4>
    <c:forEach var="seq" items="${post.sequences}">
        <div class="mb-4">
            <strong>Step ${seq.recipestep}</strong>: ${seq.explain}

            <!-- 순서별 동영상 -->
            <c:if test="${not empty seq.recipevidlink}">
                <div class="ratio ratio-16x9 my-2">
                    <iframe src="https://www.youtube.com/embed/${seq.recipevidlink}" allowfullscreen></iframe>
                </div>
            </c:if>

            <!-- 옵션 설명들 -->
            <c:if test="${seq.ingactivate == 1}">
                <p><b>재료 팁:</b> ${seq.ingexp}</p>
            </c:if>
            <c:if test="${seq.toolactivate == 1}">
                <p><b>도구 팁:</b> ${seq.toolexp}</p>
            </c:if>
            <c:if test="${seq.fireactivate == 1}">
                <p><b>불 조절:</b> ${seq.fireexp}</p>
            </c:if>
            <c:if test="${seq.tipactivate == 1}">
                <p><b>추가 팁:</b> ${seq.tipexp}</p>
            </c:if>

            <!-- 순서별 첨부 이미지 -->
            <c:if test="${not empty seq.attachments}">
                <div class="row">
                    <c:forEach var="satt" items="${seq.attachments}">
                        <div class="col-md-3 mb-2">
                            <img src="/upload/${satt.fileuuid}.${satt.fileext}" class="img-fluid rounded">
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </c:forEach>

    <a href="/post/list" class="btn btn-secondary mt-3">목록으로</a>
</body>
</html>