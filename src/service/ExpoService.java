package service;

import java.sql.SQLException;
import java.util.List;

import bean.Expo;
import dao.ExpoDao;
import util.Page;

public class ExpoService {

	private static ExpoDao dao = new ExpoDao();
	
	//得到出库列表数据分页
	public static Page<Expo> getExpoPage(Page page, int curPage) {
		//查询当前页的列表数据
		List<Expo> data = dao.getExpoInfos(page, curPage);
		//查询总记录数
		int rowsCount = dao.getExpoInfosCount();
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}
	
	//查询出库列表数据分页
	public static Page<Expo> searchExpoPage(Page page, int curPage,String goodsid) {
		//查询当前页的列表数据
		List<Expo> data = dao.searchExpoInfos(page,curPage,goodsid);
		//查询总记录数
		int rowsCount = dao.searchExpoInfosCount(goodsid);
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}

	//插入出库数据
	public static int insertExpo(Expo expo) {
		// TODO Auto-generated method stub
		 return dao.insertExpo(expo);
	}
	
	//根据id查询出库数据
	public static Expo queryExpoById(String id) {
		System.out.println(id);
		return dao.queryExpoById(id);
	}

	//保存更新
	public static int saveExpoById(Expo expo) {		
		return dao.saveExpoById(expo);
	}

	//删除出库信息
	public static int delExpo(String id) {
		return dao.delExpo(id);
	}
	
	//得到出库数量
	public int getnum(String id) throws SQLException {
		// TODO Auto-generated method stub
		return dao.getnum(id);
	}

}
