package p.minn.common.aop.log;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import p.minn.common.utils.LogUtil;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 */
public abstract class LogAop implements AfterReturningAdvice {


	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {

		if (!LogUtil.checkDaoLog(method)) {
			return;
		}
		if (!LogUtil.checkLog(method)) {
			return;
		}
       
		invoke(method,args);

	}
	
   protected void invoke(Method method,
			Object[] args){
	   
   }

	

}
