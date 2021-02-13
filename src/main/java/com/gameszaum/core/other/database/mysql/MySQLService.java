package com.gameszaum.core.other.database.mysql;


import java.sql.Connection;

public interface MySQLService {

    void createConnection();

    void closeConnection();

    Connection getConnection();

    void executeQuery(String sql);

    boolean contains(String table, String where, String whereResult);

    void update(String table, String where, String whereResult, String update, Object updateResult);

    void delete(String table, String where, String whereResult);

    String getString(String table, String where, String whereResult, String column);

    Integer getInteger(String table, String where, String whereResult, String column);

    Long getLong(String table, String where, String whereResult, String column);

}
