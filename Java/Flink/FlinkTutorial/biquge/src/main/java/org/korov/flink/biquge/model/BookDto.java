package org.korov.flink.biquge.model;

import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-10-08 17:53
 */
@Data
public class BookDto {
    private BookInfo bookInfo;
    private ChapterInfo chapterInfo;
}
