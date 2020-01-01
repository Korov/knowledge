package com.korov.springboot.controller;

import com.korov.springboot.entity.UserEntity;
import com.korov.springboot.service.IUserService;
import com.korov.springboot.util.ResultVoUtil;
import com.korov.springboot.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/operation/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/test/{code}", method = RequestMethod.POST)
    public ResultVo test(@PathVariable(name = "code") Integer code, @RequestParam(name = "desc") String desc) {
        System.out.println("spring boot controller test");
        ResultVo resultVo = new ResultVo(code, desc, null);
        return resultVo;
    }

    @RequestMapping(value = "queryAll", method = RequestMethod.GET)
    public String queryAll(Model model) {
        List<UserEntity> entities = userService.selectAll();
        model.addAttribute("entities", entities);
        return "user/users";
    }

    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAss(Model model) {
        model.addAttribute("user", new UserEntity());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo addUser(@RequestBody String user) {
        // 直接将json信息打印出来
        ResultVo<UserEntity> dataVo = ResultVoUtil.getTableDataVo(user, UserEntity.class);
        int success = userService.insertAll(dataVo.getData());
        
        /*
        System.out.println(success);*/
        ResultVo resultVo = null;
        if (success > 0) {
            resultVo = ResultVo.getFail();
        } else {
            resultVo = ResultVo.getSuccess();
        }
        return resultVo;
    }
}
