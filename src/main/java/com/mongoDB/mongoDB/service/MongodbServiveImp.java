package com.mongoDB.mongoDB.service;


import com.mongoDB.mongoDB.repositori.MongodbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongodbServiveImp implements MongodbService{
    @Autowired
    private MongodbRepository mongodbRepository;

}
