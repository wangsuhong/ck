package warehouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.Goods;
import dao.GoodDao;
import dao.ProviderDao;
import service.GoodService;
import service.ProviderService;
import util.Page;

/**
 * Servlet implementation class GoodsServlet
 */
@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;      
	GoodService service = new GoodService();
	ProviderService providerService=new ProviderService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if(method.equals("goodsList")) { //跳转货物列表
			goodsList(request,response);
		}else if(method.equals("toGoodsAdd")) { //跳转增加货物页面
			toGoodsAdd(request,response);
		}else if(method.equals("GoodsAdd")) { //增加货物信息
			try {
				GoodsAdd(request,response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("serachGoods")) { //查找货物信息
			serachGoods(request,response);
		}else if(method.equals("toGoodsUpdate")) { //跳转修改货物页面
			toGoodsUpdate(request,response);
		}else if(method.equals("GoodsUpdate")) { //更新货物页面
				try {
					GoodsUpdate(request,response);
				} catch (ServletException | IOException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else if(method.equals("delGoods")) { //删除货物
			delGoods(request,response);
		}else {
			response.getWriter().write("requestError");
		}
	}
	

	/*
	 * 列表查询
	 */
	public void goodsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取当前要查的页码
		String curPageStr = request.getParameter("curPage");
		//第一次进入的时候，默认为第一页
		int curPage = 1;
		if (curPageStr != null) {
			//如果不是第一次进入     将页面传过来的页码赋值给初始的第一页
			curPage = Integer.parseInt(curPageStr);
		}
		// 获取分页对象
		Page page = (Page) request.getAttribute("page");
		//判断是否是第一次请求
		if (page == null) {
			page = new Page(5);// 参数为每页显示数量
		}
		// 去数据库查询货物表，获取数据 返回是list
		page = GoodService.getGoodPage(page, curPage);
		// 将数据返回给页面
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/view/goods-list.jsp").forward(request, response);
	}
	
	//跳转货物增加页面
	public void toGoodsAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/view/goods-add.jsp").forward(request, response);
	}
	
	//货物增加
	public void GoodsAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException{ 
	  //获取到参数 String
	    String goodsid=request.getParameter("goodsid");
	    String goodsname=request.getParameter("goodsname"); 
	    String goodsnum=request.getParameter("goodsnum"); 
	    String goodsprice=request.getParameter("goodsprice"); 
	    String typename=request.getParameter("typename");
	    String date=request.getParameter("date"); 
	    String proid=request.getParameter("proid");
	    String goodsdesc=request.getParameter("goodsdesc");   
	  
	    System.out.println("goodsid"+goodsid);
	    System.out.println("goodsname"+goodsname);
	    System.out.println("goodsnum"+goodsnum);
	    System.out.println("goodsprice"+goodsprice);
	    System.out.println("typename"+typename);
	    System.out.println("date"+date);
        System.out.println("proid"+proid);
	    System.out.println("goodsdesc"+goodsdesc);
	  
	  //将数据封装成Good对象 
	  	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date data=sdf.parse(date);
	    Goods goods=new Goods();
		goods.setGoodsid(Integer.parseInt(goodsid));
		goods.setGoodsname(goodsname);
		goods.setGoodsnum(Integer.parseInt(goodsnum));
		goods.setGoodsprice(Double.parseDouble(goodsprice));
		goods.setTypename(typename);
		goods.setDate(data);
		goods.setProid(Integer.parseInt(proid));
		goods.setGoodsdesc(goodsdesc);  	  
		
		//查询是否有相同货物编号
		   int s;
		   s = service.checkGoodsid(goodsid);
		   System.out.println(s);
		   if(s>0) {
		        PrintWriter out = response.getWriter();
				out.print("<script>alert('商品编号重复，添加失败');window.location.href='GoodsServlet?method=goodsList'</script>"); 
				return;
		      }
		   
		//查询是否有该供货商，如果没有返回提示，跳转货物列表页面
		   int same = providerService.searchProviderInfosCount(proid);
		   System.out.println(same);
		   if(same <= 0) {
			   PrintWriter out = response.getWriter();
		       out.print("<script>alert('没有该供货商，请先添加供货商信息');window.location.href='GoodsServlet?method=goodsList'</script>"); 
			   return;
		   }	
		   
		   //检查是否超出仓库数量   
		   int e=(Integer.parseInt(goodsnum));
		   if(e>10000){
			   PrintWriter out = response.getWriter();
		       out.print("<script>alert('该货物数量大于10000，请先检查仓库容量是否足够');window.location.href='GoodsServlet?method=goodsList'</script>"); 
			   return;		   
		   }
		
	  //将数据插入到数据库中 
	  int result=GoodService.insertGoods(goods); 
	  if(result>0){ 
	  //重定向在此servlet方法中调用另外一个方法 
		  response.sendRedirect("GoodsServlet?method=goodsList"); 
	  }else{
		  PrintWriter out = response.getWriter();
		  out.print("<script>alert('添加失败，返回添加页面');window.location.href='GoodsServlet?method=toGoodsAdd'</script>");
	}
  } 
	 
	 //查找货物编号跳转更新页面并将数据显示在更新页面
	public void toGoodsUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goodsid= request.getParameter("goodsid");
		System.out.println(goodsid);
		Goods goods=GoodService.queryGoodsByGoodsId(goodsid);
		request.setAttribute("goods", goods);
		request.getRequestDispatcher("/WEB-INF/view/goods-update.jsp").forward(request, response);
	}
	
	//修改货物数据
	public void GoodsUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException{
		//获取到参数
		String id = request.getParameter("id");
		String oldgoodsid = request.getParameter("oldgoodsid");
		String goodsid = request.getParameter("goodsid");
		String goodsname = request.getParameter("goodsname");
		String goodsnum =	request.getParameter("goodsnum");
		String goodsprice =	request.getParameter("goodsprice");
		String typename =	request.getParameter("typename");
		String date =	request.getParameter("date");
		String proid =	request.getParameter("proid");
		String goodsdesc =	request.getParameter("goodsdesc");
		
	    System.out.println("goodsid"+goodsid);
	    System.out.println("goodsname"+goodsname);
	    System.out.println("goodsnum"+goodsnum);
	    System.out.println("goodsprice"+goodsprice);
	    System.out.println("typename"+typename);
	    System.out.println("date"+date);
	    System.out.println("proid"+proid);
	    System.out.println("goodsdesc"+goodsdesc);
		  
		//将数据封装成goods对象
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date data=sdf.parse(date);
		Goods goods=new Goods();
		goods.setId(Integer.parseInt(id));
		goods.setGoodsid(Integer.parseInt(goodsid));
		goods.setGoodsname(goodsname);
		goods.setGoodsnum(Integer.parseInt(goodsnum));
		goods.setGoodsprice(Double.parseDouble(goodsprice));
		goods.setTypename(typename);
		goods.setDate(data);
		goods.setProid(Integer.parseInt(proid));
		goods.setGoodsdesc(goodsdesc);
		
		//老的货物编号与新的货物编号比较之后，查询是否有相同货物编号
		if(oldgoodsid.equals(goodsid)) {
			goodsid=null;
		}else {
			goodsid=goodsid;
		}
		System.out.println(goodsid);
		   int s;
		   s = service.checkGoodsid(goodsid);
		   System.out.println(s);
		   if(s>0) {
		        PrintWriter out = response.getWriter();
				out.print("<script>alert('商品编号重复，修改失败');window.location.href='GoodsServlet?method=goodsList'</script>"); 
				return;
		      }
		
		//查询是否有该供货商，如果没有返回提示，跳转货物列表页面
		   int same = providerService.searchProviderInfosCount(proid);
		   System.out.println(same);
		   if(same <= 0) {
			   PrintWriter out = response.getWriter();
		       out.print("<script>alert('没有该供货商，请先添加供货商信息');window.location.href='GoodsServlet?method=goodsList'</script>"); 
			   return;
		   }
		   		   
		   //检查是否超出仓库数量   
		   int e=(Integer.parseInt(goodsnum));
		   if(e>10000){
			   PrintWriter out = response.getWriter();
		       out.print("<script>alert('修改后该货物数量大于10000，请先检查修改信息是否正确');window.location.href='GoodsServlet?method=goodsList'</script>"); 
			   return;		   
		   }
				
		int result=0;
		result = GoodService.saveGoodsById(goods);
		if(result>0){
			//重定向   在此servlet方法中调用另外一个方法
			response.sendRedirect("GoodsServlet?method=goodsList"); 
		}else{
			PrintWriter out = response.getWriter();
			out.print("<script>alert('修改失败，返回货物列表页面');window.location.href='GoodsServlet?method=goodsList'</script>");
//			 response.getWriter().write("系统异常,新增数据失败,3秒后跳转回添加页面");
//			  response.setHeader("refresh", "3;url=GoodsServlet?method=goodsList"); 
		}
}
	
	//删除货物
	public void delGoods(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取页面的id参数
		String id=	request.getParameter("id");
		int ss = GoodService.delGoods(id);
		if(ss>0){
			response.getWriter().write("ok");
		}else {
			response.getWriter().write("no");
		}
	}
	
	//查找货物编号
	public void serachGoods(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取前台发送的goodsid
		String goodsid = request.getParameter("goodsid");
		System.out.println(goodsid);
		// 获取当前要查的页码
		String curPageStr = request.getParameter("curPage");	
		//第一次进入的时候，默认为第一页
		int curPage = 1;
		if (curPageStr != null) {
			//如果不是第一次进入     将页面传过来的页码赋值给初始的第一页
			curPage = Integer.parseInt(curPageStr);
		}
		// 获取分页对象
		Page page = (Page) request.getAttribute("page");
		//判断是否是第一次请求
		if (page == null) {
			page = new Page(5);// 参数为每页显示数量
		}
		// 去数据库查询货物表，获取数据 返回是list
		page = GoodService.searchGoodPage(page, curPage,goodsid);
		// 将数据返回给页面
		request.setAttribute("page",page);
		request.getRequestDispatcher("/WEB-INF/view/goods-search.jsp").forward(request, response);
	}

}
