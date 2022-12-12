<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>学生作业管理系统 ——批改作业</title>
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

	<script type="text/javascript">
	       $(function(){
	    	    	   /** 给数据行绑定鼠标覆盖以及鼠标移开事件  */
		    	$("tr[id^='data_']").hover(function(){
		    		$(this).css("backgroundColor","#eeccff");
		    	},function(){
		    		$(this).css("backgroundColor","#ffffff");
		    	})
		    	
		    	
	       })
	</script>
</head>
<body>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr><td height="10"></td></tr>
	  <tr>
	    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
		<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：批改作业 &gt; 显示作业</td>
		<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
	  </tr>
	</table>
	
	<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
	
	  
	  <!-- 数据展示区 -->
		<tr valign="top">
			<td height="20">
				<table width="100%" border="1" cellpadding="5" cellspacing="0"
					style="border: #c2c6cc 1px solid; border-collapse: collapse;">
					<tr class="main_trbg_tit" align="center">
						<td>序号</td>
						<td>作业题目</td>
						<td>作业状态</td>

						<td>作业所在学期</td>
						<td>班级</td>
						<td>课程</td>
						<td>教师</td>
						<td>创建时间</td>
						<td align="center">查看学生</td>
                        <td align="center">统计作业中习题错误情况</td>
					</tr>
					<c:forEach items="${requestScope.workbooks}" var="workbook"
						varStatus="stat">
						<tr id="data_${stat.index}" class="main_trbg" align="center">
							<td><input type="hidden" value="${workbook.id}">${stat.index}</td>
							<td>${workbook.title }</td>
							<td><c:choose>
									<c:when test="${workbook.wflag==1}">发布</c:when>
									<c:when test="${workbook.wflag==2}">批改</c:when>
									<c:otherwise>未发布</c:otherwise>
								</c:choose></td>
							
							<td>${workbook.term }</td>
							<td>${workbook.clazz.cname}</td>
							<td>${workbook.course.cname}</td>
							<td>${workbook.teacher.username}</td>
							<td><f:formatDate value="${workbook.createDate}" type="date"
									dateStyle="long" /></td>

							<td align="center" width="40px;"><a
								href="${ctx}/correctWorkbook/findStudentByWorkbookId?workbook_id=${workbook.id}
								&clazz_id=${workbook.clazz.id}">
								<img title="查看学生" src="${ctx}/images/update.gif" />
							</a></td>
							<td align="center" width="40px;"><a
								href="${ctx}/correctWorkbook/findExerciseInfoByWorkbookId?workbook_id=${workbook.id}">
								<img title="习题错误情况" src="${ctx}/images/update.gif" />
							</a></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<!-- 分页标签 -->
	  <tr valign="top"><td align="center" class="font3">
	  	 <fkjava:pager
	  	        pageIndex="${requestScope.pageModel.pageIndex}" 
	  	        pageSize="${requestScope.pageModel.pageSize}" 
	  	        recordCount="${requestScope.pageModel.recordCount}" 
	  	        style="digg"
	  	        submitUrl="${ctx}/correctWorkbook/selectCorrectWorkbook?pageIndex={0}&isSimilarity=1"/>
	  </td></tr>
	</table>
	<div style="height:10px;"></div>
</body>
</html>