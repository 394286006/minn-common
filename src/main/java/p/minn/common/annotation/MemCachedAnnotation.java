package p.minn.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import p.minn.common.aop.mybatis.MybatisMethodEnum;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MemCachedAnnotation {
  String key() default "";
  int exp() default 5000;
  MybatisMethodEnum method() default MybatisMethodEnum.query;
}
