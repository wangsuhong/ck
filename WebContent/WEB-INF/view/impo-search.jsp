<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/page-tags" prefix="p" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/materialdesignicons.min.css" rel="stylesheet">
		<link href="css/style.min.css" rel="stylesheet">
		<title>入库列表查询</title>
	</head>
	<body>
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<h4>入库查询列表</h4>
					</div>
					<div class="card-toolbar clearfix">
						<form class="pull-right search-bar" method="post" action="#!" role="form">
							<div class="input-group">
								<input type="text" class="form-control" value="" name="goodsid" id="goodsid" placeholder="请输入货物编号">
								<div class="input-group-btn">
									<a class="btn btn-primary m-r-5" onclick="searchImpo()"><i>查询</i></a>
								</div>
							</div>
						</form>
						<a class="btn btn-primary m-r-5" onclick="addImpo()"><i>增加</i></a>
					</div>
					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>入库序号</th>
										<th>入库货物编号</th>
										<th>货物货物介绍</th>
										<th>入库数量</th>
										<th>入库价格</th>
										<th>入库时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.data}" var="impo" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${impo.goodsid}</td>
											<td>${impo.imponame}</td>
											<td>${impo.imponum}</td>
											<td>${impo.impoprice}</td>
											<td><fmt:formatDate value="${impo.impodate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>
												<div class="btn-group">
													<a class="btn btn-xs btn-default" onclick="addUpdate(${impo.id})" title="编辑" data-toggle="tooltip"><i
														 class="mdi mdi-pencil"></i></a>
													<a class="btn btn-xs btn-default" onclick="del(${impo.id})" title="删除" data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 生成分页工具栏 -->
							<p:page action="ImpoServlet?method=searchImpo" />
						</div>
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
						url: "ImpoServlet?method=delImpo", //url
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

			function addImpo() {
				//跳转入库页面
				window.location.href = "ImpoServlet?method=toImpoAdd";
			}

			function addUpdate(id) {
				//跳转入库修改页面
				window.location.href = "ImpoServlet?method=toImpoUpdate&id=" + id;
			}

			function searchImpo() {
				//跳转查找入库页面
				var r = /^[0-9]\d*$/;
				var goodsid = $("#goodsid").val();
				if (goodsid == null || goodsid == "") {
					alert("货物编号不能为空");
					return;
					}else if(!r.test(goodsid)){
					alert("货物编号必须为数字");
					return;
				}else{
				window.location.href = "ImpoServlet?method=searchImpo&goodsid=" + goodsid;
				}
			}
		</script>
	</body>
</html>
