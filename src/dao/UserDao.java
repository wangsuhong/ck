package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bean.Goods;
import bean.User;
import util.C3P0Utils;
import util.Page;

public class UserDao {
	
	
	    static QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
	    //得到账号密码
		public User getUser(String username,String password) throws SQLException{
			String sql = "select * from user where username= ? and password = ?";
			User user = qr.query(sql, new BeanHandler<User>(User.class),username,password);		
			return user;
		}
				
		
		//获取管理员列表信息
		public List<User> getUserInfos(Page page,int curPage) {			
			//sql语句  查询表	
			String sql="select * from user order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
			List<User> list=new ArrayList<User>();
			try {
				 list=qr.query(sql, new BeanListHandler<>(User.class));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		public static int getUserInfosCount() {
			String sql = "select count(id) from user";
			Long rowsCount = 0L;
			try {
				rowsCount = qr.query(sql, new ScalarHandler<Long>());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return rowsCount.intValue();
		}
		
		//查找管理员信息
		public List<User> searchUserInfos(Page page,int curPage,String username) {
			//sql语句  查询表
			String sql="select * from user  where username=? order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
			List<User> list=new ArrayList<User>();
			try {
				 list=qr.query(sql,new BeanListHandler<>(User.class),username);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}		
		
		//查询管理员记录长度
		public static int searchUserInfosCount(String username) {
			String sql = "select count(id) from user where username=?";
			Long rowsCount = 0L;
			try {
				rowsCount = qr.query(sql, new ScalarHandler<Long>(),username);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(rowsCount);
			return rowsCount.intValue();
		}
		
		public List<User> getUserList() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		//修改密码
		public int updatepassword(String newpassword, String id) throws SQLException {
			String sql="update user set password=? where id=?";
			return qr.update(sql,newpassword,id);
		}
		
		//修改个人信息
		public int updateUser(String email, String phone,String id) throws SQLException {
			String sql="update user set email=?,phone=? where id=?";
			return qr.update(sql,email,phone,id);
		}
	
}		

