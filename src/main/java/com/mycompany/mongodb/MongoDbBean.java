package com.mycompany.mongodb;


import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoDbBean {
    private final MongoDbFactory mongo;

    @Autowired
    public MongoDbBean(MongoDbFactory mongo) {
        this.mongo = mongo;
    }

    // ...
    public void example() {
        MongoDatabase db = mongo.getDb();
// ...
    }
}
