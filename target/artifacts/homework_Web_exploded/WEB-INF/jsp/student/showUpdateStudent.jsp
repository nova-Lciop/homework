<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>学生作业管理系统——修改学生</title>
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
		
    	/** 学生表单提交 */
		$("#studentForm").submit(function(){
			var username = $("#username");
			var loginname = $("#loginname");
			var password = $("#password");
			var clazz = $("#clazz");
			
			var msg = "";
			if ($.trim(username.val()) == ""){
				msg = "姓名不能为空！";
				username.focus();
			}else if ($.trim(loginname.val()) == ""){
				msg = "登录名不能为空！";
				loginname.focus();
			}else if ($.trim(password.val()) == ""){
				msg = "密码不能为空！";
				password.focus();
			}else if ($.trim(clazz.val()) == ""){
				msg = "班级名称不能为空！";
				clazz.focus();
			}
			if (msg != ""){
				$.ligerDialog.error(msg);
				return false;
			}else{
				return true;
			}
			$("#studentForm").submit();
		});
    });
		

	</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td height="10"></td></tr>
  <tr>
    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
	<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：学生管理  &gt; 修改学生</td>
	<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
  </tr>
</table>
<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
  <tr valign="top">
    <td>
    	 <form action="${ctx}/student/updateStudent" id="studentForm" method="post">
			<!-- 隐藏表单，flag表示添加标记 -->
    	 	<input type="hidden" name="flag" value="2">
			<input type="hidden" name="id" value="${student.id }">
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr><td class="font3 fftd">
		    	<table>
		    		
		    		<tr>
		    			<td class="font3 fftd">班级&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：
		    		<select id="clazz" name="clazz_id" style="width:143px;">
						   <option value="0">--班级选择--</option>
						   <c:forEach items="${requestScope.clazzs }" var="clazz">
						   		<c:choose>
			    					<c:when test="${student.clazz.id == clazz.id }">
			    						<option value="${clazz.id }" selected="selected">${clazz.cname }</option>
			    					</c:when>
			    					<c:otherwise>
			    						<option value="${clazz.id }">${clazz.cname }</option>
			    					</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
					</select>
					    </td>
		    		</tr>
		    		<tr>
		    			<td class="font3 fftd">学生姓名：<input type="text" name="username" id="username" size="20" value="${student.username }"/></td>
		    			<td class="font3 fftd">登录名：<input type="text" name="loginname" id="loginname" size="20" value="${student.loginname }"/></td>
		    			<td class="font3 fftd">密码：<input name="password" id="password" size="20" value="${student.password }"/></td>
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