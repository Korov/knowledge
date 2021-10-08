package org.korov.flink.biquge.model;

import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-10-08 17:52
 */
@Data
public class ChapterInfo {
    private Long id;
    private Long bookId;
    private String chapterName;
    private String chapterContent;
}
