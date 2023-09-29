package com.bleuon.security;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.entity.User;
import com.bleuon.mapper.AuthMapper;
import com.bleuon.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户名密码登录 Service 实现类。
 * <p>
 * 当用户选择用户名、邮箱、手机号 + 密码的方式登录时处理。
 * <p>
 * 同时也是替换 InMemoryUserDetailManager，即通过数据库查询用户信息，得到的结果封装为 UserDetails 对象。
 *
 * @author zheng
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService {

    private final AuthMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByFiled(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }

        List<String> authorities = mapper.getAuthority(Map.of("username", user.getUsername()));
        CustomUserDetails details = new CustomUserDetails(user.getUsername(), user.getPassword(), authorities);
        details.setId(user.getId());

        return details;
    }

    private User findUserByFiled(String field) {
        return query()
                .eq("username", field)
                .or()
                .eq("email", field)
                .or()
                .eq("phone", field)
                .one();
    }

}
