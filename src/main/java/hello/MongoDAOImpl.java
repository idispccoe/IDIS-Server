package hello;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import scala.annotation.meta.getter;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
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
	private static 	DBCollection waterFlowMeterColl;
	private static 	DBCollection motorStatusColl;
	private static 	DBCollection activityLogColl;
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
			waterFlowMeterColl = db.getCollection("waterFlowMeter");
			motorStatusColl = db.getCollection("motorStatus");
			activityLogColl = db.getCollection("ActivityLog");
			tableCollectionMap.put("users", userColl);
			tableCollectionMap.put("sensor1", sensor1Coll);
			tableCollectionMap.put("sensor2", sensor2Coll);
			tableCollectionMap.put("waterLevel", waterLevelColl);
			tableCollectionMap.put("motorStatus", motorStatusColl);
			tableCollectionMap.put("waterFlowMeter", waterFlowMeterColl);
			tableCollectionMap.put("ActivityLog", activityLogColl);
			
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
	
	public void insertData(String tableName, JSONObject jo){
		if(tableName!=null && !tableName.isEmpty() && jo!=null && jo.length()>0){
			if(tableCollectionMap.containsKey(tableName)){
				DBCollection collection = tableCollectionMap.get(tableName);
				if(collection!=null){
					BasicDBObject basicDBObject = (BasicDBObject)com.mongodb.util.JSON.parse(jo.toString());
					collection.insert(basicDBObject);
				}
			}
		}
	}
	
	public String findMonthlyWaterFlownData(String monthNo, String year){
		BasicDBObject findQuery = new  BasicDBObject();
		findQuery.put("DateTime",  java.util.regex.Pattern.compile(monthNo+"/[0-9]*/"+year+" *"));//3/1/15 8:15
		DBCursor cursor = activityLogColl.find(findQuery);
		Double totalWaterFlown = new Double(0);
		try {
			if(cursor.hasNext()) {
				while(cursor.hasNext()){
					DBObject dbObject = cursor.next();
					if(dbObject!=null && dbObject.get("TotalWaterFlown")!=null){
						System.out.println("Date: "+dbObject.get("DateTime").toString()+" WaterFlown: "+dbObject.get("TotalWaterFlown").toString());
						totalWaterFlown += Double.valueOf(dbObject.get("TotalWaterFlown").toString());
					}
				}
			}
		} finally {
			cursor.close();
		}
		return totalWaterFlown.toString();
	}
	
	
}
