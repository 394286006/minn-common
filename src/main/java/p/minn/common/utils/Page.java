package p.minn.common.utils;


/**
 * 分页工具
 * @author minn
 * @QQ:3942986006
 */
public class Page {

	private Integer page;
	
	private Integer rp;
	
	private Integer startR;
	
	private Integer endR=-1;
	
	private String query;
	
	private Integer total=-1;
	
	private String qtype;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRp() {
		return rp;
	}

	public void setRp(Integer rp) {
		this.rp = rp;
	}

	public Integer getStartR() throws Exception {
		if(this.total==-1){
			throw new Exception("totoal param must be set!");
		}
		return startR;
	}

	public void setStartR(Integer startR) {
		this.startR = startR;
	}

	public Integer getEndR() {
		return endR;
	}

	public void setEndR(Integer endR) {
		this.endR = endR;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
	public Integer getTotal() {
		return total;
	}

	/**
	 * 必须设置
	 * @param total
	 */
	public void setTotal(Integer total) {
		this.total = total;
		caculate();
	}

	
	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	private void caculate(){
		this.startR=(this.page-1)*this.rp;
		this.endR=this.page*this.rp;
		if(this.endR>this.total){
			this.endR=total;
		}
	}
	
}
