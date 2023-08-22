package com.bleuon.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsUtil {

    public static List<String> getAuths(UserDetails details) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority authority : details.getAuthorities()) {
            list.add(authority.getAuthority());
        }
        return list;
    }

}
