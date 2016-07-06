package p.minn.common.mongo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public abstract class MongoDaoTemplate {
  
  @Autowired
  private MongoClient mongoClient;
  
  private MongoDatabase mongoDatabase;
  
  private String databaseName;
  
  public MongoCollection<Document> getCollection(String cname){
     return mongoDatabase.getCollection(cname);
  }
  public String getDatabaseName() {
    
    return databaseName;
  }
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
    if(mongoDatabase==null){
      mongoDatabase=mongoClient.getDatabase(databaseName);
    }
  }
  
  public void add(String collectionName,Document doc){
    getCollection(collectionName).insertOne(doc);
  }
  
  public abstract void update(Document doc);
  
  public abstract void delete(Document doc);
  
}
