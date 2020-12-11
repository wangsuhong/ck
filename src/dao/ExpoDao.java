package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bean.Expo;
import bean.Goods;
import util.C3P0Utils;
import util.Page;


public class ExpoDao {

static QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	
	//出库列表分页
	public List<Expo> getExpoInfos(Page page,int curPage) {
		//sql语句  查询表	
		String sql="select * from export order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
		List<Expo> list=new ArrayList<Expo>();
		try {
			 list=qr.query(sql, new BeanListHandler<>(Expo.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	 //查询出库列表的总记录数量
	public static int getExpoInfosCount() {
		String sql = "select count(id) from export";
		Long rowsCount = 0L;
		try {
			rowsCount = qr.query(sql, new ScalarHandler<Long>());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsCount.intValue();
	}
	
	//查找出库分页
	public List<Expo> searchExpoInfos(Page page,int curPage,String goodsid) {
		//sql语句  查询表	
		String sql="select * from export  where goodsid=? order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
		List<Expo> list=new ArrayList<Expo>();
		try {
			 list=qr.query(sql,new BeanListHandler<>(Expo.class),goodsid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查找出库记录数
	public static int searchExpoInfosCount(String goodsid) {
		String sql = "select count(*) from export where goodsid=?";
		Long rowsCount = 0L;
		try {
			rowsCount = qr.query(sql, new ScalarHandler<Long>(),goodsid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(rowsCount);
		return rowsCount.intValue();
	}
	
	/**
	 * 根据Id删除一条数据
	 * @param id
	 * @return
	 */
	//删除
	public int delExpo(String id){
		String sql="delete from export where id=?";
		int s=0;
		try {
			s=qr.execute(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	//插入出库信息
	public  int insertExpo(Expo Expo) {
		String sql="insert into export(goodsid,exponame,exponum,expoprice,expodate) values(?,?,?,?,?)";
		int res=0;
		try {
			res=qr.execute(sql,Expo.getGoodsid(),Expo.getExponame(),Expo.getExponum(),Expo.getExpoprice(),Expo.getExpodate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	//查找id显示
	public static Expo queryExpoById(String id) {
		String sql ="select * from export where id=?";
		Expo expo = new Expo();
		try {
			List<Expo> list= qr.query(sql, new BeanListHandler<>(Expo.class),id);
			expo =list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return expo ;
	}
	
	//保存更新
	public static int saveExpoById(Expo expo) {
		String sql="update export set goodsid=?,exponame=? ,exponum=?,expoprice=?,expodate=?  where id =?";
		int n=0;
		try {
			return qr.update(sql,expo.getGoodsid(),expo.getExponame(),expo.getExponum(),expo.getExpoprice(),expo.getExpodate(),expo.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return n;
	}
	
	
	public List<Expo> getExpoList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//根据id查询出库数量
	public int getnum(String id) throws SQLException {
		String sql = "select * from export where id=?";
		Expo exponum = qr.query(sql, new BeanHandler<Expo>(Expo.class),id);
		return exponum.getExponum();
	}
}
