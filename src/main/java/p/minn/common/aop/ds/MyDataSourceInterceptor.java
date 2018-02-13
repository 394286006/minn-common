package p.minn.common.aop.ds;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
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
@Component
public class MyDataSourceInterceptor {

	@Autowired
	private MyDataSourceRegister mr;

	@Around("execution(public * *..service..*.*(..))&&@annotation(param)")
	public Object getDataSource(ProceedingJoinPoint point, MyDataSource param) throws Throwable {
		Object[] args = point.getArgs();
		if (args.length > 0 && args[args.length - 1] instanceof NamedParameterJdbcTemplate[]) {
			String[] params = param.value().split(",");
			NamedParameterJdbcTemplate[] list = new NamedParameterJdbcTemplate[params.length];
			for (int i = 0; i < params.length; i++) {
				list[i] = new NamedParameterJdbcTemplate(mr.getDataSource(params[i]));
			}
			args[args.length - 1] = list;
			return point.proceed(args);
		} else {
			return point.proceed();
		}

	}

}
