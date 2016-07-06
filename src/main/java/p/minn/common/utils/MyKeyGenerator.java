package p.minn.common.utils;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class MyKeyGenerator implements KeyGenerator {
  StringBuilder key = new StringBuilder();
  private int keyLength=80;
  @Override
  public Object generate(Object target, Method method, Object... params) {
    // TODO Auto-generated method stub
    key.delete(0, key.length());
    for (Object obj : params) {
      key.append(obj.toString());
    }
    key.append(method.getName());
    key.append(target.getClass().getSimpleName());
    String tmpkey=Encodes.encodeHex(key.toString().getBytes());
    return tmpkey.length()>keyLength? tmpkey.substring(0,keyLength):tmpkey;
  }
  
  
  public int getKeyLength() {
    return keyLength;
  }
  public void setKeyLength(int keyLength) {
    this.keyLength = keyLength;
  }

  
  
}
