<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="utf-8">
		<title>仓库管理系统</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/materialdesignicons.min.css" rel="stylesheet">
		<link href="css/style.min.css" rel="stylesheet">
		<!--时间选择插件-->
		<link rel="stylesheet" href="js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
		<!--日期选择插件-->
		<link rel="stylesheet" href="js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
	</head>
	<body>
		<div class="card">
			<div class="card-header">
				<h4>增加出库货物信息</h4>
			</div>
			<div class="lyear-layout-sidebar-scroll">
			<div class="card-body">
				<div style="height: 60px;"></div>
				<form class="form-horizontal" action="ExpoServlet?method=expoAdd" method="post" id="myform">
					<label class="col-md-3 control-label">出库货物编号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="goodsid" id="goodsid" value="" />
					</div>
					<div style="height: 50px;"><a id="goodsidMsg"></a></div>
					<label class="col-md-3 control-label">出库货物介绍</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="exponame" id="exponame" value="" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">出库货物数量</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="exponum" id="exponum" value="" />
					</div>
					<div style="height: 50px;"><a id="exponumMsg"></a></div>
					<label class="col-md-3 control-label">出库货物单价</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="expoprice" id="expoprice" value="" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">出库货物登记时间</label>
					<div class="col-xs-4">
						<input class="form-control js-datetimepicker" type="text" id="expodate" name="expodate" value=""
						data-side-by-side="true" data-format="YYYY-MM-DD HH:mm:ss" />
					</div>
					<div style="height: 50px;"></div>
					<div style="margin-left:400px;">
							<button type="submit" class="btn btn-primary m-r-5" id="submitBtn">确 定</button>
							<button type="button" class="btn btn-default" onclick="javascript:window.location.href='ExpoServlet?method=expoList'">返回</button>
					</div>
					<div style="height: 60px;"></div>
				</form>
			</div>
		</div>
	</div>

		<!--End 页面主要内容-->
		<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/perfect-scrollbar.min.js"></script>
		<!--时间选择插件-->
		<script src="js/bootstrap-datetimepicker/moment.min.js"></script>
		<script src="js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
		<script src="js/bootstrap-datetimepicker/locale/zh-cn.js"></script>
		<!--日期选择插件-->
		<script src="js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
		<script src="js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
		<script type="text/javascript" src="js/main.min.js"></script>
	
	
	<script type="text/javascript">
		$(function(){
		$("#goodsid").blur(function() {
			var r = /^[0-9]\d*$/;
			var goodsid = $("#goodsid").val();			
			if (goodsid == null || goodsid == "") {
				$("#goodsidMsg").html("不能为空且输入必须为数字!!!");
				return;
				}
			if(!r.test(goodsid)){
				$("#goodsidMsg").html("货物编号必须为数字!!!");
				return;
			}	
			checkGoodsid(goodsid);
		  })
		})			
		function checkGoodsid(goodsid){
			$.ajax({
				url: "CheckServlet?method=checkGoodsid", //url
			    type : "post",
			    dataType: "text",
				data : {"goodsid": goodsid},
				success : function(s) {
				//已经存在提示用户
				    if(s == "1"){
					$("#goodsidMsg").html("该货物编号已登记，可出库");
					}else{
					$("#goodsidMsg").html("货物编号不存在!!!");
					}
				  }
				})
			  }	
	</script>
	
	<script type="text/javascript">
	$(function(){
	$("#exponum").blur(function() {
		var r = /^[0-9]\d*$/;
		var exponum = $("#exponum").val();
		var goodsid = $("#goodsid").val();
		if (exponum == null || exponum == "") {
			$("#exponumMsg").html("不能为空且输入必须为数字!!!");
			return;
			}
		if(!r.test(exponum)){
			$("#exponumMsg").html("出库货物数量必须为数字!!!");
			return;
		}
		checkExpo(exponum,goodsid);
	  })
	})
	function checkExpo(exponum,goodsid){
		$.ajax({
			url: "CheckServlet?method=checkExpo", //url
		    type : "post",
		    dataType: "text",
			data : {"exponum": exponum,"goodsid": goodsid},
			success : function(s) {
			//提示数量
			    if(s == "1"){
				$("#exponumMsg").html("出库该数量后货物数量小于0!!!");
				}else{
				$("#exponumMsg").html("");
				}
			  }
			})
		  }	
</script>
	
		<script type="text/javascript">
			$(function() {
				//匿名函数   没有名字的方法
				$("#submitBtn").click(function() {
					//验证表单项是否为空
					var r = /^[0-9]\d*$/;
					var d= /^(\-)?\d+(\.\d{0,2})?$/;
					var goodsid = $("#goodsid").val();
					if (goodsid == null || goodsid == "") {
						alert("货物编号不能为空");
						//鼠标聚焦到指定的输入框中
						$("#goodsid").focus();
						return false;
					}
					if(!r.test(goodsid)){
						alert("货物编号必须为数字");	
						$("#goodsid").focus();
						return false;
					}
					var exponame = $("#exponame").val();
					if (exponame == null || exponame == "") {
						alert("出库货物介绍不能为空");
						$("#exponame").focus();
						return false;
					}
					var exponum = $("#exponum").val();
					if (exponum == null || exponum == "") {
						alert("出库货物数量不能为空");
						$("#exponum").focus();
						return false;
					}
					if(!r.test(exponum)){
						alert("出库货物数量必须为数字");	
						$("#exponum").focus();
						return false;
					}
					var expoprice = $("#expoprice").val();
					if (expoprice == null || expoprice == "") {
						alert("出库货物单价不能为空");
						$("#expoprice").focus();
						return false;
					}
					if(!d.test(expoprice)){
						alert("出库货物单价必须为数字");	
						$("#expoprice").focus();
						return false;
					}
					var expodate = $("#expodate").val();
					if (expodate == null || expodate == "") {
						alert("出库货物时间不能为空");
						$("#goodsid").focus();
						return false;
					}
					//提交表单
					$("#myform").submit();
				});
			})
		</script>
	</body>
</html>
