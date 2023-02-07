package com.mongoDB.mongoDB.repositori;

import com.mongoDB.mongoDB.model.Persona;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongodbRepository extends MongoRepository<Persona, Integer> {
//    MongoRepository tiene dentro el JPA, por lo que tienes todos findByID, findAll...
}
