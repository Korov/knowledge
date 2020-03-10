package com.rolemanager.commons.vo;

import com.alibaba.fastjson.JSON;
import com.rolemanager.commons.constant.Constant;
import com.rolemanager.commons.util.StringUtil;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultVo<T> {

    private Integer code;
    private String description;
    private T data;

    public ResultVo() {
        super();
    }

    public ResultVo(Integer code, String description, T data) {
        set(code, description, data);
    }

    public void set(Integer code, String description, T data) {
        this.setCode(code);
        this.setDescription(description);
        this.setData(data);
    }

    public static ResultVo getFail() {
        return new ResultVo(Constant.OPERATION_FAIL, Constant.DESCRIPTION_FAIL, null);
    }

    public static ResultVo getSuccess() {
        return new ResultVo(Constant.OPERATION_SUCCESS, Constant.DESCRIPTION_SUCCESS, null);
    }

    public static <T> ResultVo<T> getResultVo(String vo) {
        if (StringUtil.isEmpty(vo)) {
            return getFail();
        }
        ResultVo<T> tableData = JSON.parseObject(vo, ResultVo.class);
        if (tableData != null) {
            return tableData;
        }
        return getFail();
    }
}

