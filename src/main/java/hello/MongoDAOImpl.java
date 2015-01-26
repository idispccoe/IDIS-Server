package hello;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDAOImpl {

	// Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
	private static MongoClientURI uri;
	private static MongoClient client;
	private static 	DB db;

	// get a collection object to work with
	private static 	DBCollection coll;

	public MongoDAOImpl(){
		try {
			uri = new MongoClientURI("mongodb://pccoe:pccoe2015@ds027758.mongolab.com:27758/idis"); 
			client = new MongoClient(uri);
			db = client.getDB(uri.getDatabase());
			coll = db.getCollection("users");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean login(String userName, String password){		
		BasicDBObject findQuery = new  BasicDBObject("uname", userName).append("password", password);

		DBCursor cursor = coll.find(findQuery);
		try {
			if(cursor.hasNext()) {
				return true;
			}
		} finally {
			cursor.close();
		}
		return false;
	}

}
