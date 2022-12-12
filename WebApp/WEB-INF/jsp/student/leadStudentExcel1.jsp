<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>   
    <script type="text/javascript" src="../js/jquery-1.7.1.js"></script> 
    <script type="text/javascript" src="../js/jquery.form.js"></script>     
    <script type="text/javascript">  
   
         
               
             //JS校验form表单信息  
             function checkData(){  
                var fileDir = $("#upfile").val();  
                var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
                if("" == fileDir){  
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
 
    <form method="POST"  enctype="multipart/form-data" id="form1" action="${ctx}/student/leadStudentExcel">  
       
             <label>上传文件: </label>
             <input id="upfile" type="file" name="upfile"><br> <br> 
       
            <input type="submit" value="表单提交" onclick="return checkData()">
           

    </form>       
  </body>  
</html>  