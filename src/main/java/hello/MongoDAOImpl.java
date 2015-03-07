package hello;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDAOImpl {
	
	private static MongoDAOImpl STATICINSTANCE;

	// Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
	private static MongoClientURI uri;
	private static MongoClient client;
	private static 	DB db;

	// get a collection object to work with
	private static 	DBCollection userColl;
	private static 	DBCollection sensor1Coll;
	private static 	DBCollection sensor2Coll;
	private static 	DBCollection waterLevelColl;
	private static 	DBCollection motorStatusColl;
	private static Map<String, DBCollection> tableCollectionMap = new HashMap<String, DBCollection>();

	public MongoDAOImpl(){
		try {
			uri = new MongoClientURI("mongodb://pccoe:pccoe2015@ds027758.mongolab.com:27758/idis"); 
			client = new MongoClient(uri);
			db = client.getDB(uri.getDatabase());
			userColl = db.getCollection("users");
			sensor1Coll = db.getCollection("sensor1");
			sensor2Coll = db.getCollection("sensor2");
			waterLevelColl = db.getCollection("waterLevel");
			motorStatusColl = db.getCollection("motorStatus");
			tableCollectionMap.put("users", userColl);
			tableCollectionMap.put("sensor1", sensor1Coll);
			tableCollectionMap.put("sensor2", sensor2Coll);
			tableCollectionMap.put("waterLevel", waterLevelColl);
			tableCollectionMap.put("motorStatus", motorStatusColl);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static MongoDAOImpl getInstance(){
		if(STATICINSTANCE==null){
			STATICINSTANCE = new MongoDAOImpl();
		}
		return STATICINSTANCE;
	}

	public boolean login(String userName, String password){		
		BasicDBObject findQuery = new  BasicDBObject("uname", userName).append("password", password);

		DBCursor cursor = userColl.find(findQuery);
		try {
			if(cursor.hasNext()) {
				return true;
			}
		} finally {
			cursor.close();
		}
		return false;
	}
	

	public void insert(String tableName, Map<String, String> data){
		if(tableName!=null && !tableName.isEmpty() && data!=null && !data.isEmpty()){
			if(tableCollectionMap.containsKey(tableName)){
				DBCollection collection = tableCollectionMap.get(tableName);
				if(collection!=null){
					BasicDBObject basicDBObject = new BasicDBObject();
					for(String colName : data.keySet()){
						basicDBObject.put(colName, data.get(colName));
					}
					collection.insert(basicDBObject);
				}
			}
		}
	}
}
