package com.insanity.io;

import java.sql.*;

public class MySQL {

    private String host;
    private String username;
    private String password;
    private Connection connection;

    public MySQL(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.connection = this.createConnection();
    }

    private Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host, this.username, this.password);
        } catch (Exception e) {
            this.connection = null;
            System.out.println("MySQL - Error connecting to database");
        }
        return this.connection;
    }

    protected Connection getConnection() {
        if (this.connection == null) this.connection = createConnection();
        return connection;
    }

    public ResultSet query(PreparedStatement preparedStatement) {
        ResultSet result = null;
        try {
            if (preparedStatement == null) throw new SQLException();
            result = preparedStatement.executeQuery();
        } catch (Exception e) {
            System.out.println("MySQL - Error executing prepared statement (query)");
        }
        return result;
    }

    public boolean execute(String sql) {
        return execute(prepareStatement(sql));
    }

    public boolean execute(PreparedStatement preparedStatement) {
        Boolean result = false;
        try {
            if (preparedStatement == null) throw new SQLException();
            result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("MySQL - Error executing prepared statement (execute)");
        }
        return result;
    }

    public PreparedStatement prepareStatement(String query) {
        PreparedStatement preparedStatement = null;
        if (getConnection() != null) {
            try {
                preparedStatement = this.connection.prepareStatement(query);
            } catch (Exception e) {
                System.out.println("Error preparing query: " + query);
            }
        }
        return preparedStatement;
    }

    public PreparedStatement prepareConcurrent(String query) {
        PreparedStatement preparedStatement = null;
        if (getConnection() != null) {
            try {
                preparedStatement = this.connection.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            } catch (Exception e) {
                System.out.println("Error preparing concurrent query: " + query);
            }
        }
        return preparedStatement;
    }
}