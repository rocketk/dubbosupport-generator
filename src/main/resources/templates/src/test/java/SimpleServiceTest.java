package ${basePackageName}.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Generator.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring.xml"
})
public class SimpleServiceTest {
    @Autowired
    SimpleService simpleService;
    @Test
    public void test() {
        Assert.assertNotNull(simpleService);
        System.out.println(simpleService);
        System.out.println(simpleService.getUserInfo("testUser"));
    }
}
