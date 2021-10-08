package org.korov.flink.biquge.sink;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zhu.lei
 * @date 2021-10-08 17:49
 */
public class MysqlSink extends RichSinkFunction<String> {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    private HikariDataSource dataSource;

    public MysqlSink(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public void invoke(String sql, Context context) {
        if (dataSource == null) {
            return;
        }
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        dataSource = new HikariDataSource(config);
    }


    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
