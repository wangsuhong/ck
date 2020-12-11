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
	
	public class GoodDao {
		static QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		
	    //得到货物列表
		public List<Goods> getGoodsInfos(Page page,int curPage) {
			//sql语句  查询表	
			String sql="select * from goods order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
			List<Goods> list=new ArrayList<Goods>();
			try {
				 list=qr.query(sql, new BeanListHandler<>(Goods.class));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		/**
		 * 查询类型列表的总记录数量
		 * @return
		 */
		public static int getGoodsInfosCount() {
			String sql = "select count(id) from goods";
			Long rowsCount = 0L;
			try {
				rowsCount = qr.query(sql, new ScalarHandler<Long>());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return rowsCount.intValue();
		}
		
		//查找货物
		public List<Goods> searchGoodsInfos(Page page,int curPage,String goodsid) {
			//sql语句  查询表
			String sql="select * from goods  where goodsid=? order by id desc limit "+((curPage-1)*page.getPageSize())+", "+page.getPageSize();
			List<Goods> list=new ArrayList<Goods>();
			try {
				 list=qr.query(sql,new BeanListHandler<>(Goods.class),goodsid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		//计算获得数据长度
		public static int searchGoodsInfosCount(String goodsid) {
			String sql = "select count(id) from goods where goodsid=?";
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
		public int delGoods(String id){
			String sql="delete from goods where id=?";
			int s=0;
			try {
				s=qr.execute(sql, id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return s;
		}
		
		//插入
		public  int insertGoods(Goods goods) {
			String sql="insert into goods(goodsid,goodsname,goodsnum,goodsprice,typename,date,proid,goodsdesc) values(?,?,?,?,?,?,?,?)";
			int res=0;
			try {
				res=qr.execute(sql,goods.getGoodsid(),goods.getGoodsname(),goods.getGoodsnum(),goods.getGoodsprice(),goods.getTypename(),
						goods.getDate(),goods.getProid(),goods.getGoodsdesc());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}
		
		//查找货物编号显示
		public static Goods queryGoodsByGoodsId(String goodsid) {
			String sql ="select * from goods where goodsid=?";
			Goods goods=new Goods();
			try {
				List<Goods> list= qr.query(sql, new BeanListHandler<>(Goods.class),goodsid);
				goods =list.get(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return goods ;
		}
	
		//保存更新
		public static int saveGoodsById(Goods goods) {
			String sql="update goods set goodsid=?,goodsname=? ,goodsnum=?,goodsprice=?,typename=?,date=?,proid=?,goodsdesc=?  where id =?";
			int n=0;
			try {
				return qr.update(sql,goods.getGoodsid(),goods.getGoodsname(),goods.getGoodsnum(),goods.getGoodsprice(),
						goods.getTypename(),goods.getDate(),goods.getProid(),goods.getGoodsdesc(),goods.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return n;
		}
		
		//入库数量增加
		public int impoNum(String imponum, String goodsid) throws SQLException {
			String sql="update goods set goods.goodsnum=goodsnum+? where goodsid=?";
			return qr.update(sql,imponum,goodsid);
		}
	
		//出库数量减少
		public int expoNum(String exponum, String goodsid) throws SQLException {
			String sql="update goods set goods.goodsnum=goodsnum-? where goodsid=?";
			return qr.update(sql,exponum,goodsid);
		}
		//修改的数量变动-
		public int po1(int e, String goodsid) throws SQLException {
			String sql="update goods set goods.goodsnum=goodsnum-? where goodsid=?";
			return qr.update(sql,e,goodsid);
		}
		//修改的数量变动+
		public int po2(int e, String goodsid) throws SQLException {
			String sql="update goods set goods.goodsnum=goodsnum+? where goodsid=?";
			return qr.update(sql,e,goodsid);
		}
		
		
		public List<Goods> getGoodsList() {
			// TODO Auto-generated method stub
			return null;
		}
		
		//得到货物数量
		public int GoodsNum (String goodsid) throws SQLException{
			String sql = "select * from goods where goodsid=?";
			Goods goodsnum = qr.query(sql, new BeanHandler<Goods>(Goods.class),goodsid);
			return goodsnum.getGoodsnum();
		}
		
		//检查货物编号
		public static int checkGoodsid(String goodsid) {
			String sql = "select count(*) from goods where goodsid=?";
			Long rowsCount = 0L;
			try {
				rowsCount = qr.query(sql, new ScalarHandler<Long>(),goodsid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return rowsCount.intValue();
		}
		
	
	}


