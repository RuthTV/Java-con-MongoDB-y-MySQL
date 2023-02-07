package com.mongoDB.mongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static java.util.Arrays.asList;


public class Connection {
    public static void main(final String[] args) {
//        Logger root = (Logger) LoggerFactory.getLogger("org.mongodb.driver");
//        // Available levels are: OFF, ERROR, WARN, INFO, DEBUG, TRACE, ALL
//        root.setLevel(Level.WARN);
        ConnectionString connectionString = new ConnectionString("mongodb+srv://myAtlasDBUser:myatlas-001@myatlasclusteredu.u63rhzi." +
                "mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).serverApi(
                        ServerApi.builder()
                                .version(ServerApiVersion.V1)
                                .build())
                .build();

//        String connectionString = System.getenv("MONGODB_URI");
        try (MongoClient client = MongoClients.create(settings)) {
            List<Document> databases = client.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));

            //CREATING A DOCUMENT
            Document sampleDocument = new Document("_id", new ObjectId())
                    .append("account_id", "MDB255054629")
                    .append("account_holder", "Mai Kalange")
                    .append("account_type", "savings")
                    .append("balance", 2340)
                    .append("last_updated", new Date());

            //CRUD
            Crud crud = new Crud(client);
            crud.insertOne(sampleDocument);

            //FIND ONE
            Bson documentToFind = and(gte("balance", 1000), eq("account_type", "checking"));
            crud.findOneDocument(documentToFind);
            Bson documentsToFind = and(gte("balance", 1000), eq("account_type", "checking"));
            crud.findDocuments(documentsToFind);
            Bson query = Filters.eq("account_id", "MDB333829449"); //TODO: define the query variable
            Bson update = Updates.combine(Updates.set("account_status", "active"), Updates.inc("balance", 100)); //TODO: define the update variable
            crud.updateOneDocument(query, update);
            Document updateManyFilter = new Document().append("account_type", "savings");
            Bson updatesForMany = Updates.combine(Updates.set("minimum_balance", 100));
            crud.updateManyDocuments(updateManyFilter, updatesForMany);
            Bson documentToDelete = eq("account_holder", "Jacob Thompson");
            crud.deleteOneDocument(documentToDelete);
            Bson documentsToDelete = lt("balance", 500);
            crud.deleteManyDocuments(documentsToDelete);

            //Transaction
            Transaction txn = new Transaction(client);
            String senderAccountFilter = "MDB310054629";
            String receiverAccountFilter = "MDB643731035";
            double transferAmount = 200;
            txn.transferMoney(senderAccountFilter, transferAmount, receiverAccountFilter);
        }
    }
}