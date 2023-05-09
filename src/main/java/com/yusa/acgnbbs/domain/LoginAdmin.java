package com.yusa.acgnbbs.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.yusa.acgnbbs.domain.entity.Admin;
import com.yusa.acgnbbs.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LoginAdmin implements UserDetails {


        private Admin admin;

        List<String> permissions;
        // false为手机号，true为邮箱
        @JSONField(serialize = false)
        List<SimpleGrantedAuthority> authorities = null;
        public List<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<String> permissions) {
            this.permissions = permissions;
        }
        public LoginAdmin() {
        }

        public LoginAdmin(Admin admin, List<String> permissions) {
            this.admin = admin;
            this.permissions = permissions;
        }

        public LoginAdmin(Admin admin, List<String> permissions, boolean loginMethod) {
            this.admin = admin;
            this.permissions = permissions;
        }

        public Admin getAdmin() {
            return admin;
        }

        public void setUser(Admin admin) {
            this.admin = admin;
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
            return admin.getPassword();
        }

        @Override
        public String getUsername() {
            return admin.getAdminName();
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
            return "LoginAdmin{" +
                    "admin=" + admin +
                    ", permissions=" + permissions +
                    '}';
        }
    }


