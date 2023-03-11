package com.yusa.acgnbbs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class AcgnbbsApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void testBcrypt(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        String encode2 = passwordEncoder.encode("jcy112");
        System.out.println(encode2);
        boolean matches = passwordEncoder.matches("jcy112", "$2a$10$5KWQD/B76oAaz0zszIo8euYUfhqlUpmdozMmnFvct6GY26RhwnwVe");
        System.out.println(matches);
    }
    @Test
    public void te(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(dateFormat.format(calendar.getTime()));
        Date date = new Date();
        System.out.println(date);
    }

}
