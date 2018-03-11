package p.minn.common.aop.ds;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import p.minn.common.annotation.MyDataSource;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Aspect
public abstract class MyDataSourceInterceptor<T> {

	@Autowired
	protected MySpringDataSourceRegister mr;
	
	protected Map<String,T> ds=new HashMap<String,T>();

	@Around("execution(public * *..service..*.*(..))&&@annotation(param)")
	public Object getDataSource(ProceedingJoinPoint point, MyDataSource param) throws Throwable {
		Object[] args = point.getArgs();
		if (args.length > 0 && args[args.length - 1].getClass().getTypeName().equals(param.type().getTypeName()+"[]")) {
			String[] params = param.value().split(",");
			args[args.length - 1] = get(params);
			return point.proceed(args);
		} else {
			return point.proceed();
		}

	}
	
	protected abstract T[] get(String[] params);

}
