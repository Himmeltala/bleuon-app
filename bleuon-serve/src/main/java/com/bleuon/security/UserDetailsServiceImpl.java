package com.bleuon.security;

import com.bleuon.constant.Constants;
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
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminMapper adminMapper;
    private final PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminModel exists = adminMapper.findByUsernameOrPhoneOrEmail(new AdminModel(username));

        if (Objects.isNull(exists)) {
            throw new UsernameNotFoundException("该用户不存在！");
        }

        List<String> authorities = permissionService.findAdminAuthorityList(null, exists.getUsername());
        return new CustomUserDetails(exists.getId(), exists.getUsername(), exists.getPassword(), Constants.USER_TYPE_ADMIN, authorities);
    }

}
