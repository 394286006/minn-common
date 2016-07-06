package p.minn.common.aop.mongo;




import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;


import p.minn.common.annotation.MongoAnnotation;
import p.minn.common.aop.mybatis.MybatisMethodEnum;

/**
 * 
 * @author minn
 * @QQ:3942986006
 */
@Component
public class MyMongoProxy implements MethodInterceptor {
  
  @Autowired
  private MyMongoProxyDBAManager myMongoDBAManager;
  @Autowired
  private KeyGenerator myKeyGenerator;
  
  
  @Override
  public Object invoke(MethodInvocation mi) throws Throwable {
    // TODO Auto-generated method stub
    MongoAnnotation mca= mi.getMethod().getAnnotation(MongoAnnotation.class);
    if(mca==null){
      return mi.proceed();
    }
    String key=mca.key();
    if(StringUtils.isEmpty(key)){
      key=(String) myKeyGenerator.generate(mi.getMethod().getDeclaringClass(), mi.getMethod(), mi.getArguments()) ;
    }
    Object entity=null;
   if(mca.method().equals(MybatisMethodEnum.query)||mca.method().equals(MybatisMethodEnum.add)){
     entity=myMongoDBAManager.queryOne(mca, key);
     if(entity==null){
       entity=mi.proceed();
       myMongoDBAManager.insertOne(mca,key,entity);
     }
   }
   if(mca.method().equals(MybatisMethodEnum.remove)){
     myMongoDBAManager.deleteOne(mca,key);
     entity=mi.proceed();
   }
   if(mca.method().equals(MybatisMethodEnum.update)){
     entity=mi.proceed();
     myMongoDBAManager.replaceOne(mca,key,entity) ;
   }
       
    return entity;
  }
  
}
