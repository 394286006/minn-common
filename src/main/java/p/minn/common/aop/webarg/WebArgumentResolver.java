package p.minn.common.aop.webarg;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import p.minn.common.annotation.MyParam;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * 
 */
public class WebArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return checkType(parameter, String.class, MyParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		if (checkType(parameter, String.class, MyParam.class)) {
			return getValue(parameter, String.class, MyParam.class,webRequest.getParameterMap());
		}

		return null;
	}
	
	private Object getValue(MethodParameter parameter, Class<?> ptype,
			Class<?> target,Map<String,String[]> param){
		Object rs=null;
		Class<?> klass = parameter.getParameterType();
		if (klass.isAssignableFrom(ptype)) {
			Annotation[] as = parameter.getParameterAnnotations();
			for (Annotation a : as) {
				if (a.annotationType() == target) {
					MyParam bp=(MyParam) a;
					if("language".equalsIgnoreCase(bp.value()))
					{
						rs= LocaleContextHolder.getLocale().getLanguage();
					}
					
					
				}
			}
		}
		return rs;
	}

	private boolean checkType(MethodParameter parameter, Class<?> ptype,
			Class<?> target) {
		Class<?> klass = parameter.getParameterType();
		if (klass.isAssignableFrom(ptype)) {
			Annotation[] as = parameter.getParameterAnnotations();
			for (Annotation a : as) {
				if (a.annotationType() == target) {
					return true;
				}
			}
		}
		return false;
	}

}
