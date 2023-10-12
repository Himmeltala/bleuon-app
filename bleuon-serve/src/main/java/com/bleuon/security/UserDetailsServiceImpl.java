package com.bleuon.security;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.entity.Consumer;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.mapper.AuthorityMapper;
import com.bleuon.mapper.ConsumerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class UserDetailsServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements UserDetailsService {

    private final AuthorityMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Consumer exists = findUserByFiled(username);

        if (Objects.isNull(exists)) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }

        List<String> authorities = mapper.getAuthority(Map.of("username", exists.getUsername()));
        return new CustomUserDetails(exists.getId(), exists.getUsername(), exists.getPassword(), authorities);
    }

    private Consumer findUserByFiled(String field) {
        return query()
                .eq("username", field)
                .or()
                .eq("email", field)
                .or()
                .eq("phone", field)
                .one();
    }

}
