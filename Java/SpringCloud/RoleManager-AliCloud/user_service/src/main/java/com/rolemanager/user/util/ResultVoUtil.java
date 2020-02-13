package com.rolemanager.user.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rolemanager.user.vo.ResultVo;

import java.lang.reflect.Type;

public class ResultVoUtil {
    public static <T> ResultVo<T> getTableDataVo(String vo, Class<T> clazz) {
        if (null == vo) {
            return null;
        }
        TypeReference<ResultVo<T>> typeReference = new TypeReference<ResultVo<T>>(clazz) {
        };
        final Type type = typeReference.getType();
        ResultVo<T> tableData = JSON.parseObject(vo, type);
        if (tableData != null && tableData.getData().size() == 0) {
            return tableData;
        }
        return null;
    }
}
