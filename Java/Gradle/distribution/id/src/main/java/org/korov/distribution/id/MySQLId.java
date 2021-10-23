package org.korov.distribution.id;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class MySQLId {
    private static final long COUNT = 100000L;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/test?useSSL=true&serverTimezone=GMT";
        String user = "root";
        String password = "root123";

        Connection conn = DriverManager.getConnection(url, user, password);

        String sql = String.format("insert into table_id(mark) values ('%s')", "demo");
        long startTime = System.currentTimeMillis();
        long idIndex = 0L;
        while (idIndex <= COUNT) {
            try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idIndex = generatedKeys.getLong(1);
                        log.info("id:{}", idIndex);
                    }
                }
            }
        }
        log.info("id count:{}, cost{}", idIndex, System.currentTimeMillis() - startTime);
    }
}
