package edu.mit.scansite.sql.statements;

import edu.mit.scansite.sql.provider.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStatement {
    protected Connection connection;
    protected PreparedStatement stmt;
    protected String stmtTemplate;
    protected StatementUtils utils;
    protected List<List<String>> parameters;
    protected final String SEPARATOR = ", ";


    public AbstractStatement() {
        utils = new StatementUtils();
        parameters = new ArrayList<>();
        try {
            connection = new ConnectionProvider().provideConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initTemplate();
    protected abstract void prepareStatement();

    public abstract List<?> process();

    public PreparedStatement getStmt() {
        return stmt;
    }
}
