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
	<script type="text/javascript" src="${ctx }/js/jquery-3.2.1.min.js"></script>

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
						<td width="100%">课程：${workbook.course.cname}&nbsp;&nbsp;作业标题：${workbook.title}
							&nbsp;&nbsp;班级:${workbook.clazz.cname}&nbsp;&nbsp;学生姓名:${student.username}
							<p>
						</td></tr>
				</table>
			</td>
		</tr>
		<tr valign="top">
			<td height="20" width="100%">
				<form action="${ctx}/correctWorkbook/correctWorkbook"
					method="post">
					<input type="hidden" name="flag" value="2">
					<input type="hidden" name="student_id" value="${student_id}">
					<input type="hidden" name="workbook_id" value="${workbook_id}">
					
					 <table class="main_locbg font2" width="100%" border="1" cellpadding="5"  cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
						<tr>
							<td width="20%" >序号</td>
							<td  width="10%">题型</td>
							<td width="70%">题干</td>
						</tr>
						<c:forEach
							items="${requestScope.saveStudentWork.studentWorkbooks}"
							var="studentWorkbook" varStatus="stat">
							<input type="hidden"
								name="studentWorkbooks[${stat.index }].student.id"
								value="${studentWorkbook.student.id }">
						
							<tr id="data_${stat.index}" class="main_trbg" align="center">
								<td >${stat.index+1}&nbsp;&nbsp;</td>
								<td><c:if test="${studentWorkbook.exercise.kind==1}">
					选择题 
					 <c:set var="rows1" value="5">
										</c:set>
										<c:set var="cols1" value="180"></c:set>
										<c:set var="rows2" value="1">
										</c:set>
										<c:set var="cols2" value="180"></c:set>
										<c:set var="maxlength" value="200"></c:set>
									</c:if> <c:if test="${studentWorkbook.exercise.kind==2}">
					  填空题
					  
					  <c:set var="rows1" value="5">
										</c:set>
										<c:set var="cols1" value="180"></c:set>
										<c:set var="rows2" value="1">
										</c:set>
										<c:set var="cols2" value="180"></c:set>
										<c:set var="maxlength" value="200"></c:set>
									</c:if> <c:if test="${studentWorkbook.exercise.kind==3}">
					  简答题 
					  <c:set var="rows1" value="5">
										</c:set>
										<c:set var="cols1" value="180"></c:set>
										<c:set var="rows2" value="40">
										</c:set>
										<c:set var="cols2" value="180"></c:set>
										<c:set var="maxlength" value="5800"></c:set>
									</c:if></td>
								<td width="80%" class="main_locbg font2"><textarea rows="${rows1}" cols="${cols1}"
										readonly="readonly" id="content" name="content">${studentWorkbook.exercise.content }</textarea>
										
							</tr><c:if test="${studentWorkbook.exercise.kind==3}">
							<tr><td colspan="3"> 查重率：${studentWorkbook.studentRate}&nbsp对应的学生：${studentWorkbook.instructions}</td></tr>
							</c:if>
							<tr>
						
							<tr>
								<td colspan="2"  class="main_locbg font2"  >学生答案:</td>
								<td ><textarea  readonly="readonly"
										name="studentWorkbooks[${stat.index }].studentAnswer" 
										id="studentAnswer" 
										rows="${rows2}" cols="${cols1}" maxlength="${maxlength}">${studentWorkbook.studentAnswer }
					   </textarea>
	                                      
					   </td>
							</tr>
							<tr>
								<td colspan="2" class="main_locbg font2">参考答案:</td>
								<td class="main_locbg font2"><textarea readonly="readonly"
										rows="${rows2}" cols="${cols1}">${studentWorkbook.exercise.answer }
					   </textarea></td>
							</tr>
							<tr>
								<td colspan="2" class="main_locbg font2">教师批注:</td>
						<td class="main_locbg font2"><textarea	name="studentWorkbooks[${stat.index }].notes" rows="3"
										cols="180">${studentWorkbook.notes}
					   </textarea></td>
							</tr>
							<tr>
								<td colspan="2" class="main_locbg font2">本题得分:</td>
								<td class="main_locbg font2">(本题分值${studentWorkbook.grade})： <input
									type="text" name="studentWorkbooks[${stat.index }].score"
									value="${studentWorkbook.score}">
								</td>
							</tr>

						</c:forEach>
						<tr>
						<td  align="right" colspan="3">
						<table width="100%" border="1" cellpadding="5" cellspacing="0"
					style="border: #c2c6cc 1px solid; border-collapse: collapse;">
					<tr class="main_trbg_tit" align="center">
						<td width="100%">课程：${workbook.course.cname}&nbsp;&nbsp;作业标题：${workbook.title}
							&nbsp;&nbsp;班级:${workbook.clazz.cname}
							&nbsp;&nbsp;学生姓名:${student.loginname}
							&nbsp;&nbsp;学生姓名:${student.username}
						</td><td><input type="submit" value="提交" ></td></tr>
				</table>
	
						</td></tr>
		  </table>
					
			
				</form>
			</td>
		</tr>

	</table>
	<div style="height: 10px;"></div>
</body>
</html>