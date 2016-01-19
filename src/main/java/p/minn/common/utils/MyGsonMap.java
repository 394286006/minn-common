package p.minn.common.utils;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class MyGsonMap<T1,T2> {

	private Gson gson;
	
	private String messageBody;

	private MyGsonMap(String messageBody) {
		super();
		this.messageBody=messageBody;
		gson=new Gson();
	}
	
	public static <T1,T2> MyGsonMap<T1, T2> getInstance(String messageBody,Class<T1> T1,Class<T2> T2){
		return new MyGsonMap<T1,T2>(messageBody);
	}
	
	@SuppressWarnings("hiding")
	public <T2> T2 gson2T(Class<T2> T2){
		return  gson.fromJson(messageBody, T2);
	}
	
	@SuppressWarnings("unchecked")
	public  T1 gson2Map(){
		return  (T1) gson.fromJson(messageBody, Map.class);
	}
	
}
