<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>学生作业管理系统 —— 批改作业</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${ctx}/css/css.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ligerUI/skins/Aqua/css/ligerui-dialog.css"/>
	<link href="${ctx}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
	<script src="${ctx}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerResizable.jss" type="text/javascript"></script>
	<link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />

</head>
<body>
	<table>

		<!-- 当前作业内容数据展示区 -->
		<tr>
			<td>
				<table width="100%" border="1" cellpadding="5" cellspacing="0"
					style="border: #c2c6cc 1px solid; border-collapse: collapse;">
					<tr class="main_trbg_tit" align="center">
						<td width="100%">作业标题：${workbook.title}&nbsp;&nbsp;班级:${workbook.clazz.cname}
							&nbsp;&nbsp;课程：${workbook.course.cname}
							<p>
						</td>
				</table>
			</td>
		</tr>
		<tr valign="top">
			<td height="20" width="100%">
				<form action="${ctx}/correctWorkbook/selectCorrectWorkbook"
					method="post">
					<input type="hidden" name="flag" value="2">
					<input type="hidden" name="isSimilarity" value="1">
					<table width="100%" border="1" cellpadding="5" cellspacing="0"
						style="border: #c2c6cc 1px solid; border-collapse: collapse;">
						<c:forEach
							items="${requestScope.exerciseInfos}"
							var="exerciseInfo" varStatus="stat">
							<tr id="data_${stat.index}" class="main_trbg" align="center">
								<td width="100%">
								
								<input type="hidden"
									name="exerciseInfos[${stat.index }].exercis.id"
									value="${exerciseInfo.exercise.id}"> 
									序号:${stat.index+1}&nbsp;&nbsp; 题型: <c:if
										test="${exerciseInfo.exercise.kind==1}">选择题 </c:if> <c:if
										test="${exerciseInfo.exercise.kind==2}">填空题 </c:if> <c:if
										test="${exerciseInfo.exercise.kind==3}">简答题 </c:if>
							</tr>
							<tr>
								<td>习题内容：<textarea rows="10" cols="180" readonly="readonly">${exerciseInfo.exercise.content }</textarea></td>
							<tr>
								<td align="left">本题出错人数：${exerciseInfo.count} 
								</td>
							</tr>
						</c:forEach>
						<tr>
							<td align="right"><input type="submit" value="提交"></td>
						</tr>
					</table>
				</form>
			</td>
		</tr>

	</table>
	<div style="height: 10px;"></div>
</body>
</html>