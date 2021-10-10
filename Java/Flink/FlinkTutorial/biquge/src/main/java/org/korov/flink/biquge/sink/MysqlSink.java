package org.korov.flink.biquge.sink;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.korov.flink.biquge.model.BookDto;
import org.korov.flink.biquge.model.BookInfo;
import org.korov.flink.biquge.model.ChapterInfo;

import java.sql.*;

/**
 * @author zhu.lei
 * @date 2021-10-08 17:49
 */
@Slf4j
public class MysqlSink extends RichSinkFunction<BookDto> {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    private HikariDataSource dataSource;
    private Connection connection;

    public MysqlSink(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public void invoke(BookDto bookDto, Context context) {
        if (dataSource == null) {
            return;
        }
        BookInfo bookInfo = bookDto.getBookInfo();
        ChapterInfo chapterInfo = bookDto.getChapterInfo();
        Long bookId = null;
        String queryIdSql = String.format("select id from book_info where book_name='%s' and author_name='%s'", bookInfo.getBookName(), bookInfo.getAuthorName());
        String insertSql = String.format("insert into book_info(book_name, author_name, book_category, book_url, book_description) VALUES ('%s','%s','%s','%s','%s')",
                bookInfo.getBookName(), bookInfo.getAuthorName(), bookInfo.getBookCategory(), bookInfo.getBookUrl(), bookInfo.getBookDescription());

        try (Statement bookQueryPs = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
             PreparedStatement bookInsertPs = connection.prepareStatement(insertSql, new String[]{"id"});
             ResultSet resultSet = bookQueryPs.executeQuery(queryIdSql)) {
            if (resultSet.next()) {
                bookId = resultSet.getLong("id");
            }
            log.info("book id:{}", bookId);
            if (bookId == null) {
                bookInsertPs.execute();
                ResultSet resultSet1 = bookInsertPs.getGeneratedKeys();
                if (resultSet1.next()) {
                    bookId = resultSet1.getLong(1);
                }
                resultSet1.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (bookId != null) {
            String chapterQuerySql = String.format("select count(*) as count from chapter_info where book_id = %s and chapter_name = '%s'", bookId, chapterInfo.getChapterName());
            String chapterInsertSql = String.format("insert into chapter_info(book_id, chapter_name, chapter_url, chapter_content) VALUES (%s, '%s', '%s', '%s')",
                    bookId, chapterInfo.getChapterName(), chapterInfo.getChapterUrl(), chapterInfo.getChapterContent());
            try (Statement chapterQueryPs = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                 PreparedStatement chapterInsertPs = connection.prepareStatement(chapterInsertSql, new String[]{"id"});
                 ResultSet chapterResultSet = chapterQueryPs.executeQuery(chapterQuerySql)) {
                if (chapterResultSet.next()) {
                    long count = chapterResultSet.getLong("count");
                    log.info("chapter countqq:{}", count);
                    if (count < 1) {
                        log.info("chapter insert start");
                        chapterInsertPs.execute();
                        ResultSet resultSet1 = chapterInsertPs.getGeneratedKeys();
                        log.info("chapter insert finish");
                        if (resultSet1.next()) {
                            Long chapterId = resultSet1.getLong(1);
                            log.info("chapter id:{}", chapterId);
                        }
                        resultSet1.close();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
        connection = dataSource.getConnection();
    }


    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
