<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>仓库管理系统登陆页面</title>
		<link href="css/style.css" rel="stylesheet" type="text/css">
		<link href="css/bootstrap.min.css" rel="stylesheet">

	</head>
	<body class="login">
		<div class="login_m">
			<div class="login_logo">仓库管理系统</div>
			<div class="login_boder">
				<div class="login_padding">
					<h2>用户名</h2>
					<label>
						<input type="text" name="username" id="username" placeholder="请输入你的账号" class="txt_input">
					</label>
					<h2>密码</h2>
					<label>
						<input type="password" name="password" id="password" placeholder="请输入你的密码" class="txt_input">
					</label>
					<div class="rem_sub">						
						<center><label>
						<input type="button" class="sub_button" onclick="toLogin()" value="登录">
						</label></center>
					</div>
				</div>
			</div>
		</div>
		<br />
		<br />
		<p align="center">@ 仓库管理 2020</p>

		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/perfect-scrollbar.min.js"></script>
		<script type="text/javascript" src="js/main.min.js"></script>

		<script type="text/javascript">
			function toLogin() {
				var username = $("#username").val();
				var password = $("#password").val();
				//html  json  text   xml  
				$.ajax({
					type: "post", //方法类型
					url: "LoginServlet", //url
					dataType: "text",
					async: true,
					data: {
						"username": username,
						"password": password
					},
					success: function(data) {
						if (data == "yes") {
							alert("登陆成功");
							window.location.href="UserServlet?method=Admin";
						} else{
							alert("登陆失败");
						}
					},
					error: function() {
						alert("异常请求！");
					}
				});
			}
		</script>

		<!--模态框 -->
		<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">请输入注册采购员所需要的密匙
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
					</div>
					<div class="modal-body">
						注册密匙：<input type="text" id="id" name="id" placeholder="请输入你的密匙" value="">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="toRegister()">确认密匙</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<script type="text/javascript">
			function register() {
				//打开隐藏的模态框
				$('#myModal3').modal();
				//设置模态框剧中
				$('#myModal3').on('show.bs.modal', function() {
					var $this = $(this);
					var $modal_dialog = $this.find('.modal-dialog');
					//设置为块级元素     inline 内联元素
					$this.css('display', 'block');
					$modal_dialog.css({
						'margin-top': Math.max(0, ($(window.parent).height() - $modal_dialog.height()) / 2) - 90
					});
				});
			}
		</script>
		<script type="text/javascript">
			function toRegister() {
				var id = $("#id").val();
				if (id == null || id == "") {
					alert("密匙不能为空，请重新输入")
					return;
				} else {
					$.ajax({
						type: "post", //方法类型
						url: "UserServlet?method=toRegister", //url
						data: {
							id: id
						},
						success: function(data) {
							if (data == "no") {
								alert("验证失败，请重试或联系管理员");
							} else {
								alert("验证成功，跳转注册页面！")
								//跳转注册页面
								window.location.href = "UserServlet?method=toUserAdd";
							}
						},
						error: function() {
							alert("异常请求！");
						}
					});
				}
			}
		</script>
	    <script type="text/javascript">

		</script>
	</body>
</html>
