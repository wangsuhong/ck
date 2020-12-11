package service;

import java.sql.SQLException;
import java.util.List;

import bean.User;
import dao.UserDao;
import util.Page;

public class UserService {
	private static  UserDao dao = new UserDao();
	
   //得到管理员信息
   public User login(String username,String password) throws SQLException{
	   return dao.getUser(username, password);   
    }	
	
	//得到管理员列表页面信息
	public static Page<User> getUserPage(Page page, int curPage) {
		//查询当前页的列表数据
		List<User> data = dao.getUserInfos(page,curPage);
		//查询总记录数
		int rowsCount = dao.getUserInfosCount();
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}
	
	//查询管理员
	public static Page<User> searchUserPage(Page page, int curPage,String username) {
		//查询当前页的列表数据
		List<User> data = dao.searchUserInfos(page,curPage,username);
		//查询总记录数
		int rowsCount = dao.searchUserInfosCount(username);
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}
	
	//更新密码
	public int updatepassword(String newpassword, String id) throws SQLException {
		return dao.updatepassword(newpassword,id);
	}
	
	//更新信息
	public int updaUser(String email, String phone,String id) throws SQLException {
		// TODO Auto-generated method stub
		return dao.updateUser(email,phone,id);
	}
	
}
