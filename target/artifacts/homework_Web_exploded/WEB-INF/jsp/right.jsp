<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />
		<link href="${ctx}/css/css.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
			<table width="100%" height="90%" border="0" cellpadding="10" cellspacing="0" class="main_tabbor">
		  	<tr valign="top">
			    <td>
				  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
				    <tr><td class="font3 fftd">教师姓名：
				     <c:if test="${teacher_session!=null}">
				    <font color="red" size="2">${teacher_session.username}</font>
					</c:if>
					 <c:if test="${student_session!=null}">
				    <font color="red" size="2">${student_session.username}</font>
				 </c:if>
				
					
					</td></tr>
					<tr><td class="main_tdbor"></td></tr>
					
				
					<tr><td></td></tr>
				  </table>
				</td>
		  	</tr>
		</table>
		<div style="height:10px;"></div>
	</body>
</html>