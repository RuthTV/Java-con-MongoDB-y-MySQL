package com.mongoDB.mongoDB.controller;

import com.mongoDB.mongoDB.model.Persona;

public interface MongodbController {
    Persona findById(Integer id);
}
