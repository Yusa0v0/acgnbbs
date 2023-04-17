package com.yusa.acgnbbs.config;

import com.yusa.acgnbbs.filter.JwtAuthenticationFilter;
import com.yusa.acgnbbs.security.CustomAuthenticationProvider;
import com.yusa.acgnbbs.security.CustomAuthenticationToken;
import com.yusa.acgnbbs.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//

@Configuration
//开启判断用户对某个控制层的方法是否具有访问权限的功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/post/**/").permitAll()
                .antMatchers("/comment/**/").authenticated()
                .antMatchers("/userCommentList/{userId}/{pageNum}").permitAll()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/user/loginByCaptcha").anonymous()
                .antMatchers("/user/logout").authenticated()
                .antMatchers("/user/userInfo/{id}").permitAll()
                .antMatchers("/user/userInfo/").authenticated()
                .antMatchers("/user/userInfoList/{currentPage}/{pageSize}").permitAll()
                .antMatchers("/user/banUser/{id}").permitAll()
                .antMatchers("/user/cancelBanUser/{id}").permitAll()
                .antMatchers("/notice/**/").permitAll()
                .antMatchers("/report/**/").permitAll()

                .antMatchers("/user/sendCaptcha/{phone}").permitAll()
                .antMatchers("/user/sendEmail/{toEmail}").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/userInfo").permitAll()

                .antMatchers("/favorite/**/").authenticated()
                .antMatchers("/like/**/").authenticated()
                .antMatchers("/follow/**/").permitAll()
//                .antMatchers("/follow/addFollow/").authenticated()
//                .antMatchers("/follow/cancelFollow/").authenticated()
                .antMatchers("/eduoss/fileoss/").permitAll()
                .antMatchers("/favorite/userFavoriteList/{userId}/{currentPage}/{pageSize}").permitAll()

                .antMatchers("/rank/**/").permitAll()

                .antMatchers("/sign/**/").permitAll()

                .antMatchers("/pageViews/**/").permitAll()

                .antMatchers("/swagger-ui.html").permitAll()

                .antMatchers("/statistics/**/").permitAll()

//                .antMatchers("/user/login").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().anonymous();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        决定一下保留
//        http.addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class);


    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);//自定义的
        authenticationManagerBuilder.authenticationProvider(authProvider());//原来默认的

        return authenticationManagerBuilder.build();
    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

}

