package service;

import java.sql.SQLException;
import java.util.List;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import bean.Goods;
import dao.GoodDao;
import util.Page;

public class GoodService {
	private static GoodDao dao = new GoodDao();
	
	
	//得到货物列表页面信息
	public static Page<Goods> getGoodPage(Page page, int curPage) {
		//查询当前页的列表数据
		List<Goods> data = dao.getGoodsInfos(page,curPage);
		//查询总记录数
		int rowsCount = GoodDao.getGoodsInfosCount();
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}
	
	
	//查询出的货物信息
	public static Page searchGoodPage(Page page, int curPage, String goodsid) {
		//查询当前页的列表数据
		List<Goods> data = dao.searchGoodsInfos(page,curPage,goodsid);
		//查询总记录数
		int rowsCount = GoodDao.searchGoodsInfosCount(goodsid);
		//将分页信息封装到page对象中
		page.setParam(data, curPage, rowsCount);
		return page;
	}
	

    //插入货物信息
	public static int insertGoods(Goods goods) {
		// TODO Auto-generated method stub
		 return dao.insertGoods(goods);
	}
	
	//以货物编号显示
	public static Goods queryGoodsByGoodsId(String goodsid) {
		return dao.queryGoodsByGoodsId(goodsid);
	}

    //保存货物信息
	public static int saveGoodsById(Goods goods) {		
		return dao.saveGoodsById(goods);
	}
	
	//以ID删除货物
	public static int delGoods(String id) {
		return dao.delGoods(id);
	}

	//检查货物编号信息
	public int checkGoodsid(String goodsid) throws SQLException {
	    return dao.checkGoodsid(goodsid);
	    
	}

	//检查货物数量
	public int GoodsNum(String goodsid) throws SQLException {
		// TODO Auto-generated method stub
		return dao.GoodsNum(goodsid);
	}

	//货物数量减少
	public int expoNum(String exponum, String goodsid) throws SQLException {
		// TODO Auto-generated method stub
		return dao.expoNum(exponum, goodsid);
	}

	//修改的数量变动-
	public int po1(int e, String goodsid) throws SQLException {
		// TODO Auto-generated method stub
		return dao.po1(e, goodsid);
	}

	//修改的数量变动+
	public int po2(int e, String goodsid) throws SQLException {
		// TODO Auto-generated method stub
		return dao.po2(e, goodsid);
	}

	//货物数量增加
	public int impoNum(String imponum, String goodsid) throws SQLException {
		// TODO Auto-generated method stub
		return dao.impoNum(imponum, goodsid);
	}

}


  
