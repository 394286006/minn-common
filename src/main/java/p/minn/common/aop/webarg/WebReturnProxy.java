package p.minn.common.aop.webarg;

import java.util.HashMap;
import java.util.Map;



import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import p.minn.common.utils.UtilCommon;
import p.minn.common.exception.WebException;

/**
 * 
 * @author minn
 * @QQ:3942986006
 * @comment 返回数据格式的重新封装，对于String类型，不作处理，对于非String类型，包含两个关键字，success【true/false】,data[返回自定义的封装数据]
 * HttpHeaders hthed=new HttpHeaders();
 *          hthed.add("Access-Control-Allow-Origin", "*");
 *          hthed.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
 *          hthed.add("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
 *          hthed.add("Access-Control-Max-Age", "1728000");
 *
 */
@Component
public class WebReturnProxy implements MethodInterceptor {
	
	private static final String SUCCESS_KEY="success";
	
	private static final String SUCCESS_DATA="data";
	
	@Override
	public Object invoke(MethodInvocation pjp) throws Throwable {

		Object entity=null;
		Map<String,Object> obj=new HashMap<String,Object>();
		entity=pjp.proceed();
		if(entity==null){
			obj.put(SUCCESS_KEY, true);
			obj.put(SUCCESS_DATA, pjp.getMethod().getName());
			entity=new ResponseEntity<Object>(obj,HttpStatus.OK);
		}else{
			if(entity instanceof WebException){
				WebException e=(WebException)entity;
				HttpHeaders rs=UtilCommon.ceateResponseHeader(e.getMessage(),e.getCauseMessage());
				obj.put(SUCCESS_KEY, false);
				obj.put(SUCCESS_DATA, e.getSelfdescript());
				entity=new ResponseEntity<Object>(obj,rs,HttpStatus.OK);
			}else if(!(entity instanceof String)){
				obj.put(SUCCESS_KEY, true);
				obj.put(SUCCESS_DATA, entity);
				entity=new ResponseEntity<Object>(obj,HttpStatus.OK);
			}
		}
		return entity;
	}

	

}
