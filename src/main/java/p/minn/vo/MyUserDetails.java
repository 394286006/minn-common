package p.minn.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public abstract class MyUserDetails implements Serializable{

	private Integer id;
	
	private List<String> roles;
	
	private String loginName;
	
	private String password;
	
	private Integer type;
	
	private String language;
	
	private String accessKey;
	
	private String secretKey;

	public MyUserDetails(String loginName,
        String password) {
    super();
    this.loginName = loginName;
    this.password = password;
}
	
	public MyUserDetails(Integer id, String loginName,
			String password,String language,Integer type, List<String> roles) {
		super();
		this.id = id;
		this.roles = roles;
		this.type=type;
		this.language=language;
		this.loginName = loginName;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	
}
