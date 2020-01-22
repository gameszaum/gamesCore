package com.gameszaum.core.exception.model;

public class ModelDoesntExist extends Exception {

    public ModelDoesntExist() {
        super("This model doesn't exists.");
    }

}
