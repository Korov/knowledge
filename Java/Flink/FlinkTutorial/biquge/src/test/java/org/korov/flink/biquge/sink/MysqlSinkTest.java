package org.korov.flink.biquge.sink;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MysqlSinkTest {

    @Test
    public void test() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://nas.korov.org:4000/spider");
        config.setUsername("***");
        config.setPassword("***");

        HikariDataSource dataSource = new HikariDataSource(config);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        String sql = "select id from book_info where book_name='test' and author_name='test'";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            long id = resultSet.getLong("id");
            log.info("id:{}", id);
        }


        int id = 0;
        String insertSql = "insert into book_info(book_name, author_name, book_category, book_url, book_description) VALUES ('test','test','aaa','aaa','aaaa')";
        PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
        ps.execute();
        ResultSet resultSet1 = ps.getGeneratedKeys();
        if (resultSet1 != null && resultSet1.next()) {
            log.info("id:{}", resultSet1.getInt(1));
        }
    }

}