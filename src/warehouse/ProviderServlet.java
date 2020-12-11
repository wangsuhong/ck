package warehouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Provider;
import dao.ProviderDao;
import service.ImpoService;
import service.ProviderService;
import util.Page;

/**
 * Servlet implementation class GoodsServlet
 */
@WebServlet("/ProviderServlet")
public class ProviderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ProviderService service = new ProviderService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProviderServlet() {
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
		if(method.equals("providerList")) { //跳转供货商
			providerList(request,response);
		}else if(method.equals("toProviderAdd")) { // 跳转增加供货商修改页面
			toProviderAdd(request,response);
		}else if(method.equals("providerAdd")) { //  增加供货商信息
			providerAdd(request,response);
		}else if(method.equals("serachProvider")) { //  增加供货商信息
			serachProvider(request,response);
		}else if(method.equals("toProviderUpdate")) { //跳转修改供货商页面
			toProviderUpdate(request, response);
		}else if(method.equals("providerUpdate")) { //修改供货商信息
			try {
				providerUpdate(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("delProvider")) { 
			delProvider(request, response);
		}else {
			response.getWriter().write("requestError");
		}
	}
	
	/*
	 * 列表查询
	 */
	public void providerList(HttpServletRequest request, HttpServletResponse response)
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
		// 去数据库查询供货商表，获取数据 返回是list
		page = ProviderService.getProviderPage(page, curPage);
		// 将数据返回给页面
		request.setAttribute("page", page);
		System.out.println(page);
		request.getRequestDispatcher("/WEB-INF/view/provider-list.jsp").forward(request, response);
	}
	
	//跳转增加供货商页面
	public void toProviderAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/view/provider-add.jsp").forward(request, response);
	}
	
	
	//增加供货商信息
	public void providerAdd(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
	  //获取到参数 String
	  String proid=request.getParameter("proid");
	  String proname=request.getParameter("proname"); 
	  String proemail=request.getParameter("proemail");
	  String prophone=request.getParameter("prophone");
	  String prodesc=request.getParameter("prodesc");   
	  
	  System.out.println("proid"+proid);
	  System.out.println("proname"+proname);
	  System.out.println("proemail"+proemail);
	  System.out.println("prophone"+prophone);
	  System.out.println("prodesc"+prodesc);
	  //将数据封装成Good对象 
	   
	    Provider provider=new Provider();
	    provider.setProid(Integer.parseInt(proid));
		provider.setProname(proname);
		provider.setProemail(proemail);
		provider.setProphone(prophone);
		provider.setProdesc(prodesc);
		
		//获得要校验的供货商编号
		int s = service.searchProviderInfosCount(proid);
		System.out.println(s);	
		if(s>0){
			PrintWriter out = response.getWriter();
			out.print("<script>alert('添加失败，供货商编号重复，返回供货商页面');window.location.href='ProviderServlet?method=providerList'</script>");
		    return;
		}
		
	  //将数据插入到数据库中 
	  int result=ProviderService.insertProvider(provider); 
	  if(result>0){ 
	  //重定向在此servlet方法中调用另外一个方法 
		  response.sendRedirect("ProviderServlet?method=providerList"); 
	  }else{
		  PrintWriter out = response.getWriter();
		  out.print("<script>alert('添加失败，返回供货商页面');window.location.href='ProviderServlet?method=providerList'</script>");
	  //response.getWriter().write("no");
	  //response.setHeader("refresh", "3;url=ProviderServlet?method=providerList"); 
	    } 
	  }
	 
	//根据供货商编号将数据显示在更新供货商更新页面
	public void toProviderUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String proid= request.getParameter("proid");
		System.out.println(proid);
		Provider provider=ProviderService.queryProviderByProId(proid);
		request.setAttribute("provider", provider);
		request.getRequestDispatcher("/WEB-INF/view/provider-update.jsp").forward(request, response);
	}
	
	//修改供货商数据
	public void providerUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获取到参数
		String id = request.getParameter("id");
		String oldproid = request.getParameter("oldproid");
		String proid = request.getParameter("proid");
		String proname = request.getParameter("proname");
		String proemail= request.getParameter("proemail");
		String prophone =request.getParameter("prophone");
		String prodesc =request.getParameter("prodesc");
		//将数据封装成provider对象
		Provider  provider=new Provider();
		provider.setId(Integer.parseInt(id));
		provider.setProid(Integer.parseInt(proid));
		provider.setProname(proname);
		provider.setProemail(proemail);
		provider.setProphone(prophone);
		provider.setProdesc(prodesc);
				
		//老的供货商编号与新的进行比较，获得要校验的供货商编号
		if(oldproid.equals(proid)) {
			proid=null;
		}else {
			proid=proid;
		}
		System.out.println(proid);
		
		int s = service.searchProviderInfosCount(proid);
		System.out.println(s);	
		if(s>0){
			PrintWriter out = response.getWriter();
			out.print("<script>alert('修改失败，供货商编号重复，返回供货商页面');window.location.href='ProviderServlet?method=providerList'</script>");
		    return;
		}
						
		int result=0;
		result = ProviderService.saveProviderById(provider);
		if(result>0){
			//重定向   在此servlet方法中调用另外一个方法
			response.sendRedirect("ProviderServlet?method=providerList");
		}else{
			 PrintWriter out = response.getWriter();
			 out.print("<script>alert('修改失败，返回供货商页面');window.location.href='ProviderServlet?method=providerList'</script>");  
//			response.getWriter().write("修改失败，返回列表");
//			response.setHeader("refresh","3;url=ProviderServlet?method=providerList");
		}
}
	
	//删除供货商
	public void delProvider(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取页面的id参数
		String id=	request.getParameter("id");
		int ss = ProviderService.delProvider(id);
		if(ss>0){
			response.getWriter().write("ok");
		}else {
			response.getWriter().write("no");
		}
	}
	
	//查找供货商
	public void serachProvider(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String proid = request.getParameter("proid");
		System.out.println(proid);
		// 获取当前要查的页码
		String curPageStr = request.getParameter("curPage");		
		//第一次进入的时候，默认为第一页
		
		int curPage = 1;
		if (proid!= null) {
			proid=proid;
		}else {
			response.getWriter().write("proid空值异常");
			return;
		}
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
		// 去数据库查询供货商表，获取数据 返回是list
		page = ProviderService.searchProviderPage(page, curPage,proid);
		// 将数据返回给页面
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/view/provider-search.jsp").forward(request, response);
	}

}
