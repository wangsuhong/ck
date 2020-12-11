package domain;

import java.sql.SQLException;
import java.util.List;

import bean.Goods;
import bean.User;
import dao.GoodDao;
import dao.UserDao;

public class DaoTest {
	public static void main(String[] args) throws SQLException{ 
//		UserDao userDao = new UserDao();
//		User user = new User(null,"admin","admin","admin@163.com","123");
//		
//		
////		int i = userDao.addUser(user);
//		
//		GoodDao goodDao = new GoodDao();
//		List<Goods> goodsList = goodDao.getGoodsList();
//		for(Goods g:goodsList) {
//			System.out.println(g);
//		}
//		
		
//		User user = userDao.getUser("wang", "123");
//		System.out.println(i);
		int goodsid = 1001;
		
		int  a = new GoodDao().GoodsNum(String.valueOf(goodsid));
		System.out.println("a= "+a);
		
		
		
	}
}
