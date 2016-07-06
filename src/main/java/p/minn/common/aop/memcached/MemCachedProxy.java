package p.minn.common.aop.memcached;



import net.spy.memcached.MemcachedClient;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import p.minn.common.annotation.MemCachedAnnotation;
import p.minn.common.aop.mybatis.MybatisMethodEnum;


/**
 * 
 * @author minn
 * @QQ:3942986006
 */
@Component
public class MemCachedProxy implements MethodInterceptor {
  
  @Autowired
  private MemcachedClient memcachedClient;
  @Autowired
  private KeyGenerator myKeyGenerator;
  
  @Override
  public Object invoke(MethodInvocation mi) throws Throwable {
    // TODO Auto-generated method stub
    MemCachedAnnotation mca= mi.getMethod().getAnnotation(MemCachedAnnotation.class);
    if(mca==null){
      return mi.proceed();
    }
    String key=mca.key();
    if(StringUtils.isEmpty(key)){
      key=(String) myKeyGenerator.generate(mi.getMethod().getDeclaringClass(), mi.getMethod(), mi.getArguments()) ;
    }
    Object entity=null;
   if(mca.method().equals(MybatisMethodEnum.query)||mca.method().equals(MybatisMethodEnum.add)){
     entity=memcachedClient.get(key);
     if(entity==null){
       entity=mi.proceed();
       memcachedClient.add(key, mca.exp(), entity);
     }
   }
   if(mca.method().equals(MybatisMethodEnum.remove)){
     memcachedClient.delete(key);
     entity=mi.proceed();
   }
   if(mca.method().equals(MybatisMethodEnum.update)){
     entity=mi.proceed();
     memcachedClient.set(key, mca.exp(),entity);
   }
       
    return entity;
  }
  
}
