<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>学生作业管理系统 ——EXCEL上传</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="fkjava.ico" rel="shortcut icon" type="image/x-icon" />
		<link href="${ctx }/css/css.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
		<script type="text/javascript" src="${ctx}/js/tiny_mce/tiny_mce.js"></script>
		<script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
		<script type="text/javascript">
	     
        //JS校验form表单信息  
        function checkData(){  
           var fileDir = $("#upfile").val();  
           var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
           if("" == fileDir||null == fileDir){  
               alert("选择需要导入的Excel文件！");  
               return false;  
           }  
           if(".xls" != suffix && ".xlsx" != suffix ){  
               alert("选择Excel格式的文件导入！");  
               return false;  
           }  
           return true;  
        }
		</script>	
	</head>
	<body>
		
	
		<table width="100%" height="90%" border="0" cellpadding="10" cellspacing="0" class="main_tabbor">
		  	<tr valign="top">
			    <td class="fftd">
			    
				 
    <form method="POST"  enctype="multipart/form-data" id="form1" action="${ctx}/student/leadStudentExcel">  
       
             <label>上传文件: </label>
             <input id="upfile" type="file" name="upfile"><br> <br> 
             <input type="hidden" name="flag" value="2"/>
             班级：
							    <select name="clazz_id" style="width:143px;">
					    			<option value="0">--请选择班级--</option>
					    			<c:forEach items="${requestScope.clazzs }" var="clazz">
					    				<option value="${clazz.id }">${clazz.cname }</option>
					    			</c:forEach>
					    		</select>
	            <input type="submit" value="提交" onclick="return checkData()">
    </form>       
				</td>
		  	</tr>
		</table>
		<div style="height:10px;"></div>
	</body>
</html>