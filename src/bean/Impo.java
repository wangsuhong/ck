package bean;

import java.util.Date;

public class Impo {
    private Integer id;
    private Integer goodsid;
    private String  imponame;
    private Integer imponum;
    private Double impoprice;
    private Date impodate;
    
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
	public String getImponame() {
		return imponame;
	}
	public void setImponame(String imponame) {
		this.imponame = imponame;
	}
	public Integer getImponum() {
		return imponum;
	}
	public void setImponum(Integer imponum) {
		this.imponum = imponum;
	}
	public Double getImpoprice() {
		return impoprice;
	}
	public void setImpoprice(Double impoprice) {
		this.impoprice = impoprice;
	}
	public Date getImpodate() {
		return impodate;
	}
	public void setImpodate(Date impodate) {
		this.impodate = impodate;
	}

	
	@Override
	public String toString() {
		return "Import [id=" + id + ", goodsid=" + goodsid + ", imponame=" + imponame + ", imponum=" + imponum
				+ ", impoprice=" + impoprice + ", impodate=" + impodate + "]";
	}
    
    

}
