package bean;

import java.util.Date;

public class Goods {
 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public Integer getGoodsnum() {
		return goodsnum;
	}

	public void setGoodsnum(Integer goodsnum) {
		this.goodsnum = goodsnum;
	}

	public Double getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(Double goodsprice) {
		this.goodsprice = goodsprice;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getProid() {
		return proid;
	}

	public void setProid(Integer proid) {
		this.proid = proid;
	}
	
	public String getGoodsdesc() {
		return goodsdesc;
	}

	public void setGoodsdesc(String goodsdesc) {
		this.goodsdesc = goodsdesc;
	}

	
	private Integer id;
	private Integer goodsid;
	private String goodsname;
	private Integer goodsnum;
	private Double goodsprice;
	private String typename;
	private Date date;
	private Integer proid;
	private String goodsdesc;
	@Override
	public String toString() {
		return "Goods [id=" + id + ", goodsid=" + goodsid + ", goodsname=" + goodsname + ", goodsnum=" + goodsnum
				+ ", goodsprice=" + goodsprice + ", typename=" + typename + ", date=" + date + ", proid=" + proid
				+ ", goodsdesc=" + goodsdesc + "]";
	}
  
	

  
}
