package p.minn.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

	//多用于出现下拉选择框的情况，对应dictionary表中的mkey值
	String mkey() default "";
	//对应国际化资源文件中的键值/菜单键值ID【菜单管理中查看】
	String resourceKey() default "";

}
