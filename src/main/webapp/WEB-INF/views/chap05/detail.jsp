<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 글쓰기</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Single+Day&display=swap" rel="stylesheet">

    <!-- reset -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">

    <!-- fontawesome css: https://fontawesome.com -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">

    <link rel="stylesheet" href="/assets/css/main.css">
    <link rel="stylesheet" href="/assets/css/detail.css">

</head>
<body>
    <div id="wrap" class="form-container">
        <h1>${b.boardNo}번 게시물 내용~ </h1>
        <h2># 작성일자: ${b.date}</h2>
        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${b.title}" readonly>
        <label for="content">내용</label>
        <div id="content">${b.content}</div>
        <div class="buttons">
            <button class="list-btn" type="button" onclick="window.location.href='/board/list'">목록</button>
        </div>
        
    </div>
</body>
</html>
