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
    <script type="text/javascript" src="${ctx }/js/jquery.validate.js"></script>
	<script src="${ctx}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerResizable.jss" type="text/javascript"></script>
	<link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />
	<script language="javascript" type="text/javascript" src="${ctx }/js/My97DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
    $(function(){
    	
    	   	/** 表单提交检验 */
		$("#form1").submit(function(){
			var course_id = $("#course_id");
			var clazz_id = $("#clazz_id");
			var term = $("#term");
			var msg = "";
			if ($.trim(course_id.val()) == "0"){
				msg = "请选择课程！";
				course_id.focus();
			}else if($.trim(clazz_id.val()) == "0"){
				msg = "请选择班级！";
				clazz_id.focus();
			}else if($.trim(term.val()) == "0"){
				msg = "请选择学期！";
				term.focus();
			}
			if (msg != ""){
				$.ligerDialog.error(msg);
				return false;
			}else{
				return true;
			}
			$("#form1").submit();
		});
    });
    function  searchToExcel()
	{
	$("#form1").removeAttr('action');
	$("#form1").attr('action','${ctx}/workbook/exportToScoreExcel');
	$("#form1").submit();
	}
    function  searchToBrowser()
	{
	$("#form1").removeAttr('action');
	$("#form1").attr('action','${ctx}/workbook/searchStudentScore');
	$("#form1").submit();
	}
    </Script> 
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td height="10"></td></tr>
  <tr>
    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
	<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：作业管理  &gt; 输出成绩</td>
	<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
  </tr>
</table>
	<table width="100%" height="10%" border="0" cellpadding="5"
		cellspacing="0" class="main_tabbor">
		<tr valign="top">
			<td> 
				<form  action="" id="form1" name="form1"
					method="post">
					<table width="100%" border="0" cellpadding="0" cellspacing="10"
						class="main_tab">
						<tr>
							<td class="font3 fftd">
								<table>
									<tr>

										<td class="font3 fftd">班&nbsp;&nbsp;&nbsp;级：
										 <select	name="clazz_id" style="width: 143px;" id="clazz_id" class="required">
											<c:choose>
											<c:when test="${clazz.id!=null}">
												<option value="${clazz.id }" selected="selected">${clazz.cname }</option>
						            	    </c:when>
						            	    <c:otherwise><option value="0">--请选择班级--</option></c:otherwise>
										    </c:choose>
												<c:forEach items="${requestScope.clazzs }" var="clazz">
													<option value="${clazz.id }" >${clazz.cname }</option>
												</c:forEach>
										</select>
										</td>
										<td class="font3 fftd">课&nbsp;&nbsp;&nbsp;程： <select
											name="course_id" style="width: 143px;" id="course_id">
										<c:choose>
											<c:when test="${course.id!=null}">
												<option value="${course.id }" selected="selected">${course.cname }</option>
						            	    </c:when>
						            	    <c:otherwise><option value="0">--请选择课程--</option></c:otherwise>
										    </c:choose>
												<c:forEach items="${requestScope.courses }" var="course">
													<option value="${course.id }">${course.cname }</option>
												</c:forEach>
										</select>
										</td>
										<td class="font3 fftd">学&nbsp;&nbsp;&nbsp;期： <select
											name="term" style="width: 143px;" id="term">
											<c:choose>
											<c:when test="${term!=null}">
												<option value="${term}" selected="selected">${term}</option>
						            	    </c:when>
						            	    <c:otherwise><option value="0">--请选择学期--</option></c:otherwise>
										    </c:choose>
											<c:forEach items="${requestScope.terms }" var="term">
													<option value="${term}">${term }</option>
												</c:forEach>
										</select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="left" class="fftd">
   
				              <input  type="button" onclick="searchToBrowser();" value="输出到浏览器">
				              <input  type="button" onclick="searchToExcel();" value="输出到EXCEL">
									</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr><td height="10"></td></tr>
	  <tr>
	    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
		<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：显示学生作业成绩 &gt;
		 作业信息：${requestScope.workbookInfo}</td>
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
						<td>学号</td>
						<td>姓名</td>
						<c:forEach items="${scoreTitles}" var="st">
						 <td> <c:out value="${st}"></c:out></td>
				
						</c:forEach>
					

					</tr>
					<c:forEach items="${requestScope.studentScores}" var="studentScore"
						varStatus="stat">
						<tr id="data_${stat.index}" class="main_trbg" align="center">
							<td>${stat.index+1}	</td>
							<td>${studentScore.student.loginname}</td>	
							<td>${studentScore.student.username}</td>	
							<c:forEach items="${studentScore.scores}" var="score">	
							<td>${score}</td>	
							</c:forEach>	
				
						
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
</table>
</body>
</html>