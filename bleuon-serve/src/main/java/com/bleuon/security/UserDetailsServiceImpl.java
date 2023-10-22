package com.bleuon.security;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bleuon.constant.KeyVals;
import com.bleuon.entity.AdminModel;
import com.bleuon.entity.CustomUserDetails;
import com.bleuon.mapper.AdminMapper;
import com.bleuon.service.impl.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class UserDetailsServiceImpl extends ServiceImpl<AdminMapper, AdminModel> implements UserDetailsService {

    private final PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminModel exists = findAdminByUname(username);

        if (Objects.isNull(exists)) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }

        List<String> authorities = permissionService.findAdminAuthorityList(null, exists.getUsername());
        return new CustomUserDetails(exists.getId(), exists.getUsername(), exists.getPassword(), KeyVals.USER_TYPE_ADMIN, authorities);
    }

    private AdminModel findAdminByUname(String uname) {
        return query().eq("username", uname).one();
    }

}
