package com.gameszaum.core.other.database.mysql.impl;

import com.gameszaum.core.other.database.DatabaseCredentials;
import com.gameszaum.core.other.database.mysql.MySQL;

import java.sql.*;

public class MySQLImpl implements MySQL {

    private String prefix;
    private DatabaseCredentials credentials;
    private Connection connection;

    public MySQLImpl(String prefix, DatabaseCredentials credentials) {
        this.prefix = prefix;
        this.credentials = credentials;
    }

    @Override
    public void createConnection() {
        long ms = System.currentTimeMillis();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("JDBC:mysql://" + credentials.getHost() + ":"
                    + credentials.getPort() + "/" + credentials.getDb() + "?characterEncoding=latin1&useConfigs=maxPerformance", credentials.getUser(), credentials.getPass());
            System.out.println("[" + prefix + "] MySQL connected. (" + (System.currentTimeMillis() - ms) + "ms)");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
            System.out.println("[" + prefix + "] MySQL closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        long ms = System.currentTimeMillis();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM DUAL;");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return connection;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("[" + prefix + "] Connection is down or terminated. Reconnecting...");

            try {
                connection = DriverManager.getConnection("JDBC:mysql://" + credentials.getHost() + ":"
                        + credentials.getPort() + "/" + credentials.getDb() + "?characterEncoding=latin1&useConfigs=maxPerformance", credentials.getUser(), credentials.getPass());
                System.out.println("[" + prefix + "] MySQL reconnected. (" + (System.currentTimeMillis() - ms) + "ms)");
                return connection;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return connection;
    }

    @Override
    public void executeQuery(String sql) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean contains(String table, String where, String whereResult) {
        PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return false;

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void update(String table, String where, String whereResult, String update, Object updateResult) {
        PreparedStatement ps;
        try {
            String query = "UPDATE `" + table + "` SET `" + update + "` = '" + updateResult + "' WHERE `" + where + "` = '" + whereResult + "';";

            ps = getConnection().prepareStatement(query);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String table, String where, String whereResult) {
        PreparedStatement ps;
        try {
            String query = "DELETE FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';";
            ps = getConnection().prepareStatement(query);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getString(String table, String where, String whereResult, String column) {
        PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
            ResultSet rs = ps.executeQuery();
            rs.next();
            String s = rs.getString(column);
            rs.close();
            ps.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Integer getInteger(String table, String where, String whereResult, String column) {
        PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
            ResultSet rs = ps.executeQuery();
            rs.next();
            int i = rs.getInt(column);
            rs.close();
            ps.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Long getLong(String table, String where, String whereResult, String column) {
        PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = '" + whereResult + "';");
            ResultSet rs = ps.executeQuery();
            rs.next();
            long l = rs.getLong(column);
            rs.close();
            ps.close();
            return l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}