<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext/easyui/themes/green/easyui.css?t=34355">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext/easyui/themes/color.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext/farm/farm.css">
    <script type="text/javascript" src="<%=basePath%>ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>ext/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>ext/farm/helper.js?346t"></script> 
</head>
        <body>
        <div id="controlBox" style="background-color:green;">
        	<span style="color:white;">用户名称:</span>
        	<input id="genderSearch"  type="text"/>

        	<a href="javascript:void(0)" class="easyui-linkbutton c1" iconCls="icon-search" onclick="doSearch()">查询</a>

        	<a href="javascript:void(0)" class="easyui-linkbutton c2" iconCls="icon-add" onclick="javascript:grid.edatagrid('addRow')">添加</a>

        	<a href="javascript:void(0)" class="easyui-linkbutton c3" iconCls="icon-remove" onclick="javascript:grid.edatagrid('cancelRow')">取消</a>

        	<a href="javascript:void(0)" class="easyui-linkbutton c5" iconCls="icon-cancel" onclick="javascript:deleteRecord()">删除</a>
        </div>
         <div id="formContainer" class="easyui-dialog" style="width:460px;height:150px;padding:10px 10px" closed="true" buttons="#formContainerButtons">
   		 	<form id="formEditor" enctype="multipart/form-data" method="post">
   		 		<table>
   		 			<tr>  
                       <td> 上传头像:</td>  
                       <td>  
                           <input type="file" id="fuImportMultipleShipmentStatus" name="filePathName" />  
                       </td>
                   </tr> 
   		 		</table>
    		</form>
    	</div>
    	<div id="formContainerButtons">
    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="ImportShipmentStatusList()">开始上传</a>
    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#formContainer').dialog('close')">取消</a>
    	</div> 
        <table id="grid"></table>
        <div id="msgBox"></div> 
        <div id="cropGrow"  style="overflow-y:hidden!important;"></div> 
        <script>
        var grid;
        var cId;
        $(document).ready(function () {
        //配置表格
        grid = $('#grid').edatagrid({
        title: '用户清单',
        height: 600,
        method:'post',
        url: '<%=basePath%>user/gridData',
        saveUrl: '<%=basePath%>user/save',
        updateUrl: '<%=basePath%>user/save',
        destroyUrl: '<%=basePath%>user/delete',
        border: false,
        rownumbers: true,
        remoteSort: true,
        nowrap: false,
        singleSelect: true,
        fitColumns: true,
        striped: true,
        pagination: true,
        autoSave:true,
        idField: "ID",
        columns: [[
        {field: 'id',title: 'ID' , width: 20, sortable: true,align:'center'},
        {title: 'uId', field: 'uId', width: 50, sortable: true,align:'center',
        	editor:{
		        type:'validatebox',
		        options: {
		        required:true
		        }
        	}
        },
        {title: '头像', field: 'heads', width: 50, sortable: true,align:'center',
        	editor:{
		        type:'validatebox',
		        options: {
		        required:true
		        }
        	},
	    	formatter:function(value,row){
	    		return '<img style="height:30px;" src="<%=basePath%>images/headImages/'+value+'"/>';
			}
        },
        {title: '用户名', field: 'username', width: 50, sortable: true,align:'center',
        	editor:{
		        type:'validatebox',
		        options: {
		        required:true
		        }
        	}
        },
        {title: '昵称', field: 'nickname', width: 50, sortable: true,align:'center',
        	editor:{
		        type:'validatebox',
		        options: {
		        required:true
		        }
        	}
        },
        {title: '经验值', field: 'exp', width: 50, sortable: true,align:'center',
        	editor:{
		        type:'validatebox',
		        options: {
		        required:true
		        }
        	},
	    	formatter:function(value,row){
	    		return '<img style="height:30px;"  src="<%=basePath%>images/火把僵尸.png">'+value;
			}
        },
        {title: '积分', field: 'score', width: 50, sortable: true,align:'center',
        	editor:{
		        type:'validatebox',
		        options: {
		        required:true
		        }
        	},
	    	formatter:function(value,row){
	    		return '<img style="height:30px;"  src="<%=basePath%>images/僵尸.png">'+value;
			}
        },
        {title: '金币', field: 'price', width: 50, sortable: true,align:'center',
        	editor:{
		        type:'validatebox',
		        options: {
		        required:true
		        }
        	},
	    	formatter:function(value,row){
	    		return '<img style="height:30px;"  src="<%=basePath%>images/举旗僵尸.png">'+value;
			}
        },
        {title: '操作', field: 'option', width: 50,align:'center',
        	formatter:function(value,row){
                return  '<a href="javascript:void(0)" style="background-color:white;border-radius:5px;"  class="easyui-linkbutton" onclick="javascript:showFormEdit()">上传头像</a>'
                		+'<a href="javascript:void(0)" style="background-color:white;border-radius:5px;margin-left:30px;"  class="easyui-linkbutton" onclick="javascript:grid.edatagrid(\'saveRow\')">保存数据</a>'
               }
        }
        ]],
        destroyMsg:{
	        norecord:{
		        title:'警告',
		        msg:'首先需要选中记录，然后在点击删除按钮'
		        },
		        confirm:{
			        title:'确认',
			        msg:'是否删除选中记录?'
		        }
        },
        onBeforeEdit:function(rowIndex, rowData){
        	if(rowData&&rowData.isNewRecord)rowData.id=0;
        },
        onSuccess:function(index,row){
	        $.messager.show({
                title: "消息",
                msg: row.msg
            });
	        grid.datagrid('reload');
        }
        });
        grid.datagrid("getPager").pagination({
	        pageSize: 5,
	        pageList: [5,10,15,20]
        });
        grid.datagrid("resize",{
	        height:($(window).height()-36)
	        });
        });
        function doSearch(){
	        grid.datagrid("load",{
	        username: $("#genderSearch").val()
        	})
	    };   
        function deleteRecord() {
        	var row = grid.datagrid('getSelected');
        	$.post('<%=basePath%>user/delete',row,function(data){
        		 $.messager.show({
                     title: "消息",
                     msg: data.msg
                 });
        	});
        	grid.datagrid('reload');
        };
        function showFormEdit(){
        	 $('#formContainer').dialog('open').dialog('center').dialog('setTitle','上传头像');
    	}
        function ImportShipmentStatusList() {
            if ($("#fuImportMultipleShipmentStatus").val() == "" ) { 
                $.messager.show({
                    title: "消息",
                    msg: "请至少选择一个需要上传的文件"
                });
                return;  
            }  
            $('#formEditor').form('submit', {
                url: '<%=basePath%>file/saveHeadImg',
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.code == 0) {
                        $('#formContainer').dialog('close');
                    }
                    $.messager.show({
                        title: "消息",
                        msg: result.msg
                    });
                }
            })
        } 
        </script>
     </body>
</html>