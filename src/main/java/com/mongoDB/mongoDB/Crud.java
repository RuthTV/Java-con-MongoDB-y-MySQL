package com.mongoDB.mongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.BsonValue;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Crud {
    private final MongoCollection<Document> collection;

    public Crud(MongoClient client) {
        this.collection = client.getDatabase("sample_analytics").getCollection("accounts");
    }

    public void insertOne(Document doc) {
        InsertOneResult result = collection.insertOne(doc);
        BsonValue id = result.getInsertedId();
        System.out.println(id);
    }

    public void insertMany(List<Document> docs) {
        InsertManyResult result = collection.insertMany(docs);
        result.getInsertedIds().forEach((x,y)-> System.out.println(y.asObjectId()));
    }
    public void findOneDocument(Bson query) {
        Document doc = collection.find(query).first();
        System.out.println(doc != null ? doc.toJson() : null);
    }

    public void findDocuments(Bson query) {
        try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    public void updateOneDocument(Bson query, Bson update) {
        collection.updateOne(query, update);
    }

    public void updateManyDocuments(Bson query, Bson update) {
        collection.updateMany(query, update);
    }

    public void deleteOneDocument(Bson query) {
        collection.deleteOne(query);
    }
    public void deleteManyDocuments(Bson query) {
        collection.deleteMany(query);
    }
}
