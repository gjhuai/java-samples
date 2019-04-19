package com.mechanitis.mongodb;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MongoClientTest {
        MongoClient mongoClient = null;

    @Before
    public void init(){
        mongoClient = new MongoClient();
    }

    @After
    public void close(){
        if (mongoClient!=null) mongoClient.close();
    }

    @Test
    public void createIndex() throws Exception {
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        // creates an ascending index on the i field:
        // For an ascending index type, specify 1 for <type>.
        // For a descending index type, specify -1 for <type>.
        collection.createIndex(new Document("i", 1));
    }

    @Test
    public void findAll() throws Exception {
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        // Find All Documents in a Collection
        for (Document cur : collection.find()) {
            System.out.println(cur.toJson());
        }

//        MongoCursor<Document> cursor = collection.find().iterator();
//        try {
//            while (cursor.hasNext()) {
//                System.out.println(cursor.next().toJson());
//            }
//        } finally {
//            cursor.close();
//        }

    }

    @Test
    public void findFirst() throws Exception {
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        // Find the First Document in a Collection
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }


    @Test
    public void findByFilter() throws Exception {
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        // Specify a Query Filter
        Document myDoc = collection.find(eq("i", 71)).first();
        System.out.println(myDoc.toJson());
    }

    @Test
    public void findByMatch() throws Exception {
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        // Get All Documents That Match a Filter
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };

        collection.find(gt("i", 50)).forEach(printBlock);

        collection.find(and(gt("i", 50), lte("i", 100))).forEach(printBlock);
    }

    @Test
    public void insertData() throws UnknownHostException {
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");
        
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
    }

    @Test
    public void updateOne() {
        // Update a Single Document
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        // updates the first document that meets the filter i equals 10 and sets the value of i to 110:
        collection.updateOne(eq("i", 10), new Document("$set", new Document("i", 110)));
    }

    @Test
    public void updateMany() {
        // Update a Single Document
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        // increments the value of i by 100 for all documents where =i is less than 100:
        UpdateResult updateResult = collection.updateMany(lt("i", 100), Updates.inc("i", 100));
        System.out.println(updateResult.getModifiedCount());
    }

    @Test
    public void deleteOne() {
        // Delete a Single Document That Match a Filter
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        collection.deleteOne(eq("i", 110));
    }

    @Test
    public void deleteAll() {
        // Delete All Documents That Match a Filter
        MongoCollection<Document> collection = getMongoCollection("SomeDatabase", "coll");

        DeleteResult deleteResult = collection.deleteMany(gte("i", 100));
        System.out.println(deleteResult.getDeletedCount());
    }

    private MongoCollection<Document> getMongoCollection(String dbName, String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(dbName);
        return database.getCollection(collectionName);
    }
}
