<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<c:set var = "user_profile_img_src" scope = "request" value = "res/drawable/user_avatar.jpg"/>
<c:if test="${usr.profileImagePath != null && usr.profileImagePath.length() > 0}">
	<c:set var = "user_profile_img_src" scope = "request" value = "res/user/${usr.profileImagePath}"/>
</c:if>