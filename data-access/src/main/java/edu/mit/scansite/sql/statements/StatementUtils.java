package edu.mit.scansite.sql.statements;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StatementUtils {
    private Logger logger = LoggerFactory.getLogger(PreparedStatement.class);

    public PreparedStatement getStatement(Connection connection, String stmtTemplate, List<List<String>> parameters) {

        if (!StringUtils.isBlank(stmtTemplate)) {
            try {
                PreparedStatement stmt = connection.prepareStatement(stmtTemplate);
                if (parameters != null) {
                    for (int i = 0; i < parameters.size(); i++) {
                        // VALUE - TYPE
                        if (parameters.get(i).size() == 2) {
                            stmt = setParameter(stmt, i + 1, parameters.get(i));
                        }
                    }
                }

                return stmt;
            } catch (SQLException e) {
                logger.error("Could not create prepared statement!", e);
            }
        }

        return null;
    }


    private PreparedStatement setParameter(PreparedStatement stmt, int position, List<String> data) throws SQLException {
        final String value = data.get(0);
        final String type = data.get(1);
        if (type.equalsIgnoreCase("String")) {
            stmt.setString(position, value);
        } else if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("Integer")) {
            stmt.setInt(position, Integer.parseInt(value));
        } else if (type.equalsIgnoreCase("Double")) {
            stmt.setDouble(position, Double.parseDouble(value));
        } else if (type.equalsIgnoreCase("Bool") || type.equalsIgnoreCase("Boolean")) {
            stmt.setBoolean(position, Boolean.parseBoolean(value));
        }
        return stmt;
    }


    public String addCondition(String statementConditions, String operator, String condition) {
        if (StringUtils.isBlank(statementConditions)) {
            return condition;
        } else {
            return String.format("%s %s %s", statementConditions, operator, condition);
        }
    }
}
