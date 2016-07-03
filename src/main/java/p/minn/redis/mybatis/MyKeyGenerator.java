package p.minn.redis.mybatis;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

import p.minn.common.utils.Encodes;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class MyKeyGenerator implements KeyGenerator {
  StringBuilder key = new StringBuilder();
  @Override
  public Object generate(Object target, Method method, Object... params) {
    // TODO Auto-generated method stub
    key.delete(0, key.length());
    for (Object obj : params) {
      key.append(obj.toString());
    }
    key.append(method.getName());
    key.append(target.getClass().getSimpleName());
    return Encodes.encodeHex(key.toString().getBytes()).substring(0,80);
  }

}
