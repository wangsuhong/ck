package warehouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.ExpoDao;
import dao.GoodDao;
import dao.ImpoDao;
import dao.ProviderDao;
import service.ExpoService;
import service.GoodService;
import service.ImpoService;
import service.ProviderService;

/**
 * Servlet implementation class CheckServlet
 */
@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GoodService goods=new GoodService();
	ProviderService providerService=new ProviderService();//查询供货商	
	ImpoService im=new ImpoService();//用于查询入库数量
	ExpoService ex=new ExpoService();//用于查询出库数量
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckServlet() {
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
		if(method.equals("checkGoodsid")) { //检查货物编号
			try {
				checkGoodsid(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("checkGoodsnum")) {//检查货物数量
			try {
				checkGoodsnum(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("checkProid")) { //检查供货商
			try {
				checkProid(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("checkImpo")) {//检查入库数量
			try {
				checkImpo(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("checkImpoUp")) {//检查更新入库数量
			try {
				checkImpoUp(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("checkExpo")) {//检查出库数量
			   try {
				   checkExpo(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("checkExpoUp")) {//检查更新出库数量
			try {
				checkExpoUp(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			response.getWriter().write("requestError");
		}
	}
	
	//检查货物编号
	public void checkGoodsid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获得要校验的货物编号
		String goodsid = request.getParameter("goodsid");
		System.out.println(goodsid);
		//传递到service层
		int s;
        s = goods.checkGoodsid(goodsid);
        System.out.println(s);
        if(s>0) {
        	response.getWriter().write("1");
        }else {
        	response.getWriter().write("0");
		}
		
	}
	
	//检查货物数量
	public void checkGoodsnum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获得要校验的数量
		String goodsnum = request.getParameter("goodsnum");
		System.out.println(goodsnum);
		int s=(Integer.parseInt(goodsnum));
        System.out.println(s);
        if(s>10000){
        	response.getWriter().write("1");
        }else {
        	response.getWriter().write("0");
		}
		
	}
	
	//检查供货商
	public void checkProid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获得要校验的
		String proid = request.getParameter("proid");
		int s = providerService.searchProviderInfosCount(proid);
		System.out.println(s);	
        if(s<=0){
        	response.getWriter().write("1");
        }else {
        	response.getWriter().write("0");
		}
		
	}
	
	//检查入库数量
	public void checkImpo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获得要校验的数量
		String imponum = request.getParameter("imponum");
		String goodsid = request.getParameter("goodsid");
		 //检查是否超出仓库数量
		 int n=goods.GoodsNum(goodsid);//原来的货物数量
		 int e=(Integer.parseInt(imponum));//增加的货物数量
		 if(n+e>10000){
        	response.getWriter().write("1");
        }else {
        	response.getWriter().write("0");
		}
		
	}
	
	//检查更新入库数量
	public void checkImpoUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获得要校验的数量
		String id = request.getParameter("id");
		String imponum = request.getParameter("imponum");
		String goodsid = request.getParameter("goodsid");
		 //检查是否超出仓库数量
		int n=im.getnum(id);//原来的入库数量
		int e=(Integer.parseInt(imponum));//修改的入库数据
		  if(n<e){
			 e = e-n;	
			 //修改的数量是否会超出仓库数量
			   int nn=goods.GoodsNum(goodsid);//原来的货物数量
			   if(nn+e>10000){					   
				   response.getWriter().write("1");
			   }	
		}else if(n>e){
			e= n-e;	
			//修改的数量是否会超出原来的数量 
			   int nn=goods.GoodsNum(goodsid);//原来的货物数量
			   if(nn<e){
				   response.getWriter().write("0");
			   }				   
			 }		
	  }
	
	//检查出库数量
	public void checkExpo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获得要校验的数量
		String exponum = request.getParameter("exponum");
		String goodsid = request.getParameter("goodsid");
		 //检查是否超出仓库数量
		 int n=goods.GoodsNum(goodsid);//原来的货物数量
		 int e=(Integer.parseInt(exponum));//出库货物数量
		 if(n<e){
        	response.getWriter().write("1");
        }else {
        	response.getWriter().write("0");
		}		
	}

	//检查更新出库数量
	public void checkExpoUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获得要校验的数量
		String id = request.getParameter("id");
		String exponum = request.getParameter("exponum");
		String goodsid = request.getParameter("goodsid");
		 //检查是否超出仓库数量
		int n=ex.getnum(id);//原来的出库数量
		int e=(Integer.parseInt(exponum));//修改的入库数据
		  if(n<e){
			 e = e-n;	
			 //修改的数量是否会超出仓库数量
			   int nn=goods.GoodsNum(goodsid);//原来的货物数量
			   if(nn<e){					   
				   response.getWriter().write("1");
			   }	
		}else if(n>e){
			e= n-e;	
			//修改的数量是否会超出原来的数量 
			   int nn=goods.GoodsNum(goodsid);//原来的货物数量
			   if(nn+e>10000){
				   response.getWriter().write("0");
			   }				   
			 }		
		  
	  }

}
