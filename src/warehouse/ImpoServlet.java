package warehouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Goods;
import bean.Impo;
import dao.GoodDao;
import dao.ImpoDao;
import service.GoodService;
import service.ImpoService;
import util.Page;

/**
 * Servlet implementation class GoodsServlet
 */
@WebServlet("/ImpoServlet")
public class ImpoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;     
	ImpoService  service = new ImpoService();
	GoodService goods=new GoodService();//用于数量
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImpoServlet() {
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
		if(method.equals("impoList")) { //跳转入库列表页面
			impoList(request,response);
		}else if(method.equals("toImpoAdd")) { //跳转增加入库货物页面
			toImpoAdd(request,response);
		}else if(method.equals("ImpoAdd")) { //增加入库货物信息
				try {
					ImpoAdd(request,response);
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else if(method.equals("searchImpo")) { //查找入库货物信息
			searchImpo(request,response);
		}else if(method.equals("toImpoUpdate")) { //跳转修改入库货物页面
			toImpoUpdate(request,response);
		}else if(method.equals("ImpoUpdate")) { //更新入库货物			
				try {
					ImpoUpdate(request,response);
				} catch (ServletException | IOException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}else if(method.equals("delImpo")) { //删除入库
			delImpo(request,response);
		}else {
			response.getWriter().write("requestError");
		}
	}

	/*
	 * 列表查询
	 */
	public void impoList(HttpServletRequest request, HttpServletResponse response)
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
		// 去数据库查询类型管理表，获取数据 返回是list
		page = ImpoService.getImpoPage(page, curPage);
		// 将数据返回给页面
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/view/impo-list.jsp").forward(request, response);
	}
	
	//跳转增加入库货物信息页面
	public void toImpoAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/view/impo-add.jsp").forward(request, response);
	}
	
	//增加入库信息
	public void ImpoAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException{ 
	  //获取到参数 String
	  String goodsid=request.getParameter("goodsid");
	  String imponame=request.getParameter("imponame");
	  String imponum=request.getParameter("imponum"); 
	  String impoprice=request.getParameter("impoprice"); 
	  String impodate=request.getParameter("impodate"); 

	  
	  System.out.println("goodsid"+goodsid);
	  System.out.println("imponame"+imponame);
	  System.out.println("imponum"+imponum);
	  System.out.println("impoprice"+impoprice);
	  System.out.println("impodate"+impodate);
	  
	  //将数据封装成impo对象 
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date data=sdf.parse(impodate);
	   Impo impo=new Impo();
	   impo.setGoodsid(Integer.parseInt(goodsid));
	   impo.setImponame(imponame);
	   impo.setImponum(Integer.parseInt(imponum));
	   impo.setImpoprice(Double.parseDouble(impoprice));
       impo.setImpodate(data);	  
	   
	   //检查是否有相同的货物，如果没有就返回提示	   
	   int searchGood = goods.checkGoodsid(goodsid);
	   System.out.println(searchGood);
	   if(searchGood <= 0) {
		   PrintWriter out = response.getWriter();
	       out.print("<script>alert('没有该货物，请先添加货物信息');window.location.href='ImpoServlet?method=impoList'</script>"); 
		   return;
	   }
	   
	   //检查是否超出仓库数量
	   int n=goods.GoodsNum(goodsid);//原来的货物数量//原来的货物数量
	   int e=(Integer.parseInt(imponum));//增加的货物数量
	   if(n+e>10000){
		   PrintWriter out = response.getWriter();
	       out.print("<script>alert('入库该货物后数量大于10000，请先检查仓库容量是否足够');window.location.href='ImpoServlet?method=impoList'</script>"); 
		   return;		   
	   }
	   
	  //将数据插入到数据库中 
	  int  num=goods.impoNum(imponum, goodsid);
	  int result=ImpoService.insertImpo(impo); 
  
	  if(result>0){ 
	  //重定向在此servlet方法中调用另外一个方法 
	  response.sendRedirect("ImpoServlet?method=impoList"); 
	  }else{
		  PrintWriter out = response.getWriter();
	      out.print("<script>alert('增加失败，请先确认货物信息是否正确');window.location.href='ImpoServlet?method=impoList'</script>"); 
		  return;
	    } 
	  }
	  
	  
	 //查找id显示在入库货物修改更新页面并跳转
	public void toImpoUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("id");
		System.out.println(id);
		Impo impo= ImpoService.queryImpoById(id);
		request.setAttribute("impo",impo);
		request.getRequestDispatcher("/WEB-INF/view/impo-update.jsp").forward(request, response);
	}
	
	//修改入库货物数据
	public void ImpoUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException{
		//获取到参数
		String id = request.getParameter("id");
		String oldgoodsid=request.getParameter("oldgoodsid");
		String goodsid=request.getParameter("goodsid");
	    String imponame=request.getParameter("imponame");
		String imponum=request.getParameter("imponum"); 
		String impoprice=request.getParameter("impoprice"); 
		String impodate=request.getParameter("impodate"); 
		  
		System.out.println("oldgoodsid"+oldgoodsid);
		System.out.println("goodsid"+goodsid);
	    System.out.println("imponame"+imponame);
		System.out.println("imponum"+imponum);
		System.out.println("impoprice"+impoprice);
		System.out.println("impodate"+impodate);
		  
		  //将数据封装成impo对象 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date data=sdf.parse(impodate);
		Impo impo=new Impo();
		impo.setId(Integer.parseInt(id));
		impo.setGoodsid(Integer.parseInt(goodsid));
		impo.setImponame(imponame);
		impo.setImponum(Integer.parseInt(imponum));
		impo.setImpoprice(Double.parseDouble(impoprice));
	    impo.setImpodate(data);
			    	    
		   //检查是否有货物，如果没有就返回提示	   
		   int searchGood = goods.checkGoodsid(goodsid);
		   System.out.println(searchGood);
		   if(searchGood <= 0) {
			   PrintWriter out = response.getWriter();
		       out.print("<script>alert('没有该货物，请先确认修改的货物信息');window.location.href='ImpoServlet?method=impoList'</script>"); 
			   return;
		   }
		 
		   //检查老货物编号与新货物编号，是否需要修改老货物编号数量
		    if(oldgoodsid.equals(goodsid)) {
		    	goodsid=goodsid;
		    }else {
		    	String old=oldgoodsid;//需要减少数量的老货物编号
		    	int oldnum=service.getnum(id);//老编号的入库数量
		    	int nn=goods.GoodsNum(old);//原来的老货物数量
		    	String newgoodsid=goodsid;//需要增加数量的新货物编号
		    	int ss=goods.GoodsNum(newgoodsid);//新货物的原本数量
		    	int e=(Integer.parseInt(imponum));//新货物增加数量
		    	System.out.println("e"+e);
		    	System.out.println("oldnum:" +oldnum);
		    	System.out.println("old:" +old);
		    	System.out.println("newgoodsid:" +newgoodsid);
		    		if(nn-oldnum<0) {
		    			PrintWriter out = response.getWriter();
		    			out.print("<script>alert('修改的原货物数量小于0，请先确认修改的货物信息');window.location.href='ImpoServlet?method=impoList'</script>"); 
		    			return;
		    		}
		    		if(nn-oldnum>0){
		    			int cc=goods.po1(oldnum,old);//需要减少的老货物
		    		}
		    		if(ss+e>10000) {
		 	    	   PrintWriter out = response.getWriter();
		 			   out.print("<script>alert('修改的货物数量大于库存，请先确认修改的货物信息');window.location.href='ImpoServlet?method=impoList'</script>"); 
		 			   return;
		 	    	}
		 	    	if(ss+e<10000){
		 	    		int cc=goods.po2(e,newgoodsid);//需要增加的新货物
		 	    	}	 	
		    }
		   
		    //货物编号未变执行以下代码
		   if(oldgoodsid.equals(goodsid)) {	
			//修改的与原来的变动,得到要修改的货物数量
			System.out.print("aaaa");//检查是否执行此方法
			int n=service.getnum(id);//原来的入库数量			
			int e=(Integer.parseInt(imponum));//修改的入库数据
			  if(n<e){
				 e = e-n;	
				 //修改的数量是否会超出仓库数量
				   int nn=goods.GoodsNum(goodsid);//原来的货物数量
				   if(nn+e>10000){					   
					   PrintWriter out = response.getWriter();
				       out.print("<script>alert('修改后的货物数量大于10000，请先确认修改信息是否正确');window.location.href='ImpoServlet?method=impoList'</script>"); 
					   return;		   
				   }	
				   int  num=goods.po2(e,goodsid); //货物数量增加
				  }else if(n>e){
				    e= n-e;	
				  //修改的数量是否会超出原来的数量 
				   int nn=goods.GoodsNum(goodsid);//原来的货物数量
				   if(nn<e){
					   PrintWriter out = response.getWriter();
				       out.print("<script>alert('修改后的货物数量小于0，请先确认修改信息是否正确');window.location.href='ImpoServlet?method=impoList'</script>"); 
					   return;		   
				   }				   
				   int  num=goods.po1(e,goodsid);  //货物数量减少
				  }
		   }

		
		int result=0;
		result = ImpoService.saveImpoById(impo);
		if(result>0){
			//重定向   在此servlet方法中调用另外一个方法
			response.sendRedirect("ImpoServlet?method=impoList");
		}else{
			PrintWriter out = response.getWriter();
		    out.print("<script>alert('修改失败，请先确认修改信息是否正确');window.location.href='ImpoServlet?method=impoList'</script>"); 
			return;
			//response.getWriter().write("修改失败，返回列表");
			//response.setHeader("refresh","3;url=ImpoServlet?method=impoList");
		}
}
	

	//删除入库货物信息
	public void delImpo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取页面的id参数
		String id=	request.getParameter("id");
		int ss =ImpoService.delImpo(id);
		if(ss>0){
			response.getWriter().write("ok");
		}else {
			response.getWriter().write("no");
		}
	}
	
	//查找入库货物信息
	public void searchImpo(HttpServletRequest request, HttpServletResponse response)
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
			page = new Page(20);// 参数为每页显示数量
		}
		// 去数据库查询入库表，获取数据 返回是list
		page = ImpoService.searchImpoPage(page,curPage,goodsid);
		// 将数据返回给页面
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/view/impo-search.jsp").forward(request, response);
	}

}
