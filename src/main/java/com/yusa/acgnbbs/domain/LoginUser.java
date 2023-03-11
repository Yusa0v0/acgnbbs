package com.yusa.acgnbbs.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.yusa.acgnbbs.domain.entity.User;
//import com.yusa.acgnbbs.security.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoginUser implements UserDetails {


    private User user;

    List<String> permissions;
    // false为手机号，true为邮箱
    boolean loginMethod;
    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorities = null;
    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    public LoginUser() {
    }

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(User user, List<String> permissions, boolean loginMethod) {
        this.user = user;
        this.permissions = permissions;
        this.loginMethod = loginMethod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities!=null){
            return this.authorities;
        }
        authorities = new ArrayList<>();
        for (String permission : permissions){
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if(loginMethod)return user.getEmail();
        return user.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "user=" + user +
                ", permissions=" + permissions +
                '}';
    }
}
