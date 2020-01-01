package com.korov.springboot.controller;

import com.korov.springboot.entity.DepartmentEntity;
import com.korov.springboot.service.IDepartmentService;
import com.korov.springboot.util.ResultVoUtil;
import com.korov.springboot.constant.Constant;
import com.korov.springboot.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "/operation/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo addDepartments(@RequestBody String departments) {

        ResultVo<DepartmentEntity> resultVo = ResultVoUtil.getTableDataVo(departments, DepartmentEntity.class);
        if (null == resultVo || null == resultVo.getData()) {
            return ResultVo.getFail();
        }

        ResultVo result = new ResultVo();

        int count = departmentService.insertAll(resultVo.getData());
        result.set(Constant.OPERATION_SUCCESS, "共插入了" + count + "条数据", null);
        return result;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo<DepartmentEntity> queryDepartments() {
        ResultVo<DepartmentEntity> resultVo = new ResultVo<>();
        return resultVo;

    }
}
