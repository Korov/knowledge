package com.rolemanager.user.vo;

import com.alibaba.fastjson.JSON;
import com.rolemanager.user.constant.Constant;

import java.util.List;

public class ResultVo<T> {

    private Integer code;
    private String description;
    private List<T> data;

    public ResultVo(Integer code, String description, List<T> data) {
        this.code = code;
        this.description = description;
        this.data = data;
    }

    public ResultVo() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void set(Integer code, String description, List<T> data) {
        this.code = code;
        this.description = description;
        this.data = data;
    }

    public static ResultVo getFail(){
        return new ResultVo(Constant.OPERATION_FAIL, Constant.DESCRIPTION_FAIL, null);
    }

    public static ResultVo getSuccess(){
        return new ResultVo(Constant.OPERATION_SUCCESS, Constant.DESCRIPTION_SUCCESS, null);
    }

    public ResultVo<T> getResultVo(String vo,
                                   @SuppressWarnings("rawtypes") com.alibaba.fastjson.TypeReference valueTypeRef) {
        if (null == vo || "" == vo) {
            return null;
        }
        @SuppressWarnings("unchecked")
        ResultVo<T> tableData = (ResultVo<T>) JSON.parseObject(vo, valueTypeRef);
        if (tableData != null && 0 != tableData.data.size()) {
            return tableData;
        }
        return null;
    }
}

