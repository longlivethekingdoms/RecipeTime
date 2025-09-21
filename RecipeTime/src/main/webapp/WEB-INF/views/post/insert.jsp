<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피 작성</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.section { margin-bottom: 20px; }
.ingredient, .sequence, .tag { margin-top: 10px; }
.sequence-number { font-weight: bold; margin-right: 5px; }
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/template/header.jsp"/>

<h2>레시피 작성</h2>

<form id="postForm" action="<c:url value='/post/insert'/>" method="post" enctype="multipart/form-data">
    <!-- 제목 -->
    <div class="section">
        <label>제목 *</label><br>
        <input type="text" name="recipetitle" required>
    </div>

    <!-- 내용 -->
    <div class="section">
        <label>내용 *</label><br>
        <textarea name="recipecontent" rows="6" cols="60" required></textarea>
    </div>

    <!-- 카테고리 -->
    <div class="section">
        <label>카테고리 *</label><br>
        <select name="typeid" required>
            <option value="">-- 종류 선택 --</option>
            <c:forEach var="opt" items="${typeOptions}">
                <option value="${opt.optionid}">${opt.optionname}</option>
            </c:forEach>
        </select>
        <select name="situationid" required>
            <option value="">-- 상황 선택 --</option>
            <c:forEach var="opt" items="${situationOptions}">
                <option value="${opt.optionid}">${opt.optionname}</option>
            </c:forEach>
        </select>
        <select name="methodid" required>
            <option value="">-- 방법 선택 --</option>
            <c:forEach var="opt" items="${methodOptions}">
                <option value="${opt.optionid}">${opt.optionname}</option>
            </c:forEach>
        </select>
        <select name="peopleid" required>
            <option value="">-- 인원 선택 --</option>
            <c:forEach var="opt" items="${peopleOptions}">
                <option value="${opt.optionid}">${opt.optionname}</option>
            </c:forEach>
        </select>
        <select name="timeid" required>
            <option value="">-- 요리시간 선택 --</option>
            <c:forEach var="opt" items="${timeOptions}">
                <option value="${opt.optionid}">${opt.optionname}</option>
            </c:forEach>
        </select>
        <select name="difficultyid" required>
            <option value="">-- 난이도 선택 --</option>
            <c:forEach var="opt" items="${difficultyOptions}">
                <option value="${opt.optionid}">${opt.optionname}</option>
            </c:forEach>
        </select>
    </div>

    <!-- 대표 이미지 -->
    <div class="section">
        <label>대표 이미지 *</label>
        <input type="file" name="mainImage" accept="image/*" required>
    </div>

    <!-- 추가 이미지 -->
    <div class="section">
        <label>추가 이미지</label>
        <input type="file" name="attachments" multiple accept="image/*">
    </div>

    <!-- 레시피 동영상 URL -->
    <div class="section">
        <label>레시피 동영상 URL</label><br>
        <input type="url" name="recipeMainVidLink">
    </div>

    <!-- 태그 -->
    <div class="section">
        <label>태그</label>
        <div id="tagList"></div>
        <button type="button" id="addTag">태그 추가</button>
    </div>

    <!-- 재료 -->
    <div class="section">
        <label>재료 *</label>
        <div id="ingredientList"></div>
        <button type="button" id="addIngredient">재료 추가</button>
    </div>

    <!-- 요리 순서 -->
    <div class="section">
        <label>요리 순서 *</label>
        <div id="sequenceList"></div>
        <button type="button" id="addSequence">순서 추가</button>
    </div>

    <!-- 공개 여부 -->
    <input type="hidden" id="isprivate" name="isprivate" value="0">
    <div class="section">
        <button type="button" onclick="submitWithPrivacy(0)">공개</button>
        <button type="button" onclick="submitWithPrivacy(1)">비공개</button>
    </div>
</form>

<script>
let tagIndex=0, ingIndex=0, seqIndex=0;

// 태그 추가
$("#addTag").click(function() {
    $("#tagList").append(`
        <div class="tag">
            <input type="text" name="tags[${tagIndex}].tagname" placeholder="태그명" required>
            <button type="button" class="remove">삭제</button>
        </div>
    `);
    tagIndex++;
});

// 재료 추가
$("#addIngredient").click(function() {
    $("#ingredientList").append(`
        <div class="ingredient">
            <input type="text" name="ingredients[${ingIndex}].ingname" placeholder="재료명" required>
            <input type="number" name="ingredients[${ingIndex}].ingquantity" placeholder="수량" min="0">
            <input type="text" name="ingredients[${ingIndex}].unit" placeholder="단위">
            <input type="text" name="ingredients[${ingIndex}].exp" placeholder="비고">
            <button type="button" class="remove">삭제</button>
        </div>
    `);
    ingIndex++;
});

// 순서 추가
$("#addSequence").click(function() {
    $("#sequenceList").append(`
        <div class="sequence">
            <span class="sequence-number">${seqIndex+1}.</span>
            <textarea name="sequences[${seqIndex}].explain" placeholder="설명" required></textarea>
            <input type="file" name="sequenceImages${seqIndex}" multiple>
            <button type="button" class="remove">삭제</button>
        </div>
    `);
    seqIndex++;
});

// 삭제 버튼
$(document).on("click", ".remove", function() { $(this).parent().remove(); });

// 공개/비공개 submit
function submitWithPrivacy(value){
    $("#isprivate").val(value);
    $("#postForm").submit();
}
</script>
</body>
</html>