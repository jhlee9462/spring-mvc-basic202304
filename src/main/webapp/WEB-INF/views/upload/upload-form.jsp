<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .upload-box {
            width: 150px;
            height: 150px;
            border: 3px dashed orange;
            display: flex;
            justify-content: center;
            align-items: center;
            color: red;
            font-weight: 700;
            cursor: pointer;
        }

        form>input[type="file"] {
            display: none;
        }
    </style>
</head>
<body>

<h1>파일 업로드 예제</h1>

<div class="upload-box">파일 첨부</div>

<form action="/upload-file" method="post" enctype="multipart/form-data">
    <input type="file" name="thumbnail" accept="image/*" id="thumbnail">
    <button type="submit">전송</button>
</form>

<script>

    const $box = document.querySelector('.upload-box');
    const $input = document.getElementById('thumbnail');

    $box.onclick = e => {
        $input.click();
    }

</script>

</body>
</html>