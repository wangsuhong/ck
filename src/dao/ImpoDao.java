package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bean.Expo;
import bean.Impo;
import util.C3P0Utils;
import util.Page;

public class ImpoDao {
	static QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	
	//得到入库信息
	public List<Impo> getImpoInfos(Page page,int curPage) {
		//sql语句  查询表	
		String sql="select * from import order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
		List<Impo> list=new ArrayList<Impo>();
		try {
			 list=qr.query(sql, new BeanListHandler<>(Impo.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询类型列表的总记录数量
	 * @return
	 */
	public static int getImpoInfosCount() {
		String sql = "select count(id) from import";
		Long rowsCount = 0L;
		try {
			rowsCount = qr.query(sql, new ScalarHandler<Long>());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsCount.intValue();
	}
	
	//查询入库信息
	public List<Impo> searchImpoInfos(Page page,int curPage,String goodsid) {
		//sql语句  查询表	
		String sql="select * from import  where goodsid=? order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
		List<Impo> list=new ArrayList<Impo>();
		try {
			 list=qr.query(sql,new BeanListHandler<>(Impo.class),goodsid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//获得入库记录长度
	public static int searchImpoInfosCount(String goodsid) {
		String sql = "select count(id) from import where goodsid=?";
		Long rowsCount = 0L;
		try {
			rowsCount = qr.query(sql, new ScalarHandler<Long>(),goodsid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsCount.intValue();
	}
	
	/**
	 * 根据Id删除一条数据
	 * @param id
	 * @return
	 */
	//删除
	public int delImpo(String id){
		String sql="delete from import where id=?";
		int s=0;
		try {
			s=qr.execute(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	//插入
	public  int insertImpo(Impo impo) {
		String sql="insert into import(goodsid,imponame,imponum,impoprice,impodate) values(?,?,?,?,?)";
		int res=0;
		try {
			res=qr.execute(sql,impo.getGoodsid(),impo.getImponame(),impo.getImponum(),impo.getImpoprice(),impo.getImpodate());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	//查找id显示
	public static Impo queryImpoById(String id) {
		String sql ="select * from Import where id=?";
		Impo impo = new Impo();
		try {
			List<Impo> list= qr.query(sql, new BeanListHandler<>(Impo.class),id);
			impo =list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return impo ;
	}
	
	//保存更新
	public static int saveImpoById(Impo impo) {
		String sql="update Import set goodsid=?,imponame=? ,imponum=?,impoprice=?,impodate=?  where id =?";
		int s=0;
		try {
			s = qr.update(sql,impo.getGoodsid(),impo.getImponame(),impo.getImponum(),impo.getImpoprice(),impo.getImpodate(),impo.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public List<Impo> getImpoList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//根据id查询入库数量
	public int getnum(String id) throws SQLException {
		String sql = "select * from import where id=?";
		Impo exponum = qr.query(sql, new BeanHandler<Impo>(Impo.class),id);
		return exponum.getImponum();
	}
}
