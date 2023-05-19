<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- header -->
<header>
    <div class="inner-header">
        <h1 class="logo">
            <a href="/board/list">
                <img src="/assets/img/logo.png" alt="로고이미지">
            </a>
        </h1>

        <div class="profile-box">
            <c:if test="${login eq null || login.profile eq null}">

                <img src="/assets/img/annonymous.jpg" alt="프사">

            </c:if>

            <c:if test="${login ne null}">

                <img src="${login.profile}" alt="프사">

            </c:if>
        </div>

        <h2 class="intro-text">Welcome ${login == null ? "" : login.account}</h2>
        <a href="#" class="menu-open">
            <span class="menu-txt">MENU</span>
            <span class="lnr lnr-menu"></span>
        </a>
    </div>

    <nav class="gnb">
        <a href="#" class="close">
            <span class="lnr lnr-cross"></span>
        </a>
        <ul>
            <li><a href="/">Home</a></li>
            <li><a href="#">About</a></li>
            <li><a href="/board/list">Board</a></li>
            <li><a href="#">Contact</a></li>

            <c:if test="${empty login}">
                <li><a href="/members/sign-up">Sign Up</a></li>
                <li><a href="/members/sign-in">Sign In</a></li>
            </c:if>

            <c:if test="${login ne null}">
                <li>
                    <a href="#">My Page</a>
                </li>
                <li>
                    <a href="/members/sign-out">Sign out</a>
                </li>
            </c:if>

        </ul>
    </nav>

</header>
<!-- //header -->