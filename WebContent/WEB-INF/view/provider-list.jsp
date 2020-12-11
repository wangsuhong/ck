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
		<title>供应商列表</title>
	</head>
	<body>
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<h4>供货商列表</h4>
					</div>
					<div class="card-toolbar clearfix">
						<form class="pull-right search-bar" method="post" action="#!" role="form">
							<div class="input-group">
								<input type="text" class="form-control" value="" name="proid" id="proid" placeholder="请输入供货商编号">
								<div class="input-group-btn">
									<a class="btn btn-primary m-r-5" onclick="searchProvider()"><i>查询</i></a>
								</div>
							</div>
						</form>
						<a class="btn btn-primary m-r-5" onclick="addProvider()"><i>增加</i></a>
					</div>
					<div class="card-body">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>序号</th>
										<th>供货商编号</th>
										<th>供货商名称</th>
										<th>供货商邮箱</th>
										<th>供货商电话</th>
										<th>供货商说明</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.data}" var="provider" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${provider.proid}</td>
											<td>${provider.proname}</td>
											<td>${provider.proemail}</td>
											<td>${provider.prophone}</td>
											<td>${provider.prodesc}</td>
											<td>
												<div class="btn-group">
													<a class="btn btn-xs btn-default" onclick="addUpdate(${provider.proid})" title="编辑" data-toggle="tooltip"><i
														 class="mdi mdi-pencil"></i></a>
													<a class="btn btn-xs btn-default" onclick="del(${provider.id})" title="删除" data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 生成分页工具栏 -->
							<p:page action="ProviderServlet?method=providerList" />
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
						url: "ProviderServlet?method=delProvider", //url
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

			function addProvider() {
				//跳转增加供货商页面
				window.location.href = "ProviderServlet?method=toProviderAdd";
			}

			function addUpdate(proid) {
				//跳转供货商更新页面
				window.location.href = "ProviderServlet?method=toProviderUpdate&proid=" + proid;
			}

			function searchProvider(proid) {
				var r = /^[0-9]\d*$/;
				//跳转查找供货商页面
				var proid = $("#proid").val();
				if(proid==null || proid==""){
					alert("供货商编号不能为空");
					return;
				}else if(!r.test(proid)){
					alert("供货商编号必须为数字!!!");
					return;
				}else{
				window.location.href = "ProviderServlet?method=serachProvider&proid=" + proid;
				}
			}
		</script>
	</body>
</html>
