package org.korov.flink.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Biquge {
    private ObjectId id;
    private String bookAuthor;
    private String bookCategory;
    private String bookDescription;
    private String bookName;
    private List<String> chapterContent;
    private String chapterName;
    private String chapterUrl;
    private String dateTime;

    public Biquge() {
    }

    public Biquge(Document document) {
        this.id = document.getObjectId("_id");
        this.bookAuthor = document.getString("book_author");
        this.bookCategory = document.getString("book_category");
        this.bookDescription = document.getString("book_description");
        this.bookName = document.getString("book_name");
        this.chapterContent = document.getList("chapter_content", String.class);
        this.chapterName = document.getString("chapter_name");
        this.chapterUrl = document.getString("chapter_url");
        this.dateTime = document.getString("date_time");
    }
}
