package p.minn.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;


import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 工具类
 *
 */

public class CommonUtils {

	private static final String UNKNOWN="unknown";
	
	/**
	 * 获取国际化资源
	 * @param key
	 * @return
	 */
	public static String getResourceName(Object key){
		ResourceBundle bundle = ResourceBundle.getBundle("messages.messages",LocaleContextHolder.getLocale());
		String val=null;
		try{
			val= bundle.getString(key.toString());
		}catch(RuntimeException re){
			val="";
		}

		return val;
	}
	
	public static HttpHeaders ceateResponseHeader(String exceptionmsg,String couseclass){
		HttpHeaders hthed=new HttpHeaders();
		hthed.add("msg", exceptionmsg);
		hthed.add("causeclz", couseclass);
		return hthed;
	}
	
	public static String getRemoteAdd(HttpServletRequest req){
		String ip=null;
		ip=req.getHeader("x-forwarded-for");
		if(ip==null||ip.length()==0||UNKNOWN.equalsIgnoreCase(ip)){
			ip=req.getHeader("Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||UNKNOWN.equalsIgnoreCase(ip)){
			ip=req.getHeader("WL-Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||UNKNOWN.equalsIgnoreCase(ip)){
			ip=req.getRemoteAddr();
		}
		return ip;
	}
	
	public static Map<String,Object> getResultMap(int total,Page page,Object rows){
		Map<String,Object> rs=getResultMap(rows);
		rs.put("rows", rows);
		rs.put("total", total);
		rs.put("page", page.getPage());
		return rs;
	}
	
	public static Map<String,Object> getResultMap(Object rows){
		Map<String,Object> rs=new HashMap<String, Object>();
		rs.put("rows", rows);
		rs.put("stat", "ok");
		return rs;
	}
	
}
