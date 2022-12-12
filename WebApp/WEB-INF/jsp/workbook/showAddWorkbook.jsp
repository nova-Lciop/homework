<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>学生作业管理系统——添加作业</title>
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
    	/** 习题表单提交 */
		$("#workbookForm").submit(function(){
			var title1 = $("#title1");
			var term = $("#term");
		
			var teacher = $("#teacher");
			var course = $("#course");
			var clazz = $("#clazz");
			
			var msg = "";
			if ($.trim(title1.val()) == ""){
				msg = "作业标题不能为空！";
				title1.focus();
			}else if($.trim(term.val()) == ""){
				msg = "学期不能为空！";
				term.focus();
			}else if($.trim(clazz.val()) == "0"){
				msg = "请选班级！";
				clazz.focus();
			}else if($.trim(course.val()) == "0"){
				msg = "请选择课程！";
				course.focus();
			
			}
			if (msg != ""){
				$.ligerDialog.error(msg);
				return false;
			}else{
				return true;
			}
			$("#workbookForm").submit();
		});
    }); 
	

	</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td height="10"></td></tr>
  <tr>
    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
	<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：作业管理  &gt; 添加作业</td>
	<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
  </tr>
</table>
<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
  <tr valign="top">
    <td>
    	 <form action="${ctx}/workbook/addWorkbook" id="workbookForm" method="post">
		 	<!-- 隐藏表单，flag表示添加标记 -->
    	 	<input type="hidden" name="flag" value="2">
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr><td class="font3 fftd">
		    	<table>
		    		<tr>
		    			<td class="font3 fftd">作业标题：<input type="text" name="title" id="title1" size="30"/></td>
		    			<td class="font3 fftd">作业所在学期：
		    			<select name="term" id="term" style="width:143px;">
					    			<option value="0">--请选择学期--</option>
					    			<c:forEach items="${requestScope.terms }" var="term">
					    				<option value="${term}">${term}</option>
					    			</c:forEach>
					    		</select>
		    			</td>
		    			
		    		    <td class="font3 fftd">作业状态：<select name="wflag" style="width:143px;" id="wflag">
					    				<option value="0">未发布</option>
     					    		</select>
     					  </td>
		    		</tr>
		    		<tr>
		    			
		    			<td class="font3 fftd">班&nbsp;&nbsp;&nbsp;级：
		    			 <select name="clazz.id" style="width:143px;" id="clazz">
					    			<option value="0">--请选择班级--</option>
					    			<c:forEach items="${requestScope.clazzs }" var="clazz">
					    				<option value="${clazz.id }">${clazz.cname }</option>
					    			</c:forEach>
					    		</select>
					    </td>
					    <td class="font3 fftd">课&nbsp;&nbsp;&nbsp;程：
		    			 <select name="course.id" style="width:143px;"  id="course">
					    			<option value="0">--请选择课程--</option>
					    			<c:forEach items="${requestScope.courses }" var="course">
					    				<option value="${course.id }">${course.cname }</option>
					    			</c:forEach>
					    		</select>
					    </td>
					    <td class="font3 fftd">
					    </td>
		    		</tr>
		   		    	</table>
		    </td></tr>
			<tr><td class="main_tdbor"></td></tr>
			
			<tr><td align="left" class="fftd">
			<input type="submit" value="添加">&nbsp;&nbsp;<input type="reset" value="取消 " ></td></tr>
		  </table>
		 </form>
	</td>
  </tr>
</table>
<div style="height:10px;"></div>
</body>
</html>