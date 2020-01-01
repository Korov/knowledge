package com.korov.springboot.BaseTest.MapperTest;

import com.korov.springboot.SpringbootApplicationTests;
import com.korov.springboot.entity.DepartmentEntity;
import com.korov.springboot.mapper.IDepartmentMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepartmentMapperTest extends SpringbootApplicationTests {

    @Autowired
    private IDepartmentMapper departmentMapper;

    @Test
    public void test(){
        List<DepartmentEntity> entities=departmentMapper.selectPage(-1,1);
        System.out.println("end");
    }
}
