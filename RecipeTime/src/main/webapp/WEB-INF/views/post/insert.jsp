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
	    <input type="file" id="mainImage" name="mainImage" accept="image/*">
	    <div id="mainPreview"></div>
	</div>
	
	<!-- 추가 이미지 -->
	<div class="section">
	    <label>추가 이미지</label>
	    <input type="file" id="uploadFiles" name="uploadFiles" multiple accept="image/*">
	    <div id="extraPreview"></div>
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

$(function(){

    // 대표 이미지 미리보기
    $("#mainImage").on("change", function(e){
        let file = e.target.files[0];
        $("#mainPreview").empty();
        if(file && file.type.startsWith("image/")){
            let reader = new FileReader();
            reader.onload = function(ev){
                $("#mainPreview").html(`
                    <div class="preview-item">
                        <img src="\${ev.target.result}" width="200">
                        <button type="button" class="remove-main">삭제</button>
                    </div>
                `);
            }
            reader.readAsDataURL(file);
        }
    });

    // 대표 이미지 삭제
    $(document).on("click", ".remove-main", function(){
        $("#mainImage").val(""); // 파일 입력 초기화
        $("#mainPreview").empty();
    });

    // 추가 이미지 미리보기
    $("#uploadFiles").on("change", function(e){
        $("#extraPreview").empty();
        let files = e.target.files;
        Array.from(files).forEach((file, idx) => {
            if(file.type.startsWith("image/")){
                let reader = new FileReader();
                reader.onload = function(ev){
                    $("#extraPreview").append(`
                        <div class="preview-item" data-idx="${idx}">
                            <img src="\${ev.target.result}" width="120" style="margin:5px;">
                            <button type="button" class="remove-extra">삭제</button>
                        </div>
                    `);
                }
                reader.readAsDataURL(file);
            }
        });
    });

    // 추가 이미지 삭제
    $(document).on("click", ".remove-extra", function(){
        let idx = $(this).parent().data("idx");
        let input = document.getElementById("uploadFiles");

        // FileList는 수정 불가 → DataTransfer로 새롭게 복사
        let dt = new DataTransfer();
        Array.from(input.files).forEach((file, i) => {
            if(i !== idx) dt.items.add(file);
        });
        input.files = dt.files;

        $(this).parent().remove();
    });

});

// 태그 추가
$("#addTag").click(function() {
    $("#tagList").append(`
        <div class="tag">
            <input type="text" name="tags[\${tagIndex}].tagname" placeholder="태그명" required>
            <button type="button" class="remove">삭제</button>
        </div>
    `);
    tagIndex++;
});

// 재료 추가
$("#addIngredient").click(function() {
    $("#ingredientList").append(`
        <div class="ingredient">
            <input type="text" name="ingredients[\${ingIndex}].ingname" placeholder="재료명" required>
            <input type="number" name="ingredients[\${ingIndex}].ingquantity" placeholder="수량" min="0" required>
            <input type="text" name="ingredients[\${ingIndex}].unit" placeholder="단위" required>
            <input type="text" name="ingredients[\${ingIndex}].exp" placeholder="비고">
            <button type="button" class="remove">삭제</button>
        </div>
    `);
    ingIndex++;
});

//시퀀스 추가
$("#addSequence").click(function() {
    var i = $("#sequenceList .sequence").length; // 현재 개수 기반
    var html =
        '<div class="sequence" data-seq="' + i + '">' +
            '<span class="sequence-number">' + (i+1) + '.</span>' +
            '<textarea name="sequences[' + i + '].explain" placeholder="설명" required></textarea>' +
            '<input type="file" class="seq-image" name="sequences[' + i + '].images" multiple>' +
            '<div class="seq-preview"></div>' +   <!-- ★ 미리보기 영역 추가 -->
            '<br>' +
            '<label><input type="checkbox" class="toggle" data-target="ingredient"> 재료 </label>' +
            '<div class="extra ingredient">설명: <input type="text" name="sequences[' + i + '].ingexp"></div>' +
            '<label><input type="checkbox" class="toggle" data-target="tool"> 도구 </label>' +
            '<div class="extra tool">설명: <input type="text" name="sequences[' + i + '].toolexp"></div>' +
            '<label><input type="checkbox" class="toggle" data-target="fire"> 불</label>' +
            '<div class="extra fire">설명: <input type="text" name="sequences[' + i + '].fireexp"></div>' +
            '<label><input type="checkbox" class="toggle" data-target="tip"> 팁</label>' +
            '<div class="extra tip">설명: <input type="text" name="sequences[' + i + '].tipexp"></div>' +
            ' <button type="button" class="remove">삭제</button>' +
        '</div>';
    $("#sequenceList").append(html);
    
    $("#sequenceList .sequence:last .extra").hide();
});

//시퀀스 이미지 미리보기
$(document).on("change", ".seq-image", function(e){
    let container = $(this).closest(".sequence");
    let preview = container.find(".seq-preview");
    preview.empty();

    let files = e.target.files;
    Array.from(files).forEach((file, idx) => {
        if(file.type.startsWith("image/")){
            let reader = new FileReader();
            reader.onload = function(ev){
                preview.append(`
                    <div class="preview-item" data-idx="\${idx}">
                        <img src="\${ev.target.result}" width="120" style="margin:5px;">
                        <button type="button" class="remove-seq-img">삭제</button>
                    </div>
                `);
            }
            reader.readAsDataURL(file);
        }
    });
});

//시퀀스 이미지 삭제
$(document).on("click", ".remove-seq-img", function(){
    let parent = $(this).closest(".sequence");
    let idx = $(this).parent().data("idx");
    let input = parent.find(".seq-image")[0];

    let dt = new DataTransfer();
    Array.from(input.files).forEach((file, i) => {
        if(i !== idx) dt.items.add(file);
    });
    input.files = dt.files;

    $(this).parent().remove();
});

// 삭제 버튼 클릭 시
$(document).on("click", ".remove", function() {
    var parent = $(this).closest("div");
    if (parent.hasClass("sequence")) {
        parent.remove();
        reindexSequences(); // 시퀀스 전용 재정렬
    } else {
        parent.remove();
    }
});

// 시퀀스 재정렬
function reindexSequences(){
    $("#sequenceList .sequence").each(function(index){
        $(this).attr("data-seq", index);
        $(this).find(".sequence-number").text((index+1) + ".");
        $(this).find("[name]").each(function(){
            var name = $(this).attr("name");
            name = name.replace(/sequences\[\d+\]/, "sequences[" + index + "]");
            $(this).attr("name", name);
        });
    });
}

//체크박스 토글 시 설명창 열고 닫기
$(document).on("change", ".toggle", function() {
    var target = $(this).data("target");
    var container = $(this).closest(".sequence");
    var extra = container.find(".extra." + target);
    if ($(this).is(":checked")) {
        extra.show();
    } else {
        extra.hide();
    }
});

// 삭제 버튼
$(document).on("click", ".remove", function() { $(this).parent().remove(); });

// 공개/비공개 submit
function submitWithPrivacy(value){
    $("#isprivate").val(value);

    let valid = true;
    $("#postForm [required]").each(function(){
        if(!$(this).val()){
            valid = false;
            alert("필수 항목을 입력해 주세요.");
            $(this).focus();
            return false; // break
        }
    });

    if(valid){
        $("#postForm").submit();
    }
}
</script>
</body>
</html>