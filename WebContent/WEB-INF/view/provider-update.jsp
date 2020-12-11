<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="utf-8">
		<title>仓库管理系统</title>

		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/materialdesignicons.min.css" rel="stylesheet">
		<link href="css/style.min.css" rel="stylesheet">
	</head>
	<body>
		<div class="card">
			<div class="card-header">
				<h4>更新供货商</h4>
			</div>
			<div class="lyear-layout-sidebar-scroll">
			<div class="card-body">
				<div style="height: 30px;"></div>
				<form class="form-horizontal" action="ProviderServlet?method=providerUpdate" method="post" id="myform">
					<input type="hidden" class="form-control" name="id" id="id" value="${provider.id}" />
					<input type="hidden" class="form-control" name="oldproid" id="oldproid" value="${provider.proid}" />
					<label class="col-md-3 control-label">供货商编号</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="proid" id="proid" value="${provider.proid}" />
					</div>
					<div style="height: 50px;"><a id="proidMsg"></a></div>
					<label class="col-md-3 control-label">供货商名称</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="proname" id="proname" value="${provider.proname}" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">供货商邮箱</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="proemail" id="proemail" value="${provider.proemail}" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">供货商电话</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="prophone" id="prophone" value="${provider.prophone}" />
					</div>
					<div style="height: 50px;"></div>
					<label class="col-md-3 control-label">供货商介绍</label>
					<div class="col-xs-4">
						<input type="text" class="form-control" name="prodesc" id="prodesc" value="${provider.prodesc}" />
					</div>
					<div style="height: 50px;"></div>
					<div style="margin-left:400px;">
						<button type="submit" class="btn btn-primary m-r-5" id="submitBtn">确 定</button>
						<button type="button" class="btn btn-default" onclick="javascript:window.location.href='ProviderServlet?method=providerList'">返回</button>
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
	<script type="text/javascript">
	$(function(){
	$("#proid").blur(function() {
		var r = /^[0-9]\d*$/;
		var proid = $("#proid").val();
		if (proid == null || proid == "") {
			$("#proidMsg").html("供货商编号不能为空");
			return false;
		}
		if(!r.test(proid)){
			$("#proidMsg").html("供货商编号必须为数字");
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
				$("#proidMsg").html("供货商编号未登记");
				}else{
				$("#proidMsg").html("供货商编号已登记");
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
					//验证表单项是否为空
					var proid = $("#proid").val();
					if (proid == null || proid == "") {
						alert("供货商编号不能为空");
						//鼠标聚焦到指定的输入框中
						$("#proid").focus();
						return false;
					}
					if(!r.test(proid)){
						alert("供货商编号必须为数字");	
						return false;
					}	
					var proname = $("#proname").val();
					if (proname == null || proname == "") {
						alert("供货商名称不能为空");
						//鼠标聚焦到指定的输入框中
						$("#proname").focus();
						return false;
					}
					var proemail = $("#proemail").val();
					var y = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
					if (proemail == null || proemail == "") {
						alert("供货商邮箱不能为空");
						//鼠标聚焦到指定的输入框中
						$("#proemail").focus();
						return false;
					}
					if(!y.test(proemail)){
						alert("邮箱格式错误，请检查");	
						return false;
					}
					var prophone = $("#prophone").val();
					if (prophone == null || prophone == "") {
						alert("供货商电话不能为空");
						//鼠标聚焦到指定的输入框中
						$("#prophone").focus();
						return false;
					}
					if(!r.test(prophone)){
						alert("电话号码必须为数字");	
						return false;
					}	
					//提交表单
					$("#myform").submit();
				})
			})
		</script>
		<!--End 页面主要内容-->
	</body>


</html>
