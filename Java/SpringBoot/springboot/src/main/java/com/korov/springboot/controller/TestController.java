package com.korov.springboot.controller;

import com.korov.springboot.entity.TestEntity;
import com.korov.springboot.service.ITestService;
import com.korov.springboot.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private ITestService testService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test1(Map<String, String> map) {
        return "index_ready";
    }

    @RequestMapping(value = "/errorsss", method = RequestMethod.GET)
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Map<String, String> map) {
        map.put("nameKey", "Korov9");
        System.out.println(map.get("nameKey"));
        return "hello";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        List<TestEntity> entities = testService.queryAllTest();
        model.addAttribute("entities", entities);
        return "welcome";
    }

    @RequestMapping(value = "/test/{code}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test(@PathVariable(name = "code") Integer code, @RequestParam(name = "desc") String desc) {
        System.out.println("spring boot controller test");
        //ResultVo resultVo = new ResultVo(code, desc, testService.printEntity());
        ResultVo resultVo = new ResultVo(code, desc, null);
        return resultVo;
    }

    @RequestMapping(value = "/test/all/{code}", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test(@PathVariable(name = "code") Integer code, @RequestParam(name = "desc") String desc,
                         @RequestBody TestEntity data) {
        System.out.println("spring boot controller test");
        //ResultVo resultVo = new ResultVo(code, desc, data);
        ResultVo resultVo = new ResultVo(code, desc, null);
        return resultVo;
    }

    @RequestMapping(value = "/test/body", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test(@RequestBody TestEntity data) {
        System.out.println("spring boot controller test");
        //ResultVo resultVo = new ResultVo(1, "sss", data);
        ResultVo resultVo = new ResultVo(1, "sss", null);
        return resultVo;
    }
}
