<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/page-tags" prefix="p" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/materialdesignicons.min.css" rel="stylesheet">
		<link href="css/style.min.css" rel="stylesheet">
		<title>管理员信息表</title>
	</head>
	<body>
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<h4>管理员列表</h4>
					</div>
					<div class="card-toolbar clearfix">
						<form class="pull-right search-bar" method="post" action="#!" role="form">
							<div class="input-group">
								<input type="text" class="form-control" value="" name="username" id="username" placeholder="请输入管理员名称">
								<div class="input-group-btn">
									<a class="btn btn-primary m-r-5" onclick="searchUser()"><i>查询</i></a>
								</div>
							</div>
						</form>
					</div>
					<div class="card-body">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>序号</th>
										<th>管理员名称</th>
										<th>管理员邮箱</th>
										<th>管理员电话</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.data}" var="user" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${user.username}</td>
											<td>${user.email}</td>
											<td>${user.phone}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 生成分页工具栏 -->
							<p:page action="UserServlet?method=userList" />
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/perfect-scrollbar.min.js"></script>
		<script type="text/javascript" src="js/main.min.js"></script>
		<script type="text/javascript">
			function del(id) {
				if (confirm("确实要删除吗?")) {
					$.ajax({
						type: "post", //方法类型
						url: "", //url
						data: {
							id: id
						},
						success: function(data) {
							if (data == "no") {
								alert("删除失败，请稍后重试或者联系管理员！");
							} else {
								alert("删除成功！")
								//刷新当前页面
								location.reload();
							}
						},
						error: function() {
							alert("异常请求！");
						}
					});
				} else {
					return;
				}
			}

			function searchUser() {
				//跳转查找用户页面
				var username = $("#username").val();
				if(username==null || username==""){
					alert("用户名称不能为空");
					return;
				}else{
				window.location.href = "UserServlet?method=serachUser&username=" + username;
				}
			}
		</script>
	</body>
</html>