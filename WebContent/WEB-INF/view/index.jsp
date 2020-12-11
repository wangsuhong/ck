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
		<div class="lyear-layout-web">
			<div class="lyear-layout-container">
				<!--左侧导航-->
				<aside class="lyear-layout-sidebar">
					<!-- logo -->
					<div id="logo" class="sidebar-header">
						<a><img src="image/logo-sidebar.png" title="欢迎" /></a>
					</div>
					<div class="lyear-layout-sidebar-scroll">
						<nav class="sidebar-main">
							<ul class="nav nav-drawer">
								<li class="nav-item"> <a href="javascript:changeMenu('GoodsServlet?method=goodsList')" onclick="change2()"><i
										 class="mdi mdi-home"></i> 后台首页</a> </li>
								<li class="nav-item nav-item-has-subnav">
									<a href="javascript:void(0)"><i class="mdi mdi-account-card-details"></i>管理人员信息</a>
									<ul class="nav nav-subnav">
										<li id="user"> <a href="javascript:changeMenu('UserServlet?method=userList')" onclick="change1()">管理员信息表</a>
										</li>
									</ul>
								<li class="nav-item nav-item-has-subnav">
									<a href="javascript:void(0)"><i class="mdi mdi-file-outline"></i>仓库信息管理</a>
									<ul class="nav nav-subnav">
										<li id="goods"> <a href="javascript:changeMenu('GoodsServlet?method=goodsList')" onclick="change2()">仓库信息</a>
										</li>
										<li id="impo"> <a href="javascript:changeMenu('ImpoServlet?method=impoList')" onclick="change3()">入库管理</a>
										</li>
										<li id="expo"> <a href="javascript:changeMenu('ExpoServlet?method=expoList')" onclick="change4()">出库管理</a></li>
									</ul>
								</li>
								<li class="nav-item nav-item-has-subnav">
									<a href="javascript:void(0)"><i class="mdi mdi-account"></i>供货商信息管理</a>
									<ul class="nav nav-subnav">
										<li id="provider"> <a href="javascript:changeMenu('ProviderServlet?method=providerList')" onclick="change5()">供货商信息</a>
										</li>
									</ul>
								</li>
							</ul>
						</nav>
						<div class="sidebar-footer">
							<p class="copyright">@ 2020 仓库管理系统</p>
						</div>
					</div>

				</aside>
				<!--End 左侧导航-->

				<!--头部信息-->
				<header class="lyear-layout-header">
					<div class="topbar">
						<div class="topbar-left">
							<div class="lyear-aside-toggler">
								<span class="lyear-toggler-bar"></span>
								<span class="lyear-toggler-bar"></span>
								<span class="lyear-toggler-bar"></span>
							</div>
							<span class="navbar-page-title"> 欢迎来到我们的管理系统 </span>
						</div>

						<ul class="topbar-right">
							<li class="dropdown dropdown-profile">
								<a href="javascript:void(0)" data-toggle="dropdown">
									<img class="img-avatar img-avatar-48 m-r-10" src="image/hear.png" />
									<span>${userInfo.username}<span class="caret"></span></span>
								</a>
								<ul class="dropdown-menu dropdown-menu-right">
									<li> <a href="javascript:User(${userInfo.id})"><i class="mdi mdi-account"></i> 个人信息</a> </li>
									<li> <a href="javascript:changeUser(${userInfo.id})"><i class="mdi mdi-key-variant"></i> 修改密码</a></li>
									<li class="divider"></li>
									<li> <a href="outLogin"><i class="mdi mdi-logout-variant"></i> 退出登录</a> </li>
								</ul>
							</li>
						</ul>
					</div>
				</header>
				<!--End 头部信息-->

				<!--页面主要内容-->
				<main class="lyear-layout-content">
					<div class="container-fluid">
						<div class="app-content">
							<!-- 引入页面  src属性中写的是需要引入的页面的访问路径 -->
							<iframe id="main" name="ifd" src="GoodsServlet?method=goodsList" onload="this.height=this.contentWindow.document.body.scrollHeight"
							 width="100%" scrolling="no" frameborder="0"></iframe>
						</div>
					</div>
				</main>
				<!--End 页面主要内容-->
				<script type="text/javascript" src="js/jquery.min.js"></script>
				<script type="text/javascript" src="js/bootstrap.min.js"></script>
				<script type="text/javascript" src="js/perfect-scrollbar.min.js"></script>
				<script type="text/javascript" src="js/main.min.js"></script>

				<script type="text/javascript">
					function changeMenu(menuName) {
						/* 获取 iframe  dom对象        修改src属性 */
						document.getElementById("main").src = menuName;
					};
				</script>
					     
				<!--模态框 -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">用户密码修改
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
							</div>
							<div class="modal-body">
								<input type="hidden" name="userid" id="userid" value="${userInfo.id}">
								用户名：<input type="text" id="username2" name="username2" value="${userInfo.username}" readonly="readonly">
							</div>
							<div class="modal-body">
								旧密码：<input type="password" id="oldpassword" name="oldpassword">
							</div>
							<div class="modal-body">
								新密码：<input type="password" id="newpassword" name="newpassword">
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary" onclick="updatePassword()">确认修改</button>
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal -->
				</div>

				<script type="text/javascript">
					function changeUser(id) {
						//打开隐藏的模态框
						$('#myModal').modal();
						//设置模态框剧中
						$('#myModal').on('show.bs.modal', function() {
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
					function updatePassword() {
						var oldpassword = $("#oldpassword").val();
						var newpassword = $("#newpassword").val();
						if (oldpassword != '${userInfo.password}') {
							alert("原始密码输入错误，请重试")
							return;
						} else {
							if (newpassword == null || newpassword == "") {
								alert("新密码不能为空，请输入！")
								return;
							} else {
								$.ajax({
									type: "post", //方法类型
									url: "UserServlet?method=changePsd", //url
									data: {
										id: $("#userid").val(),
										newpassword: newpassword
									},
									success: function(data) {
										if (data == "no") {
											alert("修改失败，请重试或联系管理员");
										} else {
											alert("修改成功！")
											window.location.href = "outLogin";
										}
									},
									error: function() {
										alert("异常请求！");
									}
								});
							}
						}
					}
				</script>
				
				<!-- 模态框 -->
				<div class="modal fade" id="User" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">用户个人信息修改
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
							</div>
							<div class="modal-body">
								<input type="hidden" name="userid" id="userid" value="${userInfo.id}">
								用户名：<input type="text" id="username" name="username" value="${userInfo.username}" readonly="readonly">
							</div>
							<div class="modal-body">
								邮&nbsp;&nbsp;&nbsp;&nbsp;箱：<input type="text" id="email" name="email" value="${userInfo.email}">
							</div>
							<div class="modal-body">
								电&nbsp;&nbsp;&nbsp;&nbsp;话：<input type="text" id="phone" name="phone" value="${userInfo.phone}">
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary" onclick="updateUser()">确认修改
								</button>
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭
								</button>
							</div>
						</div><!-- /.modal-content -->
					</div><!-- /.modal -->
				</div>

				<script type="text/javascript">
					function User(id) {
						//打开隐藏的模态框
						$('#User').modal();
						//设置模态框剧中
						$('#User').on('show.bs.modal', function() {
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
					function updateUser() {
						var email = $("#email").val();
						var phone = $("#phone").val();
						var y = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
						var r = /^[0-9]\d*$/;
						if (email == null || email == "") {
							alert("邮箱不能为空，请重新输入")
							return;
						}
						if(!y.test(email)){
							alert("邮箱格式错误，请检查");	
							return;
						}
						if(phone == null || phone == "") {
							alert("电话不能为空，请输入")
							return;
						}
						if(!r.test(phone)){
							alert("电话输入为数字");	
							return;
							} else {
								$.ajax({
									type: "post", //方法类型
									url: "UserServlet?method=changeUser", //url
									data: {
										id: $("#userid").val(),
										email: email,
										phone: phone
									},
									success: function(data) {
										if (data == "no") {
											alert("修改失败，请重试或联系管理员");
										} else {
											alert("修改成功！");
											window.location.href = "javascript:void(0)";
											
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
					//改变标签高亮
					function change1() {
						document.getElementById('user').className = 'active';
						document.getElementById('goods').className = '';
						document.getElementById('impo').className = '';
						document.getElementById('expo').className = '';
						document.getElementById('provider').className = '';
					};
					function change2() {
						document.getElementById('user').className = '';
						document.getElementById('goods').className = 'active';
						document.getElementById('impo').className = '';
						document.getElementById('expo').className = '';
						document.getElementById('provider').className = '';
					};
					function change3() {
						document.getElementById('user').className = '';
						document.getElementById('goods').className = '';
						document.getElementById('impo').className = 'active';
						document.getElementById('expo').className = '';
						document.getElementById('provider').className = '';
					};
					function change4() {
						document.getElementById('user').className = '';
						document.getElementById('goods').className = '';
						document.getElementById('impo').className = '';
						document.getElementById('expo').className = 'active';
						document.getElementById('provider').className = '';
					};
					function change5() {
						document.getElementById('user').className = '';
						document.getElementById('goods').className = '';
						document.getElementById('impo').className = '';
						document.getElementById('expo').className = '';
						document.getElementById('provider').className = 'active';
					};
				</script>
	</body>
</html>