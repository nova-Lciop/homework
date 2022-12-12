<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>学生作业管理系统 —— 复制作业</title>
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

	$(function() {
	
	
		/** 获取上一次选中的作业数据 */
		var boxs = $("input[type='checkbox'][id^='box_']");
		
		/** 给全选按钮绑定点击事件  */
		$("#checkAll").click(function() {
			// this是checkAll  this.checked是true
			// 所有数据行的选中状态与全选的状态一致
			boxs.attr("checked", this.checked);
		})

		/** 给数据行绑定鼠标覆盖以及鼠标移开事件  */
		$("tr[id^='data_']").hover(function() {
			$(this).css("backgroundColor", "#eeccff");
		}, function() {
			$(this).css("backgroundColor", "#ffffff");
		})

		/** 添加作业绑定点击事件if ($.trim(username.val()) == "") */
		$("#replace")
				.click(
						function() {
							
							/** 获取到用户选中的复选框  */							
							var checkedBoxs = boxs.filter(":checked");
							if (checkedBoxs.length < 1) {
								$.ligerDialog.error("请选择需要复制的作业！");
							} else {
								/** 得到用户选中的所有的需要添加ids */
								var ids = checkedBoxs.map(function() {
									return this.value;
								})

								$.ligerDialog
										.confirm(
												"确认要复制吗?",
												"复制作业",
												function(r) {
													if (r) {
														// alert("添加："+ids.get());
														// 发送请求
														window.location = "${ctx }/assignment/replaceAssignment?workbook_id=${workbook_id}&ids="
																+ ids.get()
																+ "&inps";
													}
												});
							}
						})
		/** 对布置作业表tb_assignment进行删除操作*/
		/** 获取上一次选中的作业数据 */
		var boxs1 = $("input[type='checkbox'][id^='box1_']");

		/** 给全选按钮绑定点击事件  */
		$("#checkAll1").click(function() {
			// this是checkAll1  this.checked是true
			// 所有数据行的选中状态与全选的状态一致
			boxs1.attr("checked", this.checked);
		})

		/** 给数据行绑定鼠标覆盖以及鼠标移开事件  */
		$("tr[id^='data_']").hover(function() {
			$(this).css("backgroundColor", "#eeccff");
		}, function() {
			$(this).css("backgroundColor", "#ffffff");
		})
		

	
	})
</script>
</head>
<body>
	<!-- 导航 -->
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr><td height="10"></td></tr>
	  <tr>
	    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
		<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置： 布置作业&gt;  复制作业</td>
		<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
	  </tr>
	</table>
	
	<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
	
		   	<tr><td> <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
		    <tr class="main_trbg_tit" align="center">
		    <td >请在下面的作业列表中选择要复制的作业 </td><td>要复制作业的信息</td>
		    <td>班级：${fromWorkbook.clazz.cname}</td>
		    <td>作业名称：${fromWorkbook.title}</td>
		    <td height="30">
				<form name="assignmentform" method="post" id="assignmentform"
								action="${ctx}/assignment/replaceAssignment">
								<input type="hidden" name="workbook_id" value="${workbook_id}" />
									 <input id="replace" type="button" value="复制作业" />
							</form>
			</td>
		    </table></td></tr>
		<!-- 要复制的作业数据展示区 -->
		<tr valign="top">
			<td height="20">
			<div id="exercise">
				<table width="100%" border="1" cellpadding="5" cellspacing="0"
					style="border: #c2c6cc 1px solid; border-collapse: collapse;">
					<tr class="main_trbg_tit" align="center">
						<td><input type="checkbox" name="checkAll" id="checkAll"></td>
						<td>作业题目</td>
						<td>作业状态</td>

						<td>作业所在学期</td>
						<td>班级</td>
						<td>课程</td>
						<td>教师</td>
						<td>创建时间</td>
								
					</tr>
					<c:forEach items="${requestScope.workbooks}" var="workbook"
						varStatus="stat">
						<tr id="data_${stat.index}" class="main_trbg" align="center">
							<td><input type="checkbox" id="box_${stat.index}"
								value="${workbook.id}"></td>
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

											
									
						</tr>
					</c:forEach>
				</table>
			</div>
			</td>
		</tr>
		<!-- 分页标签 -->
	  <tr valign="top"><td align="center" class="font3">
	  	 <fkjava:pager
	  	        pageIndex="${requestScope.pageModel.pageIndex}" 
	  	        pageSize="${requestScope.pageModel.pageSize}" 
	  	        recordCount="${requestScope.pageModel.recordCount}" 
	  	        style="digg"
	  	        submitUrl="${ctx}/assignment/toReplaceAssignment?pageIndex={0}&workbook_id=${workbook_id}"/>
	  </td></tr>
	  
	
	 
	</table>
	<div style="height:10px;"></div>
</body>
</html>