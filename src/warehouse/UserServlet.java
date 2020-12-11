package warehouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDao;
import service.GoodService;
import service.UserService;
import util.Page;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserService service=new UserService();
	UserDao dao=new UserDao();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
		if(method.equals("userList")) { //管理员信息列表
			userList(request,response);
		}else if(method.equals("Admin")) { //跳转主页面
			Admin(request,response);
		}else if(method.equals("changePsd")) { //修改个人密码
			changePsd(request,response);
		}else if(method.equals("changeUser")) { //修改个人信息
			try {
				changeUser(request,response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("serachUser")) { //查找管理员信息
			serachUser(request,response);
		}else {
			response.getWriter().write("requestError");
		}
	}
	
	/*
	 * 列表查询
	 */
	public void userList(HttpServletRequest request, HttpServletResponse response)
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
		page = UserService.getUserPage(page, curPage);
		// 将数据返回给页面
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/view/user-list.jsp").forward(request, response);
	}
	
	//跳转后台首页
	public void Admin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        	 request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
         }
	
	//查找管理员信息
	public void serachUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取前台发送的username
		String username = request.getParameter("username");
		System.out.println(username);
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
		page = UserService.searchUserPage(page, curPage,username);
		// 将数据返回给页面
		request.setAttribute("page",page);
		request.getRequestDispatcher("/WEB-INF/view/user-search.jsp").forward(request, response);
	}
	
	//改变密码
	public void changePsd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id =	request.getParameter("id");
		String newpassword=	request.getParameter("newpassword");
		int ss=0;
		try {
			ss = service.updatepassword(newpassword,id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(ss>0){
			response.getWriter().write("ok");
		}else {
			response.getWriter().write("no");
		}
	}
	
	//改变信息
	public void changeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String email =	request.getParameter("email");
		String phone=	request.getParameter("phone");
		String id =	request.getParameter("id");
		int ss=0;
		ss = service.updaUser(email,phone,id);
		if(ss>0){
			response.getWriter().write("ok");
		}else {
			response.getWriter().write("no");
		}
	  }
	

}	
	
