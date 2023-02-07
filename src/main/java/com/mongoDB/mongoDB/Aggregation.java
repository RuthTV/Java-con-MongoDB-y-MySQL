package com.mongoDB.mongoDB;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;
import org.bson.Document;


import java.util.Arrays;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.orderBy;
import static java.util.Arrays.asList;


public class Aggregation {

    private MongoCollection<Document> collection;

    public Aggregation() {
    }

    public Aggregation(MongoClient client) {
        this.collection = client.getDatabase("bank").getCollection("accounts");
    }

    public void matchStage(MongoCollection<Document> accounts){
        Bson matchStage = Aggregates.match(Filters.eq("account_id", "MDB310054629"));
        System.out.println("Display aggregation results");
        accounts.aggregate(asList(matchStage)).forEach(document->System.out.print(document.toJson()));
    }

    public void matchAndGroupStages(MongoCollection<Document> accounts){
        Bson matchStage = Aggregates.match(Filters.eq("account_id", "MDB310054629"));
        Bson matchStage2 = Aggregates.match(lt("balance", 1000));
        Bson groupStage = Aggregates.group("$account_type", sum("total_balance", "$balance"), avg("average_balance", "$balance"));
        System.out.println("Display aggregation results");
        accounts.aggregate(asList(matchStage, groupStage)).forEach(document->System.out.print(document.toJson()));
    }

    public void matchSortAndProjectStages(MongoCollection<Document> accounts){
        Bson matchStage = Aggregates.match(Filters.and(gt("balance", 1500),
                Filters.eq("account_type", "checking")));
        Bson sortStage = Aggregates.sort(orderBy(descending("balance")));
        Bson projectStage = Aggregates.project(fields(include("account_id", "account_type", "balance"),
                Projections.computed("euro_balance", new Document("$divide", asList("$balance", 1.20F))), excludeId()));
        System.out.println("Display aggregation results");
        accounts.aggregate(asList(matchStage,sortStage, projectStage)).forEach(document -> System.out.print(document.toJson()));
    }

    public void showGBPBalancesForCheckingAccounts(MongoCollection<Document> accounts) {
        Bson matchStage = Aggregates.match(Filters.and(Filters.eq("account_type", "checking"), Filters.gt("balance", 1500)));
        Bson sortStage = Aggregates.sort(orderBy(descending("balance")));
        Bson projectStage = Aggregates.project(fields(include("account_id", "account_type", "balance"), excludeId()));
        System.out.println("Display aggregation results");
        accounts.aggregate(asList(matchStage,sortStage, projectStage)).forEach(document -> System.out.print(document.toJson()));
    }
}
