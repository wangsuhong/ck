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

import bean.Expo;
import dao.ExpoDao;
import dao.GoodDao;
import service.ExpoService;
import service.GoodService;
import util.Page;


@WebServlet("/ExpoServlet")
public class ExpoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;     
	ExpoService  service = new ExpoService();
	GoodService goods=new GoodService();;//用于数量

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpoServlet() {
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
		if(method.equals("expoList")) { //跳转出库列表页面
			expoList(request,response);
		}else if(method.equals("toExpoAdd")) { //跳转增加出库货物页面
			toExpoAdd(request,response);
		}else if(method.equals("expoAdd")) { //增加出库货物信息
			try {
				expoAdd(request,response);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("searchExpo")) { //查找出库货物信息
			searchExpo(request,response);
		}else if(method.equals("toExpoUpdate")) { //跳转修改出库货物页面
			toExpoUpdate(request,response);
		}else if(method.equals("expoUpdate")) { //更新出库货物页面	 			
			try {
				try {
					expoUpdate(request,response);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}else if(method.equals("delExpo")) { //删除
			delExpo(request,response);
		}else {
			response.getWriter().write("requestError");
		}
	}
	
	/*
	 * 列表查询
	 */
	public void expoList(HttpServletRequest request, HttpServletResponse response)
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
		page = ExpoService.getExpoPage(page, curPage);
		// 将数据返回给页面
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/view/expo-list.jsp").forward(request, response);
	}
	
	//跳转增加出库货物页面
	public void toExpoAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/view/expo-add.jsp").forward(request, response);
	}
	
	//增加出库货物
	public void expoAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException{ 
	  //获取到参数 String
	  String goodsid=request.getParameter("goodsid");
	  String exponame=request.getParameter("exponame");
	  String exponum=request.getParameter("exponum"); 
	  String expoprice=request.getParameter("expoprice"); 
	  String expodate=request.getParameter("expodate"); 

	  
	  System.out.println("goodsid"+goodsid);
	  System.out.println("exponame"+exponame);
	  System.out.println("exponum"+exponum);
	  System.out.println("expoprice"+expoprice);
	  System.out.println("expodate"+expodate);
	  
	  //将数据封装成expo对象 
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date data=sdf.parse(expodate);
	   Expo expo=new Expo();
	   expo.setGoodsid(Integer.parseInt(goodsid));
	   expo.setExponame(exponame);
	   expo.setExponum(Integer.parseInt(exponum));
	   expo.setExpoprice(Double.parseDouble(expoprice));
	   expo.setExpodate(data);
	   
	   //检查是否有相同的货物，如果没有就返回提示	   
	   int searchGood = goods.checkGoodsid(goodsid);
	   System.out.println(searchGood);
	   if(searchGood <= 0) {
		   PrintWriter out = response.getWriter();
	       out.print("<script>alert('没有该货物，请先添加货物信息');window.location.href='ExpoServlet?method=expoList'</script>"); 
		   return;
	   }
	   //检查是否超出货物原本的数量
	   int n=goods.GoodsNum(goodsid);
	   int e=(Integer.parseInt(exponum));
	   if(n<e){
		   PrintWriter out = response.getWriter();
	       out.print("<script>alert('该货物数量不足，请先检查货物数量是否足够');window.location.href='ExpoServlet?method=expoList'</script>"); 
		   return;		   
	   }
	  //将数据插入到数据库中 
	  int  num=goods.expoNum(exponum,goodsid);
	  int result=ExpoService.insertExpo(expo); 
	  if(result>0){ 
	  //重定向在此servlet方法中调用另外一个方法 
	  response.sendRedirect("ExpoServlet?method=expoList"); 
	  }else{
		  PrintWriter out = response.getWriter();
		  out.print("<script>alert('修改货物失败，请先检查货物信息是否正确');window.location.href='ExpoServlet?method=expoList'</script>"); 
	      return;
	    } 
	  }
	
	//查找id显示
	public void toExpoUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id= request.getParameter("id");
		System.out.println(id);
		Expo expo= ExpoService.queryExpoById(id);
		request.setAttribute("expo",expo);
		request.getRequestDispatcher("/WEB-INF/view/expo-update.jsp").forward(request, response);
	}
	
	//修改出库货物数据
	public void expoUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException{
		//获取到参数
		String id = request.getParameter("id");
		String oldgoodsid=request.getParameter("oldgoodsid");
		String goodsid=request.getParameter("goodsid");
		String exponame=request.getParameter("exponame"); 
		String exponum=request.getParameter("exponum"); 
		String expoprice=request.getParameter("expoprice"); 
		String expodate=request.getParameter("expodate"); 

		  
		System.out.println("goodsid"+goodsid);
	    System.out.println("exponame"+exponame);
		System.out.println("exponum"+exponum);
		System.out.println("expoprice"+expoprice);
		System.out.println("expodate"+expodate);

		  
		  //将数据封装成expo对象 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date data=sdf.parse(expodate);
		 Expo expo=new Expo();
		 expo.setId(Integer.parseInt(id));
		 expo.setGoodsid(Integer.parseInt(goodsid));
		 expo.setExponame(exponame);
		 expo.setExponum(Integer.parseInt(exponum));
		 expo.setExpoprice(Double.parseDouble(expoprice));
	     expo.setExpodate(data);
		
		  //检查是否有相同的货物，如果没有就返回提示	   
		   int searchGood = goods.checkGoodsid(goodsid);
		   System.out.println(searchGood);
		   if(searchGood <= 0) {
			   PrintWriter out = response.getWriter();
		       out.print("<script>alert('没有该货物，请先确认修改的货物信息');window.location.href='ExpoServlet?method=expoList'</script>"); 
			   return;
		   }
		   
		   //检查老货物编号与新货物编号，是否需要修改老货物编号数量
		    if(oldgoodsid.equals(goodsid)) {
		    	goodsid=goodsid;
		    }else {
		    	String old=oldgoodsid;//需要增加数量的老货物编号
		    	int oldnum=service.getnum(id);//老编号的出库数量
		    	int nn=goods.GoodsNum(old);//原来的老货物数量
		    	String newgoodsid=goodsid;//需要减少数量的新货物编号
		    	int ss=goods.GoodsNum(newgoodsid);//新的减少的货物原本数量
		    	int e=(Integer.parseInt(exponum));//新货物减少数量
		    	System.out.println("e"+e);
		    	System.out.println("oldnum:" +oldnum);
		    	System.out.println("old:" +old);
		    	System.out.println("newgoodsid:" +newgoodsid);
	    		if(nn+oldnum>10000) {
	    			PrintWriter out = response.getWriter();
	    			out.print("<script>alert('修改的原货物数量大于库存，请先确认修改的货物信息');window.location.href='ExpoServlet?method=expoList'</script>"); 
	    			return;
	    		}
	    		if(nn+oldnum<10000){
	    			int cc=goods.po2(oldnum,old); //数据库老货物增加数量
	    		}
	    		if(ss-e<0) {
	 	    	   PrintWriter out = response.getWriter();
	 			   out.print("<script>alert('修改后货物数量小于0，请先确认修改的货物信息');window.location.href='ExpoServlet?method=expoList'</script>"); 
	 			   return;
	 	    	}
	 	    	if(ss-e>0){
	 	    		int cc=goods.po1(e,newgoodsid);//数据库新货物减少数量
	 	    	}	 	
		    }   
		   
		    //货物编号未变执行以下代码
		    if(oldgoodsid.equals(goodsid)) {		 
		  //修改的与原来的变动,得到要修改的货物数量
		    System.out.print("bbbb");//检查是否执行此方法
			int n=service.getnum(id);//原来的出货数量
			int e=(Integer.parseInt(exponum));//修改的出货数据
			       if(n<e){
				   e = e-n;
				 //修改的数量是否会超出原来的数量 
				   int nn=goods.GoodsNum(goodsid);//原来的货物数量
				   if(nn<e){
					   PrintWriter out = response.getWriter();
				       out.print("<script>alert('修改后的货物数量小于0，请先确认修改信息是否正确');window.location.href='ExpoServlet?method=expoList'</script>"); 
					   return;		   
				    }
				   int  num=goods.po1(e,goodsid); //货物数量的减少
			       }else if(n>e){
				   e= n-e;
				   int nn=goods.GoodsNum(goodsid);//原来的货物数量
				   if(nn+e>10000){
					   PrintWriter out = response.getWriter();
				       out.print("<script>alert('修改后的货物数量大于10000，请先确认修改信息是否正确');window.location.href='ImpoServlet?method=impoList'</script>"); 
					   return;		   
				    }{
				   int num=goods.po2(e,goodsid);  //货物数量的增加
				  }
			    }
		    }    
		    
		int result=0;
		result = service.saveExpoById(expo);
		if(result>0){
			//重定向   在此servlet方法中调用另外一个方法
			response.sendRedirect("ExpoServlet?method=expoList");
		}else{
			PrintWriter out = response.getWriter();
		    out.print("<script>alert('修改失败，请先确认修改信息是否正确');window.location.href='ExpoServlet?method=expoList'</script>"); 
			return;
//			response.getWriter().write("修改失败，返回列表");
//			response.setHeader("refresh","3;url=ExpoServlet?method=expoList");
		}
}
	
	//删除出库货物
	public void delExpo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取页面的id参数
		String id=	request.getParameter("id");
		int ss =service.delExpo(id);
		if(ss>0){
			response.getWriter().write("ok");
		}else {
			response.getWriter().write("no");
		}
	}
	
	//查找出库货物
	public void searchExpo(HttpServletRequest request, HttpServletResponse response)
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
		// 去数据库查询货物表，获取数据 返回是list
		page = ExpoService.searchExpoPage(page,curPage,goodsid);
		// 将数据返回给页面
		request.setAttribute("page", page);
		
		request.getRequestDispatcher("/WEB-INF/view/expo-search.jsp").forward(request, response);
	}

}
