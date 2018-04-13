package p.minn.common.annotation;

import java.lang.annotation.Documented;
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
@Documented
@Target({ElementType.PARAMETER})  
@Retention(RetentionPolicy.RUNTIME)
public @interface MyParam {

	String value() ;
}
