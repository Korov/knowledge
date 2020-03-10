package com.rolemanager.user.controller;

import com.rolemanager.commons.constant.Constant;
import com.rolemanager.commons.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserLoginController {

    @PostMapping(value = "/login")
    public ResultVo login(@NotNull @RequestBody Map<String, String> loginForm) {
        String userName = loginForm.get("userName");
        String password = loginForm.get("password");
        if (!(userName.equals("admin") && password.equals("admin"))) {
            return new ResultVo(Constant.OPERATION_FAIL, Constant.DESCRIPTION_FAIL, null);
        }
        ResultVo<Map<String, String>> resultVo = new ResultVo<>();
        Map<String, String> result = new HashMap<>();
        result.put("message", "Hello " + userName);
        result.put("token", "this is a token");

        resultVo.setCode(Constant.OPERATION_SUCCESS);
        resultVo.setDescription(Constant.DESCRIPTION_SUCCESS);
        resultVo.setData(result);
        return resultVo;
    }
}
