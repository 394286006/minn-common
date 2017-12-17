package p.minn.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
		  byte[] salt=pwd.substring(pwd.length()-8>0? pwd.length()-8:0, pwd.length()).getBytes();
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
	
    public static List<Map<String,Object>> createTreeMenu(List<Map<String,Object>> source,String parent){
      List<Map<String,Object>> children=new ArrayList<Map<String,Object>>();
      for(Map<String,Object> map:source){
    	      if(map.containsKey("code")) {
    	    	    map.put("key", map.get("id")+"@"+map.get("code")+"@");
    	      }
          if(map.get("pid").toString().equals(parent)){
              Map<String,Object> state=new HashMap<String,Object>();
              if(map.get("selected").toString().equals("1")){
                  map.put("selected", true);
                  state.put("selected", true);
              }else{
                  map.put("selected", false);
                  state.put("selected", false);
              }
              map.put("state", state);
              children.add(map);
              map.put("children", createTreeMenu(source,map.get("id").toString()));
             
          }
      }
      return children;
  }	
    
   public static String[] split(String src,int len){
      return split(src,len,"-1");
   }
   public static String[] split(String src,int len,String val){
     String[] arr=null;
     if(StringUtils.isEmpty(src)){
       arr=new String[len];
       for(int i=0;i<len;i++){
         arr[i]=val;
       }
     }else{
       arr=src.split(",");
     }
     
     return arr;
  }
   public static String currentDateTime() {
     Date date=new Date();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(date);
 }
   
   public static String getString(Object obj){
     if(obj==null){
       return "";
     }else{
       return obj.toString();
     }
   }
     
	public static void main(String[] args){
		//
		System.out.println(getPwd("89bed836cd5a7f00b8d6aed6dd80a248bf035cce4a7a9f68f2b0d39e"));
	}
	
}
