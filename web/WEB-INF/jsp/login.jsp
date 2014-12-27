<%-- 
    Document   : login
    Created on : 29.11.2014, 11:30:35
    Author     : Stas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enter system</title>
        <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    </head>
    <body>
        <form action="j_spring_security_check" method="POST" class="loginForm">
            <div id="loginBox" style="padding-left: 20px">
                <h1>Enter login and password</h1>
                <div>
                    <div>
                        <div>
                            <input placeholder="input login" type="text" size="20" name="j_username"></div>
                        <div>
                            <input placeholder="input password" type="password" size="20" name="j_password">
                        </div>
                        <p>
                        <div><input type="submit" value="Sign in"></div>
                    </div>
                <div>
            </div>
        </form>
    </body>
</html>
