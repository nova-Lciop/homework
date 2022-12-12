<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>学生作业管理系统——修改习题</title>
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
	<script language="javascript" type="text/javascript" src="${ctx }/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$(function(){
		
    	/** 表单提交 */
		$("#exerciseForm").submit(function(){
			var content = $("#content");
			
			
			
			var msg = "";
			if ($.trim(content.val()) == ""){
				msg = "内容不能为空！";
				content.focus();
			}
			if (msg != ""){
				$.ligerDialog.error(msg);
				return false;
			}else{
				return true;
			}
			$("#exerciseForm").submit();
		});
    });
		

	</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td height="10"></td></tr>
  <tr>
    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
	<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：习题管理  &gt; 修改习题</td>
	<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
  </tr>
</table>
<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
  <tr valign="top">
    <td>
    	 <form action="${ctx}/exercise/updateExercise" id="exerciseForm" method="post">
			<!-- 隐藏表单，flag表示添加标记 -->
    	 	<input type="hidden" name="flag" value="2">
			<input type="hidden" name="id" value="${exercise.id }">
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr><td class="font3 fftd">
		    	<table>
		    		
		    		<tr>
		    			<td class="font3 fftd">课&nbsp;&nbsp;&nbsp;程：
		    		<select id="course" name="course_id" style="width:143px;">
						   <option value="0">--课程选择--</option>
						   <c:forEach items="${requestScope.courses }" var="course">
						   		<c:choose>
			    					<c:when test="${exercise.course.id == course.id }">
			    						<option value="${coures.id }" selected="selected">${course.cname }</option>
			    					</c:when>
			    					<c:otherwise>
			    						<option value="${course.id }">${course.cname }</option>
			    					</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
					</select>
					    <input type="hidden" name="teacher_id" value="${exercise.teacher.id }">
					 习题类型：
		    		<select id="kind" name="kind" style="width:143px;">
						   <option value="0">--类型选择--</option>
						   <c:forEach items="${requestScope.kinds }" var="kind">
						   		<c:choose>
			    					<c:when test="${exercise.kind == kind.key }">
			    						<option value="${kind.key }" selected="selected">${kind.value}</option>
			    					</c:when>
			    					<c:otherwise>
			    						<option value="${kind.key}">${kind.value}</option>
			    					</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
					</select>
					    </td>  
					  <td class="font3 fftd">习题所在章：
		    		<select id="chapter" name="chapter" style="width:143px;">
						   <option value="0">--选择章--</option>
						   <c:forEach items="${requestScope.chapters }" var="chapter">
						   		<c:choose>
			    					<c:when test="${exercise.chapter == chapter }">
			    						<option value="${chapter }" selected="selected">${chapter}</option>
			    					</c:when>
			    					<c:otherwise>
			    						<option value="${chapter}">${chapter}</option>
			    					</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
					</select>
					    </td>     
		    		</tr>
		    		<tr>
		    			<td class="font3 fftd">
		    			习题内容：<textarea  name="content" id="content" rows="20" cols="180" maxlength="2900"  >${exercise.content}</textarea></td>
		    			
		    		</tr>
		    		<tr>
		    		<td class="font3 fftd">
		    		习题答案：<textarea   name="answer" id="answer" rows="40" cols="180"  maxlength="5800" >${exercise.answer}</textarea></td>
		    			
		    		</tr>
	    		
		    	</table>
		    </td></tr>
			
			<tr><td align="left" class="fftd"><input type="submit" value="修改">&nbsp;&nbsp;<input type="reset" value="取消 "></td></tr>
		  </table>
		 </form>
	</td>
  </tr>
</table>
<div style="height:10px;"></div>
</body>
</html>