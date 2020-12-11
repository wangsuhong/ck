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
		<!--左侧导航-->
		<aside class="lyear-layout-sidebar">
			<!-- logo -->
			<div id="logo" class="sidebar-header">
				<a><img style="height: 600px" src="image/introduce.png" title="欢迎" /></a>
			</div>
			<div class="lyear-layout-sidebar-scroll">
				<nav class="sidebar-main">
					<ul class="nav nav-drawer">
					</ul>
				</nav>
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
					<span class="navbar-page-title"> 欢迎来到我们的仓库首页 </span>
				</div>
				<a href="login.jsp">进入后台 </a>
			</div>
		</header>
		<!--End 头部信息-->

		<!--页面主要内容-->
		<main class="lyear-layout-content">
			<div class="container-fluid">
				<div class="app-content">
					<div class="row">
						<div class="card">
							<div class="container-fluid">
								<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
									<ol class="carousel-indicators">
										<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
										<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
										<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
										<li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
									</ol>
									<div class="carousel-inner">
										<div class="item active"><img src="image/introduce4.png" alt="图片四" style="width:100%"></div>
										<div class="item"><img src="image/introduce3.png" alt="图片三" style="width:100%"></div>
										<div class="item"><img src="image/introduce2.png" alt="图片二" style="width:100%"></div>
										<div class="item"><img src="image/introduce1.png" alt="图片一" style="width:100%"></div>
									</div>
									<a class="left carousel-control" href="#carouselExampleControls" role="button" data-slide="prev"><span class="icon-left-open-big icon-prev"
										 aria-hidden="true"></span><span class="sr-only">Previous</span></a>
									<a class="right carousel-control" href="#carouselExampleControls" role="button" data-slide="next"><span class="icon-right-open-big icon-next"
										 aria-hidden="true"></span><span class="sr-only">Next</span></a>
								</div>
								<br>
								<center>
									<h2>仓库管理系统</h2>
								</center> <br>
								<center>
									<h4>随着互联网时代的逐步进入人民的生活，计算机应用日益普及的今天，大大小小的企业都已经离不开计算机。加之电子商务越来越成熟，企业也更需要对仓库电子化管理。一个好的仓库管理系统可以减轻仓库管理人员大量的负担，在电子屏幕上请清楚的了解自己的仓库集体情况。对于企业来说良好的仓库管理系统能大大提高管理效率，企业能很快找到管理方面的漏洞，及时的修改管理方法，提高工作效率以此来获得更高的经济效益。
										仓库管理系统它可以为企业提供全方面的管理，方便我们更好的管理仓库。</p>
								</center> <br>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
		<!--End 页面主要内容-->
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/perfect-scrollbar.min.js"></script>
		<script type="text/javascript" src="js/main.min.js"></script>
	</body>
</html>