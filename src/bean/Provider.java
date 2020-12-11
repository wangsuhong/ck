package bean;

public class Provider {

	private Integer id;
	private Integer proid;
	private String proname;
	private String proemail;
	private String prophone;
	private String prodesc;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProid() {
		return proid;
	}
	public void setProid(Integer proid) {
		this.proid = proid;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getProemail() {
		return proemail;
	}
	public void setProemail(String proemail) {
		this.proemail = proemail;
	}
	public String getProphone() {
		return prophone;
	}
	public void setProphone(String prophone) {
		this.prophone = prophone;
	}
	public String getProdesc() {
		return prodesc;
	}
	public void setProdesc(String prodesc) {
		this.prodesc = prodesc;
	}
	@Override
	public String toString() {
		return "Provider [id=" + id + ", proid=" + proid + ", proname=" + proname + ", proemail=" + proemail
				+ ", prophone=" + prophone + ", prodesc=" + prodesc + "]";
	}
	
	
	
	
}
