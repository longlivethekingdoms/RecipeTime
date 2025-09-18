<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        .hidden { display: none; }
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
            <label>대표 이미지</label><br>
            <input type="file" name="mainImage">
        </div>
        
        <!-- 추가 이미지 -->
	    <div class="section">
	        <label>이미지 추가</label><br>
	        <div id="extraImages"></div>
	        <button type="button" id="addExtraImage">이미지 추가</button>
	    </div>

        <!-- 동영상 URL -->
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
    
	    // === 추가 이미지 input ===
	    let extraImgIndex = 0;
	    $("#addExtraImage").click(function() {
	        $("#extraImages").append(
	            `<div><input type="file" name="attachments[\${extraImgIndex}]" multiple>
	            <button type="button" class="remove">삭제</button></div>`
	        );
	        extraImgIndex++;
	    });
	    
        // 태그 추가
        let tagIndex = 0;
        $("#addTag").click(function(){
            $("#tagList").append(
                `<div class="tag">
                    <input type="text" name="tags[\${tagIndex}].tagname" placeholder="태그명" required>
                    <button type="button" class="remove">삭제</button>
                </div>`
            );
            tagIndex++;
        });

        // 재료 추가
        let ingIndex = 0;
		$("#addIngredient").click(function() {
		    $("#ingredientList").append(
		        `<div class="ingredient">
		            <input type="text" name="ingredients[\${ingIndex}].ingname" placeholder="재료명" required />
		            <input type="number" name="ingredients[\${ingIndex}].ingquantity" placeholder="수량" min="0" />
		            <input type="text" name="ingredients[\${ingIndex}].unit" placeholder="단위 (예: g, ml)" />
		            <input type="text" name="ingredients[\${ingIndex}].exp" placeholder="비고" />
		            <button type="button" class="remove">삭제</button>
		        </div>`
		    );
		    ingIndex++;
		});

	    // 요리 순서 추가 버튼
	    let seqIndex = 0;
	    $("#addSequence").click(function(){
	        $("#sequenceList").append(
	            `<div class="sequence">
	                <span class="sequence-number">${seqIndex+1}.</span>
	                <textarea name="sequences[\${seqIndex}].explain" placeholder="설명" required></textarea>
	                <input type="file" name="sequences[\${seqIndex}].images" multiple>
	                <div>
	                    <label><input type="checkbox" class="toggle" data-target="ingredient"> 재료</label>
	                    <div class="extra hidden ingredient">설명: <input type="text" name="sequences[\${seqIndex}].ingexp"></div>
	
	                    <label><input type="checkbox" class="toggle" data-target="tool"> 도구</label>
	                    <div class="extra hidden tool">설명: <input type="text" name="sequences[\${seqIndex}].toolexp"></div>
	
	                    <label><input type="checkbox" class="toggle" data-target="fire"> 불</label>
	                    <div class="extra hidden fire">설명: <input type="text" name="sequences[\${seqIndex}].fireexp"></div>
	
	                    <label><input type="checkbox" class="toggle" data-target="tip"> 팁</label>
	                    <div class="extra hidden tip">설명: <input type="text" name="sequences[\${seqIndex}].tipexp"></div>
	                </div>
	                <button type="button" class="remove">삭제</button>
	            </div>`
	        );
	        seqIndex++;
	        reindexSequences();
	    });

	    // 삭제 버튼 이벤트
	    $(document).on("click", ".remove", function() {
	        $(this).parent().remove();
	        reindexSequences();
	    });
	    
	 // 순서 재정렬 (번호 + name 속성 동기화)
	    function reindexSequences() {
	        $("#sequenceList .sequence").each(function(index) {
	            $(this).attr("data-seq", index + 1);
	            $(this).find(".sequence-number").text((index + 1) + ".");

	            // textarea
	            $(this).find("textarea").attr("name", "sequences[" + index + "].explain");

	            // file input
	            $(this).find("input[type='file']").attr("name", "sequences[" + index + "].images");

	            // ingredient
	            $(this).find(".ingredient input").attr("name", "sequences[" + index + "].ingredientNote");

	            // tool
	            $(this).find(".tool input").attr("name", "sequences[" + index + "].toolNote");

	            // fire
	            $(this).find(".fire input").attr("name", "sequences[" + index + "].fireNote");

	            // tip
	            $(this).find(".tip input").attr("name", "sequences[" + index + "].tipNote");
	        });
	    }

	    // 토글 (재료/도구/불/팁)
	    $(document).on("change", ".toggle", function() {
	        let target = $(this).data("target");
	        let extra = $(this).closest(".sequence").find("." + target);
	        if ($(this).is(":checked")) {
	            extra.removeClass("hidden").find("input").attr("required", true);
	        } else {
	            extra.addClass("hidden").find("input").removeAttr("required");
	        }
	    });

        function submitWithPrivacy(value) {
            document.getElementById("isprivate").value = value;
            document.getElementById("postForm").submit();
        }

        // 작성 중 이탈 방지
        window.addEventListener("beforeunload", function (e) {
            if (document.querySelector("input[name='recipetitle']").value.trim() !== "" ||
                document.querySelector("textarea[name='recipecontent']").value.trim() !== "") {
                e.preventDefault();
                e.returnValue = "작성 중인 내용이 사라질 수 있습니다. 이동하시겠습니까?";
            }
        });
    </script>
</body>
</html>