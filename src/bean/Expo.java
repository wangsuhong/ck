package bean;

import java.util.Date;

public class Expo {

	public Integer id;
	public Integer goodsid;
	public String exponame;
	public Integer exponum;
	public Double expoprice;
	public Date expodate;
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
	public String getExponame() {
		return exponame;
	}
	public void setExponame(String exponame) {
		this.exponame = exponame;
	}
	public Integer getExponum() {
		return exponum;
	}
	public void setExponum(Integer exponum) {
		this.exponum = exponum;
	}
	public Double getExpoprice() {
		return expoprice;
	}
	public void setExpoprice(Double expoprice) {
		this.expoprice = expoprice;
	}
	public Date getExpodate() {
		return expodate;
	}
	public void setExpodate(Date expodate) {
		this.expodate = expodate;
	}

	@Override
	public String toString() {
		return "Export [id=" + id + ", goodsid=" + goodsid + ", exponame=" + exponame + ", exponum=" + exponum
				+ ", expoprice=" + expoprice + ", expodate=" + expodate + "]";
	}
	
}
