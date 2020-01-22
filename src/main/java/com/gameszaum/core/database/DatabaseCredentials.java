package com.gameszaum.core.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class DatabaseCredentials {

    private String host, db, port, user, pass;

}
