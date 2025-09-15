<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/post/insert" method="post" enctype="multipart/form-data">
    <label>레시피 제목:</label>
    <input type="text" name="recipetitle" required><br>

    <label>레시피 내용:</label>
    <textarea name="recipecontent" required></textarea><br>

    <label>종류:</label>
    <select name="maincategoryid" required>
        <option value="1">국</option>
        <option value="2">밥</option>
        <option value="3">찌개</option>
    </select><br>

    <label>상황:</label>
    <select name="subcategoryname" required>
        <option value="일상">일상</option>
        <option value="초스피드">초스피드</option>
        <option value="손님접대">손님접대</option>
    </select><br>

    <label>태그:</label>
    <input type="text" name="tags[0]" required>
    <button type="button" id="addTag">+</button>
    <div id="tagContainer"></div><br>

    <label>대표 이미지:</label>
    <input type="file" name="attachments" required><br>

    <label>단계 추가:</label>
    <div id="sequenceContainer">
        <textarea name="sequences[0].explain"></textarea>
        <input type="file" name="sequences[0].attachment"><br>
    </div>
    <button type="button" id="addSequence">단계 추가</button><br>

    <button type="submit">등록</button>
</form>

<script>
	let tagIndex = 1;
	document.getElementById('addTag').addEventListener('click', () => {
	    const div = document.createElement('div');
	    div.innerHTML = `<input type="text" name="tags[${tagIndex}]" required>
	                     <button type="button" onclick="this.parentElement.remove()">-</button>`;
	    document.getElementById('tagContainer').appendChild(div);
	    tagIndex++;
	});
</script>
</body>
</html>