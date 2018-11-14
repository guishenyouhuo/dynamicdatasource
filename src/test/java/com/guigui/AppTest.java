package com.guigui;

import com.guigui.dynamic.service.IDynamicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@ContextConfiguration(
        locations = {"classpath:application.xml"}
)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest extends AbstractJUnit4SpringContextTests
{
    @Resource
    private IDynamicService dynamicServiceImpl;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testDynamicSource() {
        // 路由DSVps数据源
        dynamicServiceImpl.dynamicRouting("DSVps", "article", "myblog");

        // 路由DSLocal数据源
        dynamicServiceImpl.dynamicRouting("DSLocal", "khmessage", "weiyaqi");
    }
}
