package p.minn.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;










import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;

import com.google.gson.Gson;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 工具类
 *
 */

public class UtilCommon {

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
	
	public static Map<?, ?> gson2Map(String messageBody){
		return (Map<?, ?>) gson2T(messageBody, Map.class);
	}
	
	public static <T>  Object gson2T(String messageBody,Class<?> T ){
		Gson gson=new Gson();
		return gson.fromJson(messageBody, T);
	}
	
	public static String  gson2Str(Object obj){
		Gson gson=new Gson();
		return gson.toJson(obj);
	}
	
	public static Map<String,String> getCondition(Page page){
		Map<String,String> rs=null;
		if(StringUtils.isNotEmpty(page.getQuery())){
			rs=new HashMap<String,String>();
			String[] cs=page.getQtype().split(",");
			String[] vs=page.getQuery().split(",");
			for(int i=0;i<cs.length; i++){
				if(StringUtils.isNotEmpty(vs[i])){
				   rs.put(cs[i],vs[i] );
				}else{
					rs.put(cs[i],UNKNOWN);
				}
			}
		}
		return rs;
	}
	
	public static String getPwd(String pwd) {
		String hexs=null;
		try{
		 String newpwd=pwd.substring(0,pwd.length()/3);
		  byte[] salt=pwd.substring(pwd.length()-8, pwd.length()).getBytes();
		  MessageDigest digest = MessageDigest.getInstance("MD5");
          if (salt != null) {
              digest.update(salt);
          }
          byte[] result = digest.digest(newpwd.getBytes());
          for (int i = 1; i < 1024; i++) {
              digest.reset();
              result = digest.digest(result);
          }
		   hexs=new String( Hex.encodeHex(result));
		} catch(NoSuchAlgorithmException e){
			throw new RuntimeException("password encoding error");
		}
		   return hexs;
	}
	
	public static String getSecurityKey(){
		return String.valueOf(System.currentTimeMillis()*((int)(Math.random()*100)));
	}
	public static String getSecurityRandom(){
		return String.valueOf((int)Math.random()*100);
	}
	
	public static void main(String[] args){
		//
		System.out.println(getPwd("89bed836cd5a7f00b8d6aed6dd80a248bf035cce4a7a9f68f2b0d39e"));
	}
	
}
