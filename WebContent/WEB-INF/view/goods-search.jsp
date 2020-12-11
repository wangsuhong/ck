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
		<title>货物列表</title>
	</head>
	<body>
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<cente>
							<h4>货物信息表</h4>
						</cente>
					</div>
					<div class="card-toolbar clearfix">
						<form class="pull-right search-bar" method="post" action="#!" role="form">
							<div class="input-group">
								<input type="text" class="form-control" value="" name="goodsid" id="goodsid" placeholder="请输入货物编号">
							     <div class="input-group-btn">
									<a class="btn btn-primary m-r-5" onclick="searchGoods()"><i>查询</i></a>
								 </div>
							</div>
						</form>
						<a class="btn btn-primary m-r-5" onclick="addGoods()"><i>增加</i></a>
					</div>
					<div class="card-body">		
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>货物序号</th>
										<th>货物编号</th>
										<th>货物名称</th>
										<th>货物数量</th>
										<th>货物单价</th>
										<th>货物类别</th>
										<th>入库时间</th>
										<th>供货商ID</th>
										<th>货物介绍</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${page.data}" var="goods" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${goods.goodsid}</td>
											<td>${goods.goodsname}</td>
											<td>${goods.goodsnum}</td>
											<td>${goods.goodsprice}</td>
											<td>${goods.typename}</td>
											<td><fmt:formatDate value="${goods.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>${goods.proid}</td>
											<td>${goods.goodsdesc}</td>
											<td>
												<div class="btn-group">
													<a class="btn btn-xs btn-default" onclick="addUpdate(${goods.goodsid})" title="编辑" data-toggle="tooltip"><i
														 class="mdi mdi-pencil"></i></a>
													<a class="btn btn-xs btn-default" onclick="del(${goods.id})" title="删除" data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 生成分页工具栏 -->
							<p:page action="GoodsServlet?method=serachGoods" />
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
						url: "GoodsServlet?method=delGoods", //url
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

			function addGoods() {
				//跳转增加货物页面
				window.location.href = "GoodsServlet?method=toGoodsAdd";
			}

			function addUpdate(goodsid) {
				//跳转货物更新页面
				window.location.href = "GoodsServlet?method=toGoodsUpdate&goodsid=" + goodsid;
			}

			function searchGoods() {
				//跳转查找货物页面
				var r = /^[0-9]\d*$/;
				var goodsid = $("#goodsid").val();	
				if(goodsid==null || goodsid==""){
					alert("货物编号不能为空");
					return;
				}else if(!r.test(goodsid)){
					alert("货物编号必须为数字!!!");
					return;
				}else{
				window.location.href = "GoodsServlet?method=serachGoods&goodsid=" + goodsid;
			 }
			}
			
		</script>
	</body>
</html>
