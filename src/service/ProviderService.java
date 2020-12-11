package service;

import java.util.List;

import bean.Impo;
import bean.Provider;
import dao.ImpoDao;
import dao.ProviderDao;
import util.Page;

public class ProviderService {

	private static ProviderDao dao = new ProviderDao();

	//得到供货商信息
	public static Page<Provider> getProviderPage(Page page, int curPage) {
		//查询当前页的列表数据
		List<Provider> data = dao.getProviderInfos(page, curPage);
		//查询总记录数
		int rowsCount = dao.getProviderInfosCount();
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}
	
	//查询的供货商信息
	public static Page searchProviderPage(Page page, int curPage, String proid) {
		//查询查找的供货商列表数据
		List<Provider> data = dao.serachProviderInfos(page, curPage, proid);
		//查询总记录数
		int rowsCount = dao.searchProviderInfosCount(proid);
		//将分页信息封装到Provider对象中
		page.setParam(data, curPage, rowsCount);
		return page;

	}


	//插入供货商
	public static int insertProvider(Provider provider) {
		// TODO Auto-generated method stub
		 return dao.insertProvider(provider);
	}
	
	//查询供货商
	public static Provider queryProviderByProId(String proid) {
		System.out.println(proid);
		return dao.queryProviderByProId(proid);
	}

	//保存更新
	public static int saveProviderById(Provider provider) {		
		return dao.saveProviderById(provider);
	}

	//删除供货商
	public static int delProvider(String id) {
		return dao.delProvider(id);
	}
	
	//检查供货商
	public int searchProviderInfosCount(String proid) {
		// TODO Auto-generated method stub
		return dao.searchProviderInfosCount(proid);
	}


}
