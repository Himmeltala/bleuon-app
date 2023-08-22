package com.bleuon;

import com.bleuon.mapper.AuthMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BleuonServeApplicationTests {

    @Autowired
    private AuthMapper authMapper;

    @Test
    void testAuthMapper() {
        List<String> list = authMapper.queryAuthsByUserId(null, "himmelbleu");
        System.out.println(list);
    }

}
