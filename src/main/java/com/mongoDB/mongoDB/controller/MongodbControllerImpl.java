package com.mongoDB.mongoDB.controller;

import com.mongoDB.mongoDB.model.Persona;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class MongodbControllerImpl implements MongodbController {
    @Override
    public Persona findById(Integer id) {
        return null;
    }
}
