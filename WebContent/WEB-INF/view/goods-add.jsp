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
				<h4>登记货物信息</h4>
			</div>
			<div class="lyear-layout-sidebar-scroll">
			<div class="card-body">
				<div style="height: 30px;"></div>
				<form class="form-horizontal" action="GoodsServlet?method=GoodsAdd" method="post" id="myform">
					<label class="col-md-3 control-label">货物编号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="goodsid" id="goodsid" value="" />
					</div>
					<div style="height: 50px;"><a id="goodsidMsg"></a></div>
					<label class="col-md-3 control-label">货物名称</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="goodsname" id="goodsname" value="" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">货物数量</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="goodsnum" id="goodsnum" value="" />
					</div>
					<div style="height: 50px;"><a id="goodsnumMsg"></a></div>
					<label class="col-md-3 control-label">货物单价</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="goodsprice" id="goodsprice" value="" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">货物类别</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="typename" id="typename" value="" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">货物登记时间</label>
					<div class="col-xs-4">
						<input class="form-control js-datetimepicker" type="text" id="date" name="date" value="" data-side-by-side="true"
						data-format="YYYY-MM-DD HH:mm:ss" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">供货商编号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="proid" id="proid" value="" />
					</div>
					<div style="height: 50px;"><a id="proidMsg"></a></div>
					<label class="col-md-3 control-label">货物介绍</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="goodsdesc" id="goodsdesc" value="" />
					</div>
					<div style="height: 50px;"></div>
					<div style="margin-left:400px;">
							<button type="submit" class="btn btn-primary m-r-5" id="submitBtn">确 定</button>
							<button type="button" class="btn btn-default" onclick="javascript:window.location.href='GoodsServlet?method=goodsList'">返回</button>
					</div>
					<div style="height: 30px;"></div>
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
				$("#goodsidMsg").html("货物编号已经存在!!!");
				}else{
				$("#goodsidMsg").html("货物编号可以使用");
				}
			  }
			})
		  }	
</script>

	<script type="text/javascript">
	$(function(){
	$("#goodsnum").blur(function() {
		var r = /^[0-9]\d*$/;
		var goodsnum = $("#goodsnum").val();
		if (goodsnum == null || goodsnum == "") {
			$("#goodsnumMsg").html("不能为空且输入必须为数字!!!");
			return;
			}
		if(!r.test(goodsnum)){
			$("#goodsnumMsg").html("货物数量必须为数字!!!");
			return;
		}
		checkGoodsnum(goodsnum);
	  })
	})
	function checkGoodsnum(goodsnum){
		$.ajax({
			url: "CheckServlet?method=checkGoodsnum", //url
		    type : "post",
		    dataType: "text",
			data : {"goodsnum": goodsnum},
			success : function(s) {
			//提示数量
			    if(s == "1"){
				$("#goodsnumMsg").html("货物数量大于库存!!!");
				}else{
				$("#goodsnumMsg").html("");
				}
			  }
			})
		  }	
</script>

	<script type="text/javascript">
	$(function(){
	$("#proid").blur(function() {
		var r = /^[0-9]\d*$/;
		var proid = $("#proid").val();
		if (proid == null || proid == "") {
			$("#proidMsg").html("供货商编号不能为空!!!");
			//鼠标聚焦到指定的输入框中
			return false;
		}
		if(!r.test(proid)){
			$("#proidMsg").html("供货商编号必须为数字!!!");
			return false;
		}	
		checkProid(proid);
	  })
	})
	function checkProid(proid){
		$.ajax({
			url: "CheckServlet?method=checkProid", //url
		    type : "post",
		    dataType: "text",
			data : {"proid": proid},
			success : function(s) {
			//提示数量
			    if(s == "1"){
				$("#proidMsg").html("没有该供货商，请先添加!!!");
				}else{
				$("#proidMsg").html("");
				}
			  }
			})
		  }	
</script>
		
		<script type="text/javascript">
			$(function() {
				//匿名函数   没有名字的方法
				$("#submitBtn").click(function() {
					var r = /^[0-9]\d*$/;
					var d= /^(\-)?\d+(\.\d{0,2})?$/;
					//验证表单项
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
					var goodsname = $("#goodsname").val();
					if (goodsname == null || goodsname == "") {
						alert("货物名称不能为空");
						//鼠标聚焦到指定的输入框中
						$("#goodsname").focus();
						return false;
					}
					var goodsnum = $("#goodsnum").val();
					if (goodsnum == null || goodsnum == "") {
						alert("货物数量不能为空");
						//鼠标聚焦到指定的输入框中
						$("#goodsnum").focus();
						return false;
					}
					if(!r.test(goodsnum)){
						alert("货物数量必须为数字");	
						$("#goodsnum").focus();
						return false;
					}
					var goodsprice = $("#goodsprice").val();
					if (goodsprice == null || goodsprice == "") {
						alert("货物单价不能为空");
						//鼠标聚焦到指定的输入框中
						$("#goodsprice").focus();
						return false;
					}
					if(!d.test(goodsprice)){
						alert("货物单价必须为数字");	
						$("#goodsprice").focus();
						return false;
					}
					var date = $("#date").val();
					if (date == null || date == "") {
						alert("时间不能为空");
						//鼠标聚焦到指定的输入框中
						$("#date").focus();
						return false;
					}
					var proid = $("#proid").val();
					if (proid == null || proid == "") {
						alert("供货商编号不能为空");
						//鼠标聚焦到指定的输入框中
						$("#proid").focus();
						return false;
					}
					if(!r.test(proid)){
						alert("供货商编号必须为数字");	
						$("#proid").focus();
						return false;
					}
					
					//提交表单
					$("#myform").submit();
				});
			})
		</script>
		
	</body>
</html>
