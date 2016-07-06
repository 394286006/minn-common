package p.minn.common.aop.mongo;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import p.minn.common.annotation.MongoAnnotation;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

/**
 * 
 * @author minn
 * @QQ:3942986006
 */
@Component
public class MyMongoProxyDBAManager {

  private static final String KEY="key";
  
  private static final String VALUE="pojo";
  
  @Autowired
  private MongoClient mongoClient;
  
  private MongoCollection<Document> getCollection(MongoAnnotation mca){
    return mongoClient.getDatabase(mca.database()).getCollection(mca.collection());
  }
  
  public Object queryOne(MongoAnnotation mca,String key){
    Object entity=null;
    try{
      entity=getCollection(mca).find(eq(KEY, key)).first().get(VALUE);
    }catch(RuntimeException e){}
    return entity;
  }
  
  public void insertOne(MongoAnnotation mca,String key,Object entity){
    try{
      Document doc=new Document(KEY,key).append(VALUE, entity);
      getCollection(mca).insertOne(doc);
    }catch(RuntimeException e){}
  }
  public void deleteOne(MongoAnnotation mca,String key){
    try{
      getCollection(mca).deleteOne(eq(KEY, key));
    }catch(RuntimeException e){}
  }
  
  public void replaceOne(MongoAnnotation mca,String key,Object entity){
    try{
      Document doc=new Document(KEY,key).append(VALUE, entity);
      getCollection(mca).replaceOne(eq(KEY, key), doc) ;
    }catch(RuntimeException e){}
  }
  
}
