<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 글쓰기</title>

    <%@ include file="../include/static-head.jsp" %>
    <link rel="stylesheet" href="/assets/css/list.css">
    <link rel="stylesheet" href="/assets/css/write.css">

    <!-- ck editor -->
    <script src="https://cdn.ckeditor.com/4.17.2/standard/ckeditor.js"></script>

</head>
<body>
<%@include file="../include/header.jsp" %>
<div id="wrap" class="form-container">


    <h1>꾸러기 게시판 글쓰기</h1>
    <form action="/board/write" method="post">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" required>
        <label for="content">내용</label>
        <textarea id="content" name="content" maxlength="200" required></textarea>
        <div class="buttons">
            <button class="list-btn" type="button" onclick="window.location.href='/board/list'">목록</button>
            <button type="submit">글쓰기</button>
        </div>
    </form>
</div>
<script>
    CKEDITOR.replace('content');
</script>
</body>
</html>
