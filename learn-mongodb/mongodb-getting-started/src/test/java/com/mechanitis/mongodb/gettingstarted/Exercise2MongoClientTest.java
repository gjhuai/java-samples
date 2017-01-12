package com.mechanitis.mongodb.gettingstarted;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Exercise2MongoClientTest {
	
    @Test
    public void shouldGetADatabaseFromTheMongoClient() throws Exception {
        // Given
        // TODO any setup

        // When
        //TODO get the database from the client
        DB database = null;

        // Then
        assertThat(database, is(notNullValue()));
    }

    @Test
    public void shouldGetACollectionFromTheDatabase() throws Exception {
        // Given
        MongoClient mongoClient = new MongoClient();

        // Then
        MongoDatabase database = mongoClient.getDatabase("SomeDatabase");
        MongoCollection<Document> collection = database.getCollection("coll");

        // Find the First Document in a Collection
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());

        // Find All Documents in a Collection
//        MongoCursor<Document> cursor = collection.find().iterator();
//        try {
//            while (cursor.hasNext()) {
//                System.out.println(cursor.next().toJson());
//            }
//        } finally {
//            cursor.close();
//        }

//        for (Document cur : collection.find()) {
//            System.out.println(cur.toJson());
//        }

        // Specify a Query Filter
        myDoc = collection.find(Filters.eq("i", 71)).first();
        System.out.println(myDoc.toJson());
        
        mongoClient.close();
    }

    @Test
    public void shouldNotBeAbleToUseMongoClientAfterItHasBeenClosed() throws UnknownHostException {
        // Given
        MongoClient mongoClient = new MongoClient();

        // Then
        MongoDatabase database = mongoClient.getDatabase("SomeDatabase");
        MongoCollection<Document> collection = database.getCollection("coll");
//        .insertOne(new BasicDBObject("field", "value"));
        
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);
        
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }
        collection.insertMany(documents);
        
        System.out.println(collection.count());
        
        mongoClient.close();
    }

}
