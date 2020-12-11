package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bean.Provider;
import util.C3P0Utils;
import util.Page;

public class ProviderDao {
	
	static QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	
	//得到供货商信息分页
	public List<Provider> getProviderInfos(Page page,int curPage) {
		//sql语句  查询表
		String sql="select * from provider order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
		List<Provider> list=new ArrayList<Provider>();
		try {
			 list=qr.query(sql, new BeanListHandler<>(Provider.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询类型列表的总记录数量
	 * @return
	 */
	public static int getProviderInfosCount() {
		String sql = "select count(id) from provider";
		Long rowsCount = 0L;
		try {
			rowsCount = qr.query(sql, new ScalarHandler<Long>());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowsCount.intValue();
	}
	
	//查询供货商分页
	public List<Provider> serachProviderInfos(Page page,int curPage,String proid) {
		//sql语句  查询表	tb_equip_type
		String sql="select * from provider  where proid=? order by proid desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
		List<Provider> list=new ArrayList<Provider>();
		try {
			 list=qr.query(sql, new BeanListHandler<>(Provider.class),proid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//查询的供货商记录长度
	public static int searchProviderInfosCount(String proid) {
		String sql = "select count(id) from provider where proid=?";
		Long rowsCount = 0L;
		try {
			rowsCount = qr.query(sql, new ScalarHandler<Long>(),proid);
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
	public int delProvider(String id){
		String sql="delete from provider where id=?";
		int s=0;
		try {
			s=qr.execute(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	//插入供货商信息
	public  int insertProvider(Provider provider) {
		String sql="insert into provider(proid,proname,proemail,prophone,prodesc) values(?,?,?,?,?)";
		int res=0;
		try {
			res=qr.execute(sql,provider.getProid(),provider.getProname(),provider.getProemail(),provider.getProphone(),provider.getProdesc());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	//查找id显示
	public static Provider queryProviderByProId(String proid) {
		String sql ="select * from provider where proid=?";
		System.out.println(proid);
		Provider provider=new Provider();
		try {
			List<Provider> list= qr.query(sql, new BeanListHandler<>(Provider.class),proid);
			provider =list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return provider ;
	}
	
	//保存更新
	public static int saveProviderById(Provider provider) {
		String sql="update provider set proid=?,proname=?,proemail=?,prophone=?,prodesc=?  where id =?";
		try {
			return qr.update(sql,provider.getProid(),provider.getProname(),provider.getProemail(),provider.getProphone(),provider.getProdesc(),provider.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public List<Provider> getProviderList() {
		// TODO Auto-generated method stub
		return null;
	}
}
