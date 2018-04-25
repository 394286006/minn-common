package p.minn.common.entity;

import java.io.Serializable;

import p.minn.common.annotation.LogAnnotation;



/**
 * 
 * @author minn
 * @QQ:3942986006
 * comment ID
 *
 */
@LogAnnotation
public class IdEntity implements Serializable{

	@LogAnnotation(resourceKey="common_id")
	private Integer id;
	
	private Integer createid;
	
	private String createtime;
	
	private Integer updateid;
	
	private String updatetime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreateid() {
		return createid;
	}

	public void setCreateid(Integer createid) {
		this.createid = createid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getUpdateid() {
		return updateid;
	}

	public void setUpdateid(Integer updateid) {
		this.updateid = updateid;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
}
