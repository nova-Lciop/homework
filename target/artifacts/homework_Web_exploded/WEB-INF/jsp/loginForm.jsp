<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>作业管理系统</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<link href="${ctx}/js/metronic/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/js/metronic/plugins/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/js/metronic/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/js/metronic/css/style-metro.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/js/metronic/css/style.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/js/metronic/css/style-responsive.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/js/metronic/css/themes/default.css" rel="stylesheet"
	type="text/css" id="style_color" />
<link href="${ctx}/js/metronic/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${ctx}/js/metronic/css/pages/lock.css" rel="stylesheet"
	type="text/css" />
<!-- END PAGE LEVEL STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
<link href="${ctx }/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />  
<script src="${ctx }/js/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${ctx }/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="${ctx }/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${ctx }/js/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<script type="text/javascript">
   
   $(function(){
	    /** 按了回车键 */
	   $(document).keydown(function(event){
		   if(event.keyCode == 13){
			   $("#login-submit-btn").trigger("click");
		   }
	   })

	   /** 给登录按钮绑定点击事件  */
	   $("#login-submit-btn").on("click",function(){
		   /** 校验登录参数 ctrl+K */
		   var loginname = $("#loginname").val();
		   var password = $("#password").val();
		   
		   var msg = "";
		   if ($.trim(loginname.val()) == ""){
				msg = "用户名不能为空！";
				loginname.focus();
			}
		   else if($.trim(password.val()) == ""){
				msg = "密码不能为空！";
				password.focus();
		   }
		   if(msg !=""){
			   $.ligerDialog.error(msg);
			   return false;
		   }
		   else return true;
		   /** 提交表单 */
		   $("#loginForm").submit();
		   
	   })
	   
   })
 


</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body style="font-family: 微软雅黑">
	<div class="page-lock">
		<div class="page-logo" style="margin-bottom: 2px">
			<div class="brand" 
				style="font-size: 22px; color: #FFF;"> 作业管理<font color="#FFCC00">Homework</font><span
				style="font-size: 13px;">1.0</span> 系统
			</div>
		</div>
		<form action="login" method="post" id="loginForm">
			<div class="page-body">
				<img class="page-lock-img" src="${ctx}/js/metronic/img/profile/logo2.jpg" alt="">
				<div class="page-lock-info">
					<span>&nbsp;</span> 
					<c:choose>
						<c:when test="${requestScope.message == null }">
							<span style="padding-top: 5px">允许以中文名称登录</span>
						</c:when>
						<c:otherwise>
							<span style="padding-top: 5px"><font color="red">${requestScope.message}</font></span>
						</c:otherwise>
					</c:choose>
					
					<div class="control-group">
		
						<div class="controls">
							<div class="input-icon left">
								<i class="icon-user"></i> <input
									class="m-wrap placeholder-no-fix" type="text" placeholder="帐号"
									id="loginname" name="loginname" value="${loginname}">
							</div>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<div class="input-icon left">
								<i class="icon-lock"></i> <input
									class="m-wrap placeholder-no-fix" type="password"
									placeholder="密码" id="password" name="password"
									value="${password}">
							</div>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<div class="input-icon left">
								
								<select  name="loginType" d="loginType"
									 >
									 <option value="2">学生</option>
									<option value="1">教师</option>
									
									</select>
							</div>
						</div>
					</div>
				</div>
				<div></div>
				<div class="relogin">
						<!-- 单击登录 -->
						<button type="submit" id="login-submit-btn" class="btn green"
							style="margin-left: 20px">
							登录 <i class="m-icon-swapright m-icon-white"></i>
						</button>
				</div>
			</div>
			
		</form>
	</div>
	

</body>
<!-- END BODY -->
</html>