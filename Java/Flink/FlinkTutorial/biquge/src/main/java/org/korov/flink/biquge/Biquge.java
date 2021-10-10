package org.korov.flink.biquge;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.bson.Document;
import org.korov.flink.biquge.model.BookDto;
import org.korov.flink.biquge.model.BookInfo;
import org.korov.flink.biquge.model.ChapterInfo;
import org.korov.flink.biquge.sink.MysqlSink;
import org.korov.flink.common.source.MongoSource;

import java.awt.print.Book;

@Slf4j
public class Biquge {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.setParallelism(2);
        env.getConfig().setUseSnapshotCompression(true);
        env.getCheckpointConfig().setCheckpointInterval(5 * 60000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(60000);
        env.getCheckpointConfig().setCheckpointTimeout(5 * 60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableExternalizedCheckpoints(
                CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

        MongoSource mongoSource = new MongoSource("nas.korov.org", 27017, "spider", "book_info");

        DataStream<Document> stream = env.addSource(mongoSource, "mongo-source", null);
        DataStream<BookDto> outputStreamOperator = stream.flatMap(new FlatMapFunction<Document, BookDto>() {
            @Override
            public void flatMap(Document document, Collector<BookDto> out) throws Exception {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setBookName(document.getString("book_name"));
                bookInfo.setAuthorName(document.getString("book_author"));
                bookInfo.setBookCategory(document.getString("book_category"));
                bookInfo.setBookDescription(document.getString("book_description"));
                bookInfo.setBookUrl(document.getString("book_url"));

                ChapterInfo chapterInfo = new ChapterInfo();
                chapterInfo.setChapterName(document.getString("chapter_name"));
                chapterInfo.setChapterUrl(document.getString("chapter_url"));
                chapterInfo.setChapterContent(Joiner.on(System.lineSeparator()).join(document.getList("chapter_content", String.class)));
                BookDto bookDto = new BookDto();
                bookDto.setBookInfo(bookInfo);
                bookDto.setChapterInfo(chapterInfo);
                out.collect(bookDto);
            }
        });

        MysqlSink sink = new MysqlSink("jdbc:mysql://nas.korov.org:4000/spider", "root", "zl7636012086");
        outputStreamOperator.addSink(sink);
        env.execute();
    }
}
