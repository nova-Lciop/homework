<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>学生作业管理系统 —— 查看作业</title>
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
	
	
		/** 获取上一次选中的习题数据 */
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

			/** 对布置作业表tb_assignment进行删除操作*/
		/** 获取上一次选中的习题数据 */
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
		/**单击搜索按钮*/
		$("#search")
				.click(
						function() {
							var kind = $('#kind option:selected').val();/*选中题型;*/
							var chapter = $('#chapter option:selected').val();/*选中的章*/
							window.location = "${ctx }/assignment/selectAssignment?workbook_id=${workbook.id}&kind="
									+ kind + "&chapter=" + chapter;
						})

		/** 添加习题绑定点击事件 */
		$("#del")
				.click(
						function() {
							/** 获取到用户选中的复选框  */
							var checkedBoxs1 = boxs1.filter(":checked");

							if (checkedBoxs1.length < 1) {
								$.ligerDialog.error("请选择需要删除的习题！");
							} else {
								/** 得到用户选中的所有的需要添加ids */
								var ids1 = checkedBoxs1.map(function() {
									return this.value;
								})

								$.ligerDialog
										.confirm(
												"确认要删除吗?",
												"从作业表中删除习题",
												function(r) {
													if (r) {
														// alert("删除："+ids.get());
														// 发送请求
														window.location = "${ctx }/assignment/delAssignment?workbook_id=${workbook.id}&ids="
																+ ids1.get();
													}
												});

							}
						})
		//更改分值 
			$("#updateGrade")
				.click(
						function() {
							/** 获取到用户选中的复选框  */
							var els =document.getElementsByName("grade");
							var id=document.getElementsByName("assignmentId");
							var ids="";
							var grades="";
                            for (var i = 0, j = els.length; i < j; i++){
                            	grades+=els[i].value+",";
                            	ids+=id[i].value+",";
                            
                            }
							window.location = "${ctx }/assignment/udateGrade?workbook_id=${workbook.id}&grades="+grades+
									"&ids="+ids;
													
											

						
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
		<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置： 布置作业&gt;  查看作业</td>
		<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
	  </tr>
	</table>
	
	<table width="100%" height="20%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
	
		  
	   <!-- 当前作业内容数据展示区 -->
	<tr><td> <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
		    <tr class="main_trbg_tit" align="center">
		    <td width="80%">作业标题：${workbook.title}&nbsp;&nbsp;班级:${workbook.clazz.cname}
			&nbsp;&nbsp;课程：${workbook.course.cname}</td><td><button type="button" id="del">删除</button></td>
			<td><button type="button" id="updateGrade">更新分值</button></td>
			<td><form name="assignmentform" method="post" id="assignmentform"
								action="${ctx}/assignment/selectExercise">
								<input type="hidden" name="workbook_id" value="${workbook.id}" />
									<input type="hidden" name="course_id" value="${workbook.course.id}" />
																
									 <input id="add" type="submit" value="添加习题" />
							</form></td>
		    </table></td></tr>
	  <tr valign="top">
	    <td height="20">
	    
		  <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
		    <tr class="main_trbg_tit" align="center">
			 <td width="2%"><input type="checkbox" name="checkAll1" id="checkAll1"></td>
			            <td width="2%">序号</td>
						<td width="2%">章</td>
						<td width="2%">题型</td>
						<td width="50%">内容</td>
						<td width="25%">答案</td>
						<td width="12%">课程</td>
						<td width="5%">出题教师</td>
		                <td width="1%">分值</td>
			</tr>
			<c:forEach items="${requestScope.assignments}" var="assignment" varStatus="stat">
				<tr id="data_${stat.index}" class="main_trbg" >
					<td><input type="checkbox" name="assignmentId" id="box1_${stat.index}" value="${assignment.id}"></td>
					 <td>${stat.index+1}</td>	
					 <td>${assignment.exercise.chapter}</td>
					  <td>
					  <c:if test="${assignment.exercise.kind==1}">选择题 </c:if>
					  <c:if test="${assignment.exercise.kind==2}">填空题 </c:if>
					  <c:if test="${assignment.exercise.kind==3}">简答题 </c:if>    
					  </td>
					  <td align="left"><textarea  rows="5" cols="115" readonly="readonly" >${assignment.exercise.content }</textarea></td>
							<td  align="left"><textarea  rows="5" cols="30" readonly="readonly">${assignment.exercise.answer }</textarea></td>
					 <td>${assignment.workbook.course.cname }</td>
					  <td>${assignment.workbook.teacher.username}</td>
					    <td><input type="text" name="grade" id="grade_${stat.index}" value="${assignment.grade}" size="1" ></td>
				
				</tr>
			</c:forEach>
		  </table>
		
		</td>
	  </tr>
	 
	</table>
	<div style="height:10px;"></div>
</body>
</html>