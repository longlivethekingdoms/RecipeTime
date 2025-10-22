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

    <label for="recipeMainVidLink">동영상 링크</label>
	<input type="text" id="recipeMainVidLink" name="recipeMainVidLink" placeholder="유튜브 링크 입력">
	
	<div id="videoPreview" style="margin-top: 15px; display: none;">
	    <iframe id="videoFrame" width="560" height="315" frameborder="0" allowfullscreen></iframe>
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

document.getElementById("recipeMainVidLink").addEventListener("input", function () {
    const url = this.value;
    let videoId = null;

    // 1) https://www.youtube.com/watch?v=VIDEO_ID
    const match1 = url.match(/v=([^&]+)/);
    if (match1) videoId = match1[1];

    // 2) https://youtu.be/VIDEO_ID
    const match2 = url.match(/youtu\.be\/([^?]+)/);
    if (match2) videoId = match2[1];
    
 	// 3) https://www.youtube.com/shorts/VIDEO_ID
    const match3 = url.match(/youtube\.com\/shorts\/([^?]+)/);
    if (match3) videoId = match3[1];

    if (videoId) {
        const embedUrl = "https://www.youtube.com/embed/" + videoId;
        document.getElementById("videoFrame").src = embedUrl;
        console.log(document.getElementById("videoFrame").src);
        document.getElementById("videoPreview").style.display = "block";
    } else {
        document.getElementById("videoPreview").style.display = "none";
    }
});

// 태그 추가
$("#addTag").click(function() {
	var i = $("#tagList .tag").length;
    $("#tagList").append(`
        <div class="tag">
            <input type="text" name="tags[\${i}].tagname" placeholder="태그명" required>
            <button type="button" class="remove">삭제</button>
        </div>
    `);
    //tagIndex++;
});

function reindexTags(){
    $("#tagList .tag").each(function(index){
        $(this).find("[name]").each(function(){
            var name = $(this).attr("name");
            name = name.replace(/tags\[\d+\]/, "tags[" + index + "]");
            $(this).attr("name", name);
        });
    });
}

// 재료 추가
$("#addIngredient").click(function() {
	 var i = $("#ingredientList .ingredient").length; 
	$("#ingredientList").append(`
        <div class="ingredient">
            <input type="text" name="ingredients[\${i}].ingname" placeholder="재료명" required>
            <input type="number" name="ingredients[\${i}].ingquantity" placeholder="수량" min="0" required>
            <input type="text" name="ingredients[\${i}].unit" placeholder="단위" required>
            <input type="text" name="ingredients[\${i}].exp" placeholder="비고">
            <button type="button" class="remove">삭제</button>
        </div>
    `);
    //ingIndex++;
});

function reindexIngredients(){
    $("#ingredientList .ingredient").each(function(index){
        $(this).find("[name]").each(function(){
            var name = $(this).attr("name");
            name = name.replace(/ingredients\[\d+\]/, "ingredients[" + index + "]");
            $(this).attr("name", name);
        });
    });
}

//시퀀스 추가 시 체크박스 상태를 확인하고 처리
$("#addSequence").click(function() {
    var i = $("#sequenceList .sequence").length; // 현재 개수 기반
    var html =
        '<div class="sequence" data-seq="' + i + '">' +
            '<span class="sequence-number">' + (i + 1) + '.</span>' +
            '<textarea name="sequences[' + i + '].explain" placeholder="설명" required></textarea>' +
            '<input type="file" class="seq-image" name="sequences[' + i + '].images" multiple>' +
            '<div class="section"><label>레시피 동영상 링크</label>' +
            '<br>' +
            '<input type="text" class="recipevidlink" name="sequences[' + i + '].recipevidlink" placeholder="유튜브 링크 입력"></div>' + 
            '<div class="videoPreview" style="margin-top: 15px; display: none;">' +
            '<iframe class="videoFrame" width="560" height="315" frameborder="0" allowfullscreen></iframe>' +
            '</div>' +
            '<div class="seq-preview"></div>' +  
            '<br>' +
            '<label><input type="checkbox" class="toggle" data-target="ingredient" name="sequences[' + i + '].ingactivate" value="1"> 재료 </label>' +
            '<div class="extra ingredient">설명: <input type="text" name="sequences[' + i + '].ingexp"></div>' +
            '<label><input type="checkbox" class="toggle" data-target="tool" name="sequences[' + i + '].toolactivate" value="1"> 도구 </label>' +
            '<div class="extra tool">설명: <input type="text" name="sequences[' + i + '].toolexp"></div>' +
            '<label><input type="checkbox" class="toggle" data-target="fire" name="sequences[' + i + '].fireactivate" value="1"> 불</label>' +
            '<div class="extra fire">설명: <input type="text" name="sequences[' + i + '].fireexp"></div>' +
            '<label><input type="checkbox" class="toggle" data-target="tip" name="sequences[' + i + '].tipactivate" value="1"> 팁</label>' +
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
    parent.remove();
    
    if (parent.hasClass("sequence")) {
        reindexSequences(); // 시퀀스 전용 재정렬
    }
    else if(parent.hasClass("tag")){
    	reindexTags();
    }
    else if(parent.hasClass("ingredient")){
    	reindexIngredients();
    }
});

//동영상 링크 입력 시 미리보기 (이벤트 위임)
$(document).on("input", ".recipevidlink", function () {
    const url = $(this).val();
    let videoId = null;

    // 1) https://www.youtube.com/watch?v=VIDEO_ID
    const match1 = url.match(/v=([^&]+)/);
    if (match1) videoId = match1[1];

    // 2) https://youtu.be/VIDEO_ID
    const match2 = url.match(/youtu\.be\/([^?]+)/);
    if (match2) videoId = match2[1];
    
 	// 3) https://www.youtube.com/shorts/VIDEO_ID
    const match3 = url.match(/youtube\.com\/shorts\/([^?]+)/);
    if (match3) videoId = match3[1];

    const container = $(this).closest(".sequence");
    const preview = container.find(".videoPreview");
    const frame = container.find(".videoFrame");

    if (videoId) {
        const embedUrl = "https://www.youtube.com/embed/" + videoId;
        frame.attr("src", embedUrl);
        preview.show();
    } else {
        frame.attr("src", "");
        preview.hide();
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

// 모든 체크박스를 확인하고, 체크되지 않은 것은 0으로 설정
$("#postForm .toggle").each(function(){
    if (!$(this).is(":checked")) {
        var name = $(this).attr("name");
        // value가 없을 때 0으로 설정
        if (!$(this).val()) {
            $(this).val("0");
        }
    }
});

if(valid){
    $("#postForm").submit();
}

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
<jsp:include page="/WEB-INF/views/template/footer.jsp"/>
</body>
</html>