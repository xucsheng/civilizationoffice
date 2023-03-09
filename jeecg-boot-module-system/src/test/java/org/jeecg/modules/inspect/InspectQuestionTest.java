package org.jeecg.modules.inspect;

import cn.hutool.core.util.RandomUtil;
import org.jeecg.JeecgSystemApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: yuyy
 * @Date: 2023/1/31 14:41
 * @Desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
@SuppressWarnings({"FieldCanBeLocal", "SpringJavaAutowiredMembersInspection"})
public class InspectQuestionTest {

    @Test
    public void testRandom() {
        System.out.println(RandomUtil.randomString(10));  //小写字母+数字
        System.out.println(RandomUtil.randomStringUpper(10)); //大写字母+数字
        System.out.println(RandomUtil.randomNumbers(10)); // 数字
        System.out.println(RandomUtil.randomStringWithoutStr(10,"0123456789")); // 不含有0123456789
        System.out.println(RandomUtil.randomString("abc",10)); // 只含有abc
        char c = RandomUtil.randomChar();
        System.out.println(c + "#" + String.valueOf(c).toUpperCase()); // 生成小写字母+数字中一个字符
        System.out.println(RandomUtil.randomNumber()); // 生成0123456789中一个字符
        System.out.println(RandomUtil.randomChar("-!?")); // 生成-!?中一个字符
    }
}
