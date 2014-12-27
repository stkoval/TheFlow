<%-- 
    Document   : registration
    Created on : 29.11.2014, 14:50:22
    Author     : Stas
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
</head>
<body>
    <div align="center">
        <form:form action="register" method="post" commandName="userForm">
            <table border="0">
                <tr>
                    <td colspan="2" align="center"><h2>Please, fill user data</h2></td>
                </tr>
                <tr>
                    <td>First name:</td>
                    <td><form:input path="firstName" /></td>
                </tr>
                <tr>
                    <td>Last name:</td>
                    <td><form:password path="lastName" /></td>
                </tr>
                <tr>
                    <td>E-mail:</td>
                    <td><form:input path="email" /></td>
                </tr>
                <tr>
                    <td>Login:</td>
                    <td><form:input path="login" /></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:input path="password" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Register" /></td>
                </tr>
            </table>
        </form:form>
    </div>
</body>
</html>
