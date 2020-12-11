package service;

import java.sql.SQLException;
import java.util.List;
import bean.Impo;
import dao.ImpoDao;
import util.Page;

public class ImpoService {
	private static ImpoDao dao = new ImpoDao();

	//获取所有数据
	public static Page<Impo> getImpoPage(Page page, int curPage) {
		//查询当前页的列表数据
		List<Impo> data = dao.getImpoInfos(page, curPage);
		//查询总记录数
		int rowsCount = dao.getImpoInfosCount();
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}
	
	//获取查找的数据
	public static Page<Impo> searchImpoPage(Page page2, int curPage,String goodsid) {
		//查询查找的入库列表数据
		List<Impo> data = dao.searchImpoInfos(page2,curPage,goodsid);
		//查询总记录数
		int rowsCount = dao.searchImpoInfosCount(goodsid);
		//将分页信息封装到impo对象中
		page2.setParam(data, curPage, rowsCount);
		return page2;
	}

	//插入入库信息
	public static int insertImpo(Impo impo) {
		// TODO Auto-generated method stub
		 return dao.insertImpo(impo);
	}
	
	//查询id显示
	public static Impo queryImpoById(String id) {
		System.out.println(id);
		return dao.queryImpoById(id);
	}

	//保存更新信息
	public static int saveImpoById(Impo impo) {		
		return dao.saveImpoById(impo);
	}
	
	//删除入库信息
	public static int delImpo(String id) {
		return dao.delImpo(id);
	}
	
	//得到原来的入库数量
	public int getnum(String id) throws SQLException {
		// TODO Auto-generated method stub
		return dao.getnum(id);
	}
}
