package com.korov.springboot.security;

import com.korov.springboot.entity.entityfull.Role;
import com.korov.springboot.entity.entityfull.SysUser;
import com.korov.springboot.exception.TestException;
import com.korov.springboot.service.IAssistService;
import com.korov.springboot.service.entityfullservice.IRoleFullService;
import com.korov.springboot.service.entityfullservice.IUserFullSevice;
import com.korov.springboot.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserFullSevice userFullSevice;

    @Autowired
    private IRoleFullService roleFullService;

    @Autowired
    private IAssistService assistService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userFullSevice.selectByUsername(username);
        if (null == user) {
            throw new TestException("用户不存在");
        }
        List<Role> roles = new ArrayList<>();

        List<Map<String, Integer>> userRoles = assistService.selectUserRoleByUserId(user.getUserEntity().getUserId());

        for (Map<String, Integer> map : userRoles) {
            roles.add(roleFullService.getRole(map.get(Constant.ROLE_ID)));
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleEntity().getRoleKey()));
        }
        return new User(user.getUserEntity().getLoginName(), user.getUserEntity().getLoginPassword(), authorities);
    }
}
