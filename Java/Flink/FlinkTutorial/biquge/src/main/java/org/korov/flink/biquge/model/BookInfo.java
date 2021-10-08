package org.korov.flink.biquge.model;

import lombok.Data;

/**
 * @author zhu.lei
 * @date 2021-10-08 17:51
 */
@Data
public class BookInfo {
    private Long id;
    private String bookName;
    private String authorName;
    private String bookCategory;
    private String bookUrl;
    private String bookDescription;
}
