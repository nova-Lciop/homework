<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>学生作业管理系统 —— 课程管理</title>
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
	    	   /** 获取上一次选中的部门数据 */
	    	   var boxs  = $("input[type='checkbox'][id^='box_']");
	    	   
	    	   /** 给全选按钮绑定点击事件  */
		    	$("#checkAll").click(function(){
		    		// this是checkAll  this.checked是true
		    		// 所有数据行的选中状态与全选的状态一致
		    		boxs.attr("checked",this.checked);
		    	})
		    	
	    	   /** 给数据行绑定鼠标覆盖以及鼠标移开事件  */
		    	$("tr[id^='data_']").hover(function(){
		    		$(this).css("backgroundColor","#eeccff");
		    	},function(){
		    		$(this).css("backgroundColor","#ffffff");
		    	})
		    	
		    	
	    	   /** 删除 课程绑定点击事件 */
	    	   $("#delete").click(function(){
	    		   /** 获取到用户选中的复选框  */
	    		   var checkedBoxs = boxs.filter(":checked");
	    		   if(checkedBoxs.length < 1){
	    			   $.ligerDialog.error("请选择一个需要删除的 课程！");
	    		   }else{
	    			   /** 得到用户选中的所有的需要删除的ids */
	    			   var ids = checkedBoxs.map(function(){
	    				   return this.value;
	    			   })
	    			   
	    			   $.ligerDialog.confirm("确认要删除吗?","删除 课程",function(r){
	    				   if(r){
	    					   // alert("删除："+ids.get());
	    					   // 发送请求
	    					   window.location = "${ctx }/exercise/removeExercise?ids=" + ids.get();
	    				   }
	    			   });
	    		   }
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
		<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置： 课程管理 &gt;  课程查询</td>
		<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
	  </tr>
	</table>
	
	<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
	  <!-- 查询区  -->
	  <tr valign="top">
	    <td height="30">
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr>
			  <td class="fftd">
				      <input type="hidden" name="exercise" value="${exercise}">
			  <input type="hidden" name="kind" value="${kind}">
			  <input type="hidden" name="chapter" value="${chpater}">
			  	<form name="exercixeform" method="post" id="exerciseform" action="${ctx}/exercise/selectExercise">
			
				    <table width="100%" border="0" cellpadding="0" cellspacing="0">
					  <tr class="font3">
					    <td >
					    	课程：
							    <select name="course.id" style="width:143px;">
					    			
					    			<option value="${course.id}">${course.cname }</option>
					    		
					    			<c:forEach items="${requestScope.courses }" var="course">
					    				<option value="${course.id }">${course.cname }</option>
					    			</c:forEach>
					    		</select></td><td>
					    	教师：
							    <select name="teacher.id" style="width:143px;">
					    			<option value="${teacher.id}">${teacher.username}</option>
					    			<c:forEach items="${requestScope.teachers }" var="teacher">
					    				<option value="${teacher.id }">${teacher.username}</option>
					    			</c:forEach>
					    		</select>
					    	</td><td>
					    	习题类型：
					<select id="kind" name="kind" style="width:143px;">
						   <option value="0">
						   <c:if test="${kind==null}">请选择题型</c:if>
					    			<c:if test="${kind!=null}">
					    			<c:if test="${kind==1}">选择</c:if>
					    			<c:if test="${kind==2}">填空</c:if>
					    			<c:if test="${kind==3}">简答</c:if>
					    		
					    			</c:if>
						   </option>
						   <option value="1">选择</option>
						   <option value="2">填空</option>
						   <option value="3">简答</option>
					</select>
					    		</td><td>
					    			习题所在章：
					    		 <select name="chapter" style="width:143px;">
					    			<option value="0">
					    			<c:if test="${chapter==null}">请选择章</c:if>
					    			<c:if test="${chapter!=null}">${chapter}</c:if>
					    			</option>
					    			<c:forEach items="${requestScope.chapters }" var="chapter">
					    				<option value="${chapter}">${chapter}</option>
					    			</c:forEach>
					    		</select>
					    		 
						    	<input type="submit" value="搜索"/>
					    	<input id="delete" type="button" value="删除"/>
					   
					    </td>
					  </tr>
					  <tr>
					   
					  </tr>
					</table>
				</form>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	  
	  <!-- 数据展示区 -->
	  <tr valign="top">
	    <td height="20">
		  <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
		    <tr class="main_trbg_tit" align="center">
			  <td><input type="checkbox" name="checkAll" id="checkAll"></td>
			  <td width="2%">章</td>
			  <td width="3%">题型</td>
			  <td width="50%">内容</td>
			  <td width="20%">答案</td>
			  <td width="10%">课程</td>
			  <td width="5%">出题教师</td>
			  <td align="center" width="5%">操作</td>
			</tr>
			<c:forEach items="${requestScope.exercises}" var="exercise" varStatus="stat">
				<tr id="data_${stat.index}" class="main_trbg" align="center">
					<td><input type="checkbox" id="box_${stat.index}" value="${exercise.id}"></td>
					 <td>${exercise.chapter}</td>
					  <td>
					  <c:if test="${exercise.kind==1}">选择题 </c:if>
					  <c:if test="${exercise.kind==2}">填空题 </c:if>
					  <c:if test="${exercise.kind==3}">简答题 </c:if>    
					  </td>
					   <td><textarea  rows="10" cols="110" readonly="readonly">${exercise.content }</textarea></td>
					    <td align="left"><textarea  rows="10" cols="34" readonly="readonly">${exercise.answer}</textarea></td>
					  <td>${exercise.course.cname }</td>
					  <td>${exercise.teacher.username}</td>
					 
					 
					  <td align="center" width="40px;">
					  <a href="${ctx}/exercise/updateExercise?flag=1&id=${exercise.id}">
							<img title="修改" src="${ctx}/images/update.gif"/></a>
					  </td>
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
	  	        submitUrl="${ctx}/exercise/selectExercise?pageIndex={0}&teacher_id=${teacher_id}&course_id=${course_id}&kind=${kind}&chapter=${chapter}" />
	  </td></tr>
	</table>
	<div style="height:10px;"></div>
</body>
</html>