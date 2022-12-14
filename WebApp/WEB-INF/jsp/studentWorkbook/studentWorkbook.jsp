<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>学生作业管理系统 ——学生作业管理</title>
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
		    	
		    	
	    	   /** 删除作业绑定点击事件 */
	    	   $("#delete").click(function(){
	    		   /** 获取到用户选中的复选框  */
	    		   var checkedBoxs = boxs.filter(":checked");
	    		   if(checkedBoxs.length < 1){
	    			   $.ligerDialog.error("请选择一个需要删除的作业！");
	    		   }else{
	    			   /** 得到用户选中的所有的需要删除的ids */
	    			   var ids = checkedBoxs.map(function(){
	    				   return this.value;
	    			   })
	    			   
	    			   $.ligerDialog.confirm("确认要删除吗?","删除作业",function(r){
	    				   if(r){
	    					   // alert("删除："+ids.get());
	    					   // 发送请求
	    					   window.location = "${ctx }/workbook/removeWorkbook?ids=" + ids.get();
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
		<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：学生作业管理 &gt; 作业查看</td>
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
						<td width="2%">序号</td>
						<td width="15%">作业题目</td>
						<td width="10%">作业所在学期</td>
						<td width="5%">班级</td>
						<td width="10%">课程</td>
						<td width="5%">教师</td>
						<td width="6%" align="center">上传与下载文件</td>
							<td width="6%" align="center">查看或做作业</td>
					</tr>
					<c:forEach items="${requestScope.workbooks}" var="workbook"
						varStatus="stat">
						<tr id="data_${stat.index}" class="main_trbg" align="center">
							<td><input type="hidden" id="hidden"
								value="${workbook.id}">${stat.index+1}</td>
							<td>${workbook.title }</td>
							<td>${workbook.term }</td>
							<td>${workbook.clazz.cname}</td>
							<td>${workbook.course.cname}</td>
							<td>${workbook.teacher.username}</td>
							<td align="center" width="40px;">
							<c:choose>
							<c:when test="${isFinish==1}"> 
							<a
								href="${ctx}/studentWorkbook/uploadFile?flag=1&id=${workbook.id}">
									<img title="上传文件" src="${ctx}/images/update.gif" />
							</a>
							</c:when>
							<c:otherwise>
							  <c:choose>
							      <c:when test="${workbook.fileName!=null }">
						        	<a
						         		href="${ctx}/studentWorkbook/downloadFile?id=${workbook.id}">
									<img title="下载文件" src="${ctx}/images/update.gif" />
						        	</a>
						        	</c:when>
						        <c:otherwise>
						        <img title="无下载文件" src="${ctx}/images/update.gif" />
						        
						        </c:otherwise>
							
						     </c:choose>
							</c:otherwise>
							</c:choose>
							</td>
							<td align="center" width="40px;">
							<c:choose>
							<c:when test="${isFinish==1}">
							<a
								href="${ctx}/studentWorkbook/doStudentWorkbook
								?flag=1&workbook_id=${workbook.id}&isFinish=${isFinish}">
									<img title="完成作业" src="${ctx}/images/bt_edit.gif" />
							</a>
							</c:when>
							<c:otherwise>
							<a
								href="${ctx}/studentWorkbook/doStudentWorkbook
								?flag=1&workbook_id=${workbook.id}&isFinish=${isFinish}"">
									<img title="查看作业" src="${ctx}/images/bt_edit.gif" />
							</a>
							</c:otherwise>
							</c:choose>
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
	  	        submitUrl="${ctx}/studentWorkbook/selectStudentWorkbook?pageIndex={0}&isFinish=${isFinish}"/>
	  </td></tr>
	</table>
	<div style="height:10px;"></div>
</body>
</html>