package com.rolemanager.commons.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {
    private int total;
    private int pageNum;
    private int pageSize;
    private List<T> contets;
}
