package com.yusa.acgnbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yusa.acgnbbs.domain.LoginUser;
import com.yusa.acgnbbs.domain.ResponseResult;
import com.yusa.acgnbbs.domain.entity.User;
import com.yusa.acgnbbs.mapper.UserMapper;
import com.yusa.acgnbbs.security.CustomAuthenticationToken;
import com.yusa.acgnbbs.service.LoginService;
import com.yusa.acgnbbs.utils.JwtUtils;
import com.yusa.acgnbbs.utils.RedisCache;
import com.yusa.acgnbbs.utils.RegexValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;
    @Autowired
    UserMapper userMapper;
    @Override
    public ResponseResult login(User user)  {
        //使用ProviderManager auth方法进行验证
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
//        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //自定义 auth方法进行验证

        CustomAuthenticationToken customAuthenticationToken =new CustomAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate=null;
        authenticate = authenticationManager.authenticate(customAuthenticationToken);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("密码错误");
        }
        //生成jwt给前端
        LoginUser loginUser =(LoginUser) (authenticate.getPrincipal());
        Integer id = loginUser.getUser().getId();
        String avatar = loginUser.getUser().getAvatar();
        String jwt = JwtUtils.createJWT(id.toString());
        Map map =new HashMap<String,String>();
        map.put("token",jwt);
        map.put("userId",id);
        map.put("avatar",avatar);
        System.out.println(loginUser);
        redisCache.setCacheObject("login:"+id,loginUser);
//        JwtUtils.
        //系统用户相关信息存放在redis
        return new  ResponseResult(200,"登录成功",map);
    }

    @Override
    public ResponseResult loginByCaptcha(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println(username);
        String code = redisCache.getCacheObject(username).toString();
        //如果验证码过期
        if(StringUtils.isEmpty(code)) {
            return new ResponseResult(200,"验证码过期",null);
        }
        //如果匹配上
        if(code.equals(password)){
            LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            boolean checkPhone = RegexValidateUtil.checkPhone(username);
            boolean checkEmail = RegexValidateUtil.checkEmail(username);
            System.out.println(RegexValidateUtil.checkEmail("1060180905@qq.com"));
            if(checkPhone) {
                lambdaQueryWrapper.eq(User::getPhone, username);
            }
            else if(checkEmail){
                lambdaQueryWrapper.eq(User::getEmail, username);
            }
            else {
                return new  ResponseResult(200,"登录失败",null);
            }
            User user1 = userMapper.selectOne(lambdaQueryWrapper);

            // TODO
            List<String> list =new ArrayList<>(Arrays.asList("aAdmin","cAdmin","gAdmin","nAdmin"));
            LoginUser loginUser= new LoginUser(user1,list,false);

            Integer id = loginUser.getUser().getId();
            String avatar = loginUser.getUser().getAvatar();
            //生成jwt给前端
            String jwt = JwtUtils.createJWT(id.toString());
            Map map =new HashMap<String,String>();
            map.put("token",jwt);
            map.put("userId",user1.getId());
            map.put("avatar",avatar);
            System.out.println(loginUser);
            redisCache.setCacheObject("login:"+id,loginUser);
//        JwtUtils.
            //系统用户相关信息存放在redis
            return new  ResponseResult(200,"登录成功",map);
        }
        return new  ResponseResult(200,"登录失败",null);
    }


    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // java.lang.String cannot be cast to com.yusa.acgnbbs.domain.LoginUser
        // TODO
//        LoginUser loginUser = (LoginUser) (authentication.getPrincipal());
//        Integer id = loginUser.getUser().getId();
//        redisCache.deleteObject("login:"+id);
        return new  ResponseResult(200,"退出成功");
    }
}
