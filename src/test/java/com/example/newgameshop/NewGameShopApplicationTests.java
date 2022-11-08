package com.example.newgameshop;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NewGameShopApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(FileUtil.getSuffix("a.png"));
    }

    @Test
    void findUserIdTest(){
        System.out.println();
    }

}
