<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>layout</title>
	<!-- layout -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<!-- ztree -->
	<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>

</head>	
<body>
	
	<!-- 方式三使用easyUI提供的API创建datagrid-->
	<hr>
	<table id="mytable">
	
	</table>
	<script type="text/javascript">
		$(function(){
			var myIndex = -1;//全局变量,值为正在编辑行的索引
			//页面加载完成后
			$("#mytable").datagrid({
				//定义标题行所有列
				columns:[[
				          {title:'编号',field:'id',checkbox:true},
				          {width:150, title:'姓名',field:'name',editor:{
				        	  								type:'validatebox',
				        	  								options:{}
				          									}},
				          									
				          {width:150, title:'年龄',field:'age', editor:{
															type:'numberbox',
							  								options:{}
															}},
				          {width:150, title:'日期',field:'address', editor:{
																type:'datebox',
								  								options:{}
																}},
				          ]],
				 url:'${pageContext.request.contextPath }/json/datagrid_data.json',
				 toolbar:[
				          {text:'添加',iconCls:'icon-add',
				        	  //为按钮绑定事件
				        	  handler:function(){
				        		  //alert('add..');
				        		  $("#mytable").datagrid("insertRow",{
				        			  index:0,//在第一行插入
				        			  row:{}//空行
				        		  });
				        		  
				        		  $("#mytable").datagrid("beginEdit",0);
				        		  myIndex = 0;
				        	  }
				          },
				          {text:'删除',iconCls:'icon-remove', handler:function(){
				        	//看选中的是哪一行索引,获得选中的行数
				        	  var rows = $("#mytable").datagrid("getSelections");
				        	  if(rows.length ==1){
				        		  var row = rows[0];
				        		  //获得选中行
				        		  myIndex = $("#mytable").datagrid("getRowIndex",row);
				        	  }
				        	  $("#mytable").datagrid("deleteRow",myIndex);
				        	 // $.post();
				          }},
				          {text:'修改',iconCls:'icon-edit',handler:function(){
				        	  //看选中的是哪一行索引,获得选中的行数
				        	  var rows = $("#mytable").datagrid("getSelections");
				        	  if(rows.length ==1){
				        		  var row = rows[0];
				        		  //获得选中行
				        		  myIndex = $("#mytable").datagrid("getRowIndex",row);
				        	  }
				        	  $("#mytable").datagrid("beginEdit",myIndex);
				          }},
				          {text:'保存',iconCls:'icon-save', handler:function(){
				        	  $("#mytable").datagrid("endEdit",myIndex);
				          }}
				          ],
				 //显示分页条
				 pagination:true,
				 //监听结束编辑事件
				 onAfterEdit:function(index,data,changes){
					 console.info(data);
					// $.post();
				 }
			});
		});
	</script>
</body>
</html>