package com.gameszaum.core.exception.model;

public class ModelAlreadyExist extends Exception {

    public ModelAlreadyExist(){
        super("This model already exists.");
    }

}
