package org.keniu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TechCheckApplication.class)
class TechCheckApplicationTests {

    @Autowired
    private TechCheckApplication techCheckApplication;

    @Test
    void contextLoads() {
        assertNotNull(techCheckApplication);
    }
}
