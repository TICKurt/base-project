package com.example.business.service.impl;

import com.example.auth.domain.vo.LoginUserVO;
import com.example.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public LoginUserVO login(String username, String password) {
        // TODO: 实际项目中应该从数据库查询用户信息
        // 这里为了演示，使用硬编码的用户信息
        if ("admin".equals(username) && "123456".equals(password)) {
            LoginUserVO loginUser = new LoginUserVO();
            loginUser.setUserId("1");
            loginUser.setUsername(username);
            loginUser.setNickname("管理员");
            loginUser.setUserType(0); // 0-超级管理员
            loginUser.setPermissions(new HashSet<>(Arrays.asList(
                "system:user:view",
                "system:user:edit",
                "system:user:add",
                "system:user:delete"
            )));
            loginUser.setRoleCodes(Arrays.asList("admin", "system"));
            return loginUser;
        }
        
        throw new RuntimeException("用户名或密码错误");
    }
} 