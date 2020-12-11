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
				<h4>更新入库货物信息</h4>
			</div>
			<div class="lyear-layout-sidebar-scroll">
			<div class="card-body">
				<div style="height: 60px;"></div>
				<form class="form-horizontal" action="ImpoServlet?method=ImpoUpdate" method="post" id="myform">
					<input type="hidden" class="form-control" name="id" id="id" value="${impo.id}" />
					<input type="hidden" class="form-control" name="oldgoodsid" id="oldgoodsid" value="${impo.goodsid}" />
					<label class="col-md-3 control-label">入库货物编号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="goodsid" id="goodsid" value="${impo.goodsid}" />
					</div>
					<div style="height: 50px;"><a id="goodsidMsg"></a></div>
					<label class="col-md-3 control-label">入库货物介绍</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="imponame" id="imponame" value="${impo.imponame}" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">入库货物数量</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="imponum" id="imponum" value="${impo.imponum}" />
					</div>
					<div style="height: 50px;"><a id="imponumMsg"></a></div>
					<label class="col-md-3 control-label">入库货物单价</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="impoprice" id="impoprice" value="${impo.impoprice}" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">入库货物登记时间</label>
					<div class="col-xs-4">
						<input class="form-control js-datetimepicker" type="text" id="impodate" name="impodate" value="${impo.impodate}" data-side-by-side="true"
						data-format="YYYY-MM-DD HH:mm:ss" />
					</div>
					<div style="height: 50px;"></div>
					<div style="margin-left:400px;">
						<button type="submit" class="btn btn-primary m-r-5" id="submitBtn">确 定</button>
						<button type="button" class="btn btn-default" onclick="javascript:window.location.href='ImpoServlet?method=impoList'">返回</button>
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
					$("#goodsidMsg").html("货物编号已登记");
					}else{
					$("#goodsidMsg").html("货物编号未登记");
					}
				  }
				})
			  }	
	</script>
	
	<script type="text/javascript">
	$(function(){
	$("#imponum").blur(function() {
		var r = /^[0-9]\d*$/;
		var imponum = $("#imponum").val();
		var goodsid = $("#goodsid").val();
		var id = $("#id").val();
		if (imponum == null || imponum == "") {
			$("#imponumMsg").html("不能为空且输入必须为数字!!!");
			return;
			}
		if(!r.test(imponum)){
			$("#imponumMsg").html("出库货物数量必须为数字!!!");
			return;
		}
		checkImpoUp(id,imponum,goodsid);
	  })
	})
	function checkImpoUp(id,imponum,goodsid){
		$.ajax({
			url: "CheckServlet?method=checkImpoUp", //url
		    type : "post",
		    dataType: "text",
			data : {"id": id,"imponum": imponum,"goodsid": goodsid},
			success : function(s) {
			//提示数量
			    if(s == "1"){
				$("#imponumMsg").html("入库该货物数量后大于库存容量!!!");
				}else if(s=="0"){
				$("#imponumMsg").html("修改后库存数量小于0!!!");
				}else{
				$("#imponumMsg").html("");	
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
					var imponame = $("#imponame").val();
					if (imponame == null || imponame == "") {
						alert("入库货物介绍不能为空");
						$("#imponame").focus();
						return false;
					}
					var imponum = $("#imponum").val();
					if (imponum == null || imponum == "") {
						alert("入库货物数量不能为空");
						$("#imponum").focus();
						return false;
					}
					if(!r.test(imponum)){
						alert("入库货物数量必须为数字");	
						$("#goodsid").focus();
						return false;
					}
					var impoprice = $("#impoprice").val();
					if (impoprice == null || impoprice == "") {
						alert("入库货物单价不能为空");
						$("#impoprice").focus();
						return false;
					}
					if(!d.test(impoprice)){
						alert("入库货物单价必须为数字");	
						$("#impoprice").focus();
						return false;
					}
					var impodate = $("#impodate").val();
					if (impodate == null || impodate == "") {
						alert("入库货物时间不能为空");
						$("#impodate").focus();
						return false;
					}
					//提交表单
					$("#myform").submit();
				});
			})
		</script>
	</body>
</html>
