package org.korov.springboot_security.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.korov.springboot_security.SpringbootSecurityApplication;
import org.korov.springboot_security.SpringbootSecurityApplicationTests;
import org.korov.springboot_security.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhu.lei
 * @date 2021-09-23 15:43
 */
@Slf4j
class SysUserMapperTest extends SpringbootSecurityApplicationTests {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void queryAll() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        List<SysUser> users = sysUserMapper.selectList(queryWrapper);
        log.info("users {}", users);
    }

}