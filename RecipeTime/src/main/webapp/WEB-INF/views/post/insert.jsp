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
    <h2>레시피 작성</h2>
    <form id="postForm" action="/post/insert" method="post" enctype="multipart/form-data">
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
		
		    <!-- 종류 -->
		    <select name="typeCategory" required>
		        <option value="">-- 종류 선택 --</option>
		        <option value="국">국</option>
		        <option value="밥">밥</option>
		        <option value="찌개">찌개</option>
		        <option value="기타">기타</option>
		    </select>
		
		    <!-- 상황 -->
		    <select name="situationCategory" required>
		        <option value="">-- 상황 선택 --</option>
		        <option value="일상">일상</option>
		        <option value="초스피드">초스피드</option>
		        <option value="손님접대">손님접대</option>
		        <option value="기타">기타</option>
		    </select>
		
		    <!-- 방법 -->
		    <select name="methodCategory" required>
		        <option value="">-- 방법 선택 --</option>
		        <option value="볶음">볶음</option>
		        <option value="끓이기">끓이기</option>
		        <option value="무침">무침</option>
		        <option value="기타">기타</option>
		    </select>
		
		    <!-- 인원 -->
		    <select name="personCategory" required>
		        <option value="">-- 인원 선택 --</option>
		        <option value="1인분">1인분</option>
		        <option value="2인분">2인분</option>
		        <option value="3인분">3인분</option>
		        <option value="기타">기타</option>
		    </select>
		
		    <!-- 요리시간 -->
		    <select name="timeCategory" required>
		        <option value="">-- 요리시간 선택 --</option>
		        <option value="5분">5분</option>
		        <option value="10분">10분</option>
		        <option value="15분">15분</option>
		        <option value="30분">30분</option>
		        <option value="1시간 이상">1시간 이상</option>
		    </select>
		
		    <!-- 난이도 -->
		    <select name="difficultyCategory" required>
		        <option value="">-- 난이도 선택 --</option>
		        <option value="쉬움">쉬움</option>
		        <option value="보통">보통</option>
		        <option value="어려움">어려움</option>
		    </select>
		</div>

        <!-- 대표 이미지 -->
        <div class="section">
            <label>대표 이미지</label><br>
            <input type="file" name="mainImage">
        </div>

        <!-- 동영상 URL -->
        <div class="section">
            <label>레시피 동영상 URL</label><br>
            <input type="url" name="recipemainvidlink">
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

         <!-- isprivate 버튼 -->
	    <div class="section">
	        <button type="button" onclick="submitWithPrivacy(0)">공개</button>
	        <button type="button" onclick="submitWithPrivacy(1)">비공개</button>
	    </div>
    </form>

    <script>
        // 태그 추가
        $("#addTag").click(function() {
            $("#tagList").append(
                '<div class="tag"><input type="text" name="tags[]" required> <button type="button" class="remove">삭제</button></div>'
            );
        });

        // 재료 추가
        $("#addIngredient").click(function() {
            $("#ingredientList").append(
                '<div class="ingredient">' +
                '재료명* <input type="text" name="ingredients[][name]" required>' +
                ' 수량* <input type="text" name="ingredients[][amount]" required>' +
                ' 단위* <input type="text" name="ingredients[][unit]" required>' +
                ' 비고 <input type="text" name="ingredients[][note]">' +
                ' <button type="button" class="remove">삭제</button>' +
                '</div>'
            );
        });

        // 요리 순서 추가
        let sequenceCount = 0;
        $("#addSequence").click(function() {
            sequenceCount++;
            $("#sequenceList").append(
                '<div class="sequence" data-seq="' + sequenceCount + '">' +
                '<span class="sequence-number">' + sequenceCount + '.</span>' +
                '<textarea name="sequences[' + sequenceCount + '][content]" required></textarea>' +
                '<input type="file" name="sequences[' + sequenceCount + '][images]" multiple>' +
                '<br>' +
                '<label><input type="checkbox" class="toggle" data-target="ingredient"> 재료 </label>' +
                '<div class="extra hidden ingredient">설명: <input type="text" name="sequences[' + sequenceCount + '][ingredientNote]"></div>' +
                '<label><input type="checkbox" class="toggle" data-target="tool"> 도구 </label>' +
                '<div class="extra hidden tool">설명: <input type="text" name="sequences[' + sequenceCount + '][toolNote]"></div>' +
                '<label><input type="checkbox" class="toggle" data-target="fire"> 불</label>' +
                '<div class="extra hidden fire">설명: <input type="text" name="sequences[' + sequenceCount + '][fireNote]"></div>' +
                '<label><input type="checkbox" class="toggle" data-target="tip"> 팁</label>' +
                '<div class="extra hidden tip">설명: <input type="text" name="sequences[' + sequenceCount + '][tipNote]"></div>' +
                ' <button type="button" class="remove">삭제</button>' +
                '</div>'
            );
        });

        // 삭제 버튼
        $(document).on("click", ".remove", function() {
            $(this).parent().remove();
            // 순서 재정렬
            $("#sequenceList .sequence").each(function(index) {
                $(this).find(".sequence-number").text((index + 1) + ".");
            });
        });

        // ON/OFF 토글
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