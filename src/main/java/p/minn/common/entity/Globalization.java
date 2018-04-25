package p.minn.common.entity;

import p.minn.common.annotation.LogAnnotation;
import p.minn.common.entity.IdEntity;



/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 用户
 *
 */
@LogAnnotation
public class Globalization extends IdEntity{
	
	@LogAnnotation(resourceKey="language_name")
	private String name;
	
	@LogAnnotation(resourceKey="language_type")
	private String language;
	
	private String tableid;
	
	private String tablename;
	
	private String tablecolumn;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTablecolumn() {
		return tablecolumn;
	}

	public void setTablecolumn(String tablecolumn) {
		this.tablecolumn = tablecolumn;
	}
	
}
